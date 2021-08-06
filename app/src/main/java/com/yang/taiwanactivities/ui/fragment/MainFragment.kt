package com.yang.taiwanactivities.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yang.taiwanactivities.R
import com.yang.taiwanactivities.data.model.Info
import com.yang.taiwanactivities.databinding.FragmentMainBinding
import com.yang.taiwanactivities.ui.viewmodel.MainViewModel


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModels()
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
        initObserver()

    }

    private fun initObserver() {
        viewModel.getActivityListLiveData.observe(viewLifecycleOwner, {
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
        viewModel.selectedPosition.observe(viewLifecycleOwner, {
            currentPosition = it
        })
    }

    private fun infoClick(imageView: ImageView, info: Info, position: Int) {
        viewModel.selectInfo(position, info)

        val extras = FragmentNavigatorExtras(
            imageView to "image_big"
        )
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_tab_activity_to_activityDetailFragment, null, null, extras)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getActivityList()
        binding.shimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmer()
        super.onPause()
    }
}