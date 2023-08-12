package fit.asta.health.onboarding.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.di.IODispatcher
import fit.asta.health.onboarding.data.modal.OnboardingData
import fit.asta.health.onboarding.data.repo.OnboardingRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class OnboardingViewModel
@Inject constructor(
    private val repo: OnboardingRepo,
    @IODispatcher val coroutineContext: CoroutineContext = Dispatchers.IO
) : ViewModel() {


    private val _mutableState = MutableStateFlow<UiState<List<OnboardingData>>>(UiState.Idle)
    val state = _mutableState.asStateFlow()

    val onboardingState = repo
        .getOnboardingShown()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    init {
        getData()
    }

    fun getData() {
        _mutableState.value = UiState.Loading
        viewModelScope.launch(coroutineContext) {
            _mutableState.value = repo.getData().toUiState()
        }
    }

    fun dismissOnboarding() = viewModelScope.launch(coroutineContext) {
        repo.dismissOnboarding()
    }
}
