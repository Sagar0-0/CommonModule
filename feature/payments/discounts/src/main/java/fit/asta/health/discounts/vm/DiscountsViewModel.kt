package fit.asta.health.discounts.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.discounts.repo.CouponsRepo
import javax.inject.Inject

@HiltViewModel
class DiscountsViewModel
@Inject constructor(
    private val repo: CouponsRepo
) : ViewModel() {

//    private val _mutableState = MutableStateFlow<UiState<CouponResponse>>(UiState.Idle)
//    val state = _mutableState.asStateFlow()
//
//    fun getData() {
//        _mutableState.value = UiState.Loading
//        viewModelScope.launch {
//            _mutableState.value = repo.getCouponCodeDetails().toUiState()
//        }
//    }
}
