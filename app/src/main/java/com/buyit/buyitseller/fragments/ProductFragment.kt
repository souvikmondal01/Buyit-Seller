package com.buyit.buyitseller.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.buyit.buyitseller.databinding.FragmentAddProductBinding
import com.buyit.buyitseller.databinding.FragmentProductBinding
import com.buyit.buyitseller.interfaces.ProductBottomSheetListener
import com.buyit.buyitseller.models.Product
import com.buyit.buyitseller.repositories.ShopRepositoryImp
import com.buyit.buyitseller.utils.Constant
import com.buyit.buyitseller.utils.Constant.PRODUCT_CATEGORY_ID
import com.buyit.buyitseller.utils.Constant.PRODUCT_CATEGORY_NAME
import com.buyit.buyitseller.utils.Constant.SPF_CATEGORY
import com.buyit.buyitseller.utils.Constant.SUCCESS
import com.buyit.buyitseller.viewmodels.ShopViewModel
import com.buyit.buyitseller.viewmodels.ShopViewModelFactory

class ProductFragment : Fragment(), ProductBottomSheetListener {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private val bottomSheetDialog = AddProductFragment(this)
    private lateinit var viewModel: ShopViewModel
    private val factory = ShopViewModelFactory(ShopRepositoryImp())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, factory)[ShopViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvBackArrow.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnAddProduct.setOnClickListener {
            bottomSheetDialog.show(childFragmentManager, "")
        }
        val sharedPreferences =
            requireActivity().getSharedPreferences(SPF_CATEGORY, Context.MODE_PRIVATE)
        val productCategoryName = sharedPreferences.getString(PRODUCT_CATEGORY_NAME, "")
        binding.tvCategoryName.text = productCategoryName

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun viewControl(binding: FragmentAddProductBinding) {
        binding.btnAddProduct.setOnClickListener {
            val productName = binding.etProductName.text.toString()
            val productPrice = binding.etProductPrice.text.toString()
            val productQuantity = binding.etProductQuantity.text.toString()
            val productUnit = binding.etProductUnit.text.toString()
            val productTotalCount = binding.etProductTotal.text.toString()

            val spf =
                requireActivity().getSharedPreferences(Constant.SPF, Context.MODE_PRIVATE)
            val shopId = spf.getString(Constant.SHOP_ID, "")
            val sharedPreferences =
                requireActivity().getSharedPreferences(SPF_CATEGORY, Context.MODE_PRIVATE)
            val productCategoryId = sharedPreferences.getString(PRODUCT_CATEGORY_ID, "")

            val product = Product(
                "",
                productName,
                productPrice,
                productQuantity,
                productUnit,
                productTotalCount
            )

            viewModel.addProduct(shopId.toString(), productCategoryId.toString(), product)
            viewModel.msg.observe(viewLifecycleOwner) {
                if (it == SUCCESS) {
                    bottomSheetDialog.dismiss()
                    binding.apply {
                        etProductName.text.clear()
                        etProductPrice.text.clear()
                        etProductQuantity.text.clear()
                        etProductUnit.text.clear()
                        etProductTotal.text.clear()
                    }
                }
            }
        }
    }
}