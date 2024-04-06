package com.kh.ite.rupp.edu.trendy.Ui.view

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.kh.ite.rupp.edu.trendy.R

class SigunUpBottomSheetFragment: BottomSheetDialogFragment() {
    private var btnBack : ImageView?=null
    private var checkBoxMale : CheckBox? = null
    private var checkBoxFemale: CheckBox? = null
    var gender: Int? = null
    private var phoneNumberEdt: TextInputEditText? = null
    private var firstnameEdt: TextInputEditText? = null
    private var lastnameEdt: TextInputEditText? = null
    private var emailEdt: TextInputEditText? = null
    private var passwordEdt: TextInputEditText? = null
    private var confirmPassword: TextInputEditText? = null
    private var signIn: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_signup, container, false)
        btnBack = view.findViewById(R.id.back_btn_login)
        checkBoxMale = view.findViewById(R.id.gender_male)
        checkBoxFemale = view.findViewById(R.id.gender_female)




        btnBack?.setOnClickListener {
            dismiss()
        }



        checkBoxMale?.setOnClickListener{
            if (checkBoxMale?.isChecked == true){
                gender = 1
                checkBoxMale?.error = null
                checkBoxFemale?.error = null
                checkBoxFemale?.isChecked = false
            }
            else{
                gender = 0
            }
        }

        checkBoxFemale?.setOnClickListener {
            if (checkBoxFemale?.isChecked == true){
                gender = 2

                checkBoxMale?.error = null
                checkBoxFemale?.error = null
                checkBoxMale?.isChecked =false
            }
            else{
                gender = 0
            }
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