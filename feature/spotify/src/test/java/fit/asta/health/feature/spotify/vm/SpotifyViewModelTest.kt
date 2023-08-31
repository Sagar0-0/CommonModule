package fit.asta.health.feature.spotify.vm

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.data.spotify.model.me.SpotifyMeModel
import fit.asta.health.data.spotify.repo.MusicRepositoryImpl
import fit.asta.health.data.spotify.repo.SpotifyRepoImpl
import fit.asta.health.feature.spotify.viewmodel.SpotifyViewModelX
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

class SpotifyViewModelTest : BaseTest() {

    private lateinit var viewModel: SpotifyViewModelX
    private val remoteRepo: SpotifyRepoImpl = mockk(relaxed = true)
    private val localRepo: MusicRepositoryImpl = mockk(relaxed = true)


    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(
            SpotifyViewModelX(remoteRepo, localRepo)
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Nested
    @DisplayName("Get Current User Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUser {

        @Test
        fun `getCurrentUserDetails, returns Success`() = runTest {

            val expected = ResponseState.Success<SpotifyMeModel>(mockk())
            coEvery { remoteRepo.getCurrentUserDetails("") } returns expected

            viewModel.getCurrentUserDetails("")

            coVerify { remoteRepo.getCurrentUserDetails("") }
            viewModel.currentUserData.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getCurrentUserDetails, returns Error`() = runTest {

            val expected = ResponseState.Error(mockk())
            coEvery { remoteRepo.getCurrentUserDetails("") } returns expected

            viewModel.getCurrentUserDetails("")

            coVerify { remoteRepo.getCurrentUserDetails("") }
            viewModel.currentUserData.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }
}