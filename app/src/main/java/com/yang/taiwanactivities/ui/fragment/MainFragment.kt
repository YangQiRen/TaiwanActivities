package com.yang.taiwanactivities.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.yang.taiwanactivities.R
import com.yang.taiwanactivities.data.factory.MainFactory
import com.yang.taiwanactivities.data.repository.MainRepository
import com.yang.taiwanactivities.ui.viewmodel.MainViewModel
import com.yang.taiwanactivities.util.LogCat


class MainFragment : Fragment() {

    private lateinit var mainFactory: MainFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainRepository: MainRepository

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()

        mainViewModel.getActivityList()
    }

    private fun initObserver() {
        mainViewModel.getActivityListLiveData.observe(viewLifecycleOwner, {
            // to get data
//            dialog.visibility = View.GONE
//            tvDelayResult.text = it.toString()
            LogCat.e("Updatetime=" + it.XMLHead.Updatetime)
        })
    }

}