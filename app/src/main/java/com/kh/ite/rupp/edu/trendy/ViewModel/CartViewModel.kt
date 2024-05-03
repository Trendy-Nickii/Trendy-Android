package com.kh.ite.rupp.edu.trendy.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kh.ite.rupp.edu.trendy.Model.AddToCartBody
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.CartCheckoutSummaryModel
import com.kh.ite.rupp.edu.trendy.Model.CartItemModel
import com.kh.ite.rupp.edu.trendy.Model.CartUpdateModel
import com.kh.ite.rupp.edu.trendy.Model.SingleProductModel
import com.kh.ite.rupp.edu.trendy.Service.repository.CartRepository
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.Util.ApiException
import com.kh.ite.rupp.edu.trendy.Util.Coroutine
import com.kh.ite.rupp.edu.trendy.Util.NoInternetException

class CartViewModel(
    private val cartRepository: CartRepository
): ViewModel() {

    private val _cartCheckout = MutableLiveData<CartCheckoutSummaryModel>()
    val cartCheckout: LiveData<CartCheckoutSummaryModel>
        get() = _cartCheckout

    private val _productOne = MutableLiveData<SingleProductModel>()
    val productOne: LiveData<SingleProductModel>
        get() = _productOne

    private val _addToCartModel = MutableLiveData<AddToCartResponseModel>()
    val addToCartModel: LiveData<AddToCartResponseModel>
        get() = _addToCartModel

    private val _itemCart = MutableLiveData<CartItemModel>()
    val itemCart: LiveData<CartItemModel>
        get() = _itemCart

    var listener : OnBackResponse<AddToCartResponseModel>? = null

    fun addToCart(body: AddToCartBody){
        Coroutine.main {
            try {
                val addToCart = cartRepository.addToCart(body)
                addToCart.message?.let {
                    listener?.success(addToCart)
                }
            }catch (e: NoInternetException){
                listener?.fail(e.message!!)
            }catch (e: ApiException){
                listener?.fail(e.message!!)
            }
        }
    }

    fun getCartItem(){
        Coroutine.ioThanMain(
            {
                try {
                    cartRepository.getCartItem()
                }catch (e:NoInternetException){
                    listener?.fail(e.message!!)
                    null
                }catch (e:ApiException){
                    listener?.fail(e.message!!)
                    null
                }
            },
            {
                if (it != null) {
                    _itemCart.value = it
                }else{
                    listener?.fail("Fail to load data")
                }
            }
        )
    }

    fun deleteCartItem(id: String){
        Coroutine.ioThanMain(
            {
                try {
                    cartRepository.deleteCartItem(id)

                }catch (e:NoInternetException){
                    listener?.fail(e.message!!)
                    null
                }catch (e:ApiException){
                    listener?.fail(e.message!!)
                    null
                }
            },
            {
                if (it != null){
                    listener?.success(it)
                }else{
                    listener?.fail("Something want wrong.")
                }
            }
        )
    }



     fun getOneProduct(id: String){
        Coroutine.ioThanMain(
            {
                try {
                    cartRepository.getOneProduct(id)

                }catch (e: NoInternetException){
                    null
                }
            },
            {
                if (it != null ){
                    _productOne.value = it

                    Log.d("PRODUCT_DETAIL", "data in nun null = $it")
                }else{
                    listener?.fail("Error to load Data")
                    Log.d("PRODUCT_DETAIL", "null data = null")
                }
            }
        )
    }

    fun updateCart(id: String, body: CartUpdateModel){
        Coroutine.main {
            try {
                val update = cartRepository.updateCart(id, body)
                update.message?.let {
                    listener?.success(update)
                    return@main
                }
            }catch (e: NoInternetException){
//               Log.d("LOGIN_SC", "error: $e")
                listener?.fail(e.message!!)
            }catch (e: ApiException){
//               Log.d("LOGIN_SC", "error: $e")
                listener?.fail(e.message!!)
            }
        }
    }


    fun checkoutCart(){
        Coroutine.ioThanMain(
            {
                try {
                    cartRepository.cartCheckout()

                }catch (e: NoInternetException){
//               Log.d("LOGIN_SC", "error: $e")
                    listener?.fail(e.message!!)
                    null
                }catch (e: ApiException){
//               Log.d("LOGIN_SC", "error: $e")
                    listener?.fail(e.message!!)
                    null
                }

            },
            {
                if (it != null){
                    _cartCheckout.value = it
                    listener?.success(AddToCartResponseModel("Cart Checkout"))
                }else{
                    listener?.fail("Fail to Load data!!")
                }
            }
        )
    }


}