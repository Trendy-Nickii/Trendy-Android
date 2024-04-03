package com.kh.ite.rupp.edu.trendy.Ui.view

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.kh.ite.rupp.edu.trendy.R

class LoginBottomSheetFragment: BottomSheetDialogFragment() {
    private var btnBack : ImageView?=null
    private var signup: TextView? = null
    private var phoneNumberEdt: TextInputEditText? = null
    private var passwordEdt: TextInputEditText? = null
    private var btnSignIn: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_login, container, false)
        btnBack = view.findViewById(R.id.back_btn_login)
        signup = view.findViewById(R.id.sign_up)
        btnBack?.setOnClickListener {
            dismiss()
        }
        signup?.setOnClickListener {
            val bottomSheetDialog = SigunUpBottomSheetFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "login_bottom_sheet_dialog")
        }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener{ dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            }
        }

        return dialog
    }

}