package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CartCheckoutSummaryModel(
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("orderDetails")
    var orderDetails: OrderDetails? = OrderDetails()
):Serializable {
    data class OrderDetails(
        @SerializedName("items")
        var items: ArrayList<Item>? = arrayListOf(),
        @SerializedName("totalAmount")
        var totalAmount: Double? = 0.0
    ):Serializable {
        data class Item(
            @SerializedName("cart_id")
            var cartId: Int? = 0,
            @SerializedName("color")
            var color: String? = "",
            @SerializedName("color_code")
            var colorCode: String? = "",
            @SerializedName("images")
            var images: List<String?>? = listOf(),
            @SerializedName("product_discount")
            var productDiscount: Double? = 0.0,
            @SerializedName("product_id")
            var productId: Int? = 0,
            @SerializedName("product_name")
            var productName: String? = "",
            @SerializedName("product_price")
            var productPrice: Double? = 0.0,
            @SerializedName("quantity")
            var quantity: Int? = 0,
            @SerializedName("size")
            var size: String? = ""
        ):Serializable
    }
}