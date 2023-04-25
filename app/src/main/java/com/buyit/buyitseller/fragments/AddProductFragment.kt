package com.buyit.buyitseller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyit.buyitseller.databinding.FragmentAddProductBinding
import com.buyit.buyitseller.interfaces.ProductBottomSheetListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddProductFragment(private val listener: ProductBottomSheetListener) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentAddProductBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener.viewControl(binding)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}