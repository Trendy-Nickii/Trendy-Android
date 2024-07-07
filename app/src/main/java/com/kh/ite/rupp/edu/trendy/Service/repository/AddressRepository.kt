package com.kh.ite.rupp.edu.trendy.Service.repository

import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.AddressListModel
import com.kh.ite.rupp.edu.trendy.Model.AddressSingleModel
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

    suspend fun getOneAddress(id: String): AddressSingleModel{
        return apiRequest { api.getOneAddress(id) }
    }

    suspend fun updateAddress(id: String, body: CreateAddressBody): AddToCartResponseModel{
        return apiRequest { api.updateAddress(id,body) }
    }

    suspend fun deleteAddress(id: String): AddToCartResponseModel{
        return apiRequest { api.deleteAddress(id) }
    }

}