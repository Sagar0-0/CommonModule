package fit.asta.health.discounts.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.discounts.remote.model.DiscountsData
import fit.asta.health.discounts.repo.DiscountsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscountsViewModel
@Inject constructor(
    private val repo: DiscountsRepo
) : ViewModel() {

    private val _mutableState = MutableStateFlow<UiState<List<DiscountsData>>>(UiState.Idle)
    val state = _mutableState.asStateFlow()

    fun getData() {
        _mutableState.value = UiState.Loading
        viewModelScope.launch {
            _mutableState.value = repo.getData().toUiState()
        }
    }
}
