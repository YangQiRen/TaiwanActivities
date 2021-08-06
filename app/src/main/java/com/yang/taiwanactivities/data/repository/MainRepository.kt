package com.yang.taiwanactivities.data.repository

import com.yang.taiwanactivities.data.api.ApiService
import com.yang.taiwanactivities.data.api.HttpResult
import com.yang.taiwanactivities.data.model.GetActivityListResult
import com.yang.taiwanactivities.data.repository.impl.MainRepositoryImpl
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: ApiService
) : MainRepositoryImpl {

    override suspend fun getActivityList(): HttpResult<GetActivityListResult> {
        return try {
            val response = api.getActivityList().await()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                HttpResult.Success(result)
            } else {
                HttpResult.Error(response.message())
            }
        } catch (e: Throwable) {
            HttpResult.Error(e.message ?: "An error occured")
        }
    }
}