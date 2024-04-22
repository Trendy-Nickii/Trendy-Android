package com.kh.ite.rupp.edu.trendy.Model

data class UserSignUpBody(
    private var username: String,
    private var phone: String,
    private var gender:Int,
    private var password: String
)
