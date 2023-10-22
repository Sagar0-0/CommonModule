package fit.asta.health.data.feedback.repo

import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.feedback.remote.FeedbackApi
import fit.asta.health.data.feedback.remote.modal.PostFeedbackDTO
import fit.asta.health.data.feedback.remote.modal.UserFeedbackDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FeedbackRepoImplTest {

    private lateinit var feedbackRepoImpl: FeedbackRepoImpl

    @RelaxedMockK
    lateinit var feedbackApi: FeedbackApi

    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        feedbackRepoImpl = FeedbackRepoImpl(
            feedbackApi, mockk(), UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `getFeedbackQuestions with valid id, return Success Response`() = runTest {
        coEvery { feedbackApi.getFeedbackQuestions(any(), any()) } returns mockk()
        val uid = "uid"
        val fid = "fid"
        val res = feedbackRepoImpl.getFeedbackQuestions(uid, fid)
        coVerify { feedbackApi.getFeedbackQuestions(uid, fid) }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `getFeedbackQuestions with invalid id, return Error Response`() = runTest {
        coEvery { feedbackApi.getFeedbackQuestions(any(), any()) } throws Exception()
        val uid = "uid"
        val fid = "fid"
        val res = feedbackRepoImpl.getFeedbackQuestions(uid, fid)
        coVerify { feedbackApi.getFeedbackQuestions(uid, fid) }
        assert(res is ResponseState.ErrorMessage)
    }

    @Test
    fun `postUserFeedback, return Success Response`() = runTest {
        val feedback = UserFeedbackDTO()
        coEvery {
            feedbackApi.postUserFeedback(
                any(),
                any()
            )
        } returns Response(data = PostFeedbackDTO())
        val res = feedbackRepoImpl.postUserFeedback(feedback)
//        coVerify { feedbackApi.postUserFeedback(any(),any()) }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `postUserFeedback, return Error Response`() = runTest {
        coEvery { feedbackApi.postUserFeedback(any(), any()) } throws Exception()
        val res = feedbackRepoImpl.postUserFeedback(mockk())
        coVerify { feedbackApi.postUserFeedback(any(), any()) }
        assert(res is ResponseState.ErrorMessage)
    }
}