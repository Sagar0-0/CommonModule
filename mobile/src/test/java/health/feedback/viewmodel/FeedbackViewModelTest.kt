package fit.asta.health.feedback.viewmodel

import CoroutinesTestExtension
import InstantExecutorExtension
import com.google.common.truth.Truth.assertThat
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.feedback.model.FakeFeedbackRepoImpl
import fit.asta.health.feedback.model.network.An
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class, CoroutinesTestExtension::class)
class FeedbackViewModelTest {

    private lateinit var viewModel: FeedbackViewModel
    private val repo = FakeFeedbackRepoImpl()

    @BeforeEach
    fun setup() {
        viewModel = FeedbackViewModel(
            repo,
            ""
        )
    }

    @Test
    fun `loadFeedbackQuestions with valid uid valid fid, return success`() {
        repo.setUid("valid")
        viewModel.loadFeedbackQuestions("valid")
        assertThat(viewModel.feedbackQuestions.value).isInstanceOf(ResponseState.Success::class.java)
    }

    @Test
    fun `loadFeedbackQuestions with valid uid invalid fid, return error`() {
        repo.setUid("valid")
        viewModel.loadFeedbackQuestions("invalid")
        assertThat(viewModel.feedbackQuestions.value).isInstanceOf(ResponseState.Error::class.java)
    }

    @Test
    fun `loadFeedbackQuestions with invalid uid valid fid, return error`() {
        repo.setUid("invalid")
        viewModel.loadFeedbackQuestions("valid")
        assertThat(viewModel.feedbackQuestions.value).isInstanceOf(ResponseState.Error::class.java)
    }

    @Test
    fun `loadFeedbackQuestions with invalid uid invalid fid, return error`() {
        repo.setUid("invalid")
        viewModel.loadFeedbackQuestions("invalid")
        assertThat(viewModel.feedbackQuestions.value).isInstanceOf(ResponseState.Error::class.java)
    }

    @Test
    fun `postUserFeedback empty data, return error`() {
        viewModel.postUserFeedback(listOf())
        assertThat(viewModel.feedbackPostState.value).isInstanceOf(ResponseState.Error::class.java)
    }

    @Test
    fun `postUserFeedback valid data, return success`() {
        viewModel.postUserFeedback(
            listOf(
                An()
            )
        )
        assertThat(viewModel.feedbackPostState.value).isInstanceOf(ResponseState.Success::class.java)
    }

}