package com.example.referral.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.di.UID
import com.example.referral.model.ApplyCodeResponse
import com.example.referral.model.ReferralDataResponse
import com.example.referral.repo.ReferralRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
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

    private val _checkCodeState =
        MutableStateFlow<UiState<ApplyCodeResponse>>(UiState.Idle)
    val applyCodeState = _checkCodeState.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            _state.value = referralRepo.getData(uid).toUiState()
        }
    }

    fun applyCode(code: String) {
        _checkCodeState.value = UiState.Loading
        viewModelScope.launch {
            _checkCodeState.value = referralRepo.applyCode(code, uid).toUiState()
        }
    }
}