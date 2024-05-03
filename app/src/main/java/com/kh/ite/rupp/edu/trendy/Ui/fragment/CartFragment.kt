package com.kh.ite.rupp.edu.trendy.Ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.CartItemModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.CartRepository
import com.kh.ite.rupp.edu.trendy.Ui.adapter.CartItemAdapter
import com.kh.ite.rupp.edu.trendy.Ui.custom.DialogX
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnUpdateDeleteClick
import com.kh.ite.rupp.edu.trendy.Ui.view.CheckOutActivity
import com.kh.ite.rupp.edu.trendy.Ui.view.UpdateCartBottomSheet
import com.kh.ite.rupp.edu.trendy.Util.toastHelper
import com.kh.ite.rupp.edu.trendy.ViewModel.CartViewModel
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.CartViewModelFactory
import com.kh.ite.rupp.edu.trendy.databinding.FragmentCartBinding
import kh.edu.rupp.ite.trendy.Base.BaseFragmentBinding
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class CartFragment : BaseFragmentBinding<FragmentCartBinding>(), OnBackResponse<AddToCartResponseModel> {

    override fun getViewBinding(): FragmentCartBinding = FragmentCartBinding.inflate(layoutInflater)
    private lateinit var networkConnectionInterceptor : NetworkConnectionInterceptor
    private lateinit var api : MyApi
    private lateinit var factory: CartViewModelFactory
    private lateinit var cartRepository: CartRepository
    private var viewModel: CartViewModel? = null
    private var cartAdapter : CartItemAdapter? = null
    private var dialogX : DialogX? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(requireContext(), networkConnectionInterceptor)
        cartRepository = CartRepository(api)
        factory = CartViewModelFactory(cartRepository)
        viewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)
        viewModel?.listener = this
        viewModel?.getCartItem()
        dialogX = DialogX(context)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated() {

        viewModel?.itemCart?.observe(viewLifecycleOwner, Observer { cartItems ->
            hideSkeleton()

            Log.i("CRD", "cart = $cartItems ")
            if (cartItems.cart.isNullOrEmpty()) {
                binding.recCart.visibility = View.GONE
                binding.noData.visibility = View.VISIBLE
                binding.noData.text = "Your cart is Empty"
                binding.checkoutBtn.visibility = View.GONE
            } else {
                itemCartRec(cartItems.cart!!)
                checkOut(cartItems)
//                val anim = ObjectAnimator.ofFloat(binding.checkoutBtn, "alpha", 0f, 1f)
//                anim.duration = 1000 // Set the duration of the animation (in milliseconds)
//                anim.startDelay = 500 // Set a delay before starting the animation (optional)
//                anim.start()

                binding.checkoutBtn.setOnClickListener {
                    dialogX?.showProgress()
                    viewModel?.checkoutCart()
                }

            }

        })



    }

    private fun checkOut(data: CartItemModel){
       var total: Double = 0.0
        for (i in data.cart!!){
            val price = i.productPrice!! * (1 - i.productDiscount!! /100)
            total += i.quantity!! * price
        }
            binding.checkoutBtn.text = requireContext().getString(R.string.checkout, String.format("%.2f", total))
    }

    private fun itemCartRec(item:ArrayList<CartItemModel.Cart>){
        val animationLayout = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slide_item_scale)
        binding.recCart.apply {
            layoutAnimation = animationLayout
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
             cartAdapter = CartItemAdapter(context, item, object : OnUpdateDeleteClick<CartItemModel.Cart>{
                override fun onUpdateListener(model: CartItemModel.Cart) {
                    val bottomSheet = UpdateCartBottomSheet(
                        context,
                        sizeSelect = model.size,
                        color = model.color,
                        qty = model.quantity,
                        productId = model.productId!!,
                        cartId = model.cartId!!,
                        object : OnBackResponse<AddToCartResponseModel>{
                            override fun success(message: AddToCartResponseModel) {
                                viewModel?.getCartItem()
                            }

                            override fun fail(message: String) {
                                MotionToast.createColorToast(requireActivity(),"Profile Update Failed!",
                                    message,
                                    MotionToastStyle.ERROR,
                                    MotionToast.GRAVITY_BOTTOM,
                                    MotionToast.LONG_DURATION,
                                    ResourcesCompat.getFont(requireContext(),R.font.roboto_regular))
                            }

                        }
                    )
                    bottomSheet.show(requireFragmentManager(), "update fragment bottomSheet")
                }

                override fun onDeleteListener(model: CartItemModel.Cart) {
                    DialogX(context).showQuestion(
                        "Remaining",
                        "Are you sure to delete this item!"
                    ){
                        viewModel?.deleteCartItem(model.cartId.toString())
                    }

                }

            })
            adapter = cartAdapter
            val marginInPx = resources.getDimensionPixelSize(R.dimen.margin_bottom_last_item)
            cartAdapter?.addLastItemMargin(binding.recCart, marginInPx)
        }
    }
    private fun showSkeleton() {
        // Show skeleton layout
        binding.loading.visibility = View.VISIBLE
        // Hide RecyclerViews
        binding.recLayout.visibility = View.GONE
        binding.checkoutBtn.visibility = View.GONE
    }

    private fun hideSkeleton() {
        // Show skeleton layout
        binding.loading.visibility = View.GONE
        // Hide RecyclerViews
        binding.recLayout.visibility = View.VISIBLE
        binding.checkoutBtn.visibility = View.VISIBLE
    }

    override fun success(message: AddToCartResponseModel) {
        val position = MotionToast.GRAVITY_BOTTOM

        MotionToast.createColorToast(requireActivity(),"Cart 🛒",
            message.message!!,
            MotionToastStyle.SUCCESS,
            position,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(requireContext(),R.font.roboto_regular))

        viewModel?.getCartItem()
        dialogX?.dismissX()
        if (viewModel?.cartCheckout!!.value != null ){
            CheckOutActivity.lunch(requireContext(), viewModel?.cartCheckout!!.value!!)
        }

        cartAdapter?.notifyDataSetChanged()
    }

    override fun fail(message: String) {
        requireContext().toastHelper(message)
    }


    override fun onResume() {
        super.onResume()
        Log.d("UPDATE_CART", "onResume: +=+=++====++==+")
    }

}