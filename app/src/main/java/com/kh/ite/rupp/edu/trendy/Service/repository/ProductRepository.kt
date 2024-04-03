package com.kh.ite.rupp.edu.trendy.Service.repository

import com.kh.ite.rupp.edu.trendy.Model.ProductListModel
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.SafeApiRequest
import retrofit2.Response

class ProductRepository (
    private val api: MyApi
): SafeApiRequest(){

    suspend fun getProductList(): ProductListModel{
        return apiRequest { api.getAllProduct() }
    }

}