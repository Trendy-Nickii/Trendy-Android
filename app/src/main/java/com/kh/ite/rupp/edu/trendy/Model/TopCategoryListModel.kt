package com.kh.ite.rupp.edu.trendy.Model


import com.google.gson.annotations.SerializedName

class TopCategoryListModel : ArrayList<TopCategoryListModel.TopCategoryListModelItem>(){
    data class TopCategoryListModelItem(
        @SerializedName("created_at")
        var createdAt: String? = "",
        @SerializedName("description")
        var description: Any? = Any(),
        @SerializedName("id")
        var id: Int? = 0,
        @SerializedName("image_url")
        var imageUrl: Any? = Any(),
        @SerializedName("name")
        var name: String? = "",
        @SerializedName("parent_id")
        var parentId: Any? = Any(),
        @SerializedName("updated_at")
        var updatedAt: String? = ""
    )
}