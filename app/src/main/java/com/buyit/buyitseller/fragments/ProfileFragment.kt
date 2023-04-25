package com.buyit.buyitseller.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyit.buyitseller.activities.StartActivity
import com.buyit.buyitseller.databinding.FragmentProfileBinding
import com.buyit.buyitseller.utils.CommonUtils
import com.buyit.buyitseller.utils.CommonUtils.auth

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireActivity(), StartActivity::class.java))
            requireActivity().finishAffinity()
        }

//        val shopRef = CommonUtils.db.collection("shop").get().addOnSuccessListener {
//            binding.tv.text = it.documents[0].get("shopName").toString()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}