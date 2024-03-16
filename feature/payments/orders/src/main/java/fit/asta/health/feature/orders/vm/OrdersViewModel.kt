package fit.asta.health.feature.orders.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UserID
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.orders.remote.OrderId
import fit.asta.health.data.orders.remote.UserId
import fit.asta.health.data.orders.remote.model.OrderData
import fit.asta.health.data.orders.remote.model.OrderDetailData
import fit.asta.health.data.orders.repo.OrdersRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel
@Inject constructor(
    private val ordersRepo: OrdersRepo,
    @UserID private val userID: UserId
) : ViewModel() {

    private val _ordersState = MutableStateFlow<UiState<List<OrderData>>>(UiState.Idle)
    val ordersState = _ordersState.asStateFlow()

    private val _orderDetailState = MutableStateFlow<UiState<OrderDetailData>>(UiState.Idle)
    val orderDetailState = _orderDetailState.asStateFlow()

    fun getOrders() {
        _ordersState.value = UiState.Loading
        viewModelScope.launch {
            _ordersState.value = ordersRepo.getOrders(userID).toUiState()
        }
    }

    fun getOrderDetail(orderId: OrderId) {
        _orderDetailState.value = UiState.Loading
        viewModelScope.launch {
            _orderDetailState.value = ordersRepo.getOrderDetail(orderId).toUiState()
        }
    }
}