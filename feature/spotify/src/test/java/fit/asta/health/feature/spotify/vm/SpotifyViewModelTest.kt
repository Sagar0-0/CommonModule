package fit.asta.health.feature.spotify.vm

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
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
import org.junit.jupiter.api.Test

class SpotifyViewModelTest : BaseTest() {

    private lateinit var viewModel: SpotifyViewModelX
    private val remoteRepo: SpotifyRepoImpl = mockk(relaxed = true)
    private val localRepo: MusicRepositoryImpl = mockk(relaxed = true)


    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(
            SpotifyViewModelX(
                remoteRepo,
                localRepo
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

//    @Test
//    fun `testing`() = runTest {
//
//
//        coEvery { remoteRepo.getCurrentUserDetails("") } returns ResponseState.Success(mockk())
//
//        viewModel.getCurrentUserDetails("")
//
//        coVerify { remoteRepo.getCurrentUserDetails("") }
//
//        viewModel.currentUserData.test {
//            assert(awaitItem() is UiState.Success)
//        }
//    }
}