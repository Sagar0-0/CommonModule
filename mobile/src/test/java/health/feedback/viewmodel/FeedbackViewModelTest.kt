package health.feedback.viewmodel

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.feedback.data.remote.modal.An
import fit.asta.health.feedback.data.remote.modal.FeedbackQuesDTO
import fit.asta.health.feedback.data.remote.modal.PostFeedbackDTO
import fit.asta.health.feedback.data.repo.FeedbackRepoImpl
import fit.asta.health.feedback.ui.vm.FeedbackViewModel
import health.BaseTest
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
class FeedbackViewModelTest : BaseTest() {

    private lateinit var viewModel: FeedbackViewModel

    private val repo: FeedbackRepoImpl = mockk(relaxed = true)


    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(
            FeedbackViewModel(
                repo,
                ""
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Test
    fun `loadFeedbackQuestions with valid fid, return success`() = runTest {
        val fid = "valid"
        coEvery { repo.getFeedbackQuestions(any(), any()) } returns ResponseState.Success(
            FeedbackQuesDTO()
        )
        viewModel.loadFeedbackQuestions(fid)
        coVerify { repo.getFeedbackQuestions(any(), fid) }
        viewModel.feedbackQuestions.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `loadFeedbackQuestions with invalid fid, return error`() = runTest {
        val fid = "invalid"
        coEvery { repo.getFeedbackQuestions(any(), any()) } returns ResponseState.Error(Exception())
        viewModel.loadFeedbackQuestions(fid)
        coVerify { repo.getFeedbackQuestions(any(), fid) }
        viewModel.feedbackQuestions.test {
            assert(awaitItem() is UiState.Error)
        }
    }

    @Test
    fun `postUserFeedback empty data, return error`() = runTest {
        val mockList = listOf<An>()
        coEvery { repo.postUserFeedback(any()) } returns ResponseState.Error(Exception())
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