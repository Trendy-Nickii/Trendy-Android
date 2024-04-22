package com.kh.ite.rupp.edu.trendy.Service.repository

import com.kh.ite.rupp.edu.trendy.Model.ListProductWithDetailByCategory
import com.kh.ite.rupp.edu.trendy.Model.SubCategoryModel
import com.kh.ite.rupp.edu.trendy.Model.TopCategoryListModel
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.SafeApiRequest

class ShopRepository(
    private val api: MyApi
):SafeApiRequest() {

    suspend fun getTopCategory(): TopCategoryListModel {
        return apiRequest { api.getTopCategory() }
    }

    suspend fun getSubCategory(id: String): SubCategoryModel {
        return apiRequest { api.getSubCategory(id) }
    }

    suspend fun getProductByCategory(id: String): ListProductWithDetailByCategory {
        return apiRequest { api.getProductByCategory(id) }
    }


}