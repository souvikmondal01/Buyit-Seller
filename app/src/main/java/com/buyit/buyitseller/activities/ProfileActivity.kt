package com.buyit.buyitseller.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.buyit.buyitseller.databinding.ActivityProfileBinding
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityProfileBinding

    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

//        binding.tv.text = auth.currentUser!!.phoneNumber
//
//        binding.btnSignOut.setOnClickListener {
//            auth.signOut()
//            startActivity(Intent(this, StartActivity::class.java))
//            finishAffinity()
//        }

//        binding.iv.setOnClickListener {
//            selectImage()
//        }
//        binding.btnUpload.setOnClickListener {
//            uploadImage()
//        }


    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 100 && resultCode == RESULT_OK) {
//            imageUri = data?.data!!
//            binding.iv.setImageURI(imageUri)
//        }
//    }

//    private fun uploadImage() {
//
//        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
//        val now = Date()
//        val filename = formatter.format(now)
//
//        val storageRef = Firebase.storage.reference
//        val imgRef = storageRef.child("images/$filename")
//        imgRef.putFile(imageUri).addOnSuccessListener {
//            binding.iv.setImageURI(null)
//            Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show()
//            imgRef.downloadUrl.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val url = task.result.toString()
//                    binding.tv.text = url
//
//                    Glide.with(this).load(url).listener(object : RequestListener<Drawable> {
//                        override fun onLoadFailed(
//                            e: GlideException?,
//                            model: Any?,
//                            target: Target<Drawable>?,
//                            isFirstResource: Boolean
//                        ): Boolean {
//                            Toast.makeText(this@ProfileActivity, "Load Failed", Toast.LENGTH_SHORT)
//                                .show()
//                            return false
//                        }
//
//                        override fun onResourceReady(
//                            resource: Drawable?,
//                            model: Any?,
//                            target: Target<Drawable>?,
//                            dataSource: DataSource?,
//                            isFirstResource: Boolean
//                        ): Boolean {
//                            Toast.makeText(
//                                this@ProfileActivity,
//                                "Load successful",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            return false
//                        }
//
//                    }).into(binding.iv)
//
//                }
//            }
//        }.addOnFailureListener {
//            Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
//        }
//
//    }


    fun loc() {

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {


            if (it != null) {
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
//                binding.tv.text = addresses!![0].getAddressLine(0)
//                binding.tv.text = addresses!![0].countryCode
            }

        }


    }
}