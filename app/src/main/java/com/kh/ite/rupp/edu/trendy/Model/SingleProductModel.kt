package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName

data class SingleProductModel(
    @SerializedName("amount")
    var amount: Int? = 0,
    @SerializedName("available_color")
    var availableColor: List<AvailableColor?>? = listOf(),
    @SerializedName("available_size")
    var availableSize: ArrayList<AvailableSize>? = arrayListOf(),
    @SerializedName("category_id")
    var categoryId: Int? = 0,
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("image")
    var image: List<Image?>? = listOf(),
    @SerializedName("items")
    var items: List<Item>? = arrayListOf(),
    @SerializedName("product_description")
    var productDescription: String? = "",
    @SerializedName("product_discount")
    var productDiscount: Double? = 0.0,
    @SerializedName("product_name")
    var productName: String? = "",
    @SerializedName("product_price")
    var productPrice: Double? = 0.0
) {
    data class AvailableColor(
        @SerializedName("amount")
        var amount: Int? = 0,
        @SerializedName("color")
        var color: String? = "",
        @SerializedName("color_code")
        var colorCode: String? = ""
    )

    data class AvailableSize(
        @SerializedName("isActive")
        var isActive: Boolean? = false,
        @SerializedName("amount")
        var amount: Int? = 0,
        @SerializedName("color_onSize")
        var colorOnSize: List<ColorOnSize?>? = listOf(),
        @SerializedName("size")
        var size: String? = ""
    ) {
        data class ColorOnSize(
            @SerializedName("amount")
            var amount: Int? = 0,
            @SerializedName("color")
            var color: String? = "",
            @SerializedName("color_code")
            var colorCode: String? = ""
        )
    }

    data class Image(
        @SerializedName("color")
        var color: String? = "",
        @SerializedName("color_code")
        var colorCode: String? = "",
        @SerializedName("image_id")
        var imageId: Int? = 0,
        @SerializedName("image_onColor")
        var imageOnColor: String? = "",
        @SerializedName("image_url")
        var imageUrl: String? = "",
        @SerializedName("public_id")
        var publicId: String? = ""
    )

    data class Item(
        @SerializedName("isActive")
        var isActive: Boolean? = false,
        @SerializedName("amount")
        var amount: Int? = 0,
        @SerializedName("color")
        var color: String? = "",
        @SerializedName("color_code")
        var colorCode: String? = "",
        @SerializedName("item_id")
        var itemId: Int? = 0,
        @SerializedName("size")
        var size: String? = ""
    )
}