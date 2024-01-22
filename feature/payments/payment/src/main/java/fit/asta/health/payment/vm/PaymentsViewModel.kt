package fit.asta.health.payment.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.payment.remote.model.OrderResponse
import fit.asta.health.payment.repo.PaymentsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel
@Inject constructor(
    private val paymentRepo: PaymentsRepo,
    private val authRepo: AuthRepo,
    @UID private val uid: String
) : ViewModel() {

    val currentUser = authRepo.getUser()
    private var pId: String? = null

    private val _orderResponseState =
        MutableStateFlow<UiState<OrderResponse>>(UiState.Loading)
    val orderResponseState = _orderResponseState.asStateFlow()

    private val _paymentResponseState =
        MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val paymentResponseState = _paymentResponseState.asStateFlow()


    fun createOrder(data: OrderRequest) = viewModelScope.launch {
        _orderResponseState.value = paymentRepo.createOrder(
            data.copy(
                uId = uid,
                country = "IND"
            )
        ).toUiState()
    }

    fun verifyAndUpdateProfile(paymentId: String? = pId) {
        if (pId == null) pId = paymentId
        _paymentResponseState.value = UiState.Loading
        viewModelScope.launch {
            _paymentResponseState.value =
                paymentRepo.verifyAndUpdateProfile(paymentId!!, authRepo.getUserId() ?: "")
                    .toUiState()
        }
    }

    fun informCancelledPayment() = viewModelScope.launch {
        paymentRepo
            .informCancelledPayment(
                (orderResponseState.value as? UiState.Success)?.data?.orderId ?: "",
                uid
            )
    }

}