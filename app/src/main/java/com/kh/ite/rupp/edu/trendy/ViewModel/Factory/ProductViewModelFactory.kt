package com.kh.ite.rupp.edu.trendy.ViewModel.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.ite.rupp.edu.trendy.Service.repository.ProductRepository
import com.kh.ite.rupp.edu.trendy.ViewModel.HomeScreenViewModel

class ProductViewModelFactory(
    private val productRepository: ProductRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)){
            return HomeScreenViewModel(productRepository) as T
        }
        throw IllegalArgumentException("View Model not found")
    }
}