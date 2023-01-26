package com.example.kasirtoko.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.kasirtoko.databinding.FragmentReportBinding
import com.example.kasirtoko.models.TransactionItem
import com.example.kasirtoko.reponses.ErrorResponse
import com.example.kasirtoko.routes.SalesRoute
import com.example.kasirtoko.utils.longToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty


@AndroidEntryPoint
class ReportFragment : Fragment() {

    private val binding: FragmentReportBinding by viewBindings()

    private fun viewBindings(): ReadOnlyProperty<ReportFragment, FragmentReportBinding> {
        TODO("Not yet implemented")
    }

    @Inject
    lateinit var numberFormat: NumberFormat
    @Inject
    lateinit var reportAdapter: ReportAdapter
    @Inject
    lateinit var salesRoute: SalesRoute

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvTotalRevenue.text = numberFormat.format(0)
            ReportAdapter.also {
                val it = null
                rvReports.adapter = it
            }
        }

        getTransaction()
    }

    private fun getTransaction() {
        lifecycleScope.launchWhenCreated {
            try {
                withContext(Dispatchers.IO) {
                    val response = salesRoute.getTransactions()
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        val transactionItems = getTransactionItems()

                        val transactions = responseBody?.data?.map { transaction ->
                            transaction.apply {
                                var items =
                                    transactionItems.filter { it.transaksiId == transaction.id }
                            }
                        } ?: emptyList()


                        withContext(Dispatchers.Main) {
                            binding.tvTotalRevenue.text = numberFormat.format(
                                transactions.filter {
                                    val calendar = Calendar.getInstance()
                                    val currentMonth = calendar[Calendar.MONTH]
                                    calendar.timeInMillis = it.date.time
                                    currentMonth == calendar[Calendar.MONTH]
                                }.sumOf {
                                    it.items.sumOf {
                                        it.priceTotal
                                    }
                                }
                            )
                            reportAdapter.submitList(transactions)
                        }
                    } else {
                        val errorBody = ErrorResponse.fromErrorBody(response.errorBody())
                        withContext(Dispatchers.Main) {
                            longToast(errorBody.message ?: "Show Transactions Failed")
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private suspend fun getTransactionItems(): List<TransactionItem> {
        val response = salesRoute.getTransactionItems()
        if (response.isSuccessful) {
            val responseBody = response.body()

            return responseBody?.data ?: emptyList()
        } else {
            val errorBody = ErrorResponse.fromErrorBody(response.errorBody())
            withContext(Dispatchers.Main) {
                longToast(errorBody.message ?: "Show Transaction Items Failed")
            }
        }
        return emptyList()
    }
}
private val Any.message: String?
    get() {
        TODO("Not yet implemented")
    }
