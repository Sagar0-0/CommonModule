package fit.asta.health.offers.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.offers.remote.model.OffersData
import fit.asta.health.offers.repo.OffersRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersViewModel
@Inject constructor(
    private val repo: OffersRepo
) : ViewModel() {

    private val _mutableState = MutableStateFlow<UiState<List<OffersData>>>(UiState.Idle)
    val state = _mutableState.asStateFlow()

    fun getData() {
        _mutableState.value = UiState.Loading
        viewModelScope.launch {
            _mutableState.value = repo.getOffers().toUiState()
        }
    }
}
