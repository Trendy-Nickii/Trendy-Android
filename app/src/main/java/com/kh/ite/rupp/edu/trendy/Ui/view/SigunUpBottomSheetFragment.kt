package com.kh.ite.rupp.edu.trendy.Ui.view

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kh.ite.rupp.edu.trendy.Application.MySharePreferences
import com.kh.ite.rupp.edu.trendy.Model.UserSignUpBody
import com.kh.ite.rupp.edu.trendy.Model.UserSignUpModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.UserRepository
import com.kh.ite.rupp.edu.trendy.Ui.custom.DialogX2
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.UserViewModelFactory
import com.kh.ite.rupp.edu.trendy.ViewModel.auth.AuthViewModel
import com.kh.ite.rupp.edu.trendy.databinding.BottomSheetSignupBinding

class SigunUpBottomSheetFragment: BottomSheetDialogFragment(),
    OnBackResponse<UserSignUpModel> {

    private lateinit var binding: BottomSheetSignupBinding
    private var gender: Int? = 1
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
        binding = BottomSheetSignupBinding.inflate(inflater,container,false)




        binding.backBtnLogin.setOnClickListener {
            dismiss()
        }



        binding.genderMale.setOnClickListener{
            if (binding.genderMale.isChecked == true){
                gender = 1
                binding.genderMale.error = null
                binding.genderFemale.error = null
                binding.genderFemale.isChecked = false
            }
            else{
                gender = 0
            }
        }

        binding.genderFemale.setOnClickListener {
            if (binding.genderFemale.isChecked){
                gender = 2

                binding.genderMale.error = null
                binding.genderFemale.error = null
                binding.genderMale.isChecked =false
            }
            else{
                gender = 0
            }
        }

        binding.signUpBtn.setOnClickListener {
            val username = "${binding.firstnameEdt.text.toString()} ${binding.lastnameEdt.text.toString()}"
            val body = UserSignUpBody(username, binding.phoneEdt.text.toString(), gender!!, binding.passwordEdt.text.toString())
            viewModel.signUp(body)
        }


        return binding.root
    }

    private fun initializeViewModel() {
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(requireContext(), networkConnectionInterceptor)
        mySharePreferences = MySharePreferences(requireContext())
        userRepository = UserRepository(api, mySharePreferences)
        factory = UserViewModelFactory(userRepository)
        viewModel = AuthViewModel(userRepository)
        viewModel.listenerB = this
//        factory = ProductDetailViewModelFactory(productRepository, productId())
//        viewModel = ViewModelProvider(this, factory).get(ProductDetailViewModel::class.java)
//        viewModel?.listener = this
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

    override fun success(message: UserSignUpModel) {
        dismiss()
    }

    override fun fail(message: String) {
        dialog.showError(getString(R.string.app_name), message)
    }

}