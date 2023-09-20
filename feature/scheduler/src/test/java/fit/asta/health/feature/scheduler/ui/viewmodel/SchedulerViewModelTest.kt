package fit.asta.health.feature.scheduler.ui.viewmodel

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.data.scheduler.db.entity.TagEntity
import fit.asta.health.data.scheduler.remote.net.tag.NetCustomTag
import fit.asta.health.data.scheduler.repo.AlarmBackendRepoImp
import fit.asta.health.data.scheduler.repo.AlarmLocalRepoImp
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.scheduler.util.StateManager
import fit.asta.health.network.data.Status
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

class SchedulerViewModelTest : BaseTest() {
    private lateinit var viewmodel: SchedulerViewModel

    private val alarmLocalRepo: AlarmLocalRepoImp = mockk(relaxed = true)
    private val backendRepo: AlarmBackendRepoImp = mockk(relaxed = true)
    private val prefManager: PrefManager = mockk(relaxed = true)
    private val stateManager: StateManager = mockk(relaxed = true)

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewmodel = spyk(
            SchedulerViewModel(
                alarmLocalRepo = alarmLocalRepo,
                backendRepo = backendRepo,
                prefManager = prefManager,
                stateManager = stateManager, "1234"
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Test
    fun `getTagData Success,returns List`() = runTest {
        coEvery { alarmLocalRepo.getAllTags() } returns flow {
            emit(
                listOf(
                    TagEntity(uid = "1234"),
                    TagEntity()
                )
            )
        }
        viewmodel.getTagData()
        coVerify { alarmLocalRepo.getAllTags() }
        viewmodel.tagsList.test {
            assert(this.awaitItem().toList().isNotEmpty())
        }
//        viewmodel.customTagList.test {
//            assert(this.awaitItem().toList().isNotEmpty())
//        }
    }

    @Test
    fun updateServerTag() = runTest {
        coEvery { backendRepo.updateScheduleTag(any()) } returns ResponseState.Success(Status())
        viewmodel.updateServerTag("", "")
        coVerify { backendRepo.updateScheduleTag(NetCustomTag(uid = "1234")) }
    }

    @Test
    fun selectedTag() = runTest {
        val tag = TagEntity(uid = "1234", name = "hi")
        viewmodel.selectedTag(tag)
        viewmodel.alarmSettingUiState.test {
            assert(this.awaitItem().tagName == tag.name)
        }
    }
}