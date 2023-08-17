package fit.asta.health.auth.ui.vm

import app.cash.turbine.test
import com.google.firebase.auth.AuthCredential
import fit.asta.health.auth.data.model.domain.User
import fit.asta.health.auth.data.repo.AuthRepoImpl
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import health.BaseTest
import io.mockk.clearAllMocks
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
                repo
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

        viewModel = AuthViewModel(repo)
        assertEquals(viewModel.currentUser,user)
        verify { repo.getUser() }
    }

    @Test
    fun `signInWithGoogleCredentials, returns Success`() = runTest {
        val cred: AuthCredential = mockk()
        val res = MutableStateFlow(ResponseState.Success(true))
        every { repo.signInWithCredential(any()) } returns res
        viewModel.signInWithGoogleCredentials(cred)
        verify { repo.signInWithCredential(cred) }
        viewModel.loginState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
        }
    }

    @Test
    fun `signInWithGoogleCredentials, returns Error`() = runTest {
        val cred: AuthCredential = mockk()
        val res = MutableStateFlow(ResponseState.Error(Exception()))
        every { repo.signInWithCredential(any()) } returns res
        viewModel.signInWithGoogleCredentials(cred)
        verify { repo.signInWithCredential(cred) }
        viewModel.loginState.test {
            val item = awaitItem()
            assert(item is UiState.Error)
        }
    }

    @Test
    fun `logout, returns Success`() = runTest {
        every { repo.signOut() } returns ResponseState.Success(true)
        viewModel.logout()
        verify { repo.signOut() }
        viewModel.logoutState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
        }
    }

    @Test
    fun `logout, returns Error`() = runTest {
        every { repo.signOut() } returns ResponseState.Error(Exception())
        viewModel.logout()
        verify { repo.signOut() }
        viewModel.logoutState.test {
            val item = awaitItem()
            assert(item is UiState.Error)
        }
    }

    @Test
    fun `delete, returns Success`() = runTest {
        val res = MutableStateFlow(ResponseState.Success(true))
        every { repo.deleteAccount() } returns res
        viewModel.deleteAccount()
        verify { repo.deleteAccount() }
        viewModel.deleteState.test {
            val item = awaitItem()
            assert(item is UiState.Success)
        }
    }

    @Test
    fun `delete, returns Error`() = runTest {
        val res = MutableStateFlow(ResponseState.Error(Exception()))
        every { repo.deleteAccount() } returns res
        viewModel.deleteAccount()
        verify { repo.deleteAccount() }
        viewModel.deleteState.test {
            val item = awaitItem()
            assert(item is UiState.Error)
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