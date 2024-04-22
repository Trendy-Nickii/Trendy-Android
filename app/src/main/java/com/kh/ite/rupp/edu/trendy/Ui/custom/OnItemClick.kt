package com.kh.ite.rupp.edu.trendy.Ui.custom

import com.kh.ite.rupp.edu.trendy.Model.ErrorModel

interface OnItemClick<T: Any> {
    fun onItemClickListener(model: T, position: Int)
}


interface OnRequestResponse{
    fun onFailed(message: String)
}

interface OnBackResponse<T: Any>{
    fun success(message: T)
    fun fail(message: String)
}