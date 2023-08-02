package fit.asta.health.payments.wallet.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.wallet.model.WalletResponse
import fit.asta.health.payments.wallet.repo.WalletRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class WalletViewModel
@Inject constructor(
    private val walletRepo: WalletRepo,
    @Named("UId") private val uid: String
) : ViewModel() {

    private val _state = MutableStateFlow<ResponseState<WalletResponse>>(ResponseState.Loading)
    val state = _state.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        _state.value = ResponseState.Loading
        viewModelScope.launch {
            _state.value = walletRepo.getData(uid)
        }
    }

}