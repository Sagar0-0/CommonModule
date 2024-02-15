@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class)

package fit.asta.health.feature.profile.create.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import fit.asta.health.common.utils.InputWrapper
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheetLayout
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.create.view.LifeStyleCreateBottomSheetType.CURRENTACTIVITIES
import fit.asta.health.feature.profile.create.view.LifeStyleCreateBottomSheetType.LIFESTYLETARGETS
import fit.asta.health.feature.profile.create.view.LifeStyleCreateBottomSheetType.PREFERREDACTIVITIES
import fit.asta.health.feature.profile.create.view.components.CreateProfileTwoButtonLayout
import fit.asta.health.feature.profile.create.view.components.ItemSelectionLayout
import fit.asta.health.feature.profile.create.vm.ComposeIndex
import fit.asta.health.feature.profile.create.vm.ProfileEvent
import fit.asta.health.feature.profile.profile.ui.UserProfileState
import fit.asta.health.feature.profile.show.view.OnlyChipSelectionCard
import fit.asta.health.feature.profile.show.view.components.UserSleepCycles
import fit.asta.health.feature.profile.show.vm.ProfileViewModel
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun LifeStyleCreateScreen(
    userProfileState: UserProfileState,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    //Data
    val propertiesDataState by viewModel.propertiesData.collectAsStateWithLifecycle()
    val composeSecondData: Map<Int, SnapshotStateList<HealthProperties>>? =
        propertiesDataState[ComposeIndex.Second]

    val searchQuery = remember { mutableStateOf("") }

    var currentBottomSheet: LifeStyleCreateBottomSheetType? by remember {
        mutableStateOf(null)
    }

    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }

    val modalBottomSheetState =
        rememberModalBottomSheetState(modalBottomSheetValue)

    val scope = rememberCoroutineScope()

    val openSheet = {
        scope.launch {
            modalBottomSheetState.show()
            if (modalBottomSheetValue == ModalBottomSheetValue.HalfExpanded) {
                modalBottomSheetValue = ModalBottomSheetValue.Expanded
            }
        }
    }

    val closeSheet = {
        scope.launch { modalBottomSheetState.hide() }
    }

    val onBottomSheetItemClick: (String) -> Unit = { propertyType ->
        currentBottomSheet?.let {
            openSheet()
            searchQuery.value = ""
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = propertyType))
        }
    }

    val onItemClick: (LifeStyleCreateBottomSheetType, String) -> Unit = { sheetType, propertyType ->
        currentBottomSheet = sheetType
        onBottomSheetItemClick(propertyType)
    }

    val cardDataList = listOf(
        OnlySelectionCardData(
            stringResource(R.string.currentAct_profile_creation),
            composeSecondData?.get(0),
            { onItemClick(CURRENTACTIVITIES, "activity") },
            0
        ), OnlySelectionCardData(
            stringResource(R.string.prefAct_profile_creation),
            composeSecondData?.get(1),
            { onItemClick(PREFERREDACTIVITIES, "activity") },
            1
        ), OnlySelectionCardData(
            stringResource(R.string.lifeStyleTarget_profile_creation),
            composeSecondData?.get(2),
            { onItemClick(LIFESTYLETARGETS, "goal") },
            2
        )
    )

    AppModalBottomSheetLayout(
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                LifeStyleCreateBottomSheetLayout(
                    viewModel = viewModel,
                    sheetLayout = it,
                    closeSheet = { closeSheet() },
                    cardList2 = composeSecondData?.get(it.cardIndex),
                    searchQuery = searchQuery
                )
            }
        },
        sheetState = modalBottomSheetState,
        content = {
            LifeStyleContent(
                userProfileState = userProfileState,
                viewModel = viewModel,
                cardList = cardDataList
            )
        }
    )
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun LifeStyleContent(
    userProfileState: UserProfileState,
    viewModel: ProfileViewModel = hiltViewModel(),
    cardList: List<OnlySelectionCardData>,
) {

    //Radio Buttons Selection
    val radioButtonSelections by viewModel.radioButtonSelections.collectAsStateWithLifecycle()

    //Time Picker Params
    val timePickers = listOf(
        TimePickerData(title = stringResource(R.string.sleepSchedule_profile_creation),
            firstColTime = viewModel.wakeUpTime.collectAsStateWithLifecycle(),
            secColTime = viewModel.bedTime.collectAsStateWithLifecycle(),
            showSecondContent = remember { mutableStateOf(false) },
            clockState = rememberUseCaseState(),
            onFirstColTimeChange = { hours, minutes ->
                viewModel.onEvent(event = ProfileEvent.OnUserWakeUpTimeChange("$hours:$minutes"))
            },
            onSecColTimeChange = { hours, minutes ->
                viewModel.onEvent(event = ProfileEvent.OnUserBedTimeChange("$hours:$minutes"))
            },
            showFirstContent = remember { mutableStateOf(false) },
            firstColType = stringResource(R.string.wakeUpTime_profile_creation),
            secondColType = stringResource(R.string.sleepTime_profile_creation),
            showContent = remember { mutableStateOf(false) }),
        TimePickerData(title = stringResource(R.string.jobSchedule_profile_creation),
            firstColTime = viewModel.jStartTime.collectAsStateWithLifecycle(),
            secColTime = viewModel.jEndTime.collectAsStateWithLifecycle(),
            showSecondContent = remember { mutableStateOf(false) },
            clockState = rememberUseCaseState(),
            onFirstColTimeChange = { hours, minutes ->
                viewModel.onEvent(event = ProfileEvent.OnUserJStartTimeChange("$hours:$minutes"))
            },
            onSecColTimeChange = { hours, minutes ->
                viewModel.onEvent(event = ProfileEvent.OnUserJEndTimeChange("$hours:$minutes"))
            },
            showFirstContent = remember { mutableStateOf(false) },
            firstColType = stringResource(R.string.jobStart_profile_creation),
            secondColType = stringResource(R.string.jobEnd_profile_creation),
            showContent = remember { mutableStateOf(false) })
    )

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level2)
                .verticalScroll(rememberScrollState())
                .background(color = AppTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            timePickers.forEach { timePicker ->
                LifeStyleTimePicker(
                    title = timePicker.title,
                    firstEvent = {
                        timePicker.showContent.value = true
                        timePicker.showFirstContent.value = true
                        timePicker.showSecondContent.value = false
                        timePicker.clockState.show()
                    },
                    secondEvent = {
                        timePicker.showContent.value = true
                        timePicker.showFirstContent.value = false
                        timePicker.showSecondContent.value = true
                        timePicker.clockState.show()
                    },
                    firstColValue = timePicker.firstColTime.value.value,
                    firstColType = timePicker.firstColType,
                    secondColType = timePicker.secondColType,
                    firstButtonType = "Select ${timePicker.title}",
                    secButtonType = "Select ${timePicker.title}",
                    secColValue = timePicker.secColTime.value.value
                )

                if (timePicker.showContent.value) {
                    CreateProfileTimePicker(
                        clockState = timePicker.clockState,
                        onPositiveClick = if (timePicker.showFirstContent.value) {
                            timePicker.onFirstColTimeChange
                        } else {
                            timePicker.onSecColTimeChange
                        }
                    )
                }

                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            }
//            LifeStyleToggleSelectionCard(selectionTypeText = PHYACTIVE.getListName(),
//                options = listOf("Less", "Moderate", "Very"),
//                selectedOption = radioButtonSelections[PHYACTIVE.key] as ThreeRadioBtnSelections?,
//                onStateChange = { state ->
//                    viewModel.updateRadioButtonSelection(PHYACTIVE.key, state)
//                }
//            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
//            LifeStyleToggleSelectionCard(selectionTypeText = WORKINGENV.getListName(),
//                options = listOf("Standing", "Sitting"),
//                selectedOption = radioButtonSelections[WORKINGENV.key] as TwoRadioBtnSelections?,
//                onStateChange = { state ->
//                    viewModel.updateRadioButtonSelection(WORKINGENV.key, state)
//                }
//            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
//            LifeStyleToggleSelectionCard(selectionTypeText = WORKINGSTYLE.getListName(),
//                options = listOf("Indoor", "Outdoor"),
//                selectedOption = radioButtonSelections[WORKINGSTYLE.key] as TwoRadioBtnSelections?,
//                onStateChange = { state ->
//                    viewModel.updateRadioButtonSelection(WORKINGSTYLE.key, state)
//                }
//            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
//            LifeStyleToggleSelectionCard(selectionTypeText = WORKINGHRS.getListName(),
//                options = listOf("Morning", "Afternoon", "Night"),
//                selectedOption = radioButtonSelections[WORKINGHRS.key] as ThreeRadioBtnSelections?,
//                onStateChange = { state ->
//                    viewModel.updateRadioButtonSelection(WORKINGHRS.key, state)
//                }
//            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            cardList.forEach { cardData ->
                OnlyChipSelectionCard(
                    cardType = cardData.cardType,
                    cardList = cardData.cardList,
                    onItemsSelect = cardData.onItemsSelect,
                    cardIndex = cardData.cardIndex,
                    composeIndex = ComposeIndex.Second,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            }
            CreateProfileTwoButtonLayout(userProfileState)
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
        }
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun LifeStyleCreateBottomSheetLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    sheetLayout: LifeStyleCreateBottomSheetType,
    closeSheet: () -> Unit,
    cardList2: SnapshotStateList<HealthProperties>?,
    searchQuery: MutableState<String>,
) {

    val cardIndex = sheetLayout.cardIndex
    val state by viewModel.healthPropState.collectAsStateWithLifecycle()

    when (state) {
        is UiState.Loading -> AppDotTypingAnimation()
        is UiState.NoInternet -> AppInternetErrorDialog {}
        is UiState.Success -> ItemSelectionLayout(
            cardList = (state as UiState.Success).data,
            cardList2 = cardList2,
            cardIndex = cardIndex,
            composeIndex = ComposeIndex.Second,
            searchQuery = searchQuery
        )

        else -> {}
    }
}


@Composable
private fun LifeStyleTimePicker(
    title: String,
    firstEvent: () -> Unit,
    secondEvent: () -> Unit,
    firstColValue: String,
    secColValue: String,
    firstColType: String,
    secondColType: String,
    firstButtonType: String,
    secButtonType: String,
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
                    textToShow = firstButtonType,
                    modifier = Modifier.weight(1f),
                    onClick = firstEvent
                )
                AppFilledButton(
                    textToShow = secButtonType,
                    modifier = Modifier.weight(1f),
                    onClick = secondEvent
                )
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                UserSleepCycles(columnType = firstColType, columnValue = firstColValue)
                Spacer(modifier = Modifier.width(AppTheme.spacing.level4))
                UserSleepCycles(columnType = secondColType, columnValue = secColValue)
            }
        }
    }
}


//@Composable
//private fun LifeStyleThreeToggleSelectionCard(
//    firstOption: String,
//    secondOption: String,
//    thirdOption: String,
//    selectionTypeText: String?,
//    selectedOption: ThreeRadioBtnSelections?,
//    onStateChange: (ThreeRadioBtnSelections) -> Unit,
//) {
//    AppCard(modifier = Modifier.fillMaxWidth()) {
//        ThreeTogglesGroups(
//            title = selectionTypeText,
//            selectedOption,
//            onStateChange,
//            firstOption,
//            secondOption,
//            thirdOption,
//        )
//    }
//}


//@Composable
//private fun LifeStyleTwoToggleSelectionCard(
//    selectionTypeText: String?,
//    selectedOption: TwoRadioBtnSelections?,
//    onStateChange: (TwoRadioBtnSelections) -> Unit,
//) {
//    AppCard {
//        TwoTogglesGroup(selectionTypeText, selectedOption, onStateChange)
//    }
//}


//@Composable
//private fun LifeStyleToggleSelectionCard(
//    selectionTypeText: String?,
//    options: List<String>,
//    selectedOption: Any?,
//    onStateChange: (Any) -> Unit,
//) {
//    AppCard(modifier = Modifier.fillMaxWidth()) {
//        when (options.size) {
//            2 -> TwoTogglesGroup(
//                selectionTypeText,
//                selectedOption as TwoRadioBtnSelections?,
//                onStateChange,
//                options[0],
//                options[1],
//            )
//
//            3 -> ThreeTogglesGroups(
//                selectionTypeText,
//                selectedOption as ThreeRadioBtnSelections?,
//                onStateChange,
//                options[0],
//                options[1],
//                options[2],
//            )
//        }
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateProfileTimePicker(
    clockState: UseCaseState,
    onPositiveClick: (Int, Int) -> Unit,
) {
    ClockDialog(
        state = clockState,
        selection = ClockSelection.HoursMinutes(onPositiveClick = onPositiveClick)
    )
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