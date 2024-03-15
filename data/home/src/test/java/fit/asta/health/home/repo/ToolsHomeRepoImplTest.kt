package fit.asta.health.home.repo

import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.home.remote.ToolsHomeApi
import fit.asta.health.home.remote.model.ToolsHome
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ToolsHomeRepoImplTest {
    private lateinit var repo: ToolsHomeRepoImpl

    @RelaxedMockK
    lateinit var api: ToolsHomeApi

    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        repo = ToolsHomeRepoImpl(
            api, UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `getHomeData, return Success Response`() = runTest {
        coEvery {
            api.getHomeData(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns Response(data = ToolsHome())
        val res = repo.getHomeData("", "", "", "", 0, 0, "")
        coVerify { api.getHomeData("", "", "", "", 0) }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `getHomeData, return Error Response`() = runTest {
        coEvery { api.getHomeData(any(), any(), any(), any(), any()) } throws Exception()
        val res = repo.getHomeData("", "", "", "", 0, 0, "")
        coVerify { api.getHomeData("", "", "", "", 0) }
        assert(res is ResponseState.ErrorMessage)
    }
}