package fit.asta.health.onboarding.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.onboarding.data.modal.OnboardingData
import fit.asta.health.onboarding.data.repo.OnboardingRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
@Inject constructor(
    private val repo: OnboardingRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {


    private val _mutableState =
        MutableStateFlow<UiState<List<OnboardingData>>>(UiState.Idle)
    val state = _mutableState.asStateFlow()

    private val _onboardingState =
        MutableStateFlow(false)
    val onboardingState = _onboardingState.asStateFlow()

    init {
        getData()
        getOnboardingShown()
    }

    fun getData() {
        _mutableState.value = UiState.Loading
        viewModelScope.launch(dispatcher) {
            repo.getData().collect {
                _mutableState.value = it
            }
        }
    }

    fun setOnboardingShown() = viewModelScope.launch(dispatcher) {
        repo.setOnboardingShown()
    }

    private fun getOnboardingShown() {
        viewModelScope.launch(dispatcher) {
            repo.getOnboardingShown().collect {
                Log.d("ON", "getOnboardingShown: $it")
                _onboardingState.value = it
            }
        }
    }
}