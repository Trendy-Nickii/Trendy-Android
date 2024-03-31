package com.kh.ite.rupp.edu.trendy.Service.intercepter

import android.content.Context
import com.kh.ite.rupp.edu.trendy.Application.MySharePreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(context: Context): Interceptor {

    private var token: String? = null
    private val sharedPreferences = MySharePreferences(context)
    override fun intercept(chain: Interceptor.Chain): Response {
        token = sharedPreferences.getToken()
        val originalRequest = chain.request()
        val modifiedRequest: Request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(modifiedRequest)
    }
}