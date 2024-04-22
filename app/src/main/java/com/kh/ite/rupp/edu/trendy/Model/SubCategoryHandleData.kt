package com.kh.ite.rupp.edu.trendy.Model

import java.io.Serializable

class SubCategoryHandleData(
    var categoryId: String? = "",
    var categoryNameHandle: String? = "",
    var subCategoryId: String? = "",
    var subCategoryName: String? = "",
    var isViewByCategory: Boolean? = false,
    var isViewAllHandle: Boolean? = false
):Serializable {
}