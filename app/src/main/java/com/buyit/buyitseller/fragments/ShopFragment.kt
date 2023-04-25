package com.buyit.buyitseller.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.buyit.buyitseller.R
import com.buyit.buyitseller.adapters.ProductCategoryAdapter
import com.buyit.buyitseller.databinding.FragmentAddProductCategoryBinding
import com.buyit.buyitseller.databinding.FragmentShopBinding
import com.buyit.buyitseller.interfaces.CategoryBottomSheetListener
import com.buyit.buyitseller.interfaces.ProductCategoryListener
import com.buyit.buyitseller.models.ProductCategory
import com.buyit.buyitseller.repositories.ShopRepositoryImp
import com.buyit.buyitseller.utils.CommonUtils.db
import com.buyit.buyitseller.utils.Constant.PRODUCT
import com.buyit.buyitseller.utils.Constant.PRODUCT_CATEGORY_ID
import com.buyit.buyitseller.utils.Constant.PRODUCT_CATEGORY_NAME
import com.buyit.buyitseller.utils.Constant.SHOP
import com.buyit.buyitseller.utils.Constant.SHOP_ID
import com.buyit.buyitseller.utils.Constant.SHOP_NAME
import com.buyit.buyitseller.utils.Constant.SPF
import com.buyit.buyitseller.utils.Constant.SPF_CATEGORY
import com.buyit.buyitseller.utils.setStatusBarColor
import com.buyit.buyitseller.viewmodels.ShopViewModel
import com.buyit.buyitseller.viewmodels.ShopViewModelFactory

class ShopFragment : Fragment(), CategoryBottomSheetListener, ProductCategoryListener {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ShopViewModel
    private val factory = ShopViewModelFactory(ShopRepositoryImp())
    private lateinit var adapter: ProductCategoryAdapter
    private val bottomSheetDialog = AddProductCategoryFragment(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        setStatusBarColor(R.color.white)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[ShopViewModel::class.java]

        val sharedPreferences =
            requireActivity().getSharedPreferences(SPF, Context.MODE_PRIVATE)
        val shopName = sharedPreferences.getString(SHOP_NAME, "")
        val shopId = sharedPreferences.getString(SHOP_ID, "")
        binding.tvShopName.text = shopName.toString()
        binding.ivDelete.setOnClickListener {
            viewModel.shopDelete(shopId.toString())
            activity?.onBackPressed()
        }

        binding.cvBackArrow.setOnClickListener {
            activity?.onBackPressed()
        }

        val query = db.collection(SHOP).document(shopId.toString()).collection(PRODUCT)
        adapter = ProductCategoryAdapter(viewModel.fetchProductCategory(query), this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter.startListening()

        binding.btnAddProductCategory.setOnClickListener {
            bottomSheetDialog.show(childFragmentManager, "")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun viewControl(binding: FragmentAddProductCategoryBinding) {
        binding.btnAddProductCategory.setOnClickListener {
            val sharedPreferences =
                requireActivity().getSharedPreferences(SPF, Context.MODE_PRIVATE)
            val shopId = sharedPreferences.getString(SHOP_ID, "")
            val category = binding.etProductCategory.text.toString()
            viewModel.addProductCategory(shopId.toString(), ProductCategory("", category))
            viewModel.msg.observe(viewLifecycleOwner) {
                Log.e("MSG", it)
                if (it == "added successfully") {
                    binding.etProductCategory.text.clear()
                }
            }
            bottomSheetDialog.dialog?.dismiss()
        }
    }

    override fun productCategoryOnClick(
        holder: ProductCategoryAdapter.ViewHolder,
        position: Int,
        model: ProductCategory
    ) {

        findNavController().navigate(R.id.action_shopFragment_to_productFragment)
        val sharedPreferences =
            requireActivity().getSharedPreferences(SPF_CATEGORY, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(PRODUCT_CATEGORY_ID, model.id)
        editor.putString(PRODUCT_CATEGORY_NAME, model.category)
        editor.apply()
    }

}