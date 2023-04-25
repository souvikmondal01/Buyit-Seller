package com.buyit.buyitseller.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.buyit.buyitseller.R
import com.buyit.buyitseller.databinding.FragmentCreateShopBinding
import com.buyit.buyitseller.models.ShopModel
import com.buyit.buyitseller.repositories.ShopRepositoryImp
import com.buyit.buyitseller.utils.Constant.PENDING
import com.buyit.buyitseller.utils.Constant.SHUT_DOWN
import com.buyit.buyitseller.utils.toast
import com.buyit.buyitseller.viewmodels.ShopViewModel
import com.buyit.buyitseller.viewmodels.ShopViewModelFactory

class CreateShopFragment : Fragment() {
    private var _binding: FragmentCreateShopBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ShopViewModel
    private val factory = ShopViewModelFactory(ShopRepositoryImp())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[ShopViewModel::class.java]
        binding.btnAdd.setOnClickListener {
            viewModel.createShop(
                ShopModel(
                    "",
                    binding.etShopName.text.toString(),
                    binding.etShopCategory.text.toString(), "", ""
                )
            )
            activity?.onBackPressed()
        }
        viewModel.getCategory()
        viewModel.category.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it)
            binding.etShopCategory.setAdapter(adapter)
        }
        binding.etShopCategory.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) binding.etShopCategory.showDropDown()
            view.setOnClickListener {
                binding.etShopCategory.showDropDown()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}