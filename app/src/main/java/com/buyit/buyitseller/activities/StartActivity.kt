package com.buyit.buyitseller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.buyit.buyitseller.R
import com.buyit.buyitseller.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

//        Create an online presence for your shop