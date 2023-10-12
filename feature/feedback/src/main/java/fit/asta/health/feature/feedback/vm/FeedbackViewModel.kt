package fit.asta.health.feature.feedback.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.feedback.remote.modal.An
import fit.asta.health.data.feedback.remote.modal.FeedbackQuesDTO
import fit.asta.health.data.feedback.remote.modal.PostFeedbackDTO
import fit.asta.health.data.feedback.remote.modal.UserFeedbackDTO
import fit.asta.health.data.feedback.repo.FeedbackRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class FeedbackViewModel
@Inject constructor(
    private val feedbackRepo: FeedbackRepo,
    @UID private val uId: String
) : ViewModel() {

    private var _fid by mutableStateOf("")
    private var _qnrId by mutableStateOf("")

    private val _feedbackQuestions =
        MutableStateFlow<UiState<FeedbackQuesDTO>>(UiState.Idle)
    val feedbackQuestions = _feedbackQuestions.asStateFlow()

    private val _feedbackPostState =
        MutableStateFlow<UiState<PostFeedbackDTO>>(UiState.Idle)
    val feedbackPostState = _feedbackPostState.asStateFlow()

    //call this before starting feedback form
    fun loadFeedbackQuestions(feature: String) {
        _feedbackQuestions.value = UiState.Loading
        viewModelScope.launch {
            _feedbackQuestions.value = feedbackRepo.getFeedbackQuestions(uId, feature).toUiState()
            if (_feedbackQuestions.value is UiState.Success) {
                _qnrId = (_feedbackQuestions.value as UiState.Success<FeedbackQuesDTO>).data.id
                _fid = (_feedbackQuestions.value as UiState.Success<FeedbackQuesDTO>).data.fid
            }
        }
    }

    fun postUserFeedback(data: List<An>) {
        _feedbackPostState.value = UiState.Loading
        val feedback = UserFeedbackDTO(
            ans = data,
            fid = _fid,
            qnrId = _qnrId,
            uid = uId
        )
        viewModelScope.launch {
            _feedbackPostState.value = feedbackRepo.postUserFeedback(feedback).toUiState()
        }
    }

    fun resetPostResultState() {
        _feedbackPostState.value = UiState.Idle
    }
}