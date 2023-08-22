package fit.asta.health.wallet.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.auth.di.UID
import fit.asta.health.wallet.model.WalletResponse
import fit.asta.health.wallet.repo.WalletRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel
@Inject constructor(
    private val walletRepo: WalletRepo,
    @UID private val uid: String
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<WalletResponse>>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            _state.value = walletRepo.getData(uid).toUiState()
        }
    }

}