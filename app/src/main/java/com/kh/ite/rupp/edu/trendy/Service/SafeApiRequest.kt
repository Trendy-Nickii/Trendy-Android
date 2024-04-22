package com.kh.ite.rupp.edu.trendy.Service

import com.google.gson.GsonBuilder
import com.kh.ite.rupp.edu.trendy.Model.ErrorModel
import com.kh.ite.rupp.edu.trendy.Model.ResponseErrorModel
import com.kh.ite.rupp.edu.trendy.Util.ApiException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>) : T {
        val response = call.invoke()
        if (response.isSuccessful){
            return response.body()!!
        }else{
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    val errorResponse = JSONObject(it).toString()
                    val gsonBuilder = GsonBuilder()
                    val gson = gsonBuilder.create()
                    val errorData = gson.fromJson(errorResponse, ResponseErrorModel::class.java) as ResponseErrorModel
                    message.append(errorData.messages!!.err)
                }catch (e: Exception){}
                message.append("\n")
            }

            message.append("Error code: ${response.code()}")

            throw ApiException(message.toString())
        }
    }
}