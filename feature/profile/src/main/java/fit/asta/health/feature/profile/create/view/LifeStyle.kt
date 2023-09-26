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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.AppModalBottomSheetLayout
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.feature.profile.create.MultiRadioBtnKeys.PHYACTIVE
import fit.asta.health.feature.profile.create.MultiRadioBtnKeys.WORKINGENV
import fit.asta.health.feature.profile.create.MultiRadioBtnKeys.WORKINGHRS
import fit.asta.health.feature.profile.create.MultiRadioBtnKeys.WORKINGSTYLE
import fit.asta.health.feature.profile.create.view.LifeStyleCreateBottomSheetType.CURRENTACTIVITIES
import fit.asta.health.feature.profile.create.view.LifeStyleCreateBottomSheetType.LIFESTYLETARGETS
import fit.asta.health.feature.profile.create.view.LifeStyleCreateBottomSheetType.PREFERREDACTIVITIES
import fit.asta.health.feature.profile.create.view.components.CreateProfileTwoButtonLayout
import fit.asta.health.feature.profile.create.view.components.ItemSelectionLayout
import fit.asta.health.feature.profile.create.vm.ComposeIndex
import fit.asta.health.feature.profile.create.vm.HPropState
import fit.asta.health.feature.profile.create.vm.ProfileEvent
import fit.asta.health.feature.profile.create.vm.ThreeRadioBtnSelections
import fit.asta.health.feature.profile.create.vm.TwoRadioBtnSelections
import fit.asta.health.feature.profile.show.view.ThreeTogglesGroups
import fit.asta.health.feature.profile.show.view.TwoTogglesGroup
import fit.asta.health.feature.profile.show.vm.ProfileViewModel
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun LifeStyleCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit,
    eventNext: () -> Unit,
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
        androidx.compose.material.rememberModalBottomSheetState(modalBottomSheetValue)

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

    AppModalBottomSheetLayout(sheetContent = {
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
    }, sheetState = modalBottomSheetState, content = {
        LifeStyleContent(
            viewModel = viewModel,
            eventPrevious = eventPrevious,
            eventNext = eventNext,
            cardList = cardDataList
        )
    })
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun LifeStyleContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit,
    eventNext: () -> Unit,
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
                .padding(horizontal = AstaThemeX.spacingX.medium)
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
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

                Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            }
            LifeStyleToggleSelectionCard(selectionTypeText = PHYACTIVE.getListName(),
                options = listOf("Less", "Moderate", "Very"),
                selectedOption = radioButtonSelections[PHYACTIVE.key] as ThreeRadioBtnSelections?,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(PHYACTIVE.key, state)
                })
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            LifeStyleToggleSelectionCard(selectionTypeText = MultiRadioBtnKeys.WORKINGENV.getListName(),
                options = listOf("Standing", "Sitting"),
                selectedOption = radioButtonSelections[WORKINGENV.key] as TwoRadioBtnSelections?,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(WORKINGENV.key, state)
                })
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            LifeStyleToggleSelectionCard(selectionTypeText = WORKINGSTYLE.getListName(),
                options = listOf("Indoor", "Outdoor"),
                selectedOption = radioButtonSelections[WORKINGSTYLE.key] as TwoRadioBtnSelections?,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(WORKINGSTYLE.key, state)
                })
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            LifeStyleToggleSelectionCard(selectionTypeText = WORKINGHRS.getListName(),
                options = listOf("Morning", "Afternoon", "Night"),
                selectedOption = radioButtonSelections[WORKINGHRS.key] as ThreeRadioBtnSelections?,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(WORKINGHRS.key, state)
                })
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))

            cardList.forEach { cardData ->
                OnlyChipSelectionCard(
                    cardType = cardData.cardType,
                    cardList = cardData.cardList,
                    onItemsSelect = cardData.onItemsSelect,
                    cardIndex = cardData.cardIndex,
                    composeIndex = ComposeIndex.Second,
                )
                Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            }
            CreateProfileTwoButtonLayout(eventPrevious, eventNext)
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
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
    val state by viewModel.stateHp.collectAsStateWithLifecycle()

    when (state) {
        is HPropState.Empty -> TODO()
        is HPropState.Error -> TODO()
        is HPropState.Loading -> LoadingAnimation()
        is HPropState.NoInternet -> AppErrorScreen(onTryAgain = {})
        is HPropState.Success -> ItemSelectionLayout(
            cardList = (state as HPropState.Success).properties,
            cardList2 = cardList2,
            cardIndex = cardIndex,
            composeIndex = ComposeIndex.Second,
            searchQuery = searchQuery
        )
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
        Column(modifier = Modifier.padding(vertical = AstaThemeX.spacingX.medium)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = AstaThemeX.spacingX.medium, end = AstaThemeX.spacingX.small),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppTexts.TitleMedium(text = title)
            }
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AstaThemeX.spacingX.medium),
                horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppButtons.AppStandardButton(onClick = firstEvent, modifier = Modifier.weight(1f)) {
                    AppTexts.LabelMedium(text = firstButtonType, textAlign = TextAlign.Center)
                }
                AppButtons.AppStandardButton(
                    onClick = secondEvent, modifier = Modifier.weight(1f)
                ) {
                    AppTexts.LabelMedium(text = secButtonType, textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                UserSleepCycles(columnType = firstColType, columnValue = firstColValue)
                Spacer(modifier = Modifier.width(AstaThemeX.spacingX.large))
                UserSleepCycles(columnType = secondColType, columnValue = secColValue)
            }
        }
    }
}


@Composable
private fun LifeStyleThreeToggleSelectionCard(
    firstOption: String,
    secondOption: String,
    thirdOption: String,
    selectionTypeText: String?,
    selectedOption: ThreeRadioBtnSelections?,
    onStateChange: (ThreeRadioBtnSelections) -> Unit,
) {
    AppCard(modifier = Modifier.fillMaxWidth()) {
        ThreeTogglesGroups(
            selectionTypeText,
            selectedOption,
            onStateChange,
            firstOption,
            secondOption,
            thirdOption,
        )
    }
}


@Composable
private fun LifeStyleTwoToggleSelectionCard(
    selectionTypeText: String?,
    selectedOption: TwoRadioBtnSelections?,
    onStateChange: (TwoRadioBtnSelections) -> Unit,
    firstOption: String = stringResource(R.string.yes),
    secondOption: String = stringResource(R.string.no),
) {
    AppCard {
        TwoTogglesGroup(selectionTypeText, selectedOption, onStateChange, firstOption, secondOption)
    }
}


@Composable
private fun LifeStyleToggleSelectionCard(
    selectionTypeText: String?,
    options: List<String>,
    selectedOption: Any?,
    onStateChange: (Any) -> Unit,
) {
    AppCard(modifier = Modifier.fillMaxWidth()) {
        when (options.size) {
            2 -> TwoTogglesGroup(
                selectionTypeText,
                selectedOption as TwoRadioBtnSelections?,
                onStateChange,
                options[0],
                options[1],
            )

            3 -> ThreeTogglesGroups(
                selectionTypeText,
                selectedOption as ThreeRadioBtnSelections?,
                onStateChange,
                options[0],
                options[1],
                options[2],
            )
        }
    }
}


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
    object CURRENTACTIVITIES : LifeStyleCreateBottomSheetType(0)
    object PREFERREDACTIVITIES : LifeStyleCreateBottomSheetType(1)
    object LIFESTYLETARGETS : LifeStyleCreateBottomSheetType(2)
}