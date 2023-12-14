package fit.asta.health.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
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
    @UID private val uid: String
) : ViewModel() {

    private val _subscriptionResponseState =
        MutableStateFlow<UiState<SubscriptionResponse>>(UiState.Idle)
    val subscriptionResponseState = _subscriptionResponseState.asStateFlow()

    private val _walletResponseState =
        MutableStateFlow<UiState<WalletResponse>>(UiState.Idle)
    val walletResponseState = _walletResponseState.asStateFlow()

    init {
        getSubscriptionData()
        getWalletData()
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