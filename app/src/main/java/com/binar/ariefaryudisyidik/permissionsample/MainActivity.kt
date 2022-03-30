package com.binar.ariefaryudisyidik.permissionsample

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var loadImageButton: Button
    private lateinit var requestLocationButton: Button
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadImageButton = findViewById(R.id.load_image_button)
        requestLocationButton = findViewById(R.id.request_location_button)
        imageView = findViewById(R.id.image_view)

        loadImageButton.setOnClickListener {
            loadImage()
        }

        requestLocationButton.setOnClickListener {
            requestLocationPermission()
        }

    }

    // Load Image
    private fun loadImage() {
        Glide.with(this)
            .load("https://img.icons8.com/plasticine/2x/flower.png")
            .circleCrop()
            .into(imageView)

        /*       Snackbar.make(loadImageButton, "Snackbar test", Snackbar.LENGTH_SHORT)
                   .setAction("Show Toast") {
                       Toast.makeText(this, "Toast is called", Toast.LENGTH_SHORT).show()
                   }
                   .show()*/

        // Indefinite Snackbar
        val snackBar =
            Snackbar.make(loadImageButton, "Indefinite Snackbar", Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction("Dismiss") {
            snackBar.dismiss()
        }
        snackBar.show()
    }

    // Request Location Permission
    private fun requestLocationPermission() {
        val permissionCheck =
            checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Location DIIZINKAN", Toast.LENGTH_LONG).show()
            getLongLat()
        } else {
            Toast.makeText(this, "Permission Location DITOLAK", Toast.LENGTH_LONG).show()
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 201)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLongLat() {
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val location: Location? =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        val latLongText = "Lat: ${location?.latitude} Long : ${location?.longitude}"

        Toast.makeText(
            this,
            latLongText,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            201 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION
                ) {
                    Toast.makeText(this, "ALLOW telah dipilih", Toast.LENGTH_LONG).show()
                    getLongLat()
                } else {
                    Toast.makeText(this, "DENY telah dipilih", Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                Toast.makeText(this, "Bukan Request yang dikirim", Toast.LENGTH_LONG).show()
            }
        }

    }
}
