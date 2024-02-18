package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.data.profile.remote.model.LifeStyle
import fit.asta.health.data.profile.remote.model.TimeSchedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LifestyleScreenState(
    val lifeStyle: LifeStyle,
    val healthPropertiesState: UiState<List<HealthProperties>>,
    val coroutineScope: CoroutineScope,
    val onEvent: (UserProfileEvent) -> Unit
) {

    //Lifestyle Page
    private var sleepStartTime = mutableStateOf(lifeStyle.sleep.from.toString())
    private var sleepEndTime = mutableStateOf(lifeStyle.sleep.to.toString())
    private var jobStartTime = mutableStateOf(lifeStyle.workingTime.from.toString())
    private var jobEndTime = mutableStateOf(lifeStyle.workingTime.to.toString())
    private var currentTimerIndex by mutableIntStateOf(0)

    private val currentActivities =
        (lifeStyle.curActivities ?: listOf()).toMutableStateList()
    private val preferredActivities =
        (lifeStyle.prefActivities ?: listOf()).toMutableStateList()
    private val lifestyleTargets =
        (lifeStyle.lifeStyleTargets ?: listOf()).toMutableStateList()
    private var currentBottomSheetIndex = 0

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
        ),
        ProfileTimePicker(
            "Job Schedule",
            "Select Job Start Time",
            "Select Job End Time",
            jobStartTime,
            jobEndTime,
        )
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
    fun openLifestyleBottomSheet(
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

    fun getLifestyleData(): LifeStyle {
        return updateLifestyle
    }

    fun setCurrentItemTime(time: String) {

    }

    data class ProfileTimePicker(
        val title: String,
        val startButtonTitle: String,
        val endButtonTitle: String,
        val startTime: MutableState<String>,
        val endTime: MutableState<String>
    )

    data class TimerType(
        val title: String,
        val onTimeChange: (String) -> Unit
    )

}