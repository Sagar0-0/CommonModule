package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.toMutableStateList
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import fit.asta.health.data.profile.remote.model.CurrentActivities_Field_Name
import fit.asta.health.data.profile.remote.model.LifeStyle
import fit.asta.health.data.profile.remote.model.LifestyleTargets_Field_Name
import fit.asta.health.data.profile.remote.model.Lifestyle_Screen_Name
import fit.asta.health.data.profile.remote.model.PhysicallyActive
import fit.asta.health.data.profile.remote.model.PreferredActivities_Field_Name
import fit.asta.health.data.profile.remote.model.TimeSchedule
import fit.asta.health.data.profile.remote.model.UserProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class LifestyleScreenState(
    val lifeStyle: LifeStyle,
    val coroutineScope: CoroutineScope,
    val onEvent: (UserProfileEvent) -> Unit
) {

    //Lifestyle Page
    private var sleepStartTime = mutableStateOf(lifeStyle.sleepTime.from?.toString())
    private var sleepEndTime = mutableStateOf(lifeStyle.sleepTime.to?.toString())
    private var jobStartTime = mutableStateOf(lifeStyle.workingTime.from?.toString())
    private var jobEndTime = mutableStateOf(lifeStyle.workingTime.to?.toString())

    private val currentActivities =
        (lifeStyle.curActivities ?: listOf()).toMutableStateList()
    private val preferredActivities =
        (lifeStyle.prefActivities ?: listOf()).toMutableStateList()
    private val lifestyleTargets =
        (lifeStyle.lifeStyleTargets ?: listOf()).toMutableStateList()
    private val physicallyActive = PhysicallyActive.LOW.value

    private val updateLifestyle = LifeStyle(
        physicalActive = physicallyActive,
        workingEnv = lifeStyle.workingEnv,
        workStyle = lifeStyle.workStyle,
        workingHours = lifeStyle.workingHours,
        curActivities = currentActivities,
        prefActivities = preferredActivities,
        lifeStyleTargets = lifestyleTargets,
        workingTime = TimeSchedule(
            from = jobStartTime.value?.toFloat(),
            to = jobEndTime.value?.toFloat()
        ),
        sleepTime = TimeSchedule(
            from = sleepStartTime.value?.toFloat(),
            to = sleepEndTime.value?.toFloat()
        )
    )

    private val currentListIndex = mutableIntStateOf(0)

    val bottomSheets: List<UserProfileState.ProfileBottomSheetPicker> = listOf(
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
    fun openLifestyleBottomSheet(
        sheetState: SheetState,
        index: Int,
        bottomSheetVisible: MutableState<Boolean>
    ) {
        currentListIndex.intValue = index
        bottomSheetVisible.value = true
        coroutineScope.launch { sheetState.expand() }
        getHealthProperties()
    }

    private fun getHealthProperties() {
        onEvent(UserProfileEvent.GetHealthProperties(bottomSheets[currentListIndex.intValue].getQueryParam))
    }


    fun saveProperties(list: List<UserProperties>) {
        bottomSheets[currentListIndex.intValue].list.apply {
            clear()
            addAll(list)
        }
        onEvent(
            UserProfileEvent.SavePropertiesList(
                Lifestyle_Screen_Name,
                bottomSheets[currentListIndex.intValue].fieldName,
                list
            )
        )
    }

    fun getUpdatedData(): LifeStyle {
        return updateLifestyle
    }

    private val currentTimerIndex = mutableIntStateOf(0)

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

    fun setCurrentItemTime(time: String) {
        timersList[currentTimerIndex.intValue].value = time
    }

    fun getCurrentList() = bottomSheets[currentListIndex.intValue].list.toList()

    fun openTimer(index: Int, useCaseState: UseCaseState) {
        currentTimerIndex.intValue = index
        useCaseState.show()
    }

    companion object {
        fun Saver(
            coroutineScope: CoroutineScope,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<LifestyleScreenState, *> = listSaver(
            save = {
                listOf(
                    it.updateLifestyle,
                    it.currentListIndex.intValue,
                    it.currentTimerIndex.intValue
                )
            },
            restore = {
                LifestyleScreenState(
                    lifeStyle = it[0] as LifeStyle,
                    coroutineScope,
                    onEvent
                ).apply {
                    this.currentListIndex.intValue = it[1] as Int
                    this.currentTimerIndex.intValue = it[2] as Int
                }
            }
        )
    }

    data class ProfileTimePicker(
        val title: String,
        val startButtonTitle: String,
        val endButtonTitle: String,
        val startTime: MutableState<String?>,
        val endTime: MutableState<String?>,
        val startIndex: Int,
        val endIndex: Int
    )
}