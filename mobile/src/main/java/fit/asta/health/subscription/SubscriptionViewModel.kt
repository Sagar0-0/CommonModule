package fit.asta.health.subscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.discounts.remote.model.CouponRequest
import fit.asta.health.discounts.remote.model.CouponResponse
import fit.asta.health.discounts.remote.model.ProductType
import fit.asta.health.discounts.repo.CouponsRepo
import fit.asta.health.subscription.remote.model.SubscriptionResponse
import fit.asta.health.subscription.repo.SubscriptionRepo
import fit.asta.health.wallet.remote.model.WalletResponse
import fit.asta.health.wallet.repo.WalletRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel
@Inject constructor(
    private val subscriptionRepo: SubscriptionRepo,
    private val walletRepo: WalletRepo,
    private val couponsRepo: CouponsRepo,
    @UID private val uid: String
) : ViewModel() {

    private val _subscriptionResponseState =
        MutableStateFlow<UiState<SubscriptionResponse>>(UiState.Idle)
    val subscriptionResponseState = _subscriptionResponseState.asStateFlow()

    private val _walletResponseState =
        MutableStateFlow<UiState<WalletResponse>>(UiState.Idle)
    val walletResponseState = _walletResponseState.asStateFlow()

    private val _couponResponseState =
        MutableStateFlow<UiState<CouponResponse>>(UiState.Idle)
    val couponResponseState = _couponResponseState.asStateFlow()

    init {
        getSubscriptionData()
        getWalletData()
    }

    fun applyCouponCode(code: String, productMRP: Double) {
        _couponResponseState.value = UiState.Loading
        viewModelScope.launch {
            _couponResponseState.value = couponsRepo.getCouponCodeDetails(
                CouponRequest(
                    productType = ProductType.SUBSCRIPTION.type,
                    couponCode = code,
                    userId = uid,
                    productMRP = productMRP
                )
            ).toUiState()
        }
    }

    private fun getSubscriptionData() {
        _subscriptionResponseState.value = UiState.Loading
        viewModelScope.launch {
            _subscriptionResponseState.value = subscriptionRepo.getData(uid, "IND").toUiState()
        }
    }

    private fun getWalletData() {
        _walletResponseState.value = UiState.Loading
        viewModelScope.launch {
            _walletResponseState.value = walletRepo.getData(uid).toUiState()
        }
    }

}