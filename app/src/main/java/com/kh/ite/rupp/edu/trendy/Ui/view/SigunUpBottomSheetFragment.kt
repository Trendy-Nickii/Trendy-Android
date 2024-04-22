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
import com.kh.ite.rupp.edu.trendy.databinding.BottomSheetLoginBinding
import com.kh.ite.rupp.edu.trendy.databinding.BottomSheetSignupBinding

class SigunUpBottomSheetFragment: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetSignupBinding
    private var gender: Int? = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetSignupBinding.inflate(inflater,container,false)




        binding.backBtnLogin.setOnClickListener {
            dismiss()
        }



        binding.genderMale.setOnClickListener{
            if (binding.genderMale.isChecked == true){
                gender = 1
                binding.genderMale.error = null
                binding.genderFemale.error = null
                binding.genderFemale.isChecked = false
            }
            else{
                gender = 0
            }
        }

        binding.genderFemale.setOnClickListener {
            if (binding.genderFemale.isChecked){
                gender = 2

                binding.genderMale.error = null
                binding.genderFemale.error = null
                binding.genderMale.isChecked =false
            }
            else{
                gender = 0
            }
        }


        return binding.root
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