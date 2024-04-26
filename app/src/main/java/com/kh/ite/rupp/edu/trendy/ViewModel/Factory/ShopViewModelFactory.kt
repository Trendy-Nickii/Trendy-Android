package com.kh.ite.rupp.edu.trendy.ViewModel.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.ite.rupp.edu.trendy.Service.repository.ShopRepository
import com.kh.ite.rupp.edu.trendy.ViewModel.ShopViewModel
import com.kh.ite.rupp.edu.trendy.ViewModel.auth.AuthViewModel

class ShopViewModelFactory(
    private val shopRepository: ShopRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopViewModel::class.java)){
            return ShopViewModel(shopRepository) as T
        }
        throw IllegalArgumentException("View Model not found")
    }
}