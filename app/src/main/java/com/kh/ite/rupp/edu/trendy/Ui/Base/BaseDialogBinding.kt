package com.kh.ite.rupp.edu.trendy.Ui.Base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.Window
import androidx.viewbinding.ViewBinding
import com.kh.ite.rupp.edu.trendy.R

abstract class BaseDialogBinding<VB : ViewBinding>(context: Context) : Dialog(context) {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = setUpViewBinding()
        setCanceledOnTouchOutside(true)
        setContentView(binding.root)
        if (window != null) {
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            window!!.attributes.windowAnimations = R.style.DialogAnimationScaleInOut
            window!!.setDimAmount(0.5f)
            window!!.setGravity(Gravity.CENTER)
            window!!.setBackgroundDrawableResource(android.R.color.transparent)
        }
        onViewInit()
    }
    open fun isCancelable() = true
    open fun isBackgroundDrawable(): Nothing? = null
    protected open fun setContentBinding(): ViewBinding? = null
    abstract fun setUpViewBinding(): VB
    open fun onViewInit() {}
    override fun show() {
        if (!isShowing)
            super.show()
        else dismiss()
    }
}