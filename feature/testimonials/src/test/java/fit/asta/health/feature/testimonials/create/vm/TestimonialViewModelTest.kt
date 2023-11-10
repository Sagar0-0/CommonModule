package fit.asta.health.feature.testimonials.create.vm

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.repo.AuthRepoImpl
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.data.testimonials.model.SaveTestimonialResponse
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.data.testimonials.repo.TestimonialRepoImpl
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
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)


    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(
            TestimonialViewModel(
                repo,
                authRepo,
                savedStateHandle
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Test
    fun `loadTestimonial with valid uid and Success, updates MutableState`() = runTest {
        coEvery { authRepo.getUser() } returns User()
        coEvery { savedStateHandle.getStateFlow(any(), Testimonial()) } returns mockk()
        coEvery { repo.getUserTestimonial(any()) } returns ResponseState.Success(
            Testimonial()
        )
        viewModel.loadUserTestimonialData()
        coVerify { authRepo.getUser() }
        coVerify { repo.getUserTestimonial("") }
        viewModel.userTestimonialState.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `loadTestimonial with valid uid and Error, updates MutableState`() = runTest {
        coEvery { authRepo.getUser() } returns User()
        coEvery { savedStateHandle.getStateFlow(any(), Testimonial()) } returns mockk()
        coEvery { repo.getUserTestimonial(any()) } returns ResponseState.ErrorMessage(
            0
        )
        viewModel.loadUserTestimonialData()
        coVerify { authRepo.getUser() }
        coVerify { repo.getUserTestimonial("") }
        viewModel.userTestimonialState.test {
            assert(awaitItem() is UiState.ErrorMessage)
        }
    }

    @Test
    fun `onEvent onSubmit Success, updates submitSate`() = runTest {
        coEvery { authRepo.getUser() } returns User()
        coEvery { repo.saveTestimonial(any()) } returns ResponseState.Success(
            SaveTestimonialResponse()
        )
        viewModel.onEvent(TestimonialEvent.OnSubmitTestimonial)
        coVerify { authRepo.getUser() }
        coVerify { repo.saveTestimonial(any()) }
//        viewModel.stateSubmit.test {
//            assert(awaitItem() is UiState.Success)
//        }
    }

    @Test
    fun `onEvent onSubmit Error, updates submitSate`() = runTest {
        coEvery { authRepo.getUser() } returns User()
        coEvery { savedStateHandle.getStateFlow(any(), Testimonial()) } returns mockk()
        coEvery { repo.saveTestimonial(any()) } returns ResponseState.ErrorMessage(
            0
        )
        viewModel.onEvent(TestimonialEvent.OnSubmitTestimonial)
        coVerify { authRepo.getUser() }
        coVerify { repo.saveTestimonial(any()) }
        viewModel.saveTestimonialState.test {
            assert(awaitItem() is UiState.ErrorMessage)
        }
    }
}