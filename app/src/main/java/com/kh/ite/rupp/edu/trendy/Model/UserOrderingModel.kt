package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName

class UserOrderingModel : ArrayList<UserOrderingModel.UserOrderingItem>(){
    data class UserOrderingItem(
        @SerializedName("address")
        var address: Address? = Address(),
        @SerializedName("delivery")
        var delivery: String? = "",
        @SerializedName("items")
        var items: List<Item?>? = listOf(),
        @SerializedName("onDate")
        var onDate: String? = "",
        @SerializedName("orderId")
        var orderId: Int? = 0,
        @SerializedName("status")
        var status: String? = "",
        @SerializedName("totalAmount")
        var totalAmount: Double? = 0.0,
        @SerializedName("totalAmountAfterDiscount")
        var totalAmountAfterDiscount: Double? = 0.0
    ) {
        data class Address(
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
    
        data class Item(
            @SerializedName("amount")
            var amount: Int? = 0,
            @SerializedName("color")
            var color: String? = "",
            @SerializedName("color_code")
            var colorCode: String? = "",
            @SerializedName("images")
            var images: List<Image?>? = listOf(),
            @SerializedName("item_quantity")
            var itemQuantity: Int? = 0,
            @SerializedName("order_id")
            var orderId: Int? = 0,
            @SerializedName("pending")
            var pending: Int? = 0,
            @SerializedName("product_discount")
            var productDiscount: Int? = 0,
            @SerializedName("product_id")
            var productId: Int? = 0,
            @SerializedName("product_item_id")
            var productItemId: Int? = 0,
            @SerializedName("product_name")
            var productName: String? = "",
            @SerializedName("product_price")
            var productPrice: Double? = 0.0,
            @SerializedName("size")
            var size: String? = "",
            @SerializedName("updated_at")
            var updatedAt: String? = ""
        ) {
            data class Image(
                @SerializedName("color")
                var color: String? = "",
                @SerializedName("color_code")
                var colorCode: String? = "",
                @SerializedName("id")
                var id: Int? = 0,
                @SerializedName("image_onColor")
                var imageOnColor: String? = "",
                @SerializedName("image_url")
                var imageUrl: String? = "",
                @SerializedName("product_id")
                var productId: Int? = 0,
                @SerializedName("public_id")
                var publicId: String? = ""
            )
        }
    }
}