package com.kh.ite.rupp.edu.trendy.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kh.ite.rupp.edu.trendy.Model.ProductListModel
import com.kh.ite.rupp.edu.trendy.Model.SingleProductModel
import com.kh.ite.rupp.edu.trendy.Service.repository.ProductRepository
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnRequestResponse
import com.kh.ite.rupp.edu.trendy.Util.Coroutine
import com.kh.ite.rupp.edu.trendy.Util.NoInternetException

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    id: String
):ViewModel() {

    private val _productList = MutableLiveData<ProductListModel>()
    val productList: LiveData<ProductListModel>
        get() = _productList

    private val _productOne = MutableLiveData<SingleProductModel>()
    val productOne: LiveData<SingleProductModel>
        get() = _productOne
    private val _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    var listener : OnRequestResponse? = null

    init {
        _isLoading.value = true
        getOneProduct(id)
    }

     private fun getOneProduct(id: String){
        Coroutine.ioThanMain(
            {
                try {
                    productRepository.getOneProduct(id)

                }catch (e: NoInternetException){
                    null
                }
            },
            {
               if (it != null ){
                   _productOne.value = it
                   _isLoading.value = false
                   Log.d("PRODUCT_DETAIL", "data in nun null = $it")
               }else{
                   listener?.onFailed("Error to load Data")
                   Log.d("PRODUCT_DETAIL", "null data = null")
               }
            }
        )
    }

    fun getProductById(id: String){
        Coroutine.ioThanMain(
            {
                try {
                    productRepository.getProductByCateId(id)
                }catch (e:NoInternetException){
                    null
                }
            },
            {
                if (it != null) {
                    _productList.value = it
                    _isLoading.value = false
                    Log.d("PRODUCT_DETAIL", "product list in vm = $it")
                } else {
                    listener?.onFailed("Fail to load data")
                }
            }
        )
    }

}