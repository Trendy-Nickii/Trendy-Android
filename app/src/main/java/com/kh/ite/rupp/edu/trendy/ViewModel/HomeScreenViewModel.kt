package com.kh.ite.rupp.edu.trendy.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kh.ite.rupp.edu.trendy.Model.ProductListModel
import com.kh.ite.rupp.edu.trendy.Service.repository.ProductRepository
import com.kh.ite.rupp.edu.trendy.Util.Coroutine

class HomeScreenViewModel(private val productRepository: ProductRepository):ViewModel() {


    private val _productList = MutableLiveData<ProductListModel>()
    val productList: LiveData<ProductListModel>
        get() = _productList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        // Set isLoading to true initially
        _isLoading.value = true
        getAllProductList()
    }
    fun getAllProductList () {
        Coroutine.ioThanMain(
            {
             productRepository.getProductList()
            },
            {
                _productList.value = it
            }
        )
    }


}