package com.kh.ite.rupp.edu.trendy.ViewModel.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kh.ite.rupp.edu.trendy.Model.UserLoginBody
import com.kh.ite.rupp.edu.trendy.Model.UserLoginSuccessResponse
import com.kh.ite.rupp.edu.trendy.Model.UserSignUpBody
import com.kh.ite.rupp.edu.trendy.Model.UserSignUpModel
import com.kh.ite.rupp.edu.trendy.Service.repository.UserRepository
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnRequestResponse
import com.kh.ite.rupp.edu.trendy.Util.ApiException
import com.kh.ite.rupp.edu.trendy.Util.Coroutine
import com.kh.ite.rupp.edu.trendy.Util.NoInternetException

class AuthViewModel(
    private val userRepository: UserRepository
):ViewModel() {

    private val _userLogin = MutableLiveData<UserLoginSuccessResponse>()
    val userLogin: LiveData<UserLoginSuccessResponse>
        get() = _userLogin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    private val _userSignUp = MutableLiveData<UserSignUpModel>()
    val userSignUp : LiveData<UserSignUpModel>
        get() = _userSignUp

    var listener : OnBackResponse<UserLoginSuccessResponse>? = null

    fun login(phone: String, password: String){
        if (phone.isEmpty() || password.isEmpty()) {
            listener?.fail("Invalid Phone or Password")
            return
        }
       Coroutine.main {
           try {
               val userLogin = userRepository.userLogin(phone, password)
               userLogin.user?.let {
                   listener?.success(userLogin)
                   return@main
               }
//               Log.d("LOGIN_SC", "success: $userLogin")

           }catch (e: NoInternetException){
//               Log.d("LOGIN_SC", "error: $e")
               listener?.fail(e.message!!)
           }catch (e: ApiException){
//               Log.d("LOGIN_SC", "error: $e")
               listener?.fail(e.message!!)
           }
       }
    }

    fun signUp(bodySignUp: UserSignUpBody){
        Coroutine.ioThanMain(
            {
                _isLoading.value = true

            },
            {

            }
        )
    }



}