package com.buyit.buyitseller.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.buyit.buyitseller.R
import com.buyit.buyitseller.activities.HomeActivity
import com.buyit.buyitseller.databinding.FragmentGetStartedBinding
import com.buyit.buyitseller.utils.CommonUtils.auth

class GetStartedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGetStartedBinding.inflate(inflater, container, false)

        binding.btnGetStarted.setOnClickListener {
            it.findNavController().navigate(R.id.action_getStartedFragment_to_loginFragment)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            startActivity(Intent(context, HomeActivity::class.java))
            requireActivity().finish()
        }
    }
}