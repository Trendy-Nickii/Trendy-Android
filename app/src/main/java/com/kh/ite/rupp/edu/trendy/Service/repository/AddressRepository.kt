package com.kh.ite.rupp.edu.trendy.Service.repository

import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.AddressListModel
import com.kh.ite.rupp.edu.trendy.Model.CreateAddressBody
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.SafeApiRequest

class AddressRepository(
    private val api: MyApi
):SafeApiRequest() {

    suspend fun createAddress(body:CreateAddressBody): AddToCartResponseModel{
        return apiRequest { api.createAddress(body) }
    }

    suspend fun getAddress():AddressListModel{
        return apiRequest { api.getListAddress() }
    }
}