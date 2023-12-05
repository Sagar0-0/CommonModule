package fit.asta.health.wallet.vm

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
class WalletViewModel
@Inject constructor(
    private val walletRepo: WalletRepo,
    private val subscriptionRepo: SubscriptionRepo,
    @UID private val uid: String
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<WalletResponse>>(UiState.Loading)
    val state = _state.asStateFlow()

    private val _subscriptionData = MutableStateFlow<UiState<SubscriptionResponse>>(UiState.Loading)
    val subscriptionData = _subscriptionData.asStateFlow()

    fun getData() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            _state.value = walletRepo.getData(uid).toUiState()
        }
    }

    fun getSubscriptionData() {
        _subscriptionData.value = UiState.Loading
        viewModelScope.launch {
            _subscriptionData.value = subscriptionRepo.getData(uid, "IND").toUiState()
        }
    }
}