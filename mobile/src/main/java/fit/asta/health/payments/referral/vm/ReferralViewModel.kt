package fit.asta.health.payments.referral.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.referral.model.ApplyCodeResponse
import fit.asta.health.payments.referral.model.ReferralDataResponse
import fit.asta.health.payments.referral.repo.ReferralRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ReferralViewModel
@Inject constructor(
    private val referralRepo: ReferralRepo,
    @Named("UId")
    private val uid: String
) : ViewModel() {

    private val _state =
        MutableStateFlow<ResponseState<ReferralDataResponse>>(ResponseState.Loading)
    val state = _state.asStateFlow()

    private val _checkCodeState =
        MutableStateFlow<ResponseState<ApplyCodeResponse>>(ResponseState.Idle)
    val applyCodeState = _checkCodeState.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        _state.value = ResponseState.Loading
        viewModelScope.launch {
            _state.value = referralRepo.getData(uid)
        }
    }

    fun applyCode(code: String) {
        _checkCodeState.value = ResponseState.Loading
        viewModelScope.launch {
            _checkCodeState.value = referralRepo.applyCode(code, uid)
        }
    }
}