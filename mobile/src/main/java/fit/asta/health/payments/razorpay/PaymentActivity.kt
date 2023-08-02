package fit.asta.health.payments.razorpay

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.razorpay.Checkout
import com.razorpay.PayloadHelper
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.razorpay.model.OrderRequest
import fit.asta.health.payments.razorpay.model.OrderResponse
import fit.asta.health.payments.razorpay.model.PaymentResponse
import fit.asta.health.payments.razorpay.vm.PaymentsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class PaymentActivity : ComponentActivity(), PaymentResultWithDataListener {

    private val paymentsViewModel: PaymentsViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()


    companion object {
        private val DATA_KEY = "orderRequest"
        private var onSuccess: () -> Unit = {}
        fun launch(context: Context, orderRequest: OrderRequest, onSuccess: () -> Unit) {
            this.onSuccess = onSuccess
            Intent(context, PaymentActivity::class.java)
                .apply {
                    putExtra(DATA_KEY, orderRequest)
                    context.startActivity(this)
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.getParcelableExtra(DATA_KEY, OrderRequest::class.java)
        paymentsViewModel.createOrder(OrderRequest(subType = "2", durType = "2", type = 1))
        setContent {
            AppTheme {
                ShowPaymentScreen()
            }
        }
    }

    @Composable
    fun ShowPaymentScreen() {
        val orderResponse by paymentsViewModel.orderResponseState.collectAsStateWithLifecycle()
        val paymentResponse by paymentsViewModel.paymentResponseState.collectAsStateWithLifecycle()

        when (orderResponse) {
            ResponseState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingAnimation()
                }
            }

            is ResponseState.Success -> {
                LaunchedEffect(
                    key1 = Unit,
                    block = {
                        try {
                            Checkout.preload(applicationContext)
                            val co = Checkout()
                            co.setKeyID((orderResponse as ResponseState.Success<OrderResponse>).data.data.apiKey)
                            co.setImage(R.drawable.splash_logo)
                            val payloadHelper = PayloadHelper(
                                "INR",
                                0,
                                (orderResponse as ResponseState.Success<OrderResponse>).data.data.orderId
                            ).apply {
                                name = "Asta.fit"
                                description = "Silver Plan 3 month subscription"
                                prefillEmail = authViewModel.currentUser?.email
                                prefillContact = authViewModel.currentUser?.phoneNumber
                                prefillName = authViewModel.currentUser?.name
                                retryEnabled = true
                                rememberCustomer = true
                            }
                            co.open(this@PaymentActivity, payloadHelper.getJson())
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@PaymentActivity,
                                "Error in payment: " + e.message,
                                Toast.LENGTH_LONG
                            ).show()
                            e.printStackTrace()
                        }
                    }
                )
                when (paymentResponse) {
                    ResponseState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Confirming your payment...")
                        }
                    }

                    is ResponseState.Success -> {
                        if ((paymentResponse as ResponseState.Success<PaymentResponse>).data.status.code == 200) {
                            finish()
                            onSuccess()
                        }
                    }

                    is ResponseState.Error -> {
                        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    else -> {}
                }
            }

            else -> {
                AppErrorScreen()
            }

        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Log.d("PAY", "onPaymentSuccess: $p0 ${p1?.paymentId}")
        p1?.paymentId?.let { paymentsViewModel.verifyAndUpdateProfile(it) }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Log.e("PAY", "onPaymentError: $p0 $p1 ${p2?.paymentId}")
        finish()
        onSuccess()
    }
}