package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName

data class AddressListModel(
    @SerializedName("address")
    var address: ArrayList<Addres>? = arrayListOf()
) {
    data class Addres(
        @SerializedName("address_line")
        var addressLine: String? = "",
        @SerializedName("address_name")
        var addressName: String? = "",
        @SerializedName("created_at")
        var createdAt: String? = "",
        @SerializedName("id")
        var id: Int? = 0,
        @SerializedName("khan")
        var khan: String? = "",
        @SerializedName("latitude")
        var latitude: String? = "",
        @SerializedName("longitude")
        var longitude: String? = "",
        @SerializedName("recipient_name")
        var recipientName: String? = "",
        @SerializedName("recipient_phone")
        var recipientPhone: String? = "",
        @SerializedName("updated_at")
        var updatedAt: String? = "",
        @SerializedName("user_id")
        var userId: Int? = 0
    )
}