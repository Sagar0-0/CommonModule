package com.example.subscription.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.di.UID
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.subscription.model.SubscriptionResponse
import fit.asta.health.subscription.repo.SubscriptionRepo
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
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
            _state.value = subscriptionRepo.getData(uid, "india", df.format(c.time)).toUiState()
        }
    }

}