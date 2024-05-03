package com.kh.ite.rupp.edu.trendy.Ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kh.ite.rupp.edu.trendy.Model.CartCheckoutSummaryModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.CartRepository
import com.kh.ite.rupp.edu.trendy.Ui.adapter.CheckoutCartItemAdapter
import com.kh.ite.rupp.edu.trendy.ViewModel.CartViewModel
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.CartViewModelFactory
import com.kh.ite.rupp.edu.trendy.databinding.ActivityCheckOutBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding

class CheckOutActivity : BaseActivityBinding<ActivityCheckOutBinding>() {
    private lateinit var networkConnectionInterceptor : NetworkConnectionInterceptor
    private lateinit var api : MyApi
    private lateinit var factory: CartViewModelFactory
    private lateinit var cartRepository: CartRepository
    private var viewModel: CartViewModel? = null
    override fun getLayoutViewBinding(): ActivityCheckOutBinding = ActivityCheckOutBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(this, networkConnectionInterceptor)
        cartRepository = CartRepository(api)
        factory = CartViewModelFactory(cartRepository)
        viewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)


    }

    private fun initCartItemRec(item: ArrayList<CartCheckoutSummaryModel.OrderDetails.Item>){
        binding.cartItemRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = CheckoutCartItemAdapter(context, item)
        }
    }
    override fun initView() {
        if (!getData().orderDetails!!.items.isNullOrEmpty()){
            initCartItemRec(getData().orderDetails!!.items!!)
            binding.orderTxt.text = this.getString(R.string.your_order , getData().orderDetails!!.items!!.size.toString())
            initOrderSummary(getData().orderDetails!!)
        }

        binding.backBtnLogin.setOnClickListener { finish() }

        binding.deliveryAddress.setOnClickListener {
            AddressActivity.lunch(this)
        }
    }

    private fun initOrderSummary(item: CartCheckoutSummaryModel.OrderDetails){
        binding.subtotalLabel.text = this.getString(R.string.subtotal, item.items!!.size.toString())
        binding.txtSubtotal.text = getString(R.string.price2, String.format("%.2f", item.totalAmount))
        binding.txtServiceFee.text = getString(R.string.price2, String.format("%.2f", 1.50))
        binding.txtTax.text = getString(R.string.price2, String.format("%.2f", 3.50))
        binding.txtTotalPayment.text = getString(R.string.price2, String.format("%.2f", 3.50+1.50+ item.totalAmount!!))
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

}