package com.example.kasirtoko.ui.product

import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kasirtoko.R
import com.example.kasirtoko.databinding.FragmentProductBinding
import com.example.kasirtoko.models.Product
import com.example.kasirtoko.utils.longToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import io.github.pengdst.salescashier.ui.product.ProductFragmentDirections
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private val binding: FragmentProductBinding by viewBindings()

    private fun viewBindings(): ReadOnlyProperty<ProductFragment, FragmentProductBinding> {
        TODO("Not yet implemented")
    }

    private val productViewModel: ProductViewModel by viewModels()

    @Inject lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        productViewModel.productsViewModel.observe(viewLifecycleOwner) {
            when(it) {
                is ProductViewModel.State.Failed -> longToast(it.message)
                is ProductViewModel.State.Loading -> Unit
                is ProductViewModel.State.Success -> updateProducts(it.data)
                is ProductViewModel.State.SuccessDelete -> updateProducts(it.data)
            }
        }

        productAdapter.setOnItemClickListener { view, model, _ ->
            when(view.id) {
                R.id.btn_delete -> MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Delete Product")
                    .setMessage("Anda ingin menghapus ${model.nama}?")
                    .setPositiveButton("Delete"
                    ) { dialog, _ ->
                        productViewModel.deleteProduct(model.id ?: -1)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel"
                    ) { dialog, _ -> dialog.dismiss() }
                    .show()
                else -> {
                    findNavController().navigate(ProductFragmentDirections.actionProductFragmentToAddProductFragment(model))
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTotalProduct.text = TextUtils.concat("0", " ",
            SpannableString("ITEM").apply {
                setSpan(RelativeSizeSpan(0.5f), 0, 4, 0)
            }
        )
        with(binding) {
            //rvProducts.adapter = productAdapter

            btnAdd.setOnClickListener {
                findNavController().navigate(ProductFragmentDirections
                    .actionProductFragmentToAddProductFragment())
            }
        }

        productViewModel.getProducts()
    }

    private fun updateProducts(products: List<Product>) {
        binding.tvTotalProduct.text = TextUtils.concat(products.size.toString(), " ",
            SpannableString("ITEM").apply {
                setSpan(RelativeSizeSpan(0.5f), 0, 4, 0)
            }
        )
        productAdapter.submitList(products)
    }
}