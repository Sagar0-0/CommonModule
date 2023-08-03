package fit.asta.health.payments.pay.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.pay.model.OrderRequest
import fit.asta.health.payments.pay.model.OrderResponse
import fit.asta.health.payments.pay.model.PaymentResponse
import fit.asta.health.payments.pay.repo.PaymentsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PaymentsViewModel
@Inject constructor(
    private val repo: PaymentsRepo,
    @Named("UId") private val uid: String,
) : ViewModel() {

    private val _orderResponseState =
        MutableStateFlow<ResponseState<OrderResponse>>(ResponseState.Loading)
    val orderResponseState = _orderResponseState.asStateFlow()

    private val _paymentResponseState =
        MutableStateFlow<ResponseState<PaymentResponse>>(ResponseState.Idle)
    val paymentResponseState = _paymentResponseState.asStateFlow()


    fun createOrder(data: OrderRequest) = viewModelScope.launch {
        _orderResponseState.value = repo.createOrder(
            data.copy(
                uId = uid,
                country = "india",
                type = 1
            )
        )
    }

    fun verifyAndUpdateProfile(paymentId: String) {
        _paymentResponseState.value = ResponseState.Loading
        viewModelScope.launch {
            _paymentResponseState.value = repo.verifyAndUpdateProfile(paymentId, uid)
        }
    }

}