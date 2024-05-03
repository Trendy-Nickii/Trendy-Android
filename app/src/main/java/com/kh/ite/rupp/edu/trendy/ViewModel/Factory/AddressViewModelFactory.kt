package com.kh.ite.rupp.edu.trendy.ViewModel.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.ite.rupp.edu.trendy.Service.repository.AddressRepository
import com.kh.ite.rupp.edu.trendy.ViewModel.AddressViewModel

class AddressViewModelFactory(
    private val addressRepository: AddressRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddressViewModel::class.java)){
            return AddressViewModel(addressRepository) as T
        }
        throw IllegalArgumentException("View Model not found")

    }
}