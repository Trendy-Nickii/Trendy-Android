package com.kh.ite.rupp.edu.trendy.Service.repository

import com.kh.ite.rupp.edu.trendy.Application.MySharePreferences
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.SafeApiRequest

class UserRepository(
    api: MyApi,
    private val sharedPreferences: MySharePreferences
): SafeApiRequest() {
    suspend fun userLogin(phone: String, password: String){

    }
    suspend fun userSignup(username: String, phone: String, password: String, gender: Int){

    }
}