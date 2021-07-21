package com.yang.taiwanactivities.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yang.taiwanactivities.data.api.HttpResult
import com.yang.taiwanactivities.data.model.GetActivityListResult
import com.yang.taiwanactivities.data.repository.MainRepository
import com.yang.taiwanactivities.util.LogCat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(var mainRepository: MainRepository) : ViewModel() {

    private val _getActivityListLiveData = MutableLiveData<GetActivityListResult>()
    val getActivityListLiveData: LiveData<GetActivityListResult>
        get() = _getActivityListLiveData

    fun getActivityList() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = mainRepository.getActivityList()

            withContext(Dispatchers.Main) {
                when (result) {
                    is HttpResult.Success -> {
                        if (result.data.isSuccessful) {
                            // To get Object
                            val body = result.data.body()
                            // Update layout here
                            _getActivityListLiveData.postValue(body)
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
}