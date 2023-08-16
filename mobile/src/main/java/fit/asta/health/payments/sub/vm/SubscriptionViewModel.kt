package fit.asta.health.payments.sub.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.di.Uid
import fit.asta.health.payments.sub.model.SubscriptionResponse
import fit.asta.health.payments.sub.repo.SubscriptionRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel
@Inject constructor(
    private val subscriptionRepo: SubscriptionRepo,
    @Uid private val uid: String
) : ViewModel() {

    private val _state =
        MutableStateFlow<ResponseState<SubscriptionResponse>>(ResponseState.Loading)
    val state = _state.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        _state.value = ResponseState.Loading
        viewModelScope.launch {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
            _state.value = subscriptionRepo.getData(uid, "india", df.format(c.time))
        }
    }

}