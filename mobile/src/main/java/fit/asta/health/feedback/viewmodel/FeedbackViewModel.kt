package fit.asta.health.feedback.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.feedback.intent.FeedbackState
import fit.asta.health.feedback.model.FeedbackRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class FeedbackViewModel
@Inject constructor(
    private val feedbackRepo: FeedbackRepo,
) : ViewModel() {

    private val mutableState = MutableStateFlow<FeedbackState>(FeedbackState.Loading)
    val state = mutableState.asStateFlow()

    init {
        loadWaterToolData("6309a9379af54f142c65fbfe")
    }

    private fun loadWaterToolData(userId: String) {
        viewModelScope.launch {
            feedbackRepo.getFeedback(userId).catch { exception ->
                mutableState.value = FeedbackState.Error(exception)
            }.collect {
                mutableState.value = FeedbackState.Success(it)
            }
        }
    }
}