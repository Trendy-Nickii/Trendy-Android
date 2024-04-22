package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName

data class UserSignUpModel(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("messages")
    var messages: Messages?,
    @SerializedName("user")
    var user: User?
) {
    data class Messages(
        @SerializedName("err")
        var err: String?
    )

    data class User(
        @SerializedName("affectedRows")
        var affectedRows: Int?,
        @SerializedName("changedRows")
        var changedRows: Int?,
        @SerializedName("fieldCount")
        var fieldCount: Int?,
        @SerializedName("insertId")
        var insertId: Int?,
        @SerializedName("message")
        var message: String?,
        @SerializedName("protocol41")
        var protocol41: Boolean?,
        @SerializedName("serverStatus")
        var serverStatus: Int?,
        @SerializedName("warningCount")
        var warningCount: Int?
    )
}