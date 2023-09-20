package fit.asta.health.feature.profile.vm

import app.cash.turbine.test
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.repo.ProfileRepo
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BasicProfileViewModelTest : BaseTest() {

    private lateinit var viewModel: BasicProfileViewModel

    @RelaxedMockK
    lateinit var repo: ProfileRepo

    @RelaxedMockK
    lateinit var auth: AuthRepo

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        MockKAnnotations.init(this)
        viewModel = spyk(
            BasicProfileViewModel(
                repo,
                auth
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
    }

    @Test
    fun `createBasicProfile with error, return error`() = runTest {
        coEvery { repo.createBasicProfile(any()) } returns ResponseState.ErrorMessage(mockk())

        viewModel.createBasicProfile(BasicProfileDTO())

        coVerify { repo.createBasicProfile(BasicProfileDTO()) }

        viewModel.createBasicProfileState.test {
            assert(awaitItem() is UiState.ErrorMessage)
        }
    }

    @Test
    fun `createBasicProfile no error, return success`() = runTest {
        coEvery { repo.createBasicProfile(any()) } returns ResponseState.Success(true)
        viewModel.createBasicProfile(BasicProfileDTO())

        coVerify { repo.createBasicProfile(BasicProfileDTO()) }

        viewModel.createBasicProfileState.test {
            assert(awaitItem() is UiState.Success)
        }
    }


    @Test
    fun `checkReferralCode with error, return error`() = runTest {
        coEvery { repo.checkReferralCode(any()) } returns ResponseState.ErrorMessage(mockk())

        viewModel.checkReferralCode("")

        coVerify { repo.checkReferralCode("") }

        viewModel.checkReferralCodeState.test {
            assert(awaitItem() is UiState.ErrorMessage)
        }
    }

    @Test
    fun `checkReferralCode no error, return success`() = runTest {
        coEvery { repo.checkReferralCode(any()) } returns ResponseState.Success(
            CheckReferralDTO(
                CheckReferralDTO.Data()
            )
        )

        viewModel.checkReferralCode("")

        coVerify { repo.checkReferralCode("") }

        viewModel.checkReferralCodeState.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `resetCheckCodeState, sets Idle`() = runTest {
        viewModel.resetCheckCodeState()
        viewModel.checkReferralCodeState.test {
            assert(awaitItem() is UiState.Idle)
        }
    }

    @Test
    fun `resetCreateProfileState, sets Idle`() = runTest {
        viewModel.resetCreateProfileState()
        viewModel.createBasicProfileState.test {
            assert(awaitItem() is UiState.Idle)
        }
    }

    @Test
    fun `setProfilePresent, calls Repo`() = runTest {
        coEvery { repo.setProfilePresent() } just Runs
        viewModel.setProfilePresent()
        coVerify { repo.setProfilePresent() }
    }

    @Test
    fun `getUser, returns User`() = runTest {
        coEvery { auth.getUser() } returns User()
        viewModel.getUser()
        coVerify { auth.getUser() }
    }
}