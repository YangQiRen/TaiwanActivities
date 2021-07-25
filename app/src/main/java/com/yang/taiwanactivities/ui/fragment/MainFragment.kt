package com.yang.taiwanactivities.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yang.taiwanactivities.R
import com.yang.taiwanactivities.data.factory.MainFactory
import com.yang.taiwanactivities.data.model.Info
import com.yang.taiwanactivities.data.repository.MainRepository
import com.yang.taiwanactivities.databinding.FragmentMainBinding
import com.yang.taiwanactivities.ui.viewmodel.MainViewModel


class MainFragment : Fragment() {

    private lateinit var mainFactory: MainFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainRepository: MainRepository
    private lateinit var binding: FragmentMainBinding

    val listener = object : ActivityAdapter.ActivityViewHolderListener {
        override fun onActivityClicked(info: Info) {
            mainViewModel.selectedInfo(info)
            Navigation.findNavController(binding.root).navigate(R.id.action_tab_activity_to_activityDetailFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainRepository = MainRepository()
        mainFactory = MainFactory(mainRepository)
        mainViewModel =
            ViewModelProvider(requireActivity(), mainFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        mainViewModel.getActivityList()
    }

    private fun initObserver() {
        mainViewModel.getActivityListLiveData.observe(viewLifecycleOwner, {
            // to get data
            val adapter = ActivityAdapter(listener)
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

        })
    }

}