@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class)

package fit.asta.health.feature.profile.profile.ui

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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import fit.asta.health.common.utils.InputWrapper
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.create.view.components.CreateProfileTwoButtonLayout
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LifestyleScreen(
    userProfileState: UserProfileState,
    healthPropertiesState: UiState<List<HealthProperties>>
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val bottomSheetVisible = rememberSaveable { mutableStateOf(false) }
    val currentBottomSheetIndex = rememberSaveable { mutableIntStateOf(0) }
    val currentTimerIndex = rememberSaveable { mutableIntStateOf(0) }
    val useCaseState = rememberUseCaseState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier)

        userProfileState.lifestyleScreenState.lifestyleTimePickers.forEach {
            LifeStyleTimePicker(
                title = it.title,
                startButtonTitle = it.startButtonTitle,
                endButtonTitle = it.endButtonTitle,
                startTime = it.startTime.value,
                endTime = it.endTime.value,
                onStartClick = {
                    currentTimerIndex.intValue = it.startIndex
                    useCaseState.show()
                },
                onEndClick = {
                    currentTimerIndex.intValue = it.endIndex
                    useCaseState.show()
                }
            )
        }


        userProfileState.lifestyleScreenState.bottomSheets.forEachIndexed { index, type ->
            BottomSheetPickerCardItem(
                name = type.name,
                list = type.list,
                onRemove = {
                    type.list.remove(it)
                }
            ) {
                currentBottomSheetIndex.intValue = index
                userProfileState.lifestyleScreenState.openLifestyleBottomSheet(
                    bottomSheetState,
                    currentBottomSheetIndex,
                    bottomSheetVisible
                )
            }
        }

        CreateProfileTwoButtonLayout(userProfileState)
        Spacer(modifier = Modifier)

        ClockDialog(
            state = useCaseState,
            selection = ClockSelection.HoursMinutes(
                onPositiveClick = { hrs, mins ->
                    userProfileState.lifestyleScreenState.setCurrentItemTime(
                        currentTimerIndex.intValue,
                        "${hrs}:${mins}"
                    )
                }
            )
        )
        AppModalBottomSheet(
            sheetVisible = bottomSheetVisible.value,
            sheetState = bottomSheetState,
            dragHandle = null,
            onDismissRequest = {
                userProfileState.closeBottomSheet(bottomSheetState, bottomSheetVisible)
            },
        ) {
            AppUiStateHandler(
                uiState = healthPropertiesState,
                isScreenLoading = false,
                onErrorMessage = {
                    userProfileState.closeBottomSheet(bottomSheetState, bottomSheetVisible)
                }
            ) {
                HealthPropertiesSearchSheet(
                    healthProperties = it,
                    searchQuery = userProfileState.bottomSheetSearchQuery,
                    onSearchQueryChange = { query ->
                        userProfileState.bottomSheetSearchQuery = query
                    },
                    isItemSelected = { prop ->
                        userProfileState.lifestyleScreenState.isPropertySelected(
                            currentBottomSheetIndex.intValue,
                            prop
                        )
                    },
                    onAdd = { prop ->
                        userProfileState.lifestyleScreenState.addProperty(
                            currentBottomSheetIndex.intValue,
                            prop
                        )
                    },
                    onRemove = { prop ->
                        userProfileState.lifestyleScreenState.removeProperty(
                            currentBottomSheetIndex.intValue,
                            prop
                        )
                    },
                )
            }
        }
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
    val cardList: SnapshotStateList<HealthProperties>?,
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