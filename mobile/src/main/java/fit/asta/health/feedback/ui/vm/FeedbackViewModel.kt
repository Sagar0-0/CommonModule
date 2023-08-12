package fit.asta.health.feedback.ui.vm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.di.IODispatcher
import fit.asta.health.feedback.data.repo.FeedbackRepo
import fit.asta.health.feedback.data.remote.modal.An
import fit.asta.health.feedback.data.remote.modal.FeedbackQuesDTO
import fit.asta.health.feedback.data.remote.modal.PostFeedbackDTO
import fit.asta.health.feedback.data.remote.modal.UserFeedbackDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext


@ExperimentalCoroutinesApi
@HiltViewModel
class FeedbackViewModel
@Inject constructor(
    private val feedbackRepo: FeedbackRepo,
    @Named("UId")
    val uId: String,
    @IODispatcher val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
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
    fun loadFeedbackQuestions(featureId: String) {
        _feedbackQuestions.value = UiState.Loading
        viewModelScope.launch(coroutineDispatcher) {
            _feedbackQuestions.value = feedbackRepo.getFeedbackQuestions(uId,featureId).toUiState()
            _fid = featureId
            if(_feedbackQuestions.value is UiState.Success)_qnrId = (_feedbackQuestions.value as UiState.Success<FeedbackQuesDTO>).data.data.id
        }
    }

    fun postUserFeedback(data: List<An>) {
        _feedbackPostState.value = UiState.Loading
        val feedback = UserFeedbackDTO(
            ans = data,
            fid = _fid,
            id = "",
            qnrId = _qnrId,
            uid = uId
        )
        Log.i("ANS", "Submitting answers as: $feedback")
        viewModelScope.launch(coroutineDispatcher) {
            _feedbackPostState.value = feedbackRepo.postUserFeedback(feedback).toUiState()
        }
    }
}