package com.buyit.buyitseller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.buyit.buyitseller.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
