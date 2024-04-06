package com.kh.ite.rupp.edu.trendy.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.ite.rupp.edu.trendy.Service.repository.ProductRepository
import com.kh.ite.rupp.edu.trendy.ViewModel.HomeScreenViewModel
import com.kh.ite.rupp.edu.trendy.ViewModel.ProductDetailViewModel

class ProductDetailViewModelFactory(
    private val productRepository: ProductRepository,
    private val id: String
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)){
            return ProductDetailViewModel(productRepository, id) as T
        }
        throw IllegalArgumentException("View Model not found")
    }
}