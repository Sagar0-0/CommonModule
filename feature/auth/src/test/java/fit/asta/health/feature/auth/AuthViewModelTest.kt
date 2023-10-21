package fit.asta.health.feature.auth

import app.cash.turbine.test
import com.google.firebase.auth.AuthCredential
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.repo.AuthRepoImpl
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.feature.auth.vm.AuthViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest : BaseTest() {

    private lateinit var viewModel: AuthViewModel
    private val repo: AuthRepoImpl = mockk(relaxed = true)


    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(
            AuthViewModel(
                repo,
                mockk(),
                mockk(),
                mockk()
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }


    @Test
    fun `currentUser, returns User`() {
        val user = User()
        every { repo.getUser() } returns user

        viewModel = AuthViewModel(
            repo, mockk(),
            mockk(),
            mockk()
        )
        assertEquals(viewModel.currentUser, user)
        verify { repo.getUser() }
    }

    @Test
    fun `signInWithGoogleCredentials, returns Success`() = runTest {
        val cred: AuthCredential = mockk()
        val res = MutableStateFlow(ResponseState.Success(User()))
        every { repo.signInWithCredential(any()) } returns res
        viewModel.signInAndNavigate(cred)
        verify { repo.signInWithCredential(cred) }
        viewModel.loginState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
        }
    }

    @Test
    fun `signInWithGoogleCredentials, returns Error`() = runTest {
        val cred: AuthCredential = mockk()
        val res = MutableStateFlow(ResponseState.ErrorMessage(mockk()))
        every { repo.signInWithCredential(any()) } returns res
        viewModel.signInAndNavigate(cred)
        verify { repo.signInWithCredential(cred) }
        viewModel.loginState.test {
            val item = awaitItem()
            assert(item is UiState.ErrorMessage)
        }
    }

    @Test
    fun `logout, returns Success`() = runTest {
        coEvery { repo.signOut() } returns mockk()
        viewModel.logout()
        coVerify { repo.signOut() }
        viewModel.logoutState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
        }
    }

    @Test
    fun `logout, returns Error`() = runTest {
        coEvery { repo.signOut() } returns mockk()
        viewModel.logout()
        coVerify { repo.signOut() }
        viewModel.logoutState.test {
            val item = awaitItem()
            assert(item is UiState.ErrorMessage)
        }
    }

    @Test
    fun `delete, returns Success`() = runTest {
        val res = MutableStateFlow(ResponseState.Success(true))
        coEvery { repo.deleteAccount() } returns res
        viewModel.deleteAccount()
        coVerify { repo.deleteAccount() }
        viewModel.deleteState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
        }
    }

    @Test
    fun `delete, returns Error`() = runTest {
        val res = MutableStateFlow(ResponseState.ErrorMessage(mockk()))
        coEvery { repo.deleteAccount() } returns res
        viewModel.deleteAccount()
        coVerify { repo.deleteAccount() }
        viewModel.deleteState.test {
            val item = awaitItem()
            assert(item is UiState.ErrorMessage)
        }
    }

    @Test
    fun resetLogoutState() = runTest {
        viewModel.resetLogoutState()
        viewModel.logoutState.test {
            val item = awaitItem()
            assert(item is UiState.Idle)
        }
    }

    @Test
    fun resetDeleteState() = runTest {
        viewModel.resetDeleteState()
        viewModel.deleteState.test {
            val item = awaitItem()
            assert(item is UiState.Idle)
        }
    }
}