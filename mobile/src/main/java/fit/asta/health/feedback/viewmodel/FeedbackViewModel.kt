package fit.asta.health.feedback.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.feedback.intent.FeedbackState
import fit.asta.health.feedback.model.FeedbackRepo
import fit.asta.health.feedback.model.network.NetUserFeedback
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
        loadFeedbackQuestions("6309a9379af54f142c65fbfe", "")
    }

    private fun loadFeedbackQuestions(userId: String, featureId: String) {
        viewModelScope.launch {
            feedbackRepo.getFeedback(userId = userId, featureId = featureId).catch { exception ->
                mutableState.value = FeedbackState.Error(exception)
            }.collect {
                mutableState.value = FeedbackState.Success(it)
            }
        }
    }

    private fun postUserFeedback(feedback: NetUserFeedback) {
        viewModelScope.launch {
            feedbackRepo.postUserFeedback(feedback).catch { exception ->
                mutableState.value = FeedbackState.Error(exception)
            }.collect {
                //mutableState.value = FeedbackState.Success(it)
            }
        }
    }
}