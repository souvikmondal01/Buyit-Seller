package com.buyit.buyitseller.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import com.buyit.buyitseller.activities.HomeActivity
import com.buyit.buyitseller.databinding.FragmentGetOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GetOtpFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var credential: PhoneAuthCredential

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentGetOtpBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        setFragmentResultListener("key") { requestKey, bundle ->
            val phoneNumber = "+91" + bundle.getString("phoneNumber").toString()
            val otp = bundle.getString("otp").toString()
            binding.tv.text = "$phoneNumber"

            binding.btnVerify.setOnClickListener {
                val sendOtp = binding.etOtp.text.toString()
                Toast.makeText(context, sendOtp, Toast.LENGTH_SHORT).show()
                credential = PhoneAuthProvider.getCredential(otp, sendOtp)

                signInWithPhoneAuthCredential(credential)
            }
        }
        return binding.root
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(activity, HomeActivity::class.java))
                    requireActivity().finish()

                } else {
                    // Sign in failed, display a message and update the UI

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(context, "invalid otp", Toast.LENGTH_SHORT).show()
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

}