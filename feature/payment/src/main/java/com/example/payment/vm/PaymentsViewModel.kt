package com.example.payment.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.UiState
import com.example.common.utils.toUiState
import com.example.payment.model.OrderRequest
import com.example.payment.model.OrderResponse
import com.example.payment.model.PaymentResponse
import com.example.payment.repo.PaymentsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.data.repo.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel
@Inject constructor(
    private val paymentRepo: PaymentsRepo,
    private val authRepo: AuthRepo
) : ViewModel() {

    val currentUser = authRepo.getUser()

    private val _orderResponseState =
        MutableStateFlow<UiState<OrderResponse>>(UiState.Loading)
    val orderResponseState = _orderResponseState.asStateFlow()

    private val _paymentResponseState =
        MutableStateFlow<UiState<PaymentResponse>>(UiState.Idle)
    val paymentResponseState = _paymentResponseState.asStateFlow()


    fun createOrder(data: OrderRequest) = viewModelScope.launch {
        _orderResponseState.value = paymentRepo.createOrder(
            data.copy(
                uId = authRepo.getUserId() ?: "",
                country = "india",
                type = 1
            )
        ).toUiState()
    }

    fun verifyAndUpdateProfile(paymentId: String) {
        _paymentResponseState.value = UiState.Loading
        viewModelScope.launch {
            _paymentResponseState.value =
                paymentRepo.verifyAndUpdateProfile(paymentId, authRepo.getUserId() ?: "")
                    .toUiState()
        }
    }

}