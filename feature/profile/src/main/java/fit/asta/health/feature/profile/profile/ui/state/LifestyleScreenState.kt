package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.toMutableStateList
import fit.asta.health.data.profile.remote.model.LifeStyle
import fit.asta.health.data.profile.remote.model.TimeSchedule
import fit.asta.health.data.profile.remote.model.UserProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LifestyleScreenState(
    val lifeStyle: LifeStyle,
    val coroutineScope: CoroutineScope,
    val onEvent: (UserProfileEvent) -> Unit
) {

    //Lifestyle Page
    private var sleepStartTime = mutableStateOf(lifeStyle.sleep.from.toString())
    private var sleepEndTime = mutableStateOf(lifeStyle.sleep.to.toString())
    private var jobStartTime = mutableStateOf(lifeStyle.workingTime.from.toString())
    private var jobEndTime = mutableStateOf(lifeStyle.workingTime.to.toString())

    private val currentActivities =
        (lifeStyle.curActivities ?: listOf()).toMutableStateList()
    private val preferredActivities =
        (lifeStyle.prefActivities ?: listOf()).toMutableStateList()
    private val lifestyleTargets =
        (lifeStyle.lifeStyleTargets ?: listOf()).toMutableStateList()

    private val updateLifestyle = LifeStyle(
        physicalActivity = lifeStyle.physicalActivity,
        workingEnv = lifeStyle.workingEnv,
        workStyle = lifeStyle.workStyle,
        workingHours = lifeStyle.workingHours,
        curActivities = currentActivities,
        prefActivities = preferredActivities,
        lifeStyleTargets = lifestyleTargets,
        workingTime = TimeSchedule(
            from = jobStartTime.value.toFloat(),
            to = jobEndTime.value.toFloat()
        ),
        sleep = TimeSchedule(
            from = sleepStartTime.value.toFloat(),
            to = sleepEndTime.value.toFloat()
        )
    )


    val bottomSheets: List<UserProfileState.ProfileBottomSheetPicker> = listOf(
        UserProfileState.ProfileBottomSheetPicker(
            "activity",
            "Current Activities",
            currentActivities
        ),
        UserProfileState.ProfileBottomSheetPicker(
            "activity",
            "Preferred Activities",
            preferredActivities
        ),
        UserProfileState.ProfileBottomSheetPicker(
            "goal",
            "Lifestyle Targets",
            lifestyleTargets
        )
    )

    val lifestyleTimePickers: List<ProfileTimePicker> = listOf(
        ProfileTimePicker(
            "Sleep Schedule",
            "Select Bed Time",
            "Select Wakeup Time",
            sleepStartTime,
            sleepEndTime,
            0, 1
        ),
        ProfileTimePicker(
            "Job Schedule",
            "Select Job Start Time",
            "Select Job End Time",
            jobStartTime,
            jobEndTime,
            2, 3
        )
    )
    private val timersList = listOf(
        sleepStartTime,
        sleepEndTime,
        jobStartTime,
        jobEndTime
    )


    fun addProperty(sheetIndex: Int, userProperties: UserProperties) {
        bottomSheets[sheetIndex].list.add(userProperties)
    }

    fun removeProperty(sheetIndex: Int, userProperties: UserProperties) {
        bottomSheets[sheetIndex].list.remove(userProperties)
    }

    fun isPropertySelected(sheetIndex: Int, userProperties: UserProperties): Boolean {
        return bottomSheets[sheetIndex].list.contains(userProperties)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun openLifestyleBottomSheet(
        sheetState: SheetState,
        index: MutableState<Int>,
        bottomSheetVisible: MutableState<Boolean>
    ) {
//        currentBottomSheetIndex = index
        bottomSheetVisible.value = true
        coroutineScope.launch { sheetState.expand() }
        getHealthProperties(index.value)
    }

    private fun getHealthProperties(sheetIndex: Int) {
        onEvent(UserProfileEvent.GetHealthProperties(bottomSheets[sheetIndex].id))
    }

    fun getLifestyleData(): LifeStyle {
        return updateLifestyle
    }

    fun setCurrentItemTime(index: Int, time: String) {
        timersList[index].value = time
    }

    companion object {
        fun Saver(
            coroutineScope: CoroutineScope,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<LifestyleScreenState, *> = listSaver(
            save = {
                listOf(
                    it.updateLifestyle
                )
            },
            restore = {
                LifestyleScreenState(
                    lifeStyle = it[0] as LifeStyle,
                    coroutineScope,
                    onEvent
                )
            }
        )
    }

    data class ProfileTimePicker(
        val title: String,
        val startButtonTitle: String,
        val endButtonTitle: String,
        val startTime: MutableState<String>,
        val endTime: MutableState<String>,
        val startIndex: Int,
        val endIndex: Int
    )
}