package com.example.kasirtoko.ui.product.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.kasirtoko.R
import com.example.kasirtoko.databinding.FragmentAddProductBinding
import com.example.kasirtoko.models.Product
import com.example.kasirtoko.requests.CreateProductRequest
import com.example.kasirtoko.requests.UpdateProductRequest
import com.example.kasirtoko.ui.product.ProductViewModel
import com.example.kasirtoko.utils.longToast
import com.example.kasirtoko.utils.shortToast
import dagger.hilt.android.AndroidEntryPoint
import io.github.pengdst.salescashier.ui.product.add.AddProductFragmentArgs
import kotlin.properties.ReadOnlyProperty

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private val binding: FragmentAddProductBinding by viewBindings()

    private fun viewBindings(): ReadOnlyProperty<AddProductFragment, FragmentAddProductBinding> {
        TODO("Not yet implemented")
    }

    private val productViewModel: ProductViewModel by viewModels()

    private val args: AddProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.productsViewModel.observe(viewLifecycleOwner) {
            binding.btnSave.isEnabled = true
            when(it) {
                is ProductViewModel.State.Failed -> longToast(it.message)
                is ProductViewModel.State.Loading -> binding.btnSave.isEnabled = false
                is ProductViewModel.State.SuccessCreate -> {
                    shortToast(it.message)
                    requireActivity().onBackPressed()
                }
                is ProductViewModel.State.SuccessUpdate -> shortToast(it.message)
                is ProductViewModel.State.SuccessDelete -> Unit
                is ProductViewModel.State.Success -> Unit
            }
        }

        showProducts(args.product)
        with(binding) {
            btnSave.setOnClickListener {
                if (args.product == null) {
                    val request = CreateProductRequest(
                        harga = tilPrice.editText?.text.toString().toIntOrNull(),
                        nama = tilName.editText?.text.toString().trim(),
                        stock = tilStock.editText?.text.toString().toIntOrNull()
                    )
                    productViewModel.createProduct(request)
                }
                else {
                    val request = UpdateProductRequest(
                        harga = tilPrice.editText?.text.toString().toIntOrNull(),
                        nama = tilName.editText?.text.toString().trim(),
                        stock = tilStock.editText?.text.toString().toIntOrNull()
                    )
                    productViewModel.updateProduct(args.product?.id, request)
                }
            }
        }
    }

    private fun showProducts(product: Product?) {
        product?.let {
            with(binding) {
                tilName.editText?.setText(it.nama)
                tilPrice.editText?.setText(it.harga?.toInt().toString())
                tilStock.editText?.setText(it.stock.toString())

                btnSave.text = getString(R.string.save)
            }
        }
    }

}