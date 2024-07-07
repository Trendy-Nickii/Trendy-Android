package com.kh.ite.rupp.edu.trendy.Service.repository

import com.kh.ite.rupp.edu.trendy.Application.MySharePreferences
import com.kh.ite.rupp.edu.trendy.Model.UserInfoModel
import com.kh.ite.rupp.edu.trendy.Model.UserLoginBody
import com.kh.ite.rupp.edu.trendy.Model.UserLoginSuccessResponse
import com.kh.ite.rupp.edu.trendy.Model.UserSignUpBody
import com.kh.ite.rupp.edu.trendy.Model.UserSignUpModel
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.SafeApiRequest

class UserRepository(
    private val api: MyApi,
    private val sharedPreferences: MySharePreferences
): SafeApiRequest() {
    suspend fun userLogin(phone: String, password: String): UserLoginSuccessResponse{
        return apiRequest { api.login(UserLoginBody(phone, password)) }
    }
    suspend fun userSignup(userInfo: UserSignUpBody): UserSignUpModel{
        return apiRequest { api.signUp(userInfo)}
    }

    suspend fun getUserInfo(): UserInfoModel{
        return apiRequest { api.getUserInfo() }
    }
}