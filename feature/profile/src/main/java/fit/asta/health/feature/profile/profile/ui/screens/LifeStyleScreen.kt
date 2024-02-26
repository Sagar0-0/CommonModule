package fit.asta.health.feature.profile.profile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import fit.asta.health.common.utils.InputWrapper
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetListItemPicker
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetProperties
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetTimePicker
import fit.asta.health.feature.profile.profile.ui.components.ClickableTextBox
import fit.asta.health.feature.profile.profile.ui.components.PageNavigationButtons
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LifestyleScreen(
    userProfileState: UserProfileState,
    userPropertiesState: UiState<List<UserProperties>>
) {
    val propertiesSheetState = rememberModalBottomSheetState()
    val timerSheetState = rememberModalBottomSheetState()
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

        userProfileState.lifestyleScreenState.timersList.forEachIndexed { index, it ->
            ClickableTextBox(
                label = it.title, value = "${it.startTime.floatValue} - ${it.endTime.floatValue}",
                leadingIcon = it.imageVector
            ) {
                userProfileState.lifestyleScreenState.openTimerSheet(index, timerSheetState)
            }
        }

        userProfileState.lifestyleScreenState.propertiesList.forEachIndexed { index, type ->
            BottomSheetListItemPicker(
                name = type.name,
                list = type.list
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
            sheetState = timerSheetState,
            onDismissRequest = {
                userProfileState.lifestyleScreenState.closeTimerSheet(timerSheetState)
            },
            onSaveClick = { from, to ->
                userProfileState.lifestyleScreenState.saveTime(from, to)
            }
        )
    }
}

@Composable
private fun LifeStyleTimePicker(
    title: String,
    startButtonTitle: String,
    endButtonTitle: String,
    startTime: String,
    endTime: String,
    onStartClick: () -> Unit,
    onEndClick: () -> Unit
) {
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(vertical = AppTheme.spacing.level2)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = AppTheme.spacing.level2, end = AppTheme.spacing.level1),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleTexts.Level2(text = title)
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppFilledButton(
                    textToShow = startButtonTitle,
                    modifier = Modifier.weight(1f),
                    onClick = onStartClick
                )
                AppFilledButton(
                    textToShow = endButtonTitle,
                    modifier = Modifier.weight(1f),
                    onClick = onEndClick
                )
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                TitleTexts.Level3(text = startTime)
                Spacer(modifier = Modifier.width(AppTheme.spacing.level4))
                TitleTexts.Level3(text = endTime)
            }
        }
    }
}


data class OnlySelectionCardData(
    val cardType: String,
    val cardList: SnapshotStateList<UserProperties>?,
    val onItemsSelect: () -> Unit,
    val cardIndex: Int,
)

data class TimePickerData(
    val title: String,
    val firstColTime: State<InputWrapper>,
    val secColTime: State<InputWrapper>,
    val showFirstContent: MutableState<Boolean>,
    val showSecondContent: MutableState<Boolean>,
    val clockState: UseCaseState,
    val onFirstColTimeChange: (Int, Int) -> Unit,
    val onSecColTimeChange: (Int, Int) -> Unit,
    val firstColType: String,
    val secondColType: String,
    val showContent: MutableState<Boolean>,
)

sealed class LifeStyleCreateBottomSheetType(val cardIndex: Int) {
    data object CURRENTACTIVITIES : LifeStyleCreateBottomSheetType(0)
    data object PREFERREDACTIVITIES : LifeStyleCreateBottomSheetType(1)
    data object LIFESTYLETARGETS : LifeStyleCreateBottomSheetType(2)
}