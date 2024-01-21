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
import fit.asta.health.offers.remote.model.OffersData
import fit.asta.health.offers.repo.OffersRepo
import fit.asta.health.subscription.remote.model.SubscriptionCategoryData
import fit.asta.health.subscription.remote.model.SubscriptionDurationsData
import fit.asta.health.subscription.remote.model.SubscriptionFinalAmountData
import fit.asta.health.subscription.repo.SubscriptionRepo
import fit.asta.health.wallet.remote.model.WalletResponse
import fit.asta.health.wallet.repo.WalletRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel
@Inject constructor(
    private val subscriptionRepo: SubscriptionRepo,
    private val offersRepo: OffersRepo,
    private val walletRepo: WalletRepo,
    private val couponsRepo: CouponsRepo,
    @UID private val uid: String
) : ViewModel() {

    private val _subscriptionCategoryDataState =
        MutableStateFlow<UiState<List<SubscriptionCategoryData>>>(UiState.Loading)
    val subscriptionCategoryDataState = _subscriptionCategoryDataState.asStateFlow()

    private val _subscriptionDurationDataState =
        MutableStateFlow<UiState<SubscriptionDurationsData>>(UiState.Idle)
    val subscriptionDurationDataState = _subscriptionDurationDataState.asStateFlow()

    private val _offersDataState = MutableStateFlow<UiState<List<OffersData>>>(UiState.Loading)
    val offersDataState = _offersDataState.asStateFlow()

    private val _subscriptionFinalPaymentState =
        MutableStateFlow<UiState<SubscriptionFinalAmountData>>(UiState.Loading)
    val subscriptionFinalPaymentState = _subscriptionFinalPaymentState.asStateFlow()

    private val _walletResponseState =
        MutableStateFlow<UiState<WalletResponse>>(UiState.Idle)
    val walletResponseState = _walletResponseState.asStateFlow()

    private val _couponResponseState =
        MutableStateFlow<UiState<CouponResponse>>(UiState.Idle)
    val couponResponseState = _couponResponseState.asStateFlow()

    fun applyCouponCode(code: String, productMRP: Double) {
        _couponResponseState.value = UiState.Loading
        viewModelScope.launch {
            _couponResponseState.update {
                couponsRepo.getCouponCodeDetails(
                    CouponRequest(
                        productType = ProductType.SUBSCRIPTION.type,
                        couponCode = code,
                        userId = uid,
                        productMRP = productMRP
                    )
                ).toUiState()
            }
        }
    }

    fun getOffersData() {
        _offersDataState.value = UiState.Loading
        viewModelScope.launch {
            _offersDataState.update {
                offersRepo.getOffers().toUiState()
            }
        }
    }

    fun getSubscriptionCategoryData() {
        _subscriptionCategoryDataState.value = UiState.Loading
        viewModelScope.launch {
            _subscriptionCategoryDataState.update {
                subscriptionRepo.getSubscriptionData().toUiState()
            }
        }
    }

    fun getSubscriptionDurationData(categoryId: String) {
        _subscriptionDurationDataState.value = UiState.Loading
        viewModelScope.launch {
            _subscriptionDurationDataState.update {
                subscriptionRepo.getSubscriptionDurationsData(categoryId).toUiState()
            }
        }
    }

    fun getSubscriptionFinalAmountData(categoryId: String, productId: String) {
        _subscriptionFinalPaymentState.value = UiState.Loading
        viewModelScope.launch {
            _subscriptionFinalPaymentState.update {
                subscriptionRepo.getFinalAmountData(
                    type = "1",//For subscription
                    categoryId = categoryId,
                    productId = productId
                ).toUiState()
            }
        }
    }


    fun getWalletData() {
        _walletResponseState.value = UiState.Loading
        viewModelScope.launch {
            _walletResponseState.update {
                walletRepo.getData(uid).toUiState()
            }
        }
    }

    fun resetCouponState() {
        _couponResponseState.value = UiState.Idle
    }

}