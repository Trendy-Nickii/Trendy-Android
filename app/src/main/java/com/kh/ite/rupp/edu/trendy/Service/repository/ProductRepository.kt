package com.kh.ite.rupp.edu.trendy.Service.repository

import android.util.Log
import com.kh.ite.rupp.edu.trendy.Model.ProductListModel
import com.kh.ite.rupp.edu.trendy.Model.SingleProductModel
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.SafeApiRequest
import retrofit2.Response

class ProductRepository (
    private val api: MyApi
): SafeApiRequest(){

    suspend fun getProductList(): ProductListModel{
        return apiRequest { api.getAllProduct() }
    }

    suspend fun getOneProduct(id: String): SingleProductModel{
        Log.d("PRODUCT_DETAIL", "data = ${api.getOneProduct(id)}")
        return apiRequest { api.getOneProduct(id) }
    }

    suspend fun getProductByCateId(id:String): ProductListModel{
        return apiRequest { api.getProductByCateId(id) }
    }

}