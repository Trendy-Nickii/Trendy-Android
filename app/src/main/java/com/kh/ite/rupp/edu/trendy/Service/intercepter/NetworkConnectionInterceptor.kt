package com.kh.ite.rupp.edu.trendy.Service.intercepter

import com.kh.ite.rupp.edu.trendy.Application.TrendyApplication
import com.kh.ite.rupp.edu.trendy.Util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!TrendyApplication.isInternetConnected)
            throw NoInternetException("Make sure you have an active data connection")

        return chain.proceed(chain.request())
    }
}