package com.buyit.buyitseller.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import com.buyit.buyitseller.R
import com.buyit.buyitseller.databinding.FragmentLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.verifyPhoneNumber
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true);
        binding.tv.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_getOtpFragment)
        }

        binding.btnGetOtp.setOnClickListener {
//            val phoneNumber = "+91" + binding.etPhone.text.toString()
            val phoneNumber = binding.etPhone.text.toString()
            if (phoneNumber.isNotEmpty()) {
                if (phoneNumber.length == 10) {

                    val options = PhoneAuthOptions.newBuilder(Firebase.auth)
                        .setPhoneNumber("+91$phoneNumber")
                        .setTimeout(30L, TimeUnit.SECONDS)
                        .setActivity(requireActivity())
                        .setCallbacks(object :
                            PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            override fun onCodeSent(
                                verificationId: String,
                                forceResendingToken: PhoneAuthProvider.ForceResendingToken
                            ) {
                                setFragmentResult(
                                    "key", bundleOf(
                                        "phoneNumber" to phoneNumber,
                                        "otp" to verificationId, "token" to forceResendingToken
                                    )
                                )
                                it.findNavController()
                                    .navigate(R.id.action_loginFragment_to_getOtpFragment)
                            }

                            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                                Toast.makeText(
                                    context,
                                    "onVerificationCompleted success",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            override fun onVerificationFailed(e: FirebaseException) {
                                binding.tv.text = e.message.toString()
                                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT)
                                    .show()

                            }
                        })
                        .build()

                    verifyPhoneNumber(options)


                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please enter correct number", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(context, "Enter Phone Number", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }



}