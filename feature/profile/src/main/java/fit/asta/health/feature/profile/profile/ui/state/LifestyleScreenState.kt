package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
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
import fit.asta.health.data.profile.remote.model.Time
import fit.asta.health.data.profile.remote.model.TimeSchedule
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.data.profile.remote.model.WorkTime_Field_Name
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
    private val sleepStartHour = mutableIntStateOf(lifeStyle.sleepTime?.startTime?.hour ?: 0)
    private val sleepStartMinute = mutableIntStateOf(lifeStyle.sleepTime?.startTime?.minute ?: 0)
    private val sleepEndHour = mutableIntStateOf(lifeStyle.sleepTime?.endTime?.hour ?: 0)
    private val sleepEndMinute = mutableIntStateOf(lifeStyle.sleepTime?.endTime?.minute ?: 0)

    private val workStartHour = mutableIntStateOf(lifeStyle.workTime?.startTime?.hour ?: 0)
    private val workStartMinute = mutableIntStateOf(lifeStyle.workTime?.startTime?.minute ?: 0)
    private val workEndHour = mutableIntStateOf(lifeStyle.workTime?.endTime?.hour ?: 0)
    private val workEndMinute = mutableIntStateOf(lifeStyle.workTime?.endTime?.minute ?: 0)

    private var currentTimerIndex by mutableIntStateOf(0)
    var timerSheetVisible by mutableStateOf(false)

    val timersList: List<ProfileTimePicker> = listOf(
        ProfileTimePicker(
            fieldName = SleepTime_Field_Name,
            title = "Sleep Schedule",
            startHour = sleepStartHour,
            startMinute = sleepStartMinute,
            endHour = sleepEndHour,
            endMinute = sleepEndMinute,
            imageVector = Icons.Default.Bedtime
        ),
        ProfileTimePicker(
            fieldName = WorkTime_Field_Name,
            title = "Work Schedule",
            startHour = workStartHour,
            startMinute = workStartMinute,
            endHour = workEndHour,
            endMinute = workEndMinute,
            imageVector = Icons.Default.Work
        )
    )

    fun openTimerSheet(index: Int, sheetState: SheetState) {
        currentTimerIndex = index
        timerSheetVisible = true
        coroutineScope.launch { sheetState.expand() }
    }

    fun closeTimerSheet(sheetState: SheetState) {
        timerSheetVisible = false
        coroutineScope.launch { sheetState.hide() }
    }

    fun saveTime(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int) {
        timersList[currentTimerIndex].startHour.intValue = startHour
        timersList[currentTimerIndex].endHour.intValue = endHour
        timersList[currentTimerIndex].startMinute.intValue = startMinute
        timersList[currentTimerIndex].endMinute.intValue = endMinute
        onEvent(
            UserProfileEvent.SaveTimeSchedule(
                Lifestyle_Screen_Name,
                timersList[currentTimerIndex].fieldName,
                TimeSchedule(
                    startTime = Time(
                        hour = startHour,
                        minute = startMinute
                    ),
                    endTime = Time(
                        hour = endHour,
                        minute = endMinute
                    )
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
        workTime = TimeSchedule(
            startTime = Time(
                hour = workStartHour.intValue,
                minute = workStartMinute.intValue
            ),
            endTime = Time(
                hour = workEndHour.intValue,
                minute = workEndMinute.intValue
            )
        ),
        sleepTime = TimeSchedule(
            startTime = Time(
                hour = sleepStartHour.intValue,
                minute = sleepStartMinute.intValue
            ),
            endTime = Time(
                hour = sleepEndHour.intValue,
                minute = sleepEndMinute.intValue
            )
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
        var startHour: MutableIntState,
        var startMinute: MutableIntState,
        var endHour: MutableIntState,
        var endMinute: MutableIntState,
        val imageVector: ImageVector
    )
}