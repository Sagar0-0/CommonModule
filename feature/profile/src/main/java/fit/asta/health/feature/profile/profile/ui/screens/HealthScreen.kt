package fit.asta.health.feature.profile.profile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.appRememberModalBottomSheetState
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetListItemPicker
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetProperties
import fit.asta.health.feature.profile.profile.ui.components.PageNavigationButtons
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState

@Composable
fun HealthScreen(
    userProfileState: UserProfileState,
    userPropertiesState: UiState<List<UserProperties>>
) {
    val bottomSheetState = appRememberModalBottomSheetState()
    val bottomSheetVisible = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppTheme.spacing.level2)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier)
        userProfileState.healthScreenState.bottomSheets.forEachIndexed { index, type ->
            BottomSheetListItemPicker(
                name = type.title,
                list = type.list,
            ) {
                userProfileState.healthScreenState.openHealthBottomSheet(
                    bottomSheetState,
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
        BottomSheetProperties(
            isVisible = bottomSheetVisible.value,
            sheetState = bottomSheetState,
            currentList = userProfileState.healthScreenState.getCurrentList(),
            onDismissRequest = {
                userProfileState.closeSheet(
                    bottomSheetState,
                    bottomSheetVisible
                )
            },
            userPropertiesState = userPropertiesState
        ) {
            userProfileState.healthScreenState.saveProperties(it)
            userProfileState.closeSheet(bottomSheetState, bottomSheetVisible)
        }
    }
}
