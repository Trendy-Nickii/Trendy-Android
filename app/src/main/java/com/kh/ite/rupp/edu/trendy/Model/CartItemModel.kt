package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName

data class CartItemModel(
    @SerializedName("cart")
    var cart: ArrayList<Cart>? = arrayListOf(),
    @SerializedName("user")
    var user: User? = User()
) {
    data class Cart(
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
    )

    data class User(
        @SerializedName("email")
        var email: Any? = Any(),
        @SerializedName("gender")
        var gender: Int? = 0,
        @SerializedName("phone")
        var phone: String? = "",
        @SerializedName("user_id")
        var userId: Int? = 0,
        @SerializedName("username")
        var username: String? = ""
    )
}