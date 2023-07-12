package fit.asta.health.onboarding.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.network.NetworkHelper
import fit.asta.health.network.data.ApiResponse
import fit.asta.health.onboarding.modal.OnboardingData
import fit.asta.health.onboarding.repo.OnboardingRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
@Inject constructor(
    private val repo: OnboardingRepo,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _mutableState = MutableStateFlow<OnboardingGetState>(OnboardingGetState.Loading)
    val state = _mutableState.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        if (!networkHelper.isConnected()) {
            _mutableState.value = OnboardingGetState.NoInternet
        } else {
            viewModelScope.launch {
                when (val result = repo.getData()) {
                    is ApiResponse.Error -> {
                        _mutableState.value =
                            OnboardingGetState.Error(result.exception.fillInStackTrace())
                    }

                    is ApiResponse.HttpError -> {
                        _mutableState.value = OnboardingGetState.Empty
                    }

                    is ApiResponse.Success -> {
                        _mutableState.value = OnboardingGetState.Success(
                            result.data
                        )
                    }
                }
            }
        }
    }
}

sealed class OnboardingGetState {
    object Loading : OnboardingGetState()
    object Empty : OnboardingGetState()
    object NoInternet : OnboardingGetState()
    class Error(val error: Throwable) : OnboardingGetState()
    class Success(val data: List<OnboardingData>) : OnboardingGetState()
}