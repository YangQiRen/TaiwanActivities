package com.yang.taiwanactivities.data.repository

import com.yang.taiwanactivities.data.api.ApiService
import com.yang.taiwanactivities.data.api.HttpResult
import com.yang.taiwanactivities.data.api.RetrofitFactory
import com.yang.taiwanactivities.data.model.GetActivityListResult
import retrofit2.Response

class MainRepository {

    private val apiService = RetrofitFactory.create(ApiService::class.java)

    suspend fun getActivityList(): HttpResult<GetActivityListResult> {
        return try {
            val response = apiService.getActivityList().await()
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