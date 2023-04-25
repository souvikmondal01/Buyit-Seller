package com.buyit.buyitseller.fragments

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyit.buyitseller.databinding.FragmentAddProductCategoryBinding
import com.buyit.buyitseller.interfaces.BottomSheetListener

class AddProductCategoryFragment(private val bottomSheetListener: BottomSheetListener) :
    BottomSheetDialogFragment() {
    private var _binding: FragmentAddProductCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetListener.viewControl(binding)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}