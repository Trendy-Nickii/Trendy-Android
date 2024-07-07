package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName

data class UserInfoModel(
    @SerializedName("error")
    var error: Boolean? = false,
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("messages")
    var messages: Messages? = Messages(),
    @SerializedName("user")
    var user: User? = User()
) {
    data class Messages(
        @SerializedName("err")
        var err: String? = ""
    )

    data class User(
        @SerializedName("create_at")
        var createAt: String? = "",
        @SerializedName("email")
        var email: Any? = Any(),
        @SerializedName("gender")
        var gender: Int? = 0,
        @SerializedName("password")
        var password: String? = "",
        @SerializedName("phone")
        var phone: String? = "",
        @SerializedName("role")
        var role: String? = "",
        @SerializedName("update_at")
        var updateAt: String? = "",
        @SerializedName("user_id")
        var userId: Int? = 0,
        @SerializedName("username")
        var username: String? = ""
    )
}