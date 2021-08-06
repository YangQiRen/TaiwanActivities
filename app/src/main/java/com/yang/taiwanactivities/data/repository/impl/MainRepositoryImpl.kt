package com.yang.taiwanactivities.data.repository.impl

import com.yang.taiwanactivities.data.api.HttpResult
import com.yang.taiwanactivities.data.model.GetActivityListResult

interface MainRepositoryImpl {

    suspend fun getActivityList(): HttpResult<GetActivityListResult>
}