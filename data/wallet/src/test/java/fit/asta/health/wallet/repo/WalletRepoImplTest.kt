package fit.asta.health.wallet.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.wallet.remote.WalletApi
import fit.asta.health.wallet.remote.model.WalletResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WalletRepoImplTest {

    private lateinit var repo: WalletRepoImpl

    @RelaxedMockK
    lateinit var api: WalletApi


    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        repo = spyk(
            WalletRepoImpl(
                remoteApi = api,
                coroutineDispatcher = UnconfinedTestDispatcher()
            )
        )
    }

    @Test
    fun `getData, returns Success`() = runTest {
        coEvery { api.getData(any()) } returns WalletResponse()
        val response = repo.getData("")
        coVerify { api.getData("") }
        assert(response is ResponseState.Success)
    }

    @Test
    fun `getData with random exception, return Error Response`() = runTest {
        coEvery { api.getData(any()) } throws Exception()
        val response = repo.getData("")
        coVerify { api.getData("") }
        assert(response is ResponseState.Error)
    }
}