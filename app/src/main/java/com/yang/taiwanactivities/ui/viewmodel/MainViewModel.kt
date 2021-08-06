package com.yang.taiwanactivities.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yang.taiwanactivities.data.api.HttpResult
import com.yang.taiwanactivities.data.model.Info
import com.yang.taiwanactivities.data.repository.MainRepository
import com.yang.taiwanactivities.util.LogCat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var mainRepository: MainRepository
) : ViewModel() {

    private val _getActivityListLiveData = MutableLiveData<List<Info>>()
    val getActivityListLiveData: LiveData<List<Info>> get() = _getActivityListLiveData

    fun getActivityList() {
        CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main) {
                when (val result = mainRepository.getActivityList()) {
                    is HttpResult.Success -> {
                        _getActivityListLiveData.postValue(result.data.XMLHead.Infos.Info)
                    }
                    is HttpResult.Error -> {
                        LogCat.e(result.message)
                    }
                }
            }
        }
    }

    // selectInfo 作用於 MainFragment 和 DetailFragment 之間溝通橋樑
    var selectedPosition: MutableLiveData<Int> = MutableLiveData()
    var selectInfoEvent: MutableLiveData<Info> = MutableLiveData()

    fun selectInfo(position: Int, info: Info) {
        selectedPosition.value = position
        selectInfoEvent.value = info
    }
}