package fit.asta.health.data.profile.repo

import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.data.profile.remote.ProfileApi
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.datastore.PrefManager
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProfileRepoImplTest {

    private lateinit var repo: ProfileRepoImpl

    @RelaxedMockK
    lateinit var api: ProfileApi

    @RelaxedMockK
    lateinit var prefManager: PrefManager


    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        repo = spyk(
            ProfileRepoImpl(
                profileApi = api,
                prefManager = prefManager,
                contentResolver = mockk(),
                coroutineDispatcher = UnconfinedTestDispatcher()
            )
        )
    }

    @Test
    fun `setProfilePresent, returns Success`() = runTest {
        coEvery { prefManager.setScreenCode(any()) } just Runs
        repo.setProfilePresent()
        coVerify { prefManager.setScreenCode(2) }
    }

    @Test
    fun `isProfileAvailable, returns Success`() = runTest {
        coEvery { api.isUserProfileAvailable(any()) } returns mockk()
        val response = repo.isBasicProfileAvailable("")
        coVerify { api.isUserProfileAvailable("") }
        assert(response is ResponseState.Success)
    }

    @Test
    fun `isProfileAvailable with random exception, return Error Response`() = runTest {
        coEvery { api.isUserProfileAvailable(any()) } throws Exception()
        val response = repo.isBasicProfileAvailable("")
        coVerify { api.isUserProfileAvailable("") }
        assert(response is ResponseState.ErrorMessage)
    }

    @Test
    fun `createBasicProfile, returns Success`() = runTest {
        coEvery {
            api.createBasicProfile(
                any(),
                any()
            )
        } returns Response(data = SubmitProfileResponse())
        val response = repo.createBasicProfile(BasicProfileDTO())
        coVerify { api.createBasicProfile(BasicProfileDTO(), any()) }
        assert(response is ResponseState.Success)
    }

    @Test
    fun `createBasicProfile with random exception, return Error Response`() = runTest {
        coEvery { api.createBasicProfile(any(), any()) } throws Exception()
        val response = repo.createBasicProfile(BasicProfileDTO())
        coVerify { api.createBasicProfile(BasicProfileDTO(), any()) }
        assert(response is ResponseState.ErrorMessage)
    }

    @Test
    fun `checkReferralCode, returns Success`() = runTest {
        coEvery { api.checkReferralCode(any()) } returns Response(data = CheckReferralDTO())
        val response = repo.checkReferralCode("")
        coVerify { api.checkReferralCode("") }
        assert(response is ResponseState.Success)
    }

    @Test
    fun `checkReferralCode with random exception, return Error Response`() = runTest {
        coEvery { api.checkReferralCode(any()) } throws Exception()
        val response = repo.checkReferralCode("")
        coVerify { api.checkReferralCode("") }
        assert(response is ResponseState.ErrorMessage)
    }
}