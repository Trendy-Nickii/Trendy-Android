package com.kh.ite.rupp.edu.trendy.Service.repository

import com.kh.ite.rupp.edu.trendy.Model.AddToCartBody
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.CartItemModel
import com.kh.ite.rupp.edu.trendy.Model.CartUpdateModel
import com.kh.ite.rupp.edu.trendy.Model.SingleProductModel
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.SafeApiRequest

class CartRepository(
    private val api: MyApi
): SafeApiRequest() {

    suspend fun addToCart(body: AddToCartBody): AddToCartResponseModel{
        return apiRequest { api.addToCart(body) }
    }

    suspend fun getCartItem(): CartItemModel{
        return apiRequest { api.getCartItem() }
    }

    suspend fun deleteCartItem(cartId: String): AddToCartResponseModel{
        return apiRequest { api.deleteCartItem(cartId) }
    }

    suspend fun getOneProduct(id: String): SingleProductModel {
//        Log.d("PRODUCT_DETAIL", "data = ${api.getOneProduct(id)}")
        return apiRequest { api.getOneProduct(id) }
    }

    suspend fun updateCart(id:String, body: CartUpdateModel): AddToCartResponseModel{
        return apiRequest { api.updateCart(id, body) }
    }

}