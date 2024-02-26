package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.vector.ImageVector
import fit.asta.health.data.profile.remote.model.CurrentActivities_Field_Name
import fit.asta.health.data.profile.remote.model.LifeStyle
import fit.asta.health.data.profile.remote.model.LifestyleTargets_Field_Name
import fit.asta.health.data.profile.remote.model.Lifestyle_Screen_Name
import fit.asta.health.data.profile.remote.model.PhysicallyActive
import fit.asta.health.data.profile.remote.model.PreferredActivities_Field_Name
import fit.asta.health.data.profile.remote.model.SleepTime_Field_Name
import fit.asta.health.data.profile.remote.model.TimeSchedule
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.data.profile.remote.model.WorkingTime_Field_Name
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class LifestyleScreenState(
    val lifeStyle: LifeStyle,
    val coroutineScope: CoroutineScope,
    val onEvent: (UserProfileEvent) -> Unit
) {

    //Properties content logic
    private val currentActivities =
        (lifeStyle.curActivities ?: listOf()).toMutableStateList()
    private val preferredActivities =
        (lifeStyle.prefActivities ?: listOf()).toMutableStateList()
    private val lifestyleTargets =
        (lifeStyle.lifeStyleTargets ?: listOf()).toMutableStateList()
    private val physicallyActive = PhysicallyActive.LOW.value

    private var currentPropertyIndex by mutableIntStateOf(0)
    val propertiesList: List<UserProfileState.ProfileBottomSheetPicker> = listOf(
        UserProfileState.ProfileBottomSheetPicker(
            CurrentActivities_Field_Name,
            "activity",
            "Current Activities",
            currentActivities
        ),
        UserProfileState.ProfileBottomSheetPicker(
            PreferredActivities_Field_Name,
            "activity",
            "Preferred Activities",
            preferredActivities
        ),
        UserProfileState.ProfileBottomSheetPicker(
            LifestyleTargets_Field_Name,
            "goal",
            "Lifestyle Targets",
            lifestyleTargets
        )
    )

    @OptIn(ExperimentalMaterial3Api::class)
    fun openPropertiesBottomSheet(
        sheetState: SheetState,
        index: Int,
        bottomSheetVisible: MutableState<Boolean>
    ) {
        currentPropertyIndex = index
        bottomSheetVisible.value = true
        coroutineScope.launch { sheetState.expand() }
        getHealthProperties()
    }

    private fun getHealthProperties() {
        onEvent(UserProfileEvent.GetHealthProperties(propertiesList[currentPropertyIndex].getQueryParam))
    }

    fun getCurrentPropertiesList() = propertiesList[currentPropertyIndex].list.toList()
    fun saveProperties(list: List<UserProperties>) {
        propertiesList[currentPropertyIndex].list.apply {
            clear()
            addAll(list)
        }
        onEvent(
            UserProfileEvent.SavePropertiesList(
                Lifestyle_Screen_Name,
                propertiesList[currentPropertyIndex].fieldName,
                list
            )
        )
    }


    //Timer Content Logic


    private val sleepStartTime = mutableFloatStateOf(lifeStyle.sleepTime.from ?: 0.0f)
    private val sleepEndTime = mutableFloatStateOf(lifeStyle.sleepTime.to ?: 0.0f)
    private val jobStartTime = mutableFloatStateOf(lifeStyle.workingTime.from ?: 0.0f)
    private val jobEndTime = mutableFloatStateOf(lifeStyle.workingTime.to ?: 0.0f)

    private var currentTimerIndex by mutableIntStateOf(0)
    var timerSheetVisible by mutableStateOf(false)

    val timersList: List<ProfileTimePicker> = listOf(
        ProfileTimePicker(
            SleepTime_Field_Name,
            "Sleep Schedule",
            sleepStartTime,
            sleepEndTime,
            Icons.Default.Bedtime
        ),
        ProfileTimePicker(
            WorkingTime_Field_Name,
            "Work Schedule",
            jobStartTime,
            jobEndTime,
            Icons.Default.Work
        )
    )

    @OptIn(ExperimentalMaterial3Api::class)
    fun openTimerSheet(index: Int, sheetState: SheetState) {
        currentTimerIndex = index
        timerSheetVisible = true
        coroutineScope.launch { sheetState.expand() }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun closeTimerSheet(sheetState: SheetState) {
        timerSheetVisible = false
        coroutineScope.launch { sheetState.hide() }
    }

    fun saveTime(from: Float, to: Float) {
        timersList[currentTimerIndex].startTime.floatValue = from
        timersList[currentTimerIndex].endTime.floatValue = to
        onEvent(
            UserProfileEvent.SaveTimeSchedule(
                Lifestyle_Screen_Name,
                timersList[currentTimerIndex].fieldName,
                TimeSchedule(
                    from, to
                )
            )
        )
    }

    private val updateLifestyle = LifeStyle(
        physicalActive = physicallyActive,
        workingEnv = lifeStyle.workingEnv,
        workStyle = lifeStyle.workStyle,
        workingHours = lifeStyle.workingHours,
        curActivities = currentActivities,
        prefActivities = preferredActivities,
        lifeStyleTargets = lifestyleTargets,
        workingTime = TimeSchedule(
            from = jobStartTime.floatValue,
            to = jobEndTime.floatValue
        ),
        sleepTime = TimeSchedule(
            from = sleepStartTime.floatValue,
            to = sleepEndTime.floatValue
        )
    )

    companion object {
        fun Saver(
            coroutineScope: CoroutineScope,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<LifestyleScreenState, *> = listSaver(
            save = {
                listOf(
                    it.updateLifestyle,
                    it.currentPropertyIndex,
                    it.currentTimerIndex,
                    it.timerSheetVisible
                )
            },
            restore = {
                LifestyleScreenState(
                    lifeStyle = it[0] as LifeStyle,
                    coroutineScope,
                    onEvent
                ).apply {
                    this.currentPropertyIndex = it[1] as Int
                    this.currentTimerIndex = it[2] as Int
                    this.timerSheetVisible = it[3] as Boolean
                }
            }
        )
    }

    data class ProfileTimePicker(
        val fieldName: String,
        val title: String,
        var startTime: MutableFloatState,
        var endTime: MutableFloatState,
        val imageVector: ImageVector
    )
}