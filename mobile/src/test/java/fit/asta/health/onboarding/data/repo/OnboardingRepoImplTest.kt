package fit.asta.health.onboarding.data.repo

import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.onboarding.data.remote.OnboardingApi
import fit.asta.health.onboarding.data.remote.modal.OnboardingDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OnboardingRepoImplTest {

    private lateinit var onboardingRepoImpl: OnboardingRepoImpl

    @RelaxedMockK
    lateinit var onboardingApi: OnboardingApi

    @RelaxedMockK
    lateinit var prefManager: PrefManager

    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, true)
        onboardingRepoImpl = spyk(
            OnboardingRepoImpl(
                remoteApi = onboardingApi,
                prefManager = prefManager,
                coroutineDispatcher = UnconfinedTestDispatcher()
            )
        )
    }

    @Test
    fun `getData with success, return Success Response`() = runTest {
        coEvery { onboardingApi.getData() } returns OnboardingDTO()
        val response = onboardingRepoImpl.getData()
        coVerify { onboardingApi.getData() }
        assert(response is ResponseState.Success)
    }

    @Test
    fun `getData with random exception, return Error Response`() = runTest {
        coEvery { onboardingApi.getData() } throws Exception()
        val response = onboardingRepoImpl.getData()
        coVerify { onboardingApi.getData() }
        assert(response is ResponseState.Error)
    }
}