package health.feedback.viewmodel

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.feedback.data.remote.modal.An
import fit.asta.health.feedback.data.remote.modal.FeedbackQuesDTO
import fit.asta.health.feedback.data.remote.modal.PostFeedbackDTO
import fit.asta.health.feedback.data.repo.FeedbackRepoImpl
import fit.asta.health.feedback.ui.vm.FeedbackViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FeedbackViewModelTest {

    private lateinit var viewModel: FeedbackViewModel

    private val repo: FeedbackRepoImpl = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        viewModel = spyk(
            FeedbackViewModel(
                repo,
                ""
            )
        )
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `loadFeedbackQuestions with valid uid valid fid, return success`() = runTest {
        coEvery {
            repo.getFeedback(
                any(),
                "valid"
            )
        } returns ResponseState.Success(FeedbackQuesDTO())
        viewModel.loadFeedbackQuestions("valid")
        coVerify { repo.getFeedback(any(), "valid") }
        viewModel.feedbackQuestions.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `loadFeedbackQuestions with valid uid invalid fid, return error`() = runTest {
        coEvery { repo.getFeedback(any(), "invalid") } returns ResponseState.Error(Exception())
        viewModel.loadFeedbackQuestions("invalid")
        coVerify { repo.getFeedback(any(), "invalid") }
        viewModel.feedbackQuestions.test {
            assert(awaitItem() is UiState.Error)
        }
    }

    @Test
    fun `postUserFeedback empty data, return error`() = runTest {
        coEvery { repo.postUserFeedback(any()) } returns ResponseState.Error(Exception())
        val mockList = listOf<An>()
        viewModel.postUserFeedback(mockList)
        coVerify { repo.postUserFeedback(any()) }
        viewModel.feedbackPostState.test {
            assert(awaitItem() is UiState.Error)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `postUserFeedback valid data, return success`() = runTest {
        coEvery { repo.postUserFeedback(any()) } returns ResponseState.Success(PostFeedbackDTO())
        val mockList = listOf(An(), An())
        viewModel.postUserFeedback(mockList)
        coVerify { repo.postUserFeedback(any()) }
        viewModel.feedbackPostState.test {
            assert(awaitItem() is UiState.Success)
        }
    }

}