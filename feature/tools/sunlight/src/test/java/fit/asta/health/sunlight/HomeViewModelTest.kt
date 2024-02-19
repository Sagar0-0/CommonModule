package fit.asta.health.sunlight

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.datastore.PrefManager
import fit.asta.health.sunlight.feature.event.SunlightHomeEvent
import fit.asta.health.sunlight.feature.viewmodels.HomeViewModel
import fit.asta.health.sunlight.remote.model.HelpAndNutrition
import fit.asta.health.sunlight.repo.SunlightHomeRepoImpl
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.flow
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
        coEvery { repo.getSupplementAndFoodInfo() } returns flow {
            emit(ResponseState.Success(HelpAndNutrition()))
        }
        viewModel.getSupplementAndFoodInfo()
        coVerify { repo.getSupplementAndFoodInfo() }
        viewModel.helpAndSuggestionState.test {
            assert(awaitItem() is UiState.Success)
        }
    }



}