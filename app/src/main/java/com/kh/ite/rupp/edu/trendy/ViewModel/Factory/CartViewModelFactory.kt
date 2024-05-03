package com.kh.ite.rupp.edu.trendy.ViewModel.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.ite.rupp.edu.trendy.Service.repository.CartRepository
import com.kh.ite.rupp.edu.trendy.ViewModel.CartViewModel

class CartViewModelFactory(
    private val cartRepository: CartRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)){
            return CartViewModel(cartRepository) as T
        }
        throw IllegalArgumentException("View Model not found")
    }
}