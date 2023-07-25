package fit.asta.health.feedback.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.feedback.model.FeedbackRepo
import fit.asta.health.feedback.model.network.An
import fit.asta.health.feedback.model.network.FeedbackQuesResponse
import fit.asta.health.feedback.model.network.PostFeedbackRes
import fit.asta.health.feedback.model.network.UserFeedback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@HiltViewModel
class FeedbackViewModel
@Inject constructor(
    private val feedbackRepo: FeedbackRepo,
    @Named("UId")
    val uId: String,
) : ViewModel() {

    private var _fid by mutableStateOf("")
    private var _qnrId by mutableStateOf("")

    private val _feedbackQuestions =
        MutableStateFlow<ResponseState<FeedbackQuesResponse>>(ResponseState.Loading)
    val feedbackQuestions = _feedbackQuestions.asStateFlow()

    private val _feedbackPostState =
        MutableStateFlow<ResponseState<PostFeedbackRes>>(ResponseState.Idle)
    val feedbackPostState = _feedbackPostState.asStateFlow()

    //call this before starting feedback form
    fun loadFeedbackQuestions(featureId: String) {
        viewModelScope.launch {
            feedbackRepo.getFeedback(userId = uId, featureId = featureId).catch { exception ->
                _feedbackQuestions.value = ResponseState.Error(exception)
            }.collect {
                _feedbackQuestions.value = ResponseState.Success(it)
                _fid = featureId
                _qnrId = it.data.id
            }
        }
    }

    fun postUserFeedback(data: List<An>) {
        _feedbackPostState.value = ResponseState.Loading
        val feedback = UserFeedback(
            ans = data,
            fid = _fid,
            id = "",
            qnrId = _qnrId,
            uid = uId
        )
        Log.i("ANS", "Submitting answers as: $feedback")
        viewModelScope.launch {
            feedbackRepo.postUserFeedback(feedback)
                .catch {
                    _feedbackPostState.value = ResponseState.Error(it)
                }
                .collect {
                    _feedbackPostState.value = ResponseState.Success(it)
                    Log.e("ANS", "postUserFeedback response: $it")
                }
        }
    }
}