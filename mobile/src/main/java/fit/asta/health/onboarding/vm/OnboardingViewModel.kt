package fit.asta.health.onboarding.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.network.NetworkHelper
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

    private val _mutableState =
        MutableStateFlow<ResponseState<List<OnboardingData>>>(ResponseState.Loading)
    val state = _mutableState.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        _mutableState.value = ResponseState.Loading
        if (!networkHelper.isConnected()) {
            _mutableState.value = ResponseState.NoInternet
        } else {
            viewModelScope.launch { _mutableState.value = repo.getData() }
        }
    }
}