package com.kh.ite.rupp.edu.trendy.Ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.databinding.ActivityMapAddressBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding
import java.io.IOException

class MapAddressActivity : BaseActivityBinding<ActivityMapAddressBinding>(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private val MY_LOCATION_REQUEST_CODE = 123
    private var selectedLocation: LatLng? = null
    override fun getLayoutViewBinding(): ActivityMapAddressBinding =
        ActivityMapAddressBinding.inflate(layoutInflater)

    private var activityResult: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.confirmAddress.setOnClickListener {
            selectedLocation?.let { location ->
                getAddressFromLocation(location.latitude, location.longitude)
            } ?: run {
                Toast.makeText(this, "Please select a location first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initView() {

        activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result : ActivityResult ->
            if (result.data != null){
                val data = result.data?.extras?.getSerializable("ACTIVITY_DATA") as AddToCartResponseModel
                finish()
            }
        }

        selectedLocation?.let { location ->
            getAddressLocation(location.latitude, location.longitude)
        } ?: run {
            Toast.makeText(this, "Please select a location first", Toast.LENGTH_SHORT).show()
        }


        binding.backBtnLogin.setOnClickListener { finish() }



    }

    override fun onMapReady(googleMap: GoogleMap) {

//        mMap = googleMap
//
//        // Enable the My Location layer if the permission has been granted
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//            || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//        ) {
//            mMap.isMyLocationEnabled = true
//        } else {
//            // Request the missing location permission.
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                MY_LOCATION_REQUEST_CODE
//            )
//        }
//
//        // Set a listener for map click events
//        mMap.setOnMapClickListener { latLng ->
//            // Clear existing markers
//            mMap.clear()
//
//            // Add a marker at the clicked location
//            mMap.addMarker(MarkerOptions().position(latLng))
//
//            // Store the selected location
//            selectedLocation = latLng
//        }

        mMap = googleMap

        // Enable the My Location layer if the permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true

            // Get the last known location of the device
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // If the last known location is available, move the camera to that location and add a marker
                    location?.let {
                        val latLng = LatLng(location.latitude, location.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        mMap.addMarker(MarkerOptions().position(latLng).title("My Location"))
                        selectedLocation = latLng


                    }

                }
        } else {
            // Request the missing location permission.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_LOCATION_REQUEST_CODE
            )
        }

        // Set a listener for map click events
        mMap.setOnMapClickListener { latLng ->
            // Clear existing markers
            mMap.clear()

            // Add a marker at the clicked location
            mMap.addMarker(MarkerOptions().position(latLng))

            // Store the selected location
            selectedLocation = latLng

            getAddressLocation(latLng.latitude, latLng.longitude)
        }


    }


    @SuppressLint("SetTextI18n")
    private fun getAddressLocation(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this)
        try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)!!
            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                var streetName = ""
                address.thoroughfare?.let { streetName ="$it, " }// Get the street name
                // Now you can send the streetName to your API
//                Toast.makeText(this, "Street Name: $streetName", Toast.LENGTH_SHORT).show()
//                Log.d("LOCATION_MAP", "location = $address ")

                binding.addressName.text = address.adminArea
                binding.addressDetail.text = "$streetName${address.subAdminArea}"

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this)
        try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)!!
            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                val streetName = address.thoroughfare // Get the street name
                // Now you can send the streetName to your API
//                Toast.makeText(this, "Street Name: $streetName", Toast.LENGTH_SHORT).show()
//                Log.d("LOCATION_MAP", "location = $address ")
                val intent = Intent(this, AddressDetailActivity::class.java)
                intent.putExtra("ACTIVITY", address)
                activityResult?.launch(intent)

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    companion object{
        fun lunch(context: Context)
        {
            val intent = Intent(context, MapAddressActivity::class.java)
            context.startActivity(intent)
        }
    }



}