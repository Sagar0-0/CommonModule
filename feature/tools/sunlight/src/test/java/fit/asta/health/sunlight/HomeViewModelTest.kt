package fit.asta.health.sunlight

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.datastore.PrefManager
import fit.asta.health.sunlight.feature.viewmodels.HomeViewModel
import fit.asta.health.sunlight.remote.model.HelpAndNutrition
import fit.asta.health.sunlight.remote.model.SunlightHomeData
import fit.asta.health.sunlight.repo.SunlightHomeRepoImpl
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HomeViewModelTes : BaseTest() {

    private lateinit var viewModel: HomeViewModel

    private val repo: SunlightHomeRepoImpl = mockk(relaxed = true)
    private val pref: PrefManager = mockk(relaxed = true)


    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(
            HomeViewModel(
                repo,
                pref,
                ""
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Test
    fun `getSupplementNutritionData, return success`() = runTest {
        coEvery { repo.getSupplementAndFoodInfo() } returns ResponseState.Success(HelpAndNutrition())
        viewModel.getSupplementAndFoodInfo()
        coVerify { repo.getSupplementAndFoodInfo() }
        viewModel.helpAndSuggestionState.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `getSupplementNutritionData, return error retry`() = runTest {
        coEvery { repo.getSupplementAndFoodInfo() } returns ResponseState.ErrorRetry(0)
        viewModel.getSupplementAndFoodInfo()
        coVerify { repo.getSupplementAndFoodInfo() }
        viewModel.helpAndSuggestionState.test {
            assert(awaitItem() is UiState.ErrorRetry)
        }
    }

    @Test
    fun `getSupplementNutritionData, return error message`() = runTest {
        coEvery { repo.getSupplementAndFoodInfo() } returns ResponseState.ErrorMessage(2)
        viewModel.getSupplementAndFoodInfo()
        coVerify { repo.getSupplementAndFoodInfo() }
        viewModel.helpAndSuggestionState.test {
            assert(awaitItem() is UiState.ErrorMessage)
        }
    }

    @Test
    fun `getSunlightHomeData, return success`() = runTest {
        coEvery { repo.getSunlightHomeData(any(), any(), any(), any(), any()) } returns ResponseState.Success(
            mockk()
        )
        viewModel.getHomeScreenData()
        coVerify { repo.getSunlightHomeData(any(), any(), any(), any(), any()) }
        viewModel.homeState.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `getSunlightHomeData, return error`() = runTest {
        coEvery {
            repo.getSunlightHomeData(
                mockk(),
                mockk(),
                mockk(),
                mockk(),
                mockk()
            )
        } returns ResponseState.ErrorMessage(mockk())
        viewModel.getHomeScreenData()
        coVerify { repo.getSunlightHomeData(
            mockk(),
            mockk(),
            mockk(),
            mockk(),
            mockk()
        ) }
        viewModel.homeState.test {
            assert(awaitItem() is UiState.ErrorMessage)
        }
    }
}
