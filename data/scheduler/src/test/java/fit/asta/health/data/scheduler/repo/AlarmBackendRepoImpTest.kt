package fit.asta.health.data.scheduler.repo

import android.content.Context
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.SchedulerApiService
import fit.asta.health.data.scheduler.remote.model.TodaySchedules
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerDeleteResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerGetListResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerGetResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerPutResponse
import fit.asta.health.data.scheduler.remote.net.tag.AstaGetTagsListResponse
import fit.asta.health.data.scheduler.remote.net.tag.NetCustomTag
import fit.asta.health.network.data.Status
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AlarmBackendRepoImpTest {
    private lateinit var repo: AlarmBackendRepoImp

    @MockK(relaxed = true)
    lateinit var remoteApi: SchedulerApiService

    @MockK
    lateinit var context: Context

    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        repo = spyk(
            AlarmBackendRepoImp(
                context = context, remoteApi
            )
        )
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `getTodayDataFromBackend,return Success`() = runTest {
        coEvery {
            remoteApi.getTodayDataFromBackend(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns TodaySchedules()
        val res = repo.getTodayDataFromBackend("", "", "", 0f, 0f)
        coVerify { remoteApi.getTodayDataFromBackend("", "", "", 0f, 0f) }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `getTodayDataFromBackend,return Error`() = runTest {
        coEvery {
            remoteApi.getTodayDataFromBackend(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } throws Exception()
        val res = repo.getTodayDataFromBackend("", "", "", 0f, 0f)
        coVerify { remoteApi.getTodayDataFromBackend("", "", "", 0f, 0f) }
        assert(res is ResponseState.ErrorMessage)
    }

    @Test
    fun `getScheduleDataFromBackend,return Success`() = runTest {
        coEvery { remoteApi.getScheduleDataFromBackend(any()) } returns AstaSchedulerGetResponse(
            mockk(), mockk()
        )
        val res = repo.getScheduleDataFromBackend("")
        coVerify { remoteApi.getScheduleDataFromBackend("") }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `getScheduleDataFromBackend,return Error`() = runTest {
        coEvery { remoteApi.getScheduleDataFromBackend(any()) } throws Exception()
        val res = repo.getScheduleDataFromBackend("")
        coVerify { remoteApi.getScheduleDataFromBackend("") }
        assert(res is ResponseState.ErrorMessage)
    }

    @Test
    fun `getScheduleListDataFromBackend,return Success`() = runTest {
        coEvery { remoteApi.getScheduleListDataFromBackend(any()) } returns AstaSchedulerGetListResponse(
            mockk(), mockk()
        )
        val res = repo.getScheduleListDataFromBackend("")
        coVerify { remoteApi.getScheduleListDataFromBackend("") }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `getScheduleListDataFromBackend,return Error`() = runTest {
        coEvery { remoteApi.getScheduleListDataFromBackend(any()) } throws Exception()
        val res = repo.getScheduleListDataFromBackend("")
        coVerify { remoteApi.getScheduleListDataFromBackend("") }
        assert(res is ResponseState.ErrorMessage)
    }

    @Test
    fun `getTagListFromBackend,return Success`() = runTest {
        coEvery { remoteApi.getTagListFromBackend(any()) } returns AstaGetTagsListResponse(
            mockk(), mockk()
        )
        val res = repo.getTagListFromBackend("")
        coVerify { remoteApi.getTagListFromBackend("") }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `getTagListFromBackend,return Error`() = runTest {
        coEvery { remoteApi.getTagListFromBackend(any()) } throws Exception()
        val res = repo.getTagListFromBackend("")
        coVerify { remoteApi.getTagListFromBackend("") }
        assert(res is ResponseState.ErrorMessage)
    }

    @Test
    fun `updateScheduleDataOnBackend,return Success`() = runTest {
        coEvery { remoteApi.updateScheduleDataOnBackend(any()) } returns AstaSchedulerPutResponse()
        val alarmEntity = mockk<AlarmEntity>()
        val res = repo.updateScheduleDataOnBackend(alarmEntity)
        coVerify { remoteApi.updateScheduleDataOnBackend(alarmEntity) }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `updateScheduleDataOnBackend,return Error`() = runTest {
        coEvery { remoteApi.updateScheduleDataOnBackend(any()) } throws Exception()
        val alarmEntity = mockk<AlarmEntity>()
        val res = repo.updateScheduleDataOnBackend(alarmEntity)
        coVerify { remoteApi.updateScheduleDataOnBackend(alarmEntity) }
        assert(res is ResponseState.ErrorMessage)
    }

    @Test
    fun `deleteScheduleDataFromBackend,return Success`() = runTest {
        coEvery { remoteApi.deleteScheduleDataFromBackend(any()) } returns AstaSchedulerDeleteResponse(
            mockk(), mockk()
        )
        val res = repo.deleteScheduleDataFromBackend("123")
        coVerify { remoteApi.deleteScheduleDataFromBackend("123") }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `deleteScheduleDataFromBackend,return Error`() = runTest {
        coEvery { remoteApi.deleteScheduleDataFromBackend(any()) } throws Exception()
        val res = repo.deleteScheduleDataFromBackend("123")
        coVerify { remoteApi.deleteScheduleDataFromBackend("123") }
        assert(res is ResponseState.ErrorMessage)
    }

    @Test
    fun `deleteTagFromBackend,return Success`() = runTest {
        coEvery { remoteApi.deleteTagFromBackend(any(), any()) } returns Status()
        val res = repo.deleteTagFromBackend("", "")
        coVerify { remoteApi.deleteTagFromBackend("", "") }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `deleteTagFromBackend,return Error`() = runTest {
        coEvery { remoteApi.deleteTagFromBackend(any(), any()) } throws Exception()
        val res = repo.deleteTagFromBackend("", "")
        coVerify { remoteApi.deleteTagFromBackend("", "") }
        assert(res is ResponseState.ErrorMessage)
    }

    @Test
    fun `updateScheduleTag,return Success`() = runTest {
        coEvery { remoteApi.updateScheduleTag(any(), any()) } returns Status()
        val tag = NetCustomTag()
        val uri = mockk<android.net.Uri>()
        tag.localUrl = uri
        tag.name = "name"
        val res = repo.updateScheduleTag(tag)
        coVerify { remoteApi.updateScheduleTag(tag, any()) }
        assert(res is ResponseState.Success)
    }

    @Test
    fun `updateScheduleTag,return Error`() = runTest {
        coEvery { remoteApi.updateScheduleTag(any(), any()) } throws Exception()
        val tag = NetCustomTag()
        val uri = mockk<android.net.Uri>()
        tag.localUrl = uri
        tag.name = "name"
        val res = repo.updateScheduleTag(tag)
        coVerify { remoteApi.updateScheduleTag(tag, any()) }
        assert(res is ResponseState.ErrorMessage)
    }


}