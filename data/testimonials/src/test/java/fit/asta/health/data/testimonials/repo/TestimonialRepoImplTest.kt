package fit.asta.health.data.testimonials.repo

import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.testimonials.remote.TestimonialApi
import fit.asta.health.data.testimonials.remote.model.SaveTestimonialResponse
import fit.asta.health.data.testimonials.remote.model.Testimonial
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestimonialRepoImplTest {
    private lateinit var repo: TestimonialRepoImpl

    @RelaxedMockK
    lateinit var api: TestimonialApi

    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        repo = spyk(
            TestimonialRepoImpl(
                contentResolver = mockk(),
                remoteApi = api,
                coroutineDispatcher = UnconfinedTestDispatcher()
            )
        )
    }

    @Test
    fun `getAllTestimonials with success, return Success Response`() = runTest {
        val res = Response(data = listOf(Testimonial()))
        coEvery { api.getAllTestimonials(any(), any()) } returns res
        val response = repo.getAllTestimonials(0, 0)
        coVerify { api.getAllTestimonials(0, 0) }
        assert(response is ResponseState.Success)
    }

    @Test
    fun `getAllTestimonials with random exception, return Error Response`() = runTest {
        coEvery { api.getAllTestimonials(any(), any()) } throws Exception()
        val response = repo.getAllTestimonials(0, 0)
        coVerify { api.getAllTestimonials(0, 0) }
        assert(response is ResponseState.ErrorMessage)
    }

    @Test
    fun `getUserTestimonial with success, return Success Response`() = runTest {
        val res = Response(status = Response.Status(0, ""), data = Testimonial())
        coEvery { api.getUserTestimonial(any()) } returns res
        val response = repo.getUserTestimonial("uid")
        coVerify { api.getUserTestimonial("uid") }
        assert(response is ResponseState.Success)
    }

    @Test
    fun `getUserTestimonial with random exception, return ErrorMessage`() = runTest {
        coEvery { api.getUserTestimonial(any()) } throws Exception()
        val response = repo.getUserTestimonial("")
        coVerify { api.getUserTestimonial("") }
        assert(response is ResponseState.ErrorMessage)
    }

    @Test
    fun `updateTestimonial with success, return Success Response`() = runTest {
        val res = Response(data = SaveTestimonialResponse())
        coEvery { api.createTestimonial(any(), any()) } returns res
        val response = repo.saveUserTestimonial(Testimonial())
        coVerify { api.createTestimonial(Testimonial(), any()) }
        assert(response is ResponseState.Success)
    }

    @Test
    fun `updateTestimonial with random exception, return ErrorMessage`() = runTest {
        coEvery { api.createTestimonial(any(), any()) } throws Exception()
        val response = repo.saveUserTestimonial(Testimonial())
        coVerify { api.createTestimonial(Testimonial(), any()) }
        assert(response is ResponseState.ErrorMessage)
    }
}