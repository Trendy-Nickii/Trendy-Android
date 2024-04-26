package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName

data class AddToCartResponseModel(
    @SerializedName("message")
    var message: String? = ""
)