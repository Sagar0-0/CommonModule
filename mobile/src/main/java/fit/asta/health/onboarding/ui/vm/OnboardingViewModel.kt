package fit.asta.health.onboarding.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.data.repo.AuthRepo
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.onboarding.data.model.OnboardingData
import fit.asta.health.onboarding.data.repo.OnboardingRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
@Inject constructor(
    private val repo: OnboardingRepo,
    private val authRepo: AuthRepo
) : ViewModel() {

    private val _mutableState = MutableStateFlow<UiState<List<OnboardingData>>>(UiState.Idle)
    val state = _mutableState.asStateFlow()

    val onboardingShown: StateFlow<Boolean> =
        repo.userPreferences
            .map { it.onboardingShown }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false,
            )

    fun isAuthenticated(): Boolean {
        return authRepo.isAuthenticated()
    }

    init {
        getData()
    }

    fun getData() {
        _mutableState.value = UiState.Loading
        viewModelScope.launch {
            _mutableState.value = repo.getData().toUiState()
        }
    }

    fun dismissOnboarding() = viewModelScope.launch {
        repo.dismissOnboarding()
    }
}
