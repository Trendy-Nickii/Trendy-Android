package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName

data class UserLoginSuccessResponse(
    @SerializedName("access_token")
    var accessToken: String? = "",
    @SerializedName("role")
    var role: String? = "",
    @SerializedName("token")
    var token: String? = "",
    @SerializedName("user")
    var user: User? = User()
) {
    data class User(
        @SerializedName("create_at")
        var createAt: String? = "",
        @SerializedName("email")
        var email: Any? = Any(),
        @SerializedName("gender")
        var gender: Int? = 0,
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