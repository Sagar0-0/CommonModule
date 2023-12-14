package fit.asta.health.subscription.vm
//
//import app.cash.turbine.test
//import fit.asta.health.common.utils.ResponseState
//import fit.asta.health.common.utils.UiState
//import fit.asta.health.core.test.BaseTest
//import fit.asta.health.subscription.remote.model.SubscriptionResponse
//import fit.asta.health.subscription.repo.SubscriptionRepo
//import io.mockk.MockKAnnotations
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.impl.annotations.RelaxedMockK
//import io.mockk.mockk
//import io.mockk.spyk
//import kotlinx.coroutines.test.runTest
//import org.junit.jupiter.api.AfterEach
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//
//class SubscriptionViewModelTest : BaseTest() {
//
//    private lateinit var viewModel: fit.asta.health.main.SubscriptionViewModel
//
//    @RelaxedMockK
//    lateinit var repo: SubscriptionRepo
//
//
//    @BeforeEach
//    override fun beforeEach() {
//        super.beforeEach()
//        MockKAnnotations.init(this)
//        viewModel = spyk(
//            fit.asta.health.main.SubscriptionViewModel(
//                repo,
//                ""
//            )
//        )
//    }
//
//    @AfterEach
//    override fun afterEach() {
//        super.afterEach()
//    }
//
//    @Test
//    fun `getData with error, return error`() = runTest {
//        coEvery { repo.getData(any(), any()) } returns ResponseState.ErrorMessage(mockk())
//
//        viewModel.getData()
//
//        coVerify { repo.getData("", "india") }
//
//        viewModel.state.test {
//            assert(awaitItem() is UiState.ErrorMessage)
//        }
//    }
//
//    @Test
//    fun `getData no error, return success`() = runTest {
//        coEvery { repo.getData(any(), any()) } returns ResponseState.Success(SubscriptionResponse())
//        viewModel.getData()
//
//        coVerify { repo.getData("", "india") }
//
//        viewModel.state.test {
//            assert(awaitItem() is UiState.Success)
//        }
//    }
//}