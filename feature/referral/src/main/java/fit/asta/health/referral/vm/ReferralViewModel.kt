package fit.asta.health.referral.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.referral.remote.model.ReferralDataResponse
import fit.asta.health.referral.repo.ReferralRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReferralViewModel
@Inject constructor(
    private val referralRepo: ReferralRepo,
    @UID private val uid: String
) : ViewModel() {

    private val _state =
        MutableStateFlow<UiState<ReferralDataResponse>>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            _state.value = referralRepo.getData(uid).toUiState()
        }
    }
}