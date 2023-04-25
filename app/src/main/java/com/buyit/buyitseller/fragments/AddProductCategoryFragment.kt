package com.buyit.buyitseller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.buyit.buyitseller.databinding.FragmentAddProductCategoryBinding
import com.buyit.buyitseller.interfaces.CategoryBottomSheetListener
import com.buyit.buyitseller.repositories.ShopRepositoryImp
import com.buyit.buyitseller.viewmodels.ShopViewModel
import com.buyit.buyitseller.viewmodels.ShopViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddProductCategoryFragment(private val bottomSheetListener: CategoryBottomSheetListener) :
    BottomSheetDialogFragment() {
    private var _binding: FragmentAddProductCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ShopViewModel
    private val factory = ShopViewModelFactory(ShopRepositoryImp())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[ShopViewModel::class.java]
        bottomSheetListener.viewControl(binding)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}