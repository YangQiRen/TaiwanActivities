package com.yang.taiwanactivities.ui.fragment

import android.bluetooth.le.AdvertisingSetParameters
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yang.taiwanactivities.R
import com.yang.taiwanactivities.data.factory.MainFactory
import com.yang.taiwanactivities.data.repository.MainRepository
import com.yang.taiwanactivities.databinding.FragmentMainBinding
import com.yang.taiwanactivities.ui.viewmodel.MainViewModel
import com.yang.taiwanactivities.util.LogCat


class MainFragment : Fragment() {

    private lateinit var mainFactory: MainFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainRepository: MainRepository
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainRepository = MainRepository()
        mainFactory = MainFactory(mainRepository)
        mainViewModel = ViewModelProvider(this, mainFactory).get(MainViewModel::class.java)
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
            val adapter = ActivityAdapter()
            binding.rvActivity.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            binding.rvActivity.layoutManager = LinearLayoutManager(requireContext())
            binding.rvActivity.adapter = adapter
            adapter.updateList(infoList = it)

        })
    }

}