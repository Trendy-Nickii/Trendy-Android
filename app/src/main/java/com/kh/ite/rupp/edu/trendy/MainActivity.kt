package com.kh.ite.rupp.edu.trendy

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kh.ite.rupp.edu.trendy.Ui.fragment.CartFragment
import com.kh.ite.rupp.edu.trendy.Ui.fragment.HomeFragment
import com.kh.ite.rupp.edu.trendy.Ui.fragment.OrderFragment
import com.kh.ite.rupp.edu.trendy.Ui.fragment.ProfileFragment
import com.kh.ite.rupp.edu.trendy.Ui.fragment.ShopFragment
import com.kh.ite.rupp.edu.trendy.databinding.ActivityMainBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding


class MainActivity : BaseActivityBinding<ActivityMainBinding>() {
    private var STATE = 0
    private var doubleBackToExitPressedOnce = false
    override fun getLayoutViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun initView() {
        setupBottomNavbar()
        binding.bottomNavigation.selectedItemId = R.id.home
    }

    private fun setupBottomNavbar() {
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation

        val colorStateList = ContextCompat.getColorStateList(this, R.color.bottom_nav_item_color_selector)
        bottomNavigationView.itemIconTintList = colorStateList
        bottomNavigationView.itemTextColor = colorStateList

        val fragmentManager = supportFragmentManager

        // Initialize the first fragment to be shown
        val initialFragment = HomeFragment()
        fragmentManager.beginTransaction()
            .replace(R.id.container, initialFragment)
            .commit()

        bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.home -> HomeFragment()
                R.id.shop -> ShopFragment()
                R.id.cart -> CartFragment()
                R.id.my_order -> OrderFragment()
                R.id.profile -> ProfileFragment()
                else -> null
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

    private fun initBottomNavigate(){
        binding.bottomNavigation.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.home ->{
                    showFragment(HomeFragment())
                    STATE = 0
                    true
                }
                R.id.shop -> {
                    showFragment(ShopFragment())
                    STATE = 1
                    true
                }
                R.id.cart ->{
                    showFragment(CartFragment())
                    STATE = 2
                    true
                }
                R.id.my_order ->{
                    showFragment(OrderFragment())
                    STATE = 3
                    true
                }
                R.id.profile ->{
                    showFragment(ProfileFragment())
                    false
                }
                else ->{
                    false
                }
            }

        }
    }
    private fun showFragment(fragment: Fragment){
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace((R.id.container), fragment )
        fragmentTransaction.commit()
    }
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount>0){
            supportFragmentManager.popBackStack()
        }else if(!doubleBackToExitPressedOnce){

            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                doubleBackToExitPressedOnce = false
            }, 2000)
        }else{
            super.onBackPressed()
            return
        }
    }



    override fun onResume() {
        super.onResume()

        when(STATE){
            0 ->{
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
}