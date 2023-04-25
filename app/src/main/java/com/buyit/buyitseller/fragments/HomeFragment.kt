package com.buyit.buyitseller.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyit.buyitseller.R
import com.buyit.buyitseller.activities.ProfileActivity
import com.buyit.buyitseller.adapters.ShopAdapter
import com.buyit.buyitseller.databinding.FragmentHomeBinding
import com.buyit.buyitseller.interfaces.ShopOnClickListener
import com.buyit.buyitseller.models.ShopModel
import com.buyit.buyitseller.repositories.ShopRepositoryImp
import com.buyit.buyitseller.utils.*
import com.buyit.buyitseller.utils.CommonUtils.db
import com.buyit.buyitseller.utils.Constant.CLOSE
import com.buyit.buyitseller.utils.Constant.DONE
import com.buyit.buyitseller.utils.Constant.ID
import com.buyit.buyitseller.utils.Constant.KEY
import com.buyit.buyitseller.utils.Constant.OPEN
import com.buyit.buyitseller.utils.Constant.SHOP
import com.buyit.buyitseller.utils.Constant.SHOP_NAME
import com.buyit.buyitseller.utils.Constant.SHUT_DOWN
import com.buyit.buyitseller.utils.Constant.STATUS
import com.buyit.buyitseller.utils.Constant.VERIFICATION_STATUS
import com.buyit.buyitseller.viewmodels.ShopViewModel
import com.buyit.buyitseller.viewmodels.ShopViewModelFactory

class HomeFragment : Fragment(), ShopOnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ShopViewModel
    private val factory = ShopViewModelFactory(ShopRepositoryImp())
    private lateinit var adapter: ShopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setStatusBarColor(R.color.orange_700)
        requireActivity().window.decorView.systemUiVisibility =
            View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_createShopFragment)
        }
        binding.ivProfile.setOnClickListener {
            requireActivity().startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }
        viewModel = ViewModelProvider(this, factory)[ShopViewModel::class.java]
        val recyclerOptions = viewModel.fetchShop()
        adapter = ShopAdapter(recyclerOptions, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.startListening()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOpenClick(shop: ShopModel, holder: ShopAdapter.ViewHolder) {
        viewModel.shopStatusUpdate(shop.id.toString(), OPEN)
    }

    override fun onCloseClick(shop: ShopModel, holder: ShopAdapter.ViewHolder) {
        viewModel.shopStatusUpdate(shop.id.toString(), CLOSE)
    }

    override fun onShutDownClick(shop: ShopModel, holder: ShopAdapter.ViewHolder) {
        viewModel.shopStatusUpdate(shop.id.toString(), SHUT_DOWN)
    }

    override fun onDeleteClick(shop: ShopModel, holder: ShopAdapter.ViewHolder) {
        viewModel.shopDelete(shop.id.toString())
    }

    override fun onShopClick(shop: ShopModel, holder: ShopAdapter.ViewHolder) {
        findNavController().navigate(R.id.action_homeFragment_to_shopFragment)
        setFragmentResult(KEY, bundleOf(SHOP_NAME to shop.shopName, ID to shop.id))
    }

    override fun viewUpdate(shop: ShopModel, holder: ShopAdapter.ViewHolder) {
        val btnOpen: Button = holder.binding.btnOpen
        val btnClose: Button = holder.binding.btnClose
        val btnShutDown: Button = holder.binding.btnShutDown
        val ivDot: ImageView = holder.binding.ivDot

        db.collection(SHOP).document(shop.id.toString()).addSnapshotListener { it, _ ->
            if (it!![VERIFICATION_STATUS].toString() == DONE) {
                holder.binding.apply {
                    tvShopStatus.hide()
                    llBtns.show()
                    llDelete.hide()
                }
            }
        }

        viewModel.getShopDetails(shop.id.toString()).addOnSuccessListener {
            when (it!![STATUS].toString()) {
                OPEN -> {
                    btnOpen.setBackgroundColour(R.color.green)
                    btnClose.setBackgroundColour(R.color.red_light)
                    btnShutDown.setBackgroundColour(R.color.grey_light)
                    ivDot.setColourFilter(R.color.green)
                }
                CLOSE -> {
                    btnOpen.setBackgroundColour(R.color.green_light)
                    btnClose.setBackgroundColour(R.color.red)
                    btnShutDown.setBackgroundColour(R.color.grey_light)
                    ivDot.setColourFilter(R.color.red)
                }
                else -> {
                    btnOpen.setBackgroundColour(R.color.green_light)
                    btnClose.setBackgroundColour(R.color.red_light)
                    btnShutDown.setBackgroundColour(R.color.grey)
                    ivDot.setColourFilter(R.color.grey)
                }
            }
        }
    }


}