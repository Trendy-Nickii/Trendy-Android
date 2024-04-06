package com.kh.ite.rupp.edu.trendy.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kh.ite.rupp.edu.trendy.Model.ProductListModel
import com.kh.ite.rupp.edu.trendy.Service.repository.ProductRepository
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnRequestResponse
import com.kh.ite.rupp.edu.trendy.Util.Coroutine
import com.kh.ite.rupp.edu.trendy.Util.NoInternetException

class HomeScreenViewModel(private val productRepository: ProductRepository):ViewModel() {


    private val _productList = MutableLiveData<ProductListModel>()
    val productList: LiveData<ProductListModel>
        get() = _productList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    var listener : OnRequestResponse? = null
    init {
        // Set isLoading to true initially
        _isLoading.value = true
        getAllProductList()
    }
    private fun getAllProductList () {
        Coroutine.ioThanMain(
            {
                try {
                    productRepository.getProductList()
                }catch (e: NoInternetException){
                    null
                }
            },
            {
                if (it != null) {
                    _productList.value = it
                    _isLoading.value = false
                } else {
                    listener?.onFailed("Fail to load data")
                }
            }
        )
    }


}