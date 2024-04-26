package com.kh.ite.rupp.edu.trendy.ViewModel.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.ite.rupp.edu.trendy.Service.repository.UserRepository
import com.kh.ite.rupp.edu.trendy.ViewModel.auth.AuthViewModel

class UserViewModelFactory(
    private val userRepository: UserRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)){
            return AuthViewModel(userRepository) as T
        }
        throw IllegalArgumentException("View Model not found")
    }
}