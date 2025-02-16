package fit.asta.health.payment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import fit.asta.health.common.utils.UiState
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.payment.remote.model.OrderRequestType
import fit.asta.health.payment.remote.model.OrderResponse
import fit.asta.health.payment.vm.PaymentsViewModel
import fit.asta.health.resources.drawables.R

@AndroidEntryPoint
class PaymentActivity : ComponentActivity(), PaymentResultWithDataListener {

    private val paymentsViewModel: PaymentsViewModel by viewModels()

    companion object {
        private const val DATA_KEY = "orderRequest"
        private var onSuccess: () -> Unit = {}
        private var onFailure: () -> Unit = {}
        fun launch(
            context: Context,
            orderRequest: OrderRequest,
            onSuccess: () -> Unit,
            onFailure: () -> Unit
        ) {
            this.onSuccess = onSuccess
            this.onFailure = onFailure
            Intent(context, PaymentActivity::class.java)
                .apply {
                    putExtra(DATA_KEY, orderRequest)
                    context.startActivity(this)
                }
        }

        fun launch(context: Context, amount: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
            this.onSuccess = onSuccess
            this.onFailure = onFailure
            val orderRequest = OrderRequest(
                amtDetails = OrderRequest.AmtDetails(
                    amt = amount.toDouble()
                ),
                type = OrderRequestType.AddInWallet.code
            )
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
        val data = intent.getParcelableExtra(DATA_KEY, OrderRequest::class.java)!!
        paymentsViewModel.createOrder(data)
        setContent {
            BackHandler(
                enabled = true
            ) {
                finish()
            }
            AppTheme {
                val orderResponse by paymentsViewModel.orderResponseState.collectAsStateWithLifecycle()
                val paymentResponse by paymentsViewModel.paymentResponseState.collectAsStateWithLifecycle()
                ShowPaymentScreen(orderResponse, paymentResponse)
            }
        }
    }

    @Composable
    fun ShowPaymentScreen(
        orderResponse: UiState<OrderResponse>,
        paymentResponse: UiState<String>
    ) {
        when (orderResponse) {
            UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    AppDotTypingAnimation()
                }
            }

            is UiState.Success -> {
                LaunchedEffect(
                    key1 = Unit,
                    block = {
                        try {
                            Checkout.preload(applicationContext)
                            val co = Checkout()
                            co.setKeyID(orderResponse.data.apiKey)
                            co.setImage(R.drawable.ic_launcher)
                            val payloadHelper = PayloadHelper(
                                "INR",
                                0,
                                orderResponse.data.orderId
                            ).apply {
                                name = "Asta.fit"
                                description = "Silver Plan 3 month subscription"
                                prefillEmail = paymentsViewModel.currentUser?.email
                                prefillContact = paymentsViewModel.currentUser?.phoneNumber
                                prefillName = paymentsViewModel.currentUser?.name
                                retryEnabled = true
                                rememberCustomer = true
                            }
                            co.open(this@PaymentActivity, payloadHelper.getJson())
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@PaymentActivity,
                                "ErrorMessage in payment: " + e.message,
                                Toast.LENGTH_LONG
                            ).show()
                            e.printStackTrace()
                        }
                    }
                )
                AppUiStateHandler(
                    uiState = paymentResponse,
                    onLoading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            TitleTexts.Level2(text = "Confirming your payment...")
                        }
                    },
                    onErrorMessage = {
                        finish()
                        onFailure()
                    },
                    onErrorRetry = {
                        paymentsViewModel.verifyAndUpdateProfile()
                    }
                ) {
                    finish()
                    onSuccess()
                }
            }

            else -> {
                AppInternetErrorDialog()
            }
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Log.d("PAY", "onPaymentSuccess: $p0 ${p1?.paymentId}")
        p1?.paymentId?.let { paymentsViewModel.verifyAndUpdateProfile(it) }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Log.e("PAY", "onPaymentError: $p0 $p1 ${p2?.paymentId}")
        paymentsViewModel.informCancelledPayment()
    }
}