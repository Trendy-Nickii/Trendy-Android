package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName

data class DeleteCartItemModel(
    @SerializedName("message")
    var message: String? = ""
)