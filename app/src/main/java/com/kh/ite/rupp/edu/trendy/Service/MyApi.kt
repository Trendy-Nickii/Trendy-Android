package com.kh.ite.rupp.edu.trendy.Service

import android.content.Context
import com.kh.ite.rupp.edu.trendy.Model.AddToCartBody
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.CartItemModel
import com.kh.ite.rupp.edu.trendy.Model.CartUpdateModel
import com.kh.ite.rupp.edu.trendy.Model.ListProductWithDetailByCategory
import com.kh.ite.rupp.edu.trendy.Model.ProductListModel
import com.kh.ite.rupp.edu.trendy.Model.SingleProductModel
import com.kh.ite.rupp.edu.trendy.Model.SubCategoryModel
import com.kh.ite.rupp.edu.trendy.Model.TopCategoryListModel
import com.kh.ite.rupp.edu.trendy.Model.UserLoginBody
import com.kh.ite.rupp.edu.trendy.Model.UserLoginSuccessResponse
import com.kh.ite.rupp.edu.trendy.Model.UserSignUpBody
import com.kh.ite.rupp.edu.trendy.Model.UserSignUpModel
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.intercepter.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MyApi {

    @GET("productsV2")
    suspend fun getAllProduct(): Response<ProductListModel>
    @GET("products/get-one/{id}")
    suspend fun getOneProduct(
        @Path("id") id : String
    ): Response<SingleProductModel>

    @GET("products-detail-by-subcategory/{id}")
    suspend fun getProductByCateId(
        @Path("id") id : String
    ): Response<ProductListModel>

    @GET("categories")
    suspend fun getTopCategory(): Response<TopCategoryListModel>

    @GET("categories/{id}/subcategories")
    suspend fun getSubCategory(
        @Path("id") id: String
    ): Response<SubCategoryModel>

    @GET("products-detail-by-subcategory/{id}")
    suspend fun getProductByCategory(
        @Path("id") id: String
    ): Response<ListProductWithDetailByCategory>

    @POST("user/login/user")
    suspend fun login(
        @Body info: UserLoginBody
    ): Response<UserLoginSuccessResponse>

    @POST("user/create")
    suspend fun signUp(
        @Body info: UserSignUpBody
    ): Response<UserSignUpModel>

    @POST("cart/add-to-cart")
    suspend fun addToCart(
        @Body info: AddToCartBody
    ):Response<AddToCartResponseModel>

    @GET("cart")
    suspend fun getCartItem(): Response<CartItemModel>

    @DELETE("cart/{id}")
    suspend fun deleteCartItem(
        @Path("id") id: String
    ): Response<AddToCartResponseModel>

    @PUT("cart/{id}")
    suspend fun updateCart(
        @Path("id") id: String,
        @Body info: CartUpdateModel
    ):Response<AddToCartResponseModel>

    companion object{
        operator fun invoke(
            context: Context,
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi{
            val tokenInterceptor = TokenInterceptor(context)
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(tokenInterceptor)
                .build()
           return Retrofit.Builder()
               .baseUrl("http://10.0.2.2:5001/api/")
               .client(okHttpClient)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
               .create(MyApi::class.java)
        }
    }
}