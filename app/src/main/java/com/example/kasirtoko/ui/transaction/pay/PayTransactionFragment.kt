package com.example.kasirtoko.ui.transaction.pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.kasirtoko.databinding.FragmentPayTransactionBinding
import com.example.kasirtoko.requests.CreateTransactionRequest
import com.example.kasirtoko.ui.transaction.TransactionViewModel
import com.example.kasirtoko.utils.longToast
import com.example.kasirtoko.utils.shortToast
import dagger.hilt.android.AndroidEntryPoint
import io.github.pengdst.salescashier.ui.transaction.pay.PayTransactionFragmentArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.NumberFormat
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty



@AndroidEntryPoint
class PayTransactionFragment : Fragment() {

    private var sumOfPrice: Double = 0.0
    private val binding: FragmentPayTransactionBinding by viewBindings()

    private fun viewBindings(): ReadOnlyProperty<PayTransactionFragment, FragmentPayTransactionBinding> {
        TODO("Not yet implemented")
    }

    private val args: PayTransactionFragmentArgs by navArgs()

    private val transactionViewModel: TransactionViewModel by viewModels()

    @Inject
    lateinit var numberFormat: NumberFormat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        transactionViewModel.productsViewModel.observe(viewLifecycleOwner) {
            when(it) {
                is TransactionViewModel.State.Loading -> Unit
                is TransactionViewModel.State.SuccessCreateItem -> shortToast(it.message)
                is TransactionViewModel.State.SuccessPayTransaction -> {
                    shortToast(it.message)
                    requireActivity().onBackPressed()
                }
                is TransactionViewModel.State.Failed -> longToast(it.message)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            sumOfPrice = args.productTransactions.toList().sumOf {
                val it = null
                it.amount (it.product.harga ?: 0.0)
            }
            tvTotalPay.text = numberFormat.format(sumOfPrice)

            tilPay.editText?.doOnTextChanged { text, _, _, _ ->
                text.toString().toDoubleOrNull()?.let { payBills ->
                    val returnMoney = payBills - sumOfPrice
                    labelKembalian.text = if (returnMoney < 0) "Uang Anda Kurang" else "Kembalian"
                    tvKembalian.text = numberFormat.format(returnMoney)
                }
            }

            btnPay.setOnClickListener {
                payTransaction()
            }
        }
    }

    private fun payTransaction() {
        binding.btnPay.isEnabled = false
        lifecycleScope.launchWhenResumed {
            try {
                withContext(Dispatchers.IO) {
                    val createTransactionRequest = CreateTransactionRequest(
                        total = args.productTransactions.size
                    )
                    transactionViewModel.payTransaction(createTransactionRequest, args.productTransactions.toList())
                }
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                binding.btnPay.isEnabled = true
            }
        }
    }
}

private fun Nothing?.amount(d: Double) {
    TODO("Not yet implemented")
}

private val Any.harga: Double?
    get() {
        TODO("Not yet implemented")
    }
private val Nothing?.product: Any
    get() {
        TODO("Not yet implemented")
    }
private val Nothing?.amount: Any
    get() {
        TODO("Not yet implemented")
    }