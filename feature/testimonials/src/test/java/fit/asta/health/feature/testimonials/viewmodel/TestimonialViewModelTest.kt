package fit.asta.health.feature.testimonials.viewmodel

import app.cash.turbine.test
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.repo.AuthRepoImpl
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.data.testimonials.model.SaveTestimonialResponse
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.data.testimonials.repo.TestimonialRepoImpl
import fit.asta.health.feature.testimonials.events.TestimonialEvent
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestimonialViewModelTest : BaseTest() {

    private lateinit var viewModel: TestimonialViewModel

    private val repo: TestimonialRepoImpl = mockk(relaxed = true)
    private val authRepo: AuthRepoImpl = mockk(relaxed = true)

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(TestimonialViewModel(repo, authRepo))
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Test
    fun `loadTestimonial with valid uid and Success, updates MutableState`() = runTest {

        coEvery { authRepo.getUser() } returns User()
        coEvery { repo.getUserTestimonial(any()) } returns ResponseState.Success(Testimonial())

        viewModel.onEvent(TestimonialEvent.GetUserTestimonial)

        coVerify { authRepo.getUser() }
        coVerify { repo.getUserTestimonial("") }

        viewModel.userTestimonialApiState.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `loadTestimonial with valid uid and Error, updates MutableState`() = runTest {

        coEvery { authRepo.getUser() } returns User()
        coEvery { repo.getUserTestimonial(any()) } returns ResponseState.ErrorMessage(0)

        viewModel.onEvent(TestimonialEvent.GetUserTestimonial)

        coVerify { authRepo.getUser() }
        coVerify { repo.getUserTestimonial("") }

        viewModel.userTestimonialApiState.test {
            assert(awaitItem() is UiState.ErrorMessage)
        }
    }

    @Test
    fun `onEvent onSubmit Success, updates submitSate`() = runTest {

        coEvery { authRepo.getUser() } returns User(uid = "", name = "", photoUrl = "")
        coEvery { repo.saveTestimonial(any()) } returns ResponseState.Success(
            SaveTestimonialResponse()
        )

        viewModel.onEvent(TestimonialEvent.OnSubmitTestimonial)

        coVerify { authRepo.getUser() }
        coVerify { repo.saveTestimonial(any()) }

        viewModel.testimonialSubmitApiState.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `onEvent onSubmit Error, updates submitSate`() = runTest {

        coEvery { authRepo.getUser() } returns User(uid = "", name = "", photoUrl = "")
        coEvery { repo.saveTestimonial(any()) } returns ResponseState.ErrorMessage(0)

        viewModel.onEvent(TestimonialEvent.OnSubmitTestimonial)

        coVerify { authRepo.getUser() }
        coVerify { repo.saveTestimonial(any()) }

        viewModel.testimonialSubmitApiState.test {
            assert(awaitItem() is UiState.ErrorMessage)
        }
    }
}