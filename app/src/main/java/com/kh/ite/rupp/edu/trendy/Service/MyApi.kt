package com.kh.ite.rupp.edu.trendy.Service

import android.content.Context
import com.kh.ite.rupp.edu.trendy.Model.ProductListModel
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.intercepter.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MyApi {

    @GET("productsV2")
    suspend fun getAllProduct(): Response<ProductListModel>

    companion object{
        operator fun invoke(
            context: Context,
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi{
            val tokenInterceptor = TokenInterceptor(context)
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(tokenInterceptor)
                .build()
           return Retrofit.Builder()
               .baseUrl("http://10.0.2.2:5001/api/")
               .client(okHttpClient)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
               .create(MyApi::class.java)
        }
    }
}