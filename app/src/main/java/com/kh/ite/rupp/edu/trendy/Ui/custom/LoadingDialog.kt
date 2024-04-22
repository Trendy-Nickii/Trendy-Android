package com.kh.ite.rupp.edu.trendy.Ui.custom

import android.content.Context
import android.icu.text.CaseMap.Title
import com.kh.ite.rupp.edu.trendy.Ui.Base.BaseDialogBinding
import com.kh.ite.rupp.edu.trendy.databinding.DialogLoadingBinding

class LoadingDialog(
    private val context: Context,
    private var title: String? ="",
    private var isLoading: Boolean,
    private val mListener: OnBackResponse<Boolean>
):BaseDialogBinding<DialogLoadingBinding>(context) {
    override fun setUpViewBinding(): DialogLoadingBinding = DialogLoadingBinding.inflate(layoutInflater)

    override fun onViewInit() {
        super.onViewInit()


    }

    private fun showProgress(){

    }


}