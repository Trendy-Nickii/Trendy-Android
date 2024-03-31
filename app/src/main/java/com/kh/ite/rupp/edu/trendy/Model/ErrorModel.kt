package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("messages")
    var messages: Messages?
) {
    data class Messages(
        @SerializedName("err")
        var err: String?
    )
}