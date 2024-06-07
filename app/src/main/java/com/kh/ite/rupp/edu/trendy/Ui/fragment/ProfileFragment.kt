package com.kh.ite.rupp.edu.trendy.Ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.kh.ite.rupp.edu.trendy.Application.MySharePreferences
import com.kh.ite.rupp.edu.trendy.MainActivity
import com.kh.ite.rupp.edu.trendy.Model.UserLoginSuccessResponse
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.UserRepository
import com.kh.ite.rupp.edu.trendy.Ui.custom.DialogX
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.Ui.view.ManageAddressActivity
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.UserViewModelFactory
import com.kh.ite.rupp.edu.trendy.ViewModel.auth.AuthViewModel
import com.kh.ite.rupp.edu.trendy.databinding.FragmentProfileBinding
import kh.edu.rupp.ite.trendy.Base.BaseFragmentBinding

class ProfileFragment : BaseFragmentBinding<FragmentProfileBinding>(),
    OnBackResponse<UserLoginSuccessResponse> {
    override fun getViewBinding(): FragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater)
    private lateinit var mySharePreferences: MySharePreferences
    private lateinit var networkConnectionInterceptor: NetworkConnectionInterceptor
    private lateinit var api: MyApi
    private lateinit var userRepository: UserRepository
    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeViewModel()
        binding.loading.visibility = View.VISIBLE
        binding.scrollView.visibility = View.GONE
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated() {
        mySharePreferences = MySharePreferences(requireContext())
        val dialog = DialogX(requireContext())
        binding.logout.setOnClickListener {
            DialogX(requireContext()).showQuestion(
                "Are you sure?",
               "Do you want to log out?"
            ){
                logOut()
                MainActivity.lunch(requireContext())
                requireActivity().finish()
            }
        }

        binding.address.setOnClickListener { ManageAddressActivity.lunch(requireContext()) }
        viewModel.userInfo.observe(viewLifecycleOwner, Observer { userInfoData ->
            userInfoData.user?.username?.let{binding.username.text = it}
            binding.loading.visibility = View.GONE
            binding.scrollView.visibility = View.VISIBLE
        })
    }


    private fun initializeViewModel() {
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(requireContext(), networkConnectionInterceptor)
        mySharePreferences = MySharePreferences(requireContext())
        userRepository = UserRepository(api, mySharePreferences)
        factory = UserViewModelFactory(userRepository)
        viewModel = AuthViewModel(userRepository)
        viewModel.getUserInfo()
        viewModel.listener = this
//        factory = ProductDetailViewModelFactory(productRepository, productId())
//        viewModel = ViewModelProvider(this, factory).get(ProductDetailViewModel::class.java)
//        viewModel?.listener = this
    }

    private fun logOut(){
        mySharePreferences.clearToken()
    }

    override fun success(message: UserLoginSuccessResponse) {

    }

    override fun fail(message: String) {

    }

}