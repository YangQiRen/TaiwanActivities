package com.yang.taiwanactivities.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yang.taiwanactivities.R
import com.yang.taiwanactivities.data.factory.MainFactory
import com.yang.taiwanactivities.data.model.Info
import com.yang.taiwanactivities.data.repository.MainRepository
import com.yang.taiwanactivities.databinding.FragmentMainBinding
import com.yang.taiwanactivities.ui.viewmodel.MainViewModel


class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mainFactory: MainFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainRepository: MainRepository
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!
    private var currentPosition: Int = 0

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        _binding = FragmentMainBinding.bind(view)
        mainRepository = MainRepository()
        mainFactory = MainFactory(mainRepository)
        mainViewModel =
            ViewModelProvider(requireActivity(), mainFactory).get(MainViewModel::class.java)
        initObserver()
        mainViewModel.getActivityList()

    }

    private fun initObserver() {
        mainViewModel.getActivityListLiveData.observe(viewLifecycleOwner, {
            // 關閉loading骨骼動畫
            binding.shimmerFrameLayout.stopShimmer()
            binding.shimmerFrameLayout.isVisible = false
            // setup recyclerview adapter
            val adapter = ActivityAdapter(infoClickListener = { imageView, info, position ->
                infoClick(
                    imageView,
                    info,
                    position
                )
            })
            if (binding.rvActivity.itemDecorationCount == 0) {
                binding.rvActivity.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
            }
            binding.rvActivity.layoutManager = LinearLayoutManager(requireContext())
            binding.rvActivity.adapter = adapter
            adapter.updateList(infoList = it)
            view?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        })
        mainViewModel.selectedPosition.observe(viewLifecycleOwner, {
            currentPosition = it
        })
    }

    private fun infoClick(imageView: ImageView, info: Info, position: Int) {
        mainViewModel.selectInfo(position, info)

        val extras = FragmentNavigatorExtras(
            imageView to "image_big"
        )
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_tab_activity_to_activityDetailFragment, null, null, extras)
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmer()
        super.onPause()
    }
}