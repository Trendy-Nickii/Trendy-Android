package com.kh.ite.rupp.edu.trendy.Ui.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.AddressSingleModel
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
    private var locationBack: ActivityResultLauncher<Intent>? = null
    private var addressData: AddressSingleModel.Address = AddressSingleModel.Address()
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
        if (isUpdate()){
            viewModel?.getAddress(getAddressId())
            viewModel?.addressOne?.observe(this, Observer {
                displayUpdate(it.address!!)
                addressData = it.address!!
            })
        }
        locationBack =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result : ActivityResult ->
            if (result.data != null){
                val data = result.data?.extras?.getSerializable("DATA_BACK") as AddressSingleModel.Address
                addressData = data
                displayUpdate(addressData)
            }
        }
    }
    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.backBtnLogin.setOnClickListener { finish() }
        if (isUpdate()){
            binding.saveBtn.text = "Update"

            binding.saveBtn.setOnClickListener {
                addressData.addressName = binding.addressName.text.toString()
                addressData.recipientName = binding.recipientName.text.toString()
                addressData.recipientPhone = binding.recipientPhone.text.toString()
                dialogX?.showProgress()
                viewModel?.updateAddress(
                    addressData.id.toString(), CreateAddressBody(
                    recipient_phone = addressData.recipientPhone!!,
                        address_name = addressData.addressName!!,
                        recipient_name = addressData.recipientName!!,
                        address_line = addressData.addressLine!!,
                        latitude = addressData.latitude!!,
                        longitude = addressData.longitude!!,
                        khan = addressData.khan!!
                )
                )
            }


        }else{
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

    }


    @SuppressLint("SetTextI18n")
    private fun displayView(data: Address){
        binding.txtLocation.setText("${data.thoroughfare}, ${data.subAdminArea}, ${data.adminArea}")

    }

    private fun displayUpdate(item: AddressSingleModel.Address){
        item.addressLine?.let { binding.txtLocation.setText(it) }
        item.addressName?.let { binding.addressName.setText(it) }
        item.recipientName?.let { binding.recipientName.setText(it) }
        item.recipientPhone?.let { binding.recipientPhone.setText(it) }


        binding.txtLocation.setOnClickListener {
            val intent = Intent(this, MapAddressActivity::class.java)
            intent.putExtra("IS_UP", true)
            intent.putExtra("ADD_DATA", addressData)
            locationBack?.launch(intent)
        }

    }

    override fun success(message: AddToCartResponseModel) {
        dialogX?.dismissX()
        toastHelper(message.message!!)
        if (isUpdate()){
            ManageAddressActivity.ADDRESS_UPDATE = true
            finish()
        }
        val intent = Intent().apply {
            putExtra("ACTIVITY", message.message)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()

    }

    override fun fail(message: String) {
        toastHelper(message)
    }

    private fun getAddressId(): String {
        return intent.getStringExtra("ADD_ID") as String
    }
    private fun isUpdate (): Boolean{
        return intent.getBooleanExtra("IS_UP", false)
    }

    companion object{
        fun lunchUpdate(context: Context, addressId: String, isUpdate: Boolean){
            val intent = Intent(context, AddressDetailActivity::class.java)
            intent.putExtra("ADD_ID", addressId)
            intent.putExtra("IS_UP", isUpdate)
            context.startActivity(intent)
        }
    }

}