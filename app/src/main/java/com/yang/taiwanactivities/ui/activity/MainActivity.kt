package com.yang.taiwanactivities.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yang.taiwanactivities.R
import com.yang.taiwanactivities.data.factory.MainFactory
import com.yang.taiwanactivities.data.repository.MainRepository
import com.yang.taiwanactivities.ui.viewmodel.MainViewModel
import com.yang.taiwanactivities.util.LogCat

class MainActivity : AppCompatActivity() {

    private lateinit var mainFactory: MainFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainRepository: MainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainRepository = MainRepository()
        mainFactory = MainFactory(mainRepository)
        mainViewModel = ViewModelProvider(this, mainFactory).get(MainViewModel::class.java)

        initObserver()

        mainViewModel.getActivityList()
    }

    private fun initObserver() {
        mainViewModel.getActivityListLiveData.observe(this, {
            // to get data
            LogCat.e("Updatetime=" + it.XMLHead.Updatetime)
        })
    }
}