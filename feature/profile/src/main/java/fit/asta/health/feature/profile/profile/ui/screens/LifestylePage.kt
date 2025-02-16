package fit.asta.health.feature.profile.profile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.data.profile.remote.model.WorkEnvironments
import fit.asta.health.data.profile.remote.model.WorkStyles
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.appRememberModalBottomSheetState
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetListItemPicker
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetProperties
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetRadioList
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetTimePicker
import fit.asta.health.feature.profile.profile.ui.components.ClickableTextBox
import fit.asta.health.feature.profile.profile.ui.components.PageNavigationButtons
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState

@Composable
fun LifestylePage(
    userProfileState: UserProfileState,
    userPropertiesState: UiState<List<UserProperties>>
) {
    val propertiesSheetState = appRememberModalBottomSheetState()
    val timerSheetState = appRememberModalBottomSheetState()
    val bottomSheetVisible = rememberSaveable { mutableStateOf(false) }

    val workStyleBottomSheetState = appRememberModalBottomSheetState()
    val workingEnvBottomSheetState = appRememberModalBottomSheetState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppTheme.spacing.level2)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier)

        ClickableTextBox(
            label = "Work Style",
            value = WorkStyles.getType(userProfileState.lifestyleScreenState.workStyle)?.name
                ?: "Select Type",
            leadingIcon = Icons.Default.Work
        ) {
            userProfileState.openSheet(
                workStyleBottomSheetState,
                userProfileState.lifestyleScreenState.workStyleBottomSheetVisible
            )
        }

        ClickableTextBox(
            label = "Work Environment",
            value = WorkEnvironments.getType(userProfileState.lifestyleScreenState.workingEnv)?.name
                ?: "Select Type",
            leadingIcon = Icons.Default.Work
        ) {
            userProfileState.openSheet(
                workingEnvBottomSheetState,
                userProfileState.lifestyleScreenState.workingEnvBottomSheetVisible
            )
        }

        userProfileState.lifestyleScreenState.timersList.forEachIndexed { index, it ->
            ClickableTextBox(
                label = it.title,
                value = "${it.startHour.intValue}:${it.startMinute.intValue} - ${it.endHour.intValue}:${it.endMinute.intValue}",
                leadingIcon = it.imageVector
            ) {
                userProfileState.lifestyleScreenState.openTimerSheet(index, timerSheetState)
            }
        }

        userProfileState.lifestyleScreenState.propertiesList.forEachIndexed { index, type ->
            BottomSheetListItemPicker(
                name = type.name,
                list = type.list.toList()
            ) {
                userProfileState.lifestyleScreenState.openPropertiesBottomSheet(
                    propertiesSheetState,
                    index,
                    bottomSheetVisible
                )
            }
        }

        PageNavigationButtons(
            onPrevious = {
                userProfileState.currentPageIndex--
            },
            onNext = {
                userProfileState.currentPageIndex++
            }
        )

        Spacer(modifier = Modifier)


        //Dialogs
        BottomSheetRadioList(
            isVisible = userProfileState.lifestyleScreenState.workStyleBottomSheetVisible.value,
            sheetState = workStyleBottomSheetState,
            isValid = {
                it != null
            },
            selectedIndex = WorkStyles.indexOf(userProfileState.lifestyleScreenState.workStyle),
            list = WorkStyles.entries.map { it.name },
            onDismissRequest = {
                userProfileState.closeSheet(
                    workStyleBottomSheetState,
                    userProfileState.lifestyleScreenState.workStyleBottomSheetVisible
                )
            }
        ) {
            if (it != null) {
                userProfileState.lifestyleScreenState.saveWorkStyle(it)
            }
        }

        BottomSheetRadioList(
            isVisible = userProfileState.lifestyleScreenState.workingEnvBottomSheetVisible.value,
            sheetState = workingEnvBottomSheetState,
            isValid = {
                it != null
            },
            selectedIndex = WorkEnvironments.indexOf(userProfileState.lifestyleScreenState.workingEnv),
            list = WorkEnvironments.entries.map { it.name },
            onDismissRequest = {
                userProfileState.closeSheet(
                    workingEnvBottomSheetState,
                    userProfileState.lifestyleScreenState.workingEnvBottomSheetVisible
                )
            }
        ) {
            if (it != null) {
                userProfileState.lifestyleScreenState.saveWorkingEnv(it)
            }
        }

        BottomSheetProperties(
            isVisible = bottomSheetVisible.value,
            sheetState = propertiesSheetState,
            currentList = userProfileState.lifestyleScreenState.getCurrentPropertiesList(),
            onDismissRequest = {
                userProfileState.closeSheet(
                    propertiesSheetState,
                    bottomSheetVisible
                )
            },
            userPropertiesState = userPropertiesState
        ) {
            userProfileState.lifestyleScreenState.saveProperties(it)
            userProfileState.closeSheet(propertiesSheetState, bottomSheetVisible)
        }

        BottomSheetTimePicker(
            isVisible = userProfileState.lifestyleScreenState.timerSheetVisible,
            isValid = { startHour, startMinute, endHour, endMinute ->
                endHour - startHour > 0 || endMinute - startMinute > 0
            },
            sheetState = timerSheetState,
            onDismissRequest = {
                userProfileState.lifestyleScreenState.closeTimerSheet(timerSheetState)
            },
            onSaveClick = { startHour, startMinute, endHour, endMinute ->
                userProfileState.lifestyleScreenState.saveTime(
                    startHour = startHour,
                    startMinute = startMinute,
                    endHour = endHour,
                    endMinute = endMinute
                )
                userProfileState.lifestyleScreenState.closeTimerSheet(timerSheetState)
            }
        )
    }
}