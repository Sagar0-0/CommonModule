package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import com.maxkeppeker.sheets.core.models.base.UseCaseState
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
    private var sleepStartTime by mutableStateOf(lifeStyle.sleep.from.toString())
    private var sleepEndTime by mutableStateOf(lifeStyle.sleep.to.toString())
    private var jobStartTime by mutableStateOf(lifeStyle.workingTime.from.toString())
    private var jobEndTime by mutableStateOf(lifeStyle.workingTime.to.toString())
    private var currentTimerIndex by mutableIntStateOf(0)

    private val currentActivities =
        (lifeStyle.curActivities ?: listOf()).toMutableStateList()
    private val preferredActivities =
        (lifeStyle.prefActivities ?: listOf()).toMutableStateList()
    private val lifestyleTargets =
        (lifeStyle.lifeStyleTargets ?: listOf()).toMutableStateList()
    private var currentBottomSheetIndex by mutableIntStateOf(0)


    val lifestyleBottomSheetTypes: List<UserProfileState.ProfileBottomSheetPicker> = listOf(
        UserProfileState.ProfileBottomSheetPicker(
            "activity",
            "Current Activities",
            currentActivities.toList()
        ),
        UserProfileState.ProfileBottomSheetPicker(
            "activity",
            "Preferred Activities",
            preferredActivities.toList()
        ),
        UserProfileState.ProfileBottomSheetPicker(
            "goal",
            "Lifestyle Targets",
            lifestyleTargets.toList()
        )
    )
    private val lifestyleTimers: List<UserProfileState.TimerType> = listOf(
        UserProfileState.TimerType("Sleep start") {
            sleepStartTime = it
        },
        UserProfileState.TimerType("Sleep End") {
            sleepEndTime = it
        },
        UserProfileState.TimerType("Job start") {
            jobStartTime = it
        },
        UserProfileState.TimerType("Job End") {
            jobEndTime = it
        },
    )
    private var currentLifestyleTimerTypeIndex by mutableIntStateOf(0)

    val lifestyleTimePickers: List<UserProfileState.ProfileTimePicker> = listOf(
        UserProfileState.ProfileTimePicker(
            "Sleep Schedule",
            "Select Bed Time",
            "Select Wakeup Time",
            sleepStartTime,
            sleepEndTime,
            0,
            1
        ),
        UserProfileState.ProfileTimePicker(
            "Job Schedule",
            "Select Job Start Time",
            "Select Job End Time",
            jobStartTime,
            jobEndTime,
            2,
            3
        ),
    )

    @OptIn(ExperimentalMaterial3Api::class)
    fun openLifestyleBottomSheet(
        sheetState: SheetState,
        index: Int,
        bottomSheetVisible: MutableState<Boolean>
    ) {
        currentBottomSheetIndex = index
        bottomSheetVisible.value = true
        coroutineScope.launch { sheetState.expand() }
//        getHealthProperties()
    }

    fun openLifestyleTimeSelection(index: Int, useCaseState: UseCaseState) {
        currentLifestyleTimerTypeIndex = index
        useCaseState.show()
    }

    fun saveLifestyleTime(time: String) {
        lifestyleTimers[currentLifestyleTimerTypeIndex].onTimeChange(time)
    }

    fun getLifestyleData(): LifeStyle {
        return LifeStyle(
            physicalActivity = lifeStyle.physicalActivity,
            workingEnv = lifeStyle.workingEnv,
            workStyle = lifeStyle.workStyle,
            workingHours = lifeStyle.workingHours,
            curActivities = currentActivities,
            prefActivities = preferredActivities,
            lifeStyleTargets = lifestyleTargets,
            workingTime = TimeSchedule(
                from = jobStartTime.toFloat(),
                to = jobEndTime.toFloat()
            ),
            sleep = TimeSchedule(
                from = sleepStartTime.toFloat(),
                to = sleepEndTime.toFloat()
            )
        )
    }

}