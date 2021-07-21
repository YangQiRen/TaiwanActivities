package com.yang.taiwanactivities.data.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yang.taiwanactivities.util.AppConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private val interceptor: Interceptor
    private val retrofit: Retrofit

    init {
        //通用攔截
        interceptor = Interceptor {chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Content_Type", "application/json")
                    .addHeader("charset", "UTF-8")
                    .build()
            )
        }

        // TODO serverAddress要改成判斷BuildConfig.DEBUG
        val serverAddress = when (AppConfig.isDebug) {
            true -> AppConfig.SERVER_ADDRESS_DEBUG
            false -> AppConfig.SERVER_ADDRESS
        }

        //Retrofit實例化
        retrofit = Retrofit.Builder()
            .baseUrl(serverAddress)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(initClient())
            .build()
    }

    /*
        OKHttp創建
     */
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(initLogInterceptor())
            .addInterceptor(interceptor)
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .build()
    }

    /*
        日誌攔截器
     */
    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (AppConfig.isDebug) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return interceptor
    }

    /*
        具體服務實例化
     */
    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

//    private val headersInterceptor = Interceptor { chain ->
//        chain.proceed(
//            chain.request().newBuilder()
//                .addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/json")
//                .build()
//        )
//    }
}