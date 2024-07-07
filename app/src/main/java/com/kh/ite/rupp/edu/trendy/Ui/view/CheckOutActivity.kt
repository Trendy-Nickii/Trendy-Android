package com.kh.ite.rupp.edu.trendy.Ui.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.AddressListModel
import com.kh.ite.rupp.edu.trendy.Model.CartCheckoutSummaryModel
import com.kh.ite.rupp.edu.trendy.Model.DeliveryMethodModel
import com.kh.ite.rupp.edu.trendy.Model.OrderBody
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.CartRepository
import com.kh.ite.rupp.edu.trendy.Ui.adapter.CheckoutCartItemAdapter
import com.kh.ite.rupp.edu.trendy.Ui.custom.DialogX
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.Util.toastHelper
import com.kh.ite.rupp.edu.trendy.ViewModel.CartViewModel
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.CartViewModelFactory
import com.kh.ite.rupp.edu.trendy.databinding.ActivityCheckOutBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding

class CheckOutActivity : BaseActivityBinding<ActivityCheckOutBinding>(),
    OnBackResponse<AddToCartResponseModel> {
    private lateinit var networkConnectionInterceptor : NetworkConnectionInterceptor
    private lateinit var api : MyApi
    private lateinit var factory: CartViewModelFactory
    private lateinit var cartRepository: CartRepository
    private var viewModel: CartViewModel? = null
    private var deliverResult: ActivityResultLauncher<Intent>? = null
    private var deliveryLocation: AddressListModel.Addres? = null
    private var dialogX: DialogX? = null
    private var deliveryMethodResult : ActivityResultLauncher<Intent>? = null
    private var deliveryMethod: DeliveryMethodModel? = null

    override fun getLayoutViewBinding(): ActivityCheckOutBinding = ActivityCheckOutBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(this, networkConnectionInterceptor)
        cartRepository = CartRepository(api)
        factory = CartViewModelFactory(cartRepository)
        viewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)
        viewModel?.listener = this

    }

    private fun initCartItemRec(item: ArrayList<CartCheckoutSummaryModel.OrderDetails.Item>){
        binding.cartItemRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = CheckoutCartItemAdapter(context, item)
        }
    }
    override fun initView() {
        dialogX = DialogX(this)
        if (!getData().orderDetails!!.items.isNullOrEmpty()){
            initCartItemRec(getData().orderDetails!!.items!!)
            binding.orderTxt.text = this.getString(R.string.your_order , getData().orderDetails!!.items!!.size.toString())
            initOrderSummary(getData().orderDetails!!)
        }
        deliverResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result : ActivityResult ->
            if (result.data != null){
                val data = result.data?.extras?.getSerializable("DATA_BACK") as AddressListModel.Addres
                deliveryLocation = data
                binding.addDetail.visibility = View.VISIBLE
                initDetailAddress(deliveryLocation!!)
                binding.deliveryAddress.setOnClickListener {
                    val intent = Intent(this, AddressActivity::class.java)
                    intent.putExtra("ADD_ID", deliveryLocation!!.id.toString())
                    deliverResult!!.launch(intent)
                }
                if (deliveryLocation != null && deliveryMethod != null){
                    binding.confirmOrderBtn.setOnClickListener {
                        dialogX?.showProgress()
                        val body = OrderBody(deliveryLocation!!.id!!, deliveryMethod!!.jsonDelivery!!)
                        viewModel?.ordering(body)
                    }
                }else{
                    binding.confirmOrderBtn.setOnClickListener {
                        toastHelper("Please Choose delivery method!")
                    }
                }
            }
        }
        deliveryMethodResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result : ActivityResult ->
            if (result.data != null){
                val data = result.data?.extras?.getSerializable("DATA_BACK") as DeliveryMethodModel
                deliveryMethod = data
                binding.deliveryMethodDetail.visibility = View.VISIBLE
                initDetailDeliveryMethod(deliveryMethod!!)
                binding.deliveryMethod.setOnClickListener {
                    val intent = Intent(this, DeliveryMethodActivity::class.java)
                    intent.putExtra("ADD_ID", deliveryMethod!!.id.toString())
                    deliveryMethodResult!!.launch(intent)
                }

                if (deliveryLocation != null && deliveryMethod != null){
                    binding.confirmOrderBtn.setOnClickListener {
                        dialogX?.showProgress()
                        val body = OrderBody(deliveryLocation!!.id!!, deliveryMethod!!.jsonDelivery!!)
                        viewModel?.ordering(body)
                    }
                }else{
                    binding.confirmOrderBtn.setOnClickListener {
                        toastHelper("Please Choose delivery address!")
                    }
                }
            }
        }
        binding.backBtnLogin.setOnClickListener { finish() }
        binding.deliveryAddress.setOnClickListener {
            if (deliveryLocation == null){
                val intent = Intent(this, AddressActivity::class.java)
                intent.putExtra("ADD_ID", "")
                deliverResult!!.launch(intent)
            }else{
                val intent = Intent(this, AddressActivity::class.java)
                intent.putExtra("ADD_ID", deliveryLocation!!.id)
                deliverResult!!.launch(intent)
            }

//            AddressActivity.lunch(this)
        }

        binding.deliveryMethod.setOnClickListener {
            if (deliveryMethod == null){
                val intent = Intent(this, DeliveryMethodActivity::class.java)
                intent.putExtra("ADD_ID", "")
                deliveryMethodResult!!.launch(intent)
            }else{
                val intent = Intent(this, DeliveryMethodActivity::class.java)
                intent.putExtra("ADD_ID", deliveryMethod!!.id.toString())
                deliveryMethodResult!!.launch(intent)
            }
        }

        if (deliveryLocation != null){
            binding.addDetail.visibility = View.VISIBLE
            initDetailAddress(deliveryLocation!!)
        }else{
            binding.addDetail.visibility = View.GONE
        }

        if (deliveryMethod != null){
            binding.deliveryMethodDetail.visibility = View.VISIBLE
            initDetailDeliveryMethod(deliveryMethod!!)
        }else{
            binding.deliveryMethodDetail.visibility = View.GONE

        }


        if (deliveryLocation != null && deliveryMethod != null){
            binding.confirmOrderBtn.setOnClickListener {
                dialogX?.showProgress()
                val body = OrderBody(deliveryLocation!!.id!!, deliveryMethod!!.jsonDelivery!!)
                viewModel?.ordering(body)
            }
        }else{
            binding.confirmOrderBtn.setOnClickListener {
                toastHelper("Please Choose delivery method and address!")
            }
        }




    }

    @SuppressLint("SetTextI18n")
    private fun initDetailAddress(item : AddressListModel.Addres){
        binding.addressName.text = "${item.addressName} (${item.recipientName})"
        binding.point.text = item.addressLine
    }
    private fun initDetailDeliveryMethod(item: DeliveryMethodModel){
        binding.deliveryMethodName.text = item.delivery
        item.img?.let { binding.imgDelivery.setImageResource(it) }
    }

    private fun initOrderSummary(item: CartCheckoutSummaryModel.OrderDetails){
        binding.subtotalLabel.text = this.getString(R.string.subtotal, item.items!!.size.toString())
        binding.txtSubtotal.text = getString(R.string.price2, String.format("%.2f", item.totalAmount))
        binding.txtServiceFee.text = getString(R.string.price2, String.format("%.2f", 2.50))
        binding.txtTax.text = getString(R.string.price2, String.format("%.2f", 1.50))
        binding.txtTotalAfterDiscount.text = getString(R.string.price2, String.format("%.2f", item.totalAmountAfterDiscount))
        binding.txtTotalPayment.text = getString(R.string.price2, String.format("%.2f", 1.50+2.50+ item.totalAmountAfterDiscount!!))
    }

    private fun getData():CartCheckoutSummaryModel{
        return intent.getSerializableExtra("DATA") as CartCheckoutSummaryModel
    }
    companion object{
        fun lunch(context: Context, data: CartCheckoutSummaryModel){
            val intent = Intent(context, CheckOutActivity::class.java)
            intent.putExtra("DATA", data)
            context.startActivity(intent)
        }
    }

    override fun success(message: AddToCartResponseModel) {
        toastHelper(message.message!!)
        dialogX?.showSuccess("Order", message.message)
        Handler(Looper.getMainLooper()).postDelayed({
            dialogX?.dismissX()
            val intent = Intent().apply {
                putExtra("DATA_BACK", message.message)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()

        },3000)

    }

    override fun fail(message: String) {
        dialogX?.showError("Order", message)
        toastHelper(message)
    }

}