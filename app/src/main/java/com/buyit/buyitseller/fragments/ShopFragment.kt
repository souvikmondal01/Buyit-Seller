package com.buyit.buyitseller.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyit.buyitseller.R
import com.buyit.buyitseller.adapters.ProductCategoryAdapter
import com.buyit.buyitseller.databinding.FragmentAddProductCategoryBinding
import com.buyit.buyitseller.databinding.FragmentShopBinding
import com.buyit.buyitseller.interfaces.BottomSheetListener
import com.buyit.buyitseller.models.ProductCategory
import com.buyit.buyitseller.repositories.ShopRepositoryImp
import com.buyit.buyitseller.utils.CommonUtils.db
import com.buyit.buyitseller.utils.Constant.ID
import com.buyit.buyitseller.utils.Constant.KEY
import com.buyit.buyitseller.utils.Constant.PRODUCT
import com.buyit.buyitseller.utils.Constant.SHOP
import com.buyit.buyitseller.utils.Constant.SHOP_ID
import com.buyit.buyitseller.utils.Constant.SHOP_NAME
import com.buyit.buyitseller.utils.Constant.SPF
import com.buyit.buyitseller.utils.setStatusBarColor
import com.buyit.buyitseller.utils.toast
import com.buyit.buyitseller.viewmodels.ShopViewModel
import com.buyit.buyitseller.viewmodels.ShopViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ShopFragment : Fragment(), BottomSheetListener {
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

        val list = ArrayList<String>()
//temp
        db.collection(SHOP).document(shopId.toString()).collection(PRODUCT).get()
            .addOnSuccessListener {
                for (i in it.documents) {
                    list.add(i["category"].toString())
                }
                adapter = ProductCategoryAdapter(list)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
                binding.recyclerView.setHasFixedSize(true)
            }


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


}