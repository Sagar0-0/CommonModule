package fit.asta.health.subscription.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.subscription.remote.model.SubscriptionResponse
import fit.asta.health.subscription.repo.SubscriptionRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel
@Inject constructor(
    private val subscriptionRepo: SubscriptionRepo,
    @UID private val uid: String
) : ViewModel() {

    private val _state =
        MutableStateFlow<UiState<SubscriptionResponse>>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            _state.value = subscriptionRepo.getData(uid, "india").toUiState()
        }
    }

}