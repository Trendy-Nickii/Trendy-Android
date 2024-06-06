package com.kh.ite.rupp.edu.trendy

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kh.ite.rupp.edu.trendy.Application.MySharePreferences
import com.kh.ite.rupp.edu.trendy.Ui.fragment.CartFragment
import com.kh.ite.rupp.edu.trendy.Ui.fragment.HomeFragment
import com.kh.ite.rupp.edu.trendy.Ui.fragment.OrderFragment
import com.kh.ite.rupp.edu.trendy.Ui.fragment.ProfileFragment
import com.kh.ite.rupp.edu.trendy.Ui.fragment.ShopFragment
import com.kh.ite.rupp.edu.trendy.Ui.view.LoginBottomSheetFragment
import com.kh.ite.rupp.edu.trendy.databinding.ActivityMainBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding


class MainActivity : BaseActivityBinding<ActivityMainBinding>() {

    private var doubleBackToExitPressedOnce = false
    private lateinit var mySharePreferences: MySharePreferences
    override fun getLayoutViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        setupBottomNavbar()
        mySharePreferences = MySharePreferences(this)
        binding.bottomNavigation.selectedItemId = R.id.home
    }

    private fun setupBottomNavbar() {
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation

        val colorStateList =
            ContextCompat.getColorStateList(this, R.color.bottom_nav_item_color_selector)
        bottomNavigationView.itemIconTintList = colorStateList
        bottomNavigationView.itemTextColor = colorStateList

        val fragmentManager = supportFragmentManager

        // Initialize the first fragment to be shown
        val initialFragment = HomeFragment()
        fragmentManager.beginTransaction()
            .replace(R.id.container, initialFragment)
            .commit()


        bottomNavigationView.setOnItemSelectedListener { item ->
            if (isLoggedIn()) {
                val fragment = when (item.itemId) {
                    R.id.home -> HomeFragment()
                    R.id.shop -> ShopFragment()
                    R.id.cart -> CartFragment()
                    R.id.my_order -> OrderFragment()
                    R.id.profile -> ProfileFragment()
                    else -> null
                }
                when(item.itemId){
                    R.id.shop ->{
                        STATE = 1
                    }
                    R.id.home ->{
                        STATE = 0
                    }
                    R.id.cart -> {
                        STATE = 2
                    }
                    R.id.my_order ->{
                        STATE = 3
                    }
                    R.id.profile ->{
                        STATE = 4
                    }
                }
                if (fragment != null) {
                    // Replace the fragment container with the selected fragment
                    fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit()

                    // Update icons based on selection/deselection
                    val menu = binding.bottomNavigation.menu
                    for (i in 0 until menu.size()) {
                        val menuItem = menu.getItem(i)
                        val iconId = if (menuItem.itemId == item.itemId) {
                            // If the current item is selected, set its icon to filled
                            getFilledIconIdForItem(item.itemId)
                        } else {
                            // Otherwise, set the icon to outlined
                            getOutlinedIconIdForItem(menuItem.itemId)
                        }
                        menuItem.setIcon(iconId)
                    }

                    true
                } else {
                    false
                }

            } else {
                val fragment = when (item.itemId) {
                    R.id.home -> HomeFragment()
                    R.id.shop -> ShopFragment()
//                    R.id.cart -> CartFragment()
                    R.id.my_order -> OrderFragment()
                    else -> null
                }

                when (item.itemId){
                    R.id.profile ->{
                        val bottomSheetDialog = LoginBottomSheetFragment(this)
                        bottomSheetDialog.show(supportFragmentManager, "login_bottom_sheet_dialog")
                    }
                    R.id.cart ->{
                        val bottomSheetDialog = LoginBottomSheetFragment(this)
                        bottomSheetDialog.show(supportFragmentManager, "login_bottom_sheet_dialog")
                    }
                }


                if (fragment != null) {
                    // Replace the fragment container with the selected fragment
                    fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit()

                    // Update icons based on selection/deselection
                    val menu = binding.bottomNavigation.menu
                    for (i in 0 until menu.size()) {
                        val menuItem = menu.getItem(i)
                        val iconId = if (menuItem.itemId == item.itemId) {
                            // If the current item is selected, set its icon to filled
                            getFilledIconIdForItem(item.itemId)
                        } else {
                            // Otherwise, set the icon to outlined
                            getOutlinedIconIdForItem(menuItem.itemId)
                        }
                        menuItem.setIcon(iconId)
                    }

                    true
                } else {
                    false
                }
            }

        }
    }

    private fun isLoggedIn(): Boolean {
        // Add your logic to check if the user is logged in
        if (mySharePreferences.getToken().isNullOrEmpty()){
            return false
        }
        else{
            return true
        }
        // Placeholder, replace with actual logic
    }

    private fun getFilledIconIdForItem(itemId: Int): Int {
        return when (itemId) {
            R.id.home -> R.drawable.home_svgrepo_com__1_
            R.id.shop -> R.drawable.shopping_cart_svgrepo_com__1_
            R.id.cart -> R.drawable.bag_5_svgrepo_com
            R.id.my_order -> R.drawable.file_document_svgrepo_com
            R.id.profile -> R.drawable.profile_svgrepo_com__1_
            else -> throw IllegalArgumentException("Invalid item ID")
        }
    }

    private fun getOutlinedIconIdForItem(itemId: Int): Int {
        return when (itemId) {
            R.id.home -> R.drawable.home_svgrepo_com
            R.id.shop -> R.drawable.shopping_cart_svgrepo_com
            R.id.cart -> R.drawable.bag_4_svgrepo_com
            R.id.my_order -> R.drawable.file_alt_svgrepo_com
            R.id.profile -> R.drawable.profile_svgrepo_com
            else -> throw IllegalArgumentException("Invalid item ID")
        }
    }


//    private fun setupBottomNavbar(){
//        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
//
//        val colorStateList = ContextCompat.getColorStateList(this, R.color.bottom_nav_item_color_selector)
//        bottomNavigationView.itemIconTintList = colorStateList
//        bottomNavigationView.itemTextColor = colorStateList
//
//        binding.bottomNavigation.setOnItemSelectedListener { item ->
//            val menu = binding.bottomNavigation.menu
//            for (i in 0 until menu.size()) {
//                val menuItem = menu.getItem(i)
//                val iconId = when (menuItem.itemId) {
//                    item.itemId -> {
//                        // If the current item is selected, set its icon to filled
//                        when (menuItem.itemId) {
//                            R.id.home -> R.drawable.home_svgrepo_com__1_
//                            R.id.shop -> R.drawable.shopping_cart_svgrepo_com__1_
//                            R.id.cart -> R.drawable.bag_5_svgrepo_com
//                            R.id.my_order -> R.drawable.file_document_svgrepo_com
//                            R.id.profile -> R.drawable.profile_svgrepo_com__1_
//                            else -> 0
//                        }
//                    }
//                    else -> {
//                        // Otherwise, set the icon to outlined
//                        when (menuItem.itemId) {
//                            R.id.home -> R.drawable.home_svgrepo_com
//                            R.id.shop -> R.drawable.shopping_cart_svgrepo_com
//                            R.id.cart -> R.drawable.bag_4_svgrepo_com
//                            R.id.my_order -> R.drawable.file_alt_svgrepo_com
//                            R.id.profile -> R.drawable.profile_svgrepo_com
//                            else -> 0
//                        }
//                    }
//                }
//                menuItem.setIcon(iconId)
//            }
//            true
//        }
//    }


    private fun showFragment(fragment: Fragment) {
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace((R.id.container), fragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else if (!doubleBackToExitPressedOnce) {

            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                doubleBackToExitPressedOnce = false
            }, 2000)
        } else {
            super.onBackPressed()
            return
        }
    }


    override fun onResume() {
        super.onResume()

        when (STATE) {
            0 -> {
                binding.bottomNavigation.selectedItemId = R.id.home
            }

            1 -> {
                binding.bottomNavigation.selectedItemId = R.id.shop
            }

            2 -> {
                binding.bottomNavigation.selectedItemId = R.id.cart
            }

            3 -> {
                binding.bottomNavigation.selectedItemId = R.id.my_order
            }

            4 -> {
                binding.bottomNavigation.selectedItemId = R.id.profile
            }
        }
    }

    companion object{
        var STATE = 0
        fun lunch(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}