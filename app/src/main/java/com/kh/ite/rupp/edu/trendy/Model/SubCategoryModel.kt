package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName

class SubCategoryModel : ArrayList<SubCategoryModel.SubCategoryModelItem>(){
    data class SubCategoryModelItem(
        @SerializedName("created_at")
        var createdAt: String? = "",
        @SerializedName("description")
        var description: Any? = Any(),
        @SerializedName("id")
        var id: Int? = 0,
        @SerializedName("image_url")
        var imageUrl: String? = "",
        @SerializedName("name")
        var name: String? = "",
        @SerializedName("parent_id")
        var parentId: Int? = 0,
        @SerializedName("updated_at")
        var updatedAt: String? = ""
    )
}