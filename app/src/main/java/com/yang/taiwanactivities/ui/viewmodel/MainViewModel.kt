package com.yang.taiwanactivities.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yang.taiwanactivities.data.api.HttpResult
import com.yang.taiwanactivities.data.model.GetActivityListResult
import com.yang.taiwanactivities.data.model.Info
import com.yang.taiwanactivities.data.repository.MainRepository
import com.yang.taiwanactivities.util.LogCat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(var mainRepository: MainRepository) : ViewModel() {

    private val _getActivityListLiveData = MutableLiveData<List<Info>>()
    val getActivityListLiveData: LiveData<List<Info>> get() = _getActivityListLiveData

    fun getActivityList() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = mainRepository.getActivityList()

            withContext(Dispatchers.Main) {
                when (result) {
                    is HttpResult.Success -> {
                        if (result.data.isSuccessful) {
                            // To get Object
                            _getActivityListLiveData.postValue(result.data.body()?.XMLHead?.Infos?.Info)
                        } else {
                            // 400 ~ 500, handle error
                        }
                    }
                    is HttpResult.Error -> {
                        LogCat.e(result.exception.toString())
                    }
                }
            }
        }
    }

    // selectInfo 作用於 MainFragment 和 DetailFragment 之間溝通橋樑
    private val mutableSelectedInfo = MutableLiveData<Info>()
    val selectedInfo: LiveData<Info> get() = mutableSelectedInfo

    fun selectedInfo(info: Info) {
        mutableSelectedInfo.value = info
    }
}