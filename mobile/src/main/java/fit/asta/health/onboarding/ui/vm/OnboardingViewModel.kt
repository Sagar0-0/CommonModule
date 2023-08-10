package fit.asta.health.onboarding.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.R
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.onboarding.data.modal.OnboardingData
import fit.asta.health.onboarding.data.repo.OnboardingRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
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
        viewModelScope.launch(dispatcher) {
            _mutableState.value = repo.getData().mapToUiState()
        }
    }

    fun dismissOnboarding() = viewModelScope.launch(dispatcher) {
        repo.dismissOnboarding()
    }
}


fun getStringFromException(e: Exception): Int {
    return when (e.message) {
        else -> R.string.unknown_error
    }
}
fun <T> ResponseState<T>.mapToUiState() : UiState<T> {
    return when(this){
        is ResponseState.Success->{
            UiState.Success(this.data)
        }
        is ResponseState.Error->{
            UiState.Error(getStringFromException(this.error))
        }
        else->{ UiState.Idle }
    }
}