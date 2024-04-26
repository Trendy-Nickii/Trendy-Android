package com.kh.ite.rupp.edu.trendy.Ui.custom

interface OnItemClick<T: Any> {
    fun onItemClickListener(model: T, position: Int)
}

interface OnUpdateDeleteClick<T: Any>{
    fun onUpdateListener(model: T)
    fun onDeleteListener(model: T)
}


interface OnRequestResponse{
    fun onFailed(message: String)
}

interface OnBackResponse<T: Any>{
    fun success(message: T)
    fun fail(message: String)
}