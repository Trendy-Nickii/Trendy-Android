package com.kh.ite.rupp.edu.trendy.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kh.ite.rupp.edu.trendy.Model.ListProductWithDetailByCategory
import com.kh.ite.rupp.edu.trendy.Model.SubCategoryModel
import com.kh.ite.rupp.edu.trendy.Model.TopCategoryListModel
import com.kh.ite.rupp.edu.trendy.Service.repository.ShopRepository
import com.kh.ite.rupp.edu.trendy.Util.Coroutine

class ShopViewModel(
    private val shopRepository: ShopRepository
): ViewModel() {

    private val _topCategoryData = MutableLiveData<TopCategoryListModel>()
    val topCategoryData : LiveData<TopCategoryListModel>
        get () = _topCategoryData

    private val _subCategoryData = MutableLiveData<SubCategoryModel>()

    val subCategoryData : LiveData<SubCategoryModel>
        get() = _subCategoryData


    private val _listProductByCategory = MutableLiveData<ListProductWithDetailByCategory>()
    val listProductByCategory : LiveData<ListProductWithDetailByCategory>
        get() = _listProductByCategory


    fun getTopCategoryData(){
        Coroutine.ioThanMain(
            {
                shopRepository.getTopCategory()
            },
            {
                _topCategoryData.value = it
            }
        )
    }

    fun getSubCategoryData(id:String){
        Coroutine.ioThanMain(
            {
                shopRepository.getSubCategory(id)
            },
            {
                _subCategoryData.value = it
            }
        )
    }

    fun getListProductByCategory(id: String){
        Coroutine.ioThanMain(
            {shopRepository.getProductByCategory(id)},
            {_listProductByCategory.value = it}
        )
    }


}