package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import fit.asta.health.data.profile.remote.model.Addictions_Field_Name
import fit.asta.health.data.profile.remote.model.Ailments_Field_Name
import fit.asta.health.data.profile.remote.model.BodyParts_Field_Name
import fit.asta.health.data.profile.remote.model.Health
import fit.asta.health.data.profile.remote.model.HealthHistory_Field_Name
import fit.asta.health.data.profile.remote.model.HealthTargets_Field_Name
import fit.asta.health.data.profile.remote.model.Health_Screen_Name
import fit.asta.health.data.profile.remote.model.Injuries_Field_Name
import fit.asta.health.data.profile.remote.model.Medications_Field_Name
import fit.asta.health.data.profile.remote.model.UserProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class HealthScreenState(
    val health: Health,
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

    private val currentListIndex = mutableIntStateOf(0)

    val bottomSheets: List<HealthBottomSheet> = listOf(
        HealthBottomSheet(
            HealthHistory_Field_Name,
            "ailment",
            "Health History",
            healthHistory,
        ),
        HealthBottomSheet(
            Injuries_Field_Name,
            "injury",
            "Injuries",
            injuries,
        ),
        HealthBottomSheet(
            Ailments_Field_Name,
            "ailment",
            "Ailments",
            ailments,
        ),
        HealthBottomSheet(
            Medications_Field_Name,
            "med",
            "Medications",
            medications,
        ),
        HealthBottomSheet(
            HealthTargets_Field_Name,
            "tgt",
            "Targets",
            targets,
        ),
        HealthBottomSheet(
            BodyParts_Field_Name,
            "bp",
            "Body parts",
            bodyPart,
        ),
        HealthBottomSheet(
            Addictions_Field_Name,
            "bp",
            "Addictions",
            addiction,
        )
    )

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

    @OptIn(ExperimentalMaterial3Api::class)
    fun openHealthBottomSheet(
        sheetState: SheetState,
        index: Int,
        bottomSheetVisible: MutableState<Boolean>
    ) {
        currentListIndex.intValue = index
        getUserProperties(index)
        bottomSheetVisible.value = true
        coroutineScope.launch { sheetState.expand() }
    }

    private fun getUserProperties(sheetIndex: Int) {
        onEvent(UserProfileEvent.GetHealthProperties(bottomSheets[sheetIndex].getQueryParam))
    }

    fun getUpdatedData(): Health {
        return updatedHealth
    }

    fun saveProperties(list: List<UserProperties>) {
        bottomSheets[currentListIndex.intValue].list.apply {
            clear()
            addAll(list)
        }
        onEvent(
            UserProfileEvent.SavePropertiesList(
                Health_Screen_Name,
                bottomSheets[currentListIndex.intValue].fieldName,
                list
            )
        )
    }

    fun getCurrentList() = bottomSheets[currentListIndex.intValue].list.toList()


    companion object {
        fun Saver(
            coroutineScope: CoroutineScope,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<HealthScreenState, *> = listSaver(
            save = {
                listOf(
                    it.updatedHealth,
                    it.currentListIndex.intValue
                )
            },
            restore = {
                HealthScreenState(
                    health = it[0] as Health,
                    coroutineScope,
                    onEvent
                ).apply {
                    this.currentListIndex.intValue = it[1] as Int
                }
            }
        )
    }

    data class HealthBottomSheet(
        val fieldName: String,
        val getQueryParam: String,
        val title: String,
        val list: SnapshotStateList<UserProperties>
    )
}