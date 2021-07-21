package com.yang.taiwanactivities.data.api

import com.yang.taiwanactivities.data.model.GetActivityListResult
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("XMLReleaseALL_public/activity_C_f.json")
    fun getActivityList(): Deferred<Response<GetActivityListResult>>
}