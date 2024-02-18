@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class)

package fit.asta.health.feature.profile.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import fit.asta.health.common.utils.InputWrapper
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LifestyleScreen(
    userProfileState: UserProfileState
) {
//
//    val bottomSheetState = rememberModalBottomSheetState()
//    val bottomSheetVisible = rememberSaveable { mutableStateOf(false) }
//    val useCaseState = rememberUseCaseState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState()),
//        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier)
//
//        userProfileState.lifestyleTimePickers.forEach { timePicker ->
//            LifeStyleTimePicker(userProfileState,timePicker,useCaseState)
//        }
//        userProfileState.lifestyleBottomSheetTypes.forEachIndexed { index, type ->
//            BottomSheetPickerCardItem(type) {
//                userProfileState.openLifestyleBottomSheet(
//                    bottomSheetState,
//                    index,
//                    bottomSheetVisible
//                )
//            }
//        }
//
//        CreateProfileTwoButtonLayout(userProfileState)
//        Spacer(modifier = Modifier)
//
//        ClockDialog(
//            state = useCaseState,
//            selection = ClockSelection.HoursMinutes(
//                onPositiveClick = { hrs, mins->
//                    userProfileState.saveLifestyleTime("${hrs}:${mins}")
//                }
//            )
//        )
//        AppModalBottomSheet(
//            sheetVisible = bottomSheetVisible.value,
//            sheetState = bottomSheetState,
//            dragHandle = null,
//            onDismissRequest = {
//                userProfileState.closeBottomSheet(bottomSheetState, bottomSheetVisible)
//            },
//        ) {
//            AppUiStateHandler(
//                uiState = userProfileState.healthPropertiesState,
//                isScreenLoading = false,
//                onErrorMessage = {
//                    userProfileState.closeBottomSheet(bottomSheetState, bottomSheetVisible)
//                }
//            ) {
//                HealthPropertiesSearchSheet(
//                    userProfileState = userProfileState,
//                    healthProperties = it
//                )
//            }
//        }
//    }
}

@Composable
private fun LifeStyleTimePicker(
    userProfileState: UserProfileState,
    type: UserProfileState.ProfileTimePicker,
    useCaseState: UseCaseState
) {
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(vertical = AppTheme.spacing.level2)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = AppTheme.spacing.level2, end = AppTheme.spacing.level1),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleTexts.Level2(text = type.title)
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
                    textToShow = type.startButtonTitle,
                    modifier = Modifier.weight(1f),
                    onClick = {
//                        userProfileState.openLifestyleTimeSelection(
//                            type.startTimerIndex,
//                            useCaseState
//                        )
                    }
                )
                AppFilledButton(
                    textToShow = type.endButtonTitle,
                    modifier = Modifier.weight(1f),
                    onClick = {
//                        userProfileState.openLifestyleTimeSelection(
//                            type.endTimerIndex,
//                            useCaseState
//                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                TitleTexts.Level3(text = type.startTime)
                Spacer(modifier = Modifier.width(AppTheme.spacing.level4))
                TitleTexts.Level3(text = type.endTime)
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