package com.kh.ite.rupp.edu.trendy.Ui.view

import android.app.Activity
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.kh.ite.rupp.edu.trendy.Application.MySharePreferences
import com.kh.ite.rupp.edu.trendy.Factory.UserViewModelFactory
import com.kh.ite.rupp.edu.trendy.Model.UserLoginSuccessResponse
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.UserRepository
import com.kh.ite.rupp.edu.trendy.Ui.custom.DialogX2
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.Util.hideKeyboard
import com.kh.ite.rupp.edu.trendy.ViewModel.auth.AuthViewModel
import com.kh.ite.rupp.edu.trendy.databinding.BottomSheetLoginBinding

class LoginBottomSheetFragment(private val activity: Activity): BottomSheetDialogFragment(), OnBackResponse<UserLoginSuccessResponse> {
    private lateinit var binding: BottomSheetLoginBinding
    private lateinit var networkConnectionInterceptor: NetworkConnectionInterceptor
    private lateinit var api: MyApi
    private lateinit var userRepository: UserRepository
    private lateinit var mySharePreferences: MySharePreferences
    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: AuthViewModel
    private lateinit var dialog : DialogX2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialog = DialogX2(requireContext())

        initializeViewModel()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetLoginBinding.inflate(inflater, container, false)

        binding.backBtnLogin.setOnClickListener {
            dismiss()
        }
        binding.signUp.setOnClickListener {
            val bottomSheetDialog = SigunUpBottomSheetFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "login_bottom_sheet_dialog")
        }


        binding.signInBtn.setOnClickListener {
            dialog.showProgress()
            viewModel.login(binding.phoneEdt.text.toString(), binding.passwordEdt.text.toString())
        }


        binding.passwordEdt.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v, activity)
            }
        }

        binding.phoneEdt.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v, activity)
            }
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener{ dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            }
        }

        return dialog
    }

    private fun dism(isSuccess: Boolean){
        if (isSuccess){
            dismiss()
        }
    }
    private fun initializeViewModel() {
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(requireContext(), networkConnectionInterceptor)
        mySharePreferences = MySharePreferences(requireContext())
        userRepository = UserRepository(api, mySharePreferences)
        factory = UserViewModelFactory(userRepository)
        viewModel = AuthViewModel(userRepository)
        viewModel.listener = this
//        factory = ProductDetailViewModelFactory(productRepository, productId())
//        viewModel = ViewModelProvider(this, factory).get(ProductDetailViewModel::class.java)
//        viewModel?.listener = this
    }

    override fun success(message: UserLoginSuccessResponse) {
        mySharePreferences.saveToken(message.accessToken!!)
        mySharePreferences.saveUserId(message.user!!.userId!!)
        dialog.showSuccess("Login Success!","")
        Handler(Looper.getMainLooper()).postDelayed({
            if (!mySharePreferences.getToken().isNullOrEmpty()){
                dism(true)

                dialog.dismissX()
            }

        },1000)
        Log.d("LOGIN_SC", "success activity: $message")
    }

    override fun fail(message: String) {
        dialog.showError(getString(R.string.app_name), message)
        Log.d("LOGIN_SC", "fail: $message")
    }

}