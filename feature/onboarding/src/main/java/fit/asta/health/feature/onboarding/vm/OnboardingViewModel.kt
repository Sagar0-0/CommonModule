package fit.asta.health.feature.onboarding.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.data.onboarding.repo.OnboardingRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
@Inject constructor(
    private val repo: OnboardingRepo
) : ViewModel() {

    private val _mutableState = MutableStateFlow<UiState<List<OnboardingData>>>(UiState.Idle)
    val state = _mutableState.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        _mutableState.value = UiState.Loading
        viewModelScope.launch {
            _mutableState.value = repo.getData().toUiState()
        }
    }

    fun navigateToAuth() = viewModelScope.launch {
        repo.setOnboardingDone()
    }
}
