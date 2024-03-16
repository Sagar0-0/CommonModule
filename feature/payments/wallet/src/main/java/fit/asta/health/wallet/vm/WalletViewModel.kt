package fit.asta.health.wallet.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UserID
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.offers.remote.model.OffersData
import fit.asta.health.offers.repo.OffersRepo
import fit.asta.health.wallet.remote.model.WalletResponse
import fit.asta.health.wallet.repo.WalletRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel
@Inject constructor(
    private val walletRepo: WalletRepo,
    private val offersRepo: OffersRepo,
    @UserID private val userID: String
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<WalletResponse>>(UiState.Loading)
    val state = _state.asStateFlow()

    private val _offersDataState = MutableStateFlow<UiState<List<OffersData>>>(UiState.Loading)
    val offersDataState = _offersDataState.asStateFlow()

    fun getData() {
        _state.update {
            UiState.Loading
        }
        viewModelScope.launch {
            _state.update {
                walletRepo.getData(userID).toUiState()
            }
        }
    }

    fun getOffersData() {
        _offersDataState.update {
            UiState.Loading
        }
        viewModelScope.launch {
            _offersDataState.update {
                offersRepo.getOffers().toUiState()
            }
        }
    }
}