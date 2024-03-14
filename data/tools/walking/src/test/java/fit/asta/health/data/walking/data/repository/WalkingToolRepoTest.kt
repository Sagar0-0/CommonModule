package fit.asta.health.data.walking.data.repository

import fit.asta.health.common.utils.EmptyResponse
import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.walking.remote.WalkingApi
import fit.asta.health.data.walking.remote.model.HomeData
import fit.asta.health.data.walking.remote.model.PutData
import fit.asta.health.data.walking.remote.model.PutDayData
import fit.asta.health.data.walking.repo.WalkingToolRepo
import fit.asta.health.data.walking.repo.WalkingToolRepoImpl
import fit.asta.health.datastore.PrefManager
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WalkingToolRepoTest {
    private lateinit var repo: WalkingToolRepo

    @MockK(relaxed = true)
    lateinit var api: WalkingApi

    private val prefManager: PrefManager = mockk(relaxed = true)


    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        repo = spyk(
            WalkingToolRepoImpl(api = api, prefManager = prefManager)
        )
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun updateStepsPermissionRejectedCount() = runTest {
        coEvery { prefManager.setStepsPermissionRejectedCount(any()) } just Runs
        repo.updateStepsPermissionRejectedCount(1)
        coVerify { prefManager.setStepsPermissionRejectedCount(1) }
    }

    @Test
    fun `getHomeData, returns Success`() = runTest {
        coEvery { api.getHomeData(any()) } returns Response(data = HomeData(mockk(), mockk()))
        repo.getHomeData("")
        coVerify { api.getHomeData("") }
    }

    @Test
    fun `getHomeData, returns Error`() = runTest {
        coEvery { api.getHomeData(any()) } throws Exception()
        val data = repo.getHomeData("")
        coVerify { api.getHomeData("") }
        assert(data is ResponseState.ErrorMessage)
    }

    @Test
    fun `getSheetData, returns Success`() = runTest {
        coEvery { api.getSheetListData(any()) } returns Response(data = emptyList())
        repo.getSheetData("")
        coVerify { api.getSheetListData("") }
    }

    @Test
    fun `getSheetData, returns Error`() = runTest {
        coEvery { api.getSheetListData(any()) } throws Exception()
        val data = repo.getSheetData("")
        coVerify { api.getSheetListData("") }
        assert(data is ResponseState.ErrorMessage)
    }

    @Test
    fun `getSheetGoalsData, returns Success`() = runTest {
        coEvery { api.getSheetGoalsListData(any()) } returns Response(data = emptyList())
        repo.getSheetGoalsData("")
        coVerify { api.getSheetGoalsListData("") }
    }

    @Test
    fun `getSheetGoalsData, returns Error`() = runTest {
        coEvery { api.getSheetGoalsListData(any()) } throws Exception()
        val data = repo.getSheetGoalsData("")
        coVerify { api.getSheetGoalsListData("") }
        assert(data is ResponseState.ErrorMessage)
    }

    @Test
    fun `putData, returns Success`() = runTest {
        coEvery { api.putData(any()) } returns Response(data = EmptyResponse())
        val putData: PutData = mockk()
        repo.putData(putData)
        coVerify { api.putData(putData) }
    }

    @Test
    fun `putData, returns Error`() = runTest {
        coEvery { api.putData(any()) } throws Exception()
        val putData: PutData = mockk()
        val data = repo.putData(putData)
        coVerify { api.putData(putData) }
        assert(data is ResponseState.ErrorMessage)
    }

    @Test
    fun `putDayData, returns Success`() = runTest {
        coEvery { api.putDayData(any()) } returns Response(data = EmptyResponse())
        val putData: PutDayData = mockk()
        repo.putDayData(putData)
        coVerify { api.putDayData(putData) }
    }

    @Test
    fun `putDayData, returns Error`() = runTest {
        coEvery { api.putDayData(any()) } throws Exception()
        val putData: PutDayData = mockk()
        val data = repo.putDayData(putData)
        coVerify { api.putDayData(putData) }
        assert(data is ResponseState.ErrorMessage)
    }
}