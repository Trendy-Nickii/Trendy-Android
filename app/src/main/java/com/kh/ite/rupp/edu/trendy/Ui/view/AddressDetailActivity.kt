package com.kh.ite.rupp.edu.trendy.Ui.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.CreateAddressBody
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.AddressRepository
import com.kh.ite.rupp.edu.trendy.Ui.custom.DialogX
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.Util.toastHelper
import com.kh.ite.rupp.edu.trendy.ViewModel.AddressViewModel
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.AddressViewModelFactory
import com.kh.ite.rupp.edu.trendy.databinding.ActivityAddressDetailBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding

class AddressDetailActivity : BaseActivityBinding<ActivityAddressDetailBinding>(), OnBackResponse<AddToCartResponseModel> {

    private lateinit var networkConnectionInterceptor : NetworkConnectionInterceptor
    private lateinit var api : MyApi
    private lateinit var factory: AddressViewModelFactory
    private lateinit var addressRepository: AddressRepository
    private var viewModel: AddressViewModel? = null
    private var dialogX: DialogX? = null
    override fun getLayoutViewBinding(): ActivityAddressDetailBinding = ActivityAddressDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(this, networkConnectionInterceptor)
        addressRepository = AddressRepository(api)
        factory = AddressViewModelFactory(addressRepository)
        viewModel = ViewModelProvider(this, factory).get(AddressViewModel::class.java)
        viewModel?.listener = this
        dialogX = DialogX(this)
    }
    override fun initView() {
        val address: Address? = intent.getParcelableExtra("ACTIVITY")
        displayView(address!!)
        Log.i("ADDRESS_GET", "address = $address ")
        binding.saveBtn.setOnClickListener {
            dialogX?.showProgress()
            var streetName = ""
            address.thoroughfare?.let { streetName = "$it," }
            val addressLine = "$streetName ${address.subAdminArea}, ${address.adminArea}"
            val lat = address.latitude
            val long = address.longitude
            val body = CreateAddressBody(
                recipient_phone = binding.recipientPhone.text.toString(),
                recipient_name = binding.recipientName.text.toString(),
                latitude = lat.toString(),
                longitude = long.toString(),
                address_line = addressLine,
                address_name = binding.addressName.text.toString(),
                khan = address.subAdminArea

            )
            viewModel?.createAddress(body)
        }
    }


    @SuppressLint("SetTextI18n")
    private fun displayView(data: Address){
        binding.txtLocation.setText("${data.thoroughfare}, ${data.subAdminArea}, ${data.adminArea}")

    }

    override fun success(message: AddToCartResponseModel) {
        dialogX?.dismissX()
        toastHelper(message.message!!)
        val intent = Intent().apply {
            putExtra("ACTIVITY", message.message)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun fail(message: String) {
        toastHelper(message)
    }

}