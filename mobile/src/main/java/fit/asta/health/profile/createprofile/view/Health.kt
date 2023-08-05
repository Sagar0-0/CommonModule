@file:OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalCoroutinesApi::class
)

package fit.asta.health.profile.createprofile.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.AppModalBottomSheetLayout
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.MultiRadioBtnKeys
import fit.asta.health.profile.createprofile.view.HealthCreateBottomSheetTypes.ADDICTION
import fit.asta.health.profile.createprofile.view.HealthCreateBottomSheetTypes.AILMENTS
import fit.asta.health.profile.createprofile.view.HealthCreateBottomSheetTypes.BODYPARTS
import fit.asta.health.profile.createprofile.view.HealthCreateBottomSheetTypes.HEALTHHISTORY
import fit.asta.health.profile.createprofile.view.HealthCreateBottomSheetTypes.HEALTHTARGETS
import fit.asta.health.profile.createprofile.view.HealthCreateBottomSheetTypes.INJURIES
import fit.asta.health.profile.createprofile.view.HealthCreateBottomSheetTypes.MEDICATIONS
import fit.asta.health.profile.createprofile.view.components.CreateProfileTwoButtonLayout
import fit.asta.health.profile.createprofile.view.components.ItemSelectionLayout
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.TwoRadioBtnSelections
import fit.asta.health.profile.view.ButtonListTypes
import fit.asta.health.profile.view.SelectionCardCreateProfile
import fit.asta.health.profile.viewmodel.HPropState
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.testimonials.model.domain.InputWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@Composable
fun HealthCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit,
    eventNext: () -> Unit,
) {

    var currentBottomSheet: HealthCreateBottomSheetTypes? by remember {
        mutableStateOf(null)
    }

    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }

    val modalBottomSheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = modalBottomSheetValue
    )

    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch { modalBottomSheetState.hide() }
    }

    val openSheet = {
        scope.launch {
            modalBottomSheetState.show()
            if (modalBottomSheetValue == ModalBottomSheetValue.HalfExpanded) {
                modalBottomSheetValue = ModalBottomSheetValue.Expanded
            }
        }
    }

    AppModalBottomSheetLayout(sheetContent = {
        Spacer(modifier = Modifier.height(1.dp))
        currentBottomSheet?.let {
            HealthCreateBtmSheetLayout(
                sheetLayout = it, sheetState = { closeSheet() }, viewModel = viewModel
            )
        }
    }, sheetState = modalBottomSheetState, content = {
        HealthContent(eventPrevious = eventPrevious, eventNext = eventNext, onHealthHistory = {
            currentBottomSheet = HEALTHHISTORY
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "ailment"))
        }, onInjuries = {
            currentBottomSheet = INJURIES
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "injury"))
        }, onAilments = {
            currentBottomSheet = AILMENTS
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "ailment"))
        }, onMedications = {
            currentBottomSheet = MEDICATIONS
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "med"))
        }, onHealthTargets = {
            currentBottomSheet = HEALTHTARGETS
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "tgt"))
        }, onBodyInjurySelect = {
            currentBottomSheet = BODYPARTS
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "bp"))
        }, onAddictionSelect = {
            currentBottomSheet = ADDICTION
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "add"))
        })
    })
}

@Composable
fun HealthContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit,
    eventNext: () -> Unit,
    onHealthHistory: () -> Unit,
    onInjuries: () -> Unit,
    onAilments: () -> Unit,
    onMedications: () -> Unit,
    onHealthTargets: () -> Unit,
    onBodyInjurySelect: () -> Unit,
    onAddictionSelect: () -> Unit,
) {
    val radioButtonList =
        listOf(ButtonListTypes(buttonType = "First"), ButtonListTypes(buttonType = "Second"))

    // Inputs
    val injurySince by viewModel.injuriesSince.collectAsStateWithLifecycle()

    // Selection Inputs
    val radioButtonSelections by viewModel.radioButtonSelections.collectAsStateWithLifecycle()

    val selectedHealthHistory =
        radioButtonSelections[MultiRadioBtnKeys.HEALTHHIS] as TwoRadioBtnSelections?
    val selectedInjuries =
        radioButtonSelections[MultiRadioBtnKeys.INJURIES] as TwoRadioBtnSelections?
    val selectedAilment =
        radioButtonSelections[MultiRadioBtnKeys.AILMENTS] as TwoRadioBtnSelections?
    val selectedMedication =
        radioButtonSelections[MultiRadioBtnKeys.MEDICATIONS] as TwoRadioBtnSelections?
    val selectedHealthTarget =
        radioButtonSelections[MultiRadioBtnKeys.HEALTHTAR] as TwoRadioBtnSelections?
    val selectedAddiction =
        radioButtonSelections[MultiRadioBtnKeys.ADDICTION] as TwoRadioBtnSelections?
    val selectedBodyPart =
        radioButtonSelections[MultiRadioBtnKeys.BODYPART] as TwoRadioBtnSelections?

    // Data
    val propertiesDataState by viewModel.propertiesData.collectAsStateWithLifecycle()

    val composeFirstData: Map<Int, SnapshotStateList<HealthProperties>>? =
        propertiesDataState[ComposeIndex.First]

    val checkedState = remember { mutableStateOf(true) }

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        HealthContentLayout(
            viewModel = hiltViewModel(),
            composeFirstData = composeFirstData,
            radioButtonList = radioButtonList,
            selections = listOf(
                Pair(ComposeIndex.First, selectedHealthHistory),
                Pair(ComposeIndex.First, selectedInjuries),
                Pair(ComposeIndex.First, selectedBodyPart),
                Pair(ComposeIndex.First, selectedAilment),
                Pair(ComposeIndex.First, selectedMedication),
                Pair(ComposeIndex.First, selectedHealthTarget),
                Pair(ComposeIndex.First, selectedAddiction)
            ),
            checkedState = checkedState,
            onItemSelectFunctions = listOf(
                onHealthHistory,
                onInjuries,
                onBodyInjurySelect,
                onAilments,
                onMedications,
                onHealthTargets,
                onAddictionSelect
            ),
            cardTypes = listOf(
                "Any Significant Health History?",
                "Any Injuries",
                "Body Part",
                "Any Ailments?",
                "Any Medications?",
                "Any Health Targets?",
                "Any Addiction?"
            ),
            inputWrappers = listOf(injurySince),
            eventPrevious = eventPrevious,
            eventNext = eventNext
        )
    }
}

@Composable
private fun HealthCreateBtmSheetLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    sheetLayout: HealthCreateBottomSheetTypes,
    sheetState: () -> Unit,
) {


    //Sealed Classes
    val cardIndex = when (sheetLayout) {
        is HEALTHHISTORY -> 0
        is INJURIES -> 1
        is BODYPARTS -> 2
        is AILMENTS -> 3
        is MEDICATIONS -> 4
        is HEALTHTARGETS -> 5
        is ADDICTION -> 6
    }

    val state by viewModel.stateHp.collectAsStateWithLifecycle()

    when (state) {
        is HPropState.NoInternet -> {
            AppErrorScreen(onTryAgain = {})
        }

        is HPropState.Success -> {
            ItemSelectionLayout(
                cardList = (state as HPropState.Success).properties,
                cardIndex = cardIndex,
                composeIndex = ComposeIndex.First
            )
        }

        is HPropState.Empty -> {
            TODO()
        }

        is HPropState.Error -> {
            TODO()
        }

        is HPropState.Loading -> LoadingAnimation()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun HealthContentLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    composeFirstData: Map<Int, SnapshotStateList<HealthProperties>>?,
    radioButtonList: List<ButtonListTypes>,
    selections: List<Pair<ComposeIndex, TwoRadioBtnSelections?>>,
    checkedState: MutableState<Boolean>,
    onItemSelectFunctions: List<() -> Unit>,
    cardTypes: List<String>,
    inputWrappers: List<InputWrapper>,
    eventPrevious: () -> Unit,
    eventNext: () -> Unit,
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

        for (index in cardTypes.indices) {
            val (composeIndex, selectedOption) = selections[index]
            val cardType = cardTypes[index]
            val onItemSelect = onItemSelectFunctions[index]

            SelectionCardCreateProfile(
                cardType = cardType,
                cardList = composeFirstData?.get(index),
                checkedState = checkedState,
                onItemsSelect = onItemSelect,
                selectedOption = selectedOption,
                onStateChange = { state ->
                    val multiRadioBtnKey = when (index) {
                        0 -> MultiRadioBtnKeys.HEALTHHIS
                        1 -> MultiRadioBtnKeys.INJURIES
                        2 -> MultiRadioBtnKeys.BODYPART
                        3 -> MultiRadioBtnKeys.AILMENTS
                        4 -> MultiRadioBtnKeys.MEDICATIONS
                        5 -> MultiRadioBtnKeys.HEALTHTAR
                        6 -> MultiRadioBtnKeys.ADDICTION
                        else -> MultiRadioBtnKeys.NONE //Sealed Classes
                    }
                    viewModel.updateRadioButtonSelection(multiRadioBtnKey, state)
                },
                cardIndex = index,
                composeIndex = composeIndex,
                listName = cardType
            )

            Spacer(modifier = Modifier.height(spacing.medium))
        }

        CreateProfileTwoButtonLayout(eventPrevious, eventNext)

        Spacer(modifier = Modifier.height(spacing.medium))
    }
}

sealed class HealthCreateBottomSheetTypes {
    object HEALTHHISTORY : HealthCreateBottomSheetTypes()
    object INJURIES : HealthCreateBottomSheetTypes()
    object AILMENTS : HealthCreateBottomSheetTypes()
    object MEDICATIONS : HealthCreateBottomSheetTypes()
    object HEALTHTARGETS : HealthCreateBottomSheetTypes()
    object BODYPARTS : HealthCreateBottomSheetTypes()
    object ADDICTION : HealthCreateBottomSheetTypes()
}