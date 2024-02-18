package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.Health
import fit.asta.health.data.profile.remote.model.HealthProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class HealthScreenState(
    val health: Health,
    val healthPropertiesState: UiState<List<HealthProperties>>,
    val coroutineScope: CoroutineScope,
    val onEvent: (UserProfileEvent) -> Unit
) {

    //Health Page
    private val medications = (health.medications ?: listOf()).toMutableStateList()
    private val targets = (health.targets ?: listOf()).toMutableStateList()
    private val ailments = (health.ailments ?: listOf()).toMutableStateList()
    private val healthHistory = (health.healthHistory ?: listOf()).toMutableStateList()
    private val injuries = (health.injuries ?: listOf()).toMutableStateList()
    private val bodyPart = (health.bodyPart ?: listOf()).toMutableStateList()
    private val addiction = (health.addiction ?: listOf()).toMutableStateList()
    private val injurySince by mutableStateOf(health.injurySince?.toString())

    private val updatedHealth = Health(
        medications = medications,
        targets = targets,
        ailments = ailments,
        healthHistory = healthHistory,
        injuries = injuries,
        bodyPart = bodyPart,
        addiction = addiction,
        injurySince = injurySince?.toIntOrNull()
    )

    private var currentBottomSheetIndex = 0

    val bottomSheets: List<HealthBottomSheet> = listOf(
        HealthBottomSheet(
            "hh",
            "Health History",
            healthHistory,
        ),
        HealthBottomSheet(
            "injury",
            "Injuries",
            injuries,
        ),
        HealthBottomSheet(
            "ailment",
            "Ailments",
            ailments,
        ),
        HealthBottomSheet(
            "med",
            "Medications",
            medications,
        ),
        HealthBottomSheet(
            "tgt",
            "Targets",
            targets,
        ),
        HealthBottomSheet(
            "bp",
            "Body parts",
            bodyPart,
        ),
        HealthBottomSheet(
            "bp",
            "Addictions",
            addiction,
        ),
    )

    fun addProperty(healthProperties: HealthProperties) {
        bottomSheets[currentBottomSheetIndex].list.add(healthProperties)
    }

    fun removeProperty(healthProperties: HealthProperties) {
        bottomSheets[currentBottomSheetIndex].list.remove(healthProperties)
    }

    fun isPropertySelected(healthProperties: HealthProperties): Boolean {
        return bottomSheets[currentBottomSheetIndex].list.contains(healthProperties)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun openHealthBottomSheet(
        sheetState: SheetState,
        index: Int,
        bottomSheetVisible: MutableState<Boolean>
    ) {
        currentBottomSheetIndex = index
        bottomSheetVisible.value = true
        coroutineScope.launch { sheetState.expand() }
        getHealthProperties()
    }

    private fun getHealthProperties() {
        onEvent(UserProfileEvent.GetHealthProperties(bottomSheets[currentBottomSheetIndex].id))
    }

    fun getHealthData(): Health {
        return updatedHealth
    }

    companion object {
        fun Saver(
            healthPropertiesState: UiState<List<HealthProperties>>,
            coroutineScope: CoroutineScope,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<HealthScreenState, *> = listSaver(
            save = {
                listOf(
                    it.updatedHealth,
                    it.currentBottomSheetIndex
                )
            },
            restore = {
                HealthScreenState(
                    health = it[0] as Health,
                    healthPropertiesState,
                    coroutineScope,
                    onEvent
                ).apply {
                    this.currentBottomSheetIndex = it[1] as Int
                }
            }
        )
    }

    data class HealthBottomSheet(
        val id: String,
        val name: String,
        val list: SnapshotStateList<HealthProperties>
    )
}