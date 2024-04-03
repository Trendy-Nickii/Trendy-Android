package com.kh.ite.rupp.edu.trendy.Ui.custom

interface OnItemClick<T: Any> {
    fun onItemClickListener(model: T, position: Int)
}