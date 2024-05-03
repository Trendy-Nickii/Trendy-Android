package com.kh.ite.rupp.edu.trendy.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.AddressListModel
import com.kh.ite.rupp.edu.trendy.Model.CreateAddressBody
import com.kh.ite.rupp.edu.trendy.Service.repository.AddressRepository
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.Util.ApiException
import com.kh.ite.rupp.edu.trendy.Util.Coroutine
import com.kh.ite.rupp.edu.trendy.Util.NoInternetException

class AddressViewModel(
    private val addressRepository: AddressRepository
):ViewModel() {

    private val _addressList = MutableLiveData<AddressListModel>()
    val addressList : LiveData<AddressListModel>
        get() = _addressList

    var listener : OnBackResponse<AddToCartResponseModel>? = null

    fun createAddress(body: CreateAddressBody){
        Coroutine.ioThanMain(
            {
                try {
                    addressRepository.createAddress(body)
                }catch (e: NoInternetException){
                    listener?.fail(e.message!!)
                    null
                }catch (e: ApiException){
                    listener?.fail(e.message!!)
                    null
                }
            },
            {
                if (it != null){
                    listener?.success(it)
                }
            }
        )
    }

    fun getAddressList(){
        Coroutine.ioThanMain(
            {
                try {
                    addressRepository.getAddress()
                }catch (e: NoInternetException){
                    listener?.fail(e.message!!)
                    null
                }catch (e: ApiException){
                    listener?.fail(e.message!!)
                    null
                }
            },
            {
                if (it != null){
                    _addressList.value = it
                }
            }
        )
    }

}