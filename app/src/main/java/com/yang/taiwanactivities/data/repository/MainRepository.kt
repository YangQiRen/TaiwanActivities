package com.yang.taiwanactivities.data.repository

import com.yang.taiwanactivities.data.api.ApiService
import com.yang.taiwanactivities.data.api.HttpResult
import com.yang.taiwanactivities.data.api.RetrofitFactory
import com.yang.taiwanactivities.data.model.GetActivityListResult
import retrofit2.Response

class MainRepository {

    private val apiService = RetrofitFactory.create(ApiService::class.java)

    suspend fun getActivityList(): HttpResult<Response<GetActivityListResult>> {
        return try {
            val result = apiService.getActivityList().await()
            HttpResult.Success(result)
        } catch (e: Throwable) {
            HttpResult.Error(e)
        }
    }
}