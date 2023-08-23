package fit.asta.health.data.feedback.repo

import android.content.ContentResolver
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.feedback.remote.FeedbackApi
import fit.asta.health.data.feedback.remote.modal.FeedbackQuesDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FeedbackRepoImplTest {

    private lateinit var feedbackRepoImpl: FeedbackRepoImpl

    @RelaxedMockK
    lateinit var feedbackApi: FeedbackApi

    @RelaxedMockK
    lateinit var contentResolver: ContentResolver

    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        feedbackRepoImpl = FeedbackRepoImpl(
            feedbackApi, contentResolver, UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `getFeedbackQuestions with valid id, return Success Response`() = runTest {
        coEvery { feedbackApi.getFeedbackQuestions(any(), any()) } returns FeedbackQuesDTO()
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
        assert(res is ResponseState.Error)
    }
}