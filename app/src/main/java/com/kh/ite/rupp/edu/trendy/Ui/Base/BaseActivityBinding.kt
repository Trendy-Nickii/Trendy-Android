package kh.edu.rupp.ite.trendy.Base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding

import com.google.android.material.snackbar.Snackbar
import com.kh.ite.rupp.edu.trendy.Application.TrendyApplication
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.Base.CoreActivity
import com.kh.ite.rupp.edu.trendy.Ui.Base.CoreView


abstract class BaseActivityBinding<VB: ViewBinding>(): CoreActivity(), CoreView {

    protected lateinit var binding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)
        binding = getLayoutViewBinding()
        findViewById<FrameLayout>(R.id.content_view).addView(binding.root)
        initView()
    }
    override fun onInternetDisconnect() {
        TrendyApplication.isInternetConnected = false
        val snackBar = Snackbar.make(
            window.decorView.findViewById(android.R.id.content),
            getString(R.string.message_no_internet),
            Snackbar.LENGTH_INDEFINITE   // Offline issue
        )
        snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.internet_disconnect))
        snackBar.show()
    }


    open fun isStatusBarDark(): Boolean {
        return false
    }

    open fun setStatusBarColor(color: Int) {
        if (isStatusBarDark() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        window.statusBarColor = color
    }


    override fun onInternetConnect() {
        if (!TrendyApplication.isInternetConnected) {
            TrendyApplication.isInternetConnected = true
            val snackBar = Snackbar.make(
                window.decorView.findViewById(android.R.id.content),
                "Internet Connected.",
                Snackbar.LENGTH_SHORT
            )
            snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.internet_connect))
            snackBar.show()
        }
    }

    protected open fun isDisplayToolbar() = false



    abstract fun getLayoutViewBinding(): VB


}