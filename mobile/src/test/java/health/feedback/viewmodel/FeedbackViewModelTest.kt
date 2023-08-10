package health.feedback.viewmodel

import MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.feedback.model.FakeFeedbackRepoImpl
import fit.asta.health.feedback.model.network.An
import fit.asta.health.feedback.viewmodel.FeedbackViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FeedbackViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: FeedbackViewModel
    private val repo = FakeFeedbackRepoImpl()

    @Before
    fun setup() {
        viewModel = FeedbackViewModel(
            repo,
            ""
        )
    }

    @Test
    fun `loadFeedbackQuestions with valid uid valid fid, return success`() = runTest {
        repo.setUid("valid")
        viewModel.loadFeedbackQuestions("valid")
        assertThat(viewModel.feedbackQuestions.value).isInstanceOf(ResponseState.Success::class.java)
    }

    @Test
    fun `loadFeedbackQuestions with valid uid invalid fid, return error`() = runTest {
        repo.setUid("valid")
        viewModel.loadFeedbackQuestions("invalid")
        assertThat(viewModel.feedbackQuestions.value).isInstanceOf(ResponseState.Error::class.java)
    }

    @Test
    fun `loadFeedbackQuestions with invalid uid valid fid, return error`() = runTest {
        repo.setUid("invalid")
        viewModel.loadFeedbackQuestions("valid")
        assertThat(viewModel.feedbackQuestions.value).isInstanceOf(ResponseState.Error::class.java)
    }

    @Test
    fun `loadFeedbackQuestions with invalid uid invalid fid, return error`() = runTest {
        repo.setUid("invalid")
        viewModel.loadFeedbackQuestions("invalid")
        assertThat(viewModel.feedbackQuestions.value).isInstanceOf(ResponseState.Error::class.java)
    }

    @Test
    fun `postUserFeedback empty data, return error`() = runTest {
        viewModel.postUserFeedback(listOf())
        assertThat(viewModel.feedbackPostState.value).isInstanceOf(ResponseState.Error::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `postUserFeedback valid data, return success`() = runTest {
        viewModel.postUserFeedback(
            listOf(
                An()
            )
        )
        assertThat(viewModel.feedbackPostState.value).isInstanceOf(ResponseState.Success::class.java)
    }

}