@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class)

package fit.asta.health.profile.createprofile.view

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppCard
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.AppModalBottomSheetLayout
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.MultiRadioBtnKeys
import fit.asta.health.profile.createprofile.view.LifeStyleCreateBottomSheetType.CURRENTACTIVITIES
import fit.asta.health.profile.createprofile.view.LifeStyleCreateBottomSheetType.LIFESTYLETARGETS
import fit.asta.health.profile.createprofile.view.LifeStyleCreateBottomSheetType.PREFERREDACTIVITIES
import fit.asta.health.profile.createprofile.view.components.CreateProfileTimePicker
import fit.asta.health.profile.createprofile.view.components.CreateProfileTwoButtonLayout
import fit.asta.health.profile.createprofile.view.components.ItemSelectionLayout
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.ThreeRadioBtnSelections
import fit.asta.health.profile.model.domain.TwoRadioBtnSelections
import fit.asta.health.profile.model.domain.UserPropertyType
import fit.asta.health.profile.view.OnlyChipSelectionCard
import fit.asta.health.profile.view.ThreeTogglesGroups
import fit.asta.health.profile.view.TwoTogglesGroup
import fit.asta.health.profile.view.components.UserSleepCycles
import fit.asta.health.profile.viewmodel.HPropState
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun LifeStyleCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit,
    eventNext: () -> Unit,
    onSkipEvent: (Int) -> Unit,
) {

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
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = propertyType))
        }
    }

    val onItemClick: (LifeStyleCreateBottomSheetType, String) -> Unit = { sheetType, propertyType ->
        currentBottomSheet = sheetType
        onBottomSheetItemClick(propertyType)
    }

    AppModalBottomSheetLayout(sheetContent = {
        Spacer(modifier = Modifier.height(1.dp))
        currentBottomSheet?.let {
            LifeStyleCreateBottomSheetLayout(
                sheetLayout = it, closeSheet = { closeSheet() }, viewModel = viewModel
            )
        }
    }, sheetState = modalBottomSheetState, content = {
        LifeStyleContent(viewModel = viewModel,
            eventPrevious = eventPrevious,
            eventNext = eventNext,
            onSkipEvent = onSkipEvent,
            onCurrentActivity = { onItemClick(CURRENTACTIVITIES, "activity") },
            onPreferredActivity = { onItemClick(PREFERREDACTIVITIES, "activity") },
            onLifeStyleTargets = { onItemClick(LIFESTYLETARGETS, "goal") })
    })
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun LifeStyleContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit,
    eventNext: () -> Unit,
    onSkipEvent: (Int) -> Unit,
    onCurrentActivity: () -> Unit,
    onPreferredActivity: () -> Unit,
    onLifeStyleTargets: () -> Unit,
) {

    //Radio Buttons Selection
    val radioButtonSelections by viewModel.radioButtonSelections.collectAsStateWithLifecycle()

    //Data
    val propertiesDataState by viewModel.propertiesData.collectAsStateWithLifecycle()
    val composeSecondData: Map<Int, SnapshotStateList<HealthProperties>>? =
        propertiesDataState[ComposeIndex.Second]

    val cardDataList = listOf(
        OnlySelectionCardData(
            "Current Activities",
            composeSecondData?.get(0),
            onCurrentActivity,
            0
        ),
        OnlySelectionCardData(
            "Preferred Activities",
            composeSecondData?.get(1),
            onPreferredActivity,
            1
        ),
        OnlySelectionCardData("LifeStyleTargets", composeSecondData?.get(2), onLifeStyleTargets, 2)
    )

    //Time Picker Params
    val clockWakeUpState = rememberUseCaseState()
    val clockBedState = rememberUseCaseState()
    val clockJStartState = rememberUseCaseState()
    val clockJEndState = rememberUseCaseState()
    val wakeUpTime by viewModel.wakeUpTime.collectAsStateWithLifecycle()
    val bedTime by viewModel.bedTime.collectAsStateWithLifecycle()
    val jStart by viewModel.jStartTime.collectAsStateWithLifecycle()
    val jEnd by viewModel.jEndTime.collectAsStateWithLifecycle()
    val showBedTimeContent = remember { mutableStateOf(false) }
    val showWakeUpTimeContent = remember { mutableStateOf(false) }
    val showJobStartContent = remember { mutableStateOf(false) }
    val showJobEndContent = remember { mutableStateOf(false) }

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.medium)
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(spacing.medium))

            //Sleep Schedule
            LifeStyleTimePicker(
                firstEvent = {
                    showWakeUpTimeContent.value = true
                    clockWakeUpState.show()
                },
                secondEvent = {
                    showBedTimeContent.value = true
                    clockBedState.show()
                },
                firstColValue = wakeUpTime.value,
                secColValue = bedTime.value,
                firstColType = "WAKE UP TIME ",
                secColType = "BED TIME",
                firstButtonType = "Select Wake Up Time",
                secButtonType = "Select Bed Time"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            //Job Schedule
            LifeStyleTimePicker(
                firstEvent = {
                    showJobStartContent.value = true
                    clockJStartState.show()
                },
                secondEvent = {
                    showJobEndContent.value = true
                    clockJEndState.show()
                },
                firstColValue = jStart.value,
                secColValue = jEnd.value,
                firstColType = "JOB START TIME",
                secColType = "JOB END TIME",
                firstButtonType = "Select Job Start Time",
                secButtonType = "Select Job End Time"
            )

            if (showWakeUpTimeContent.value) {
                CreateProfileTimePicker(clockState = clockWakeUpState,
                    onPositiveClick = { hours, minutes ->
                        viewModel.onEvent(event = ProfileEvent.OnUserWakeUpTimeChange("$hours:$minutes"))
                    })
            }

            if (showBedTimeContent.value) {
                CreateProfileTimePicker(clockState = clockBedState,
                    onPositiveClick = { hours, minutes ->
                        viewModel.onEvent(event = ProfileEvent.OnUserBedTimeChange("$hours:$minutes"))
                    })


            }

            if (showJobStartContent.value) {
                CreateProfileTimePicker(clockState = clockJStartState,
                    onPositiveClick = { hours, minutes ->
                        viewModel.onEvent(event = ProfileEvent.OnUserJStartTimeChange("$hours:$minutes"))
                    })
            }

            if (showJobEndContent.value) {
                CreateProfileTimePicker(clockState = clockJEndState,
                    onPositiveClick = { hours, minutes ->
                        viewModel.onEvent(event = ProfileEvent.OnUserJEndTimeChange("$hours:$minutes"))
                    })
            }

            Spacer(modifier = Modifier.height(spacing.medium))
            LifeStyleToggleSelectionCard(selectionTypeText = "Are you Physically Active",
                options = listOf("Less", "Moderate", "Very"),
                selectedOption = radioButtonSelections[MultiRadioBtnKeys.PHYACTIVE.key] as ThreeRadioBtnSelections?,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(MultiRadioBtnKeys.PHYACTIVE.key, state)
                })
            Spacer(modifier = Modifier.height(spacing.medium))
            LifeStyleToggleSelectionCard(selectionTypeText = "Current Working Environment",
                options = listOf("Standing", "Sitting"),
                selectedOption = radioButtonSelections[MultiRadioBtnKeys.WORKINGENV.key] as TwoRadioBtnSelections?,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(MultiRadioBtnKeys.WORKINGENV.key, state)
                })
            Spacer(modifier = Modifier.height(spacing.medium))
            LifeStyleToggleSelectionCard(selectionTypeText = "Current WorkStyle",
                options = listOf("Indoor", "Outdoor"),
                selectedOption = radioButtonSelections[MultiRadioBtnKeys.WORKINGSTYLE.key] as TwoRadioBtnSelections?,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(MultiRadioBtnKeys.WORKINGSTYLE.key, state)
                })
            Spacer(modifier = Modifier.height(spacing.medium))
            LifeStyleToggleSelectionCard(selectionTypeText = "What are your working hours",
                options = listOf("Morning", "Afternoon", "Night"),
                selectedOption = radioButtonSelections[MultiRadioBtnKeys.WORKINGHRS.key] as ThreeRadioBtnSelections?,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(MultiRadioBtnKeys.WORKINGHRS.key, state)
                })
            Spacer(modifier = Modifier.height(spacing.medium))

            cardDataList.forEach { cardData ->
                OnlyChipSelectionCard(
                    cardType = cardData.cardType,
                    cardList = cardData.cardList,
                    onItemsSelect = cardData.onItemsSelect,
                    cardIndex = cardData.cardIndex,
                    composeIndex = ComposeIndex.Second,
                )
                Spacer(modifier = Modifier.height(spacing.medium))
            }

            CreateProfileTwoButtonLayout(eventPrevious, eventNext)

            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }
}

@Composable
fun LifeStyleCreateBottomSheetLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    sheetLayout: LifeStyleCreateBottomSheetType,
    closeSheet: () -> Unit,
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
            cardIndex = cardIndex,
            composeIndex = ComposeIndex.First
        )
    }
}


sealed class LifeStyleCreateBottomSheetType(val cardIndex: Int) {
    object CURRENTACTIVITIES : LifeStyleCreateBottomSheetType(0)
    object PREFERREDACTIVITIES : LifeStyleCreateBottomSheetType(1)
    object LIFESTYLETARGETS : LifeStyleCreateBottomSheetType(2)
}

@Composable
private fun LifeStyleTimePicker(
    firstEvent: () -> Unit,
    secondEvent: () -> Unit,
    firstColValue: String,
    secColValue: String,
    firstColType: String,
    secColType: String,
    firstButtonType: String,
    secButtonType: String,
) {

    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(vertical = spacing.medium)) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = spacing.medium, end = spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppTexts.TitleMedium(text = UserPropertyType.SleepSchedule.title)
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium),
                horizontalArrangement = Arrangement.spacedBy(spacing.small),
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
            Spacer(modifier = Modifier.height(spacing.medium))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                UserSleepCycles(columnType = firstColType, columnValue = firstColValue)
                Spacer(modifier = Modifier.width(spacing.large))
                UserSleepCycles(columnType = secColType, columnValue = secColValue)
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
    firstOption: String = "Yes",
    secondOption: String = "No",
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


data class OnlySelectionCardData(
    val cardType: String,
    val cardList: SnapshotStateList<HealthProperties>?,
    val onItemsSelect: () -> Unit,
    val cardIndex: Int,
)