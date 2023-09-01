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

package fit.asta.health.profile.feature.create.view

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
import androidx.compose.material.rememberModalBottomSheetState
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
import fit.asta.health.data.testimonials.model.InputWrapper
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.AppModalBottomSheetLayout
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.profile.data.model.domain.ComposeIndex
import fit.asta.health.profile.data.model.domain.HealthProperties
import fit.asta.health.profile.data.model.domain.TwoRadioBtnSelections
import fit.asta.health.profile.feature.create.MultiRadioBtnKeys
import fit.asta.health.profile.feature.create.view.HealthCreateBottomSheetTypes.ADDICTION
import fit.asta.health.profile.feature.create.view.HealthCreateBottomSheetTypes.AILMENTS
import fit.asta.health.profile.feature.create.view.HealthCreateBottomSheetTypes.BODYPARTS
import fit.asta.health.profile.feature.create.view.HealthCreateBottomSheetTypes.HEALTHHISTORY
import fit.asta.health.profile.feature.create.view.HealthCreateBottomSheetTypes.HEALTHTARGETS
import fit.asta.health.profile.feature.create.view.HealthCreateBottomSheetTypes.INJURIES
import fit.asta.health.profile.feature.create.view.HealthCreateBottomSheetTypes.MEDICATIONS
import fit.asta.health.profile.feature.create.view.components.CreateProfileTwoButtonLayout
import fit.asta.health.profile.feature.create.view.components.ItemSelectionLayout
import fit.asta.health.profile.feature.create.vm.HPropState
import fit.asta.health.profile.feature.create.vm.ProfileEvent
import fit.asta.health.profile.feature.show.view.ButtonListTypes
import fit.asta.health.profile.feature.show.view.SelectionCardCreateProfile
import fit.asta.health.profile.feature.show.vm.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun HealthCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit,
    eventNext: () -> Unit,
) {

    val propertiesDataState by viewModel.propertiesData.collectAsStateWithLifecycle()
    val composeFirstData: Map<Int, SnapshotStateList<HealthProperties>>? =
        propertiesDataState[ComposeIndex.First]

    val searchQuery = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    var currentBottomSheet: HealthCreateBottomSheetTypes? by remember {
        mutableStateOf(null)
    }
    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = modalBottomSheetValue)

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

    val onItemClick: (HealthCreateBottomSheetTypes, String) -> Unit = { sheetType, propertyType ->
        currentBottomSheet = sheetType
        onBottomSheetItemClick(propertyType)
    }

    AppModalBottomSheetLayout(sheetContent = {
        Spacer(modifier = Modifier.height(1.dp))
        currentBottomSheet?.let {
            HealthCreateBtmSheetLayout(
                sheetLayout = it,
                sheetState = { closeSheet() },
                viewModel = viewModel,
                cardList2 = composeFirstData?.get(it.cardIndex),
                searchQuery = searchQuery
            )
        }
    }, sheetState = modalBottomSheetState, content = {
        HealthContent(
            eventPrevious = eventPrevious,
            eventNext = eventNext,
            onHealthHistory = { onItemClick(HEALTHHISTORY, "ailment") },
            onInjuries = { onItemClick(INJURIES, "injury") },
            onAilments = { onItemClick(AILMENTS, "ailment") },
            onMedications = { onItemClick(MEDICATIONS, "med") },
            onHealthTargets = { onItemClick(HEALTHTARGETS, "tgt") },
            onBodyInjurySelect = { onItemClick(BODYPARTS, "bp") },
            onAddictionSelect = { onItemClick(ADDICTION, "add") },
            composeFirstData = composeFirstData
        )
    })
}


@OptIn(ExperimentalFoundationApi::class)
@ExperimentalCoroutinesApi
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
    composeFirstData: Map<Int, SnapshotStateList<HealthProperties>>?,
) {
    val radioButtonList =
        listOf(ButtonListTypes(buttonType = "First"), ButtonListTypes(buttonType = "Second"))

    // Inputs
    val injurySince by viewModel.injuriesSince.collectAsStateWithLifecycle()

    // Selection Inputs
    val radioButtonSelections by viewModel.radioButtonSelections.collectAsStateWithLifecycle()

    val selectedHealthHistory =
        radioButtonSelections[MultiRadioBtnKeys.HEALTHHIS.key] as TwoRadioBtnSelections?
    val selectedInjuries =
        radioButtonSelections[MultiRadioBtnKeys.INJURIES.key] as TwoRadioBtnSelections?
    val selectedAilment =
        radioButtonSelections[MultiRadioBtnKeys.AILMENTS.key] as TwoRadioBtnSelections?
    val selectedMedication =
        radioButtonSelections[MultiRadioBtnKeys.MEDICATIONS.key] as TwoRadioBtnSelections?
    val selectedHealthTarget =
        radioButtonSelections[MultiRadioBtnKeys.HEALTHTAR.key] as TwoRadioBtnSelections?
    val selectedAddiction =
        radioButtonSelections[MultiRadioBtnKeys.ADDICTION.key] as TwoRadioBtnSelections?
    val selectedBodyPart =
        radioButtonSelections[MultiRadioBtnKeys.BODYPART.key] as TwoRadioBtnSelections?

    //List Creation
    val selectionList = listOf(
        Pair(ComposeIndex.First, selectedHealthHistory),
        Pair(ComposeIndex.First, selectedInjuries),
        Pair(ComposeIndex.First, selectedBodyPart),
        Pair(ComposeIndex.First, selectedAilment),
        Pair(ComposeIndex.First, selectedMedication),
        Pair(ComposeIndex.First, selectedHealthTarget),
        Pair(ComposeIndex.First, selectedAddiction)
    )

    val onItemSelectionFunctionList = listOf(
        onHealthHistory,
        onInjuries,
        onBodyInjurySelect,
        onAilments,
        onMedications,
        onHealthTargets,
        onAddictionSelect
    )

    val cardTypeList = listOf(
        MultiRadioBtnKeys.HEALTHHIS,
        MultiRadioBtnKeys.INJURIES,
        MultiRadioBtnKeys.BODYPART,
        MultiRadioBtnKeys.AILMENTS,
        MultiRadioBtnKeys.MEDICATIONS,
        MultiRadioBtnKeys.HEALTHTAR,
        MultiRadioBtnKeys.ADDICTION
    )

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        HealthContentLayout(
            viewModel = hiltViewModel(),
            composeFirstData = composeFirstData,
            radioButtonList = radioButtonList,
            selections = selectionList,
            onItemSelectFunctions = onItemSelectionFunctionList,
            cardTypes = cardTypeList,
            inputWrappers = listOf(injurySince),
            eventPrevious = eventPrevious,
            eventNext = eventNext
        )
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun HealthCreateBtmSheetLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    sheetLayout: HealthCreateBottomSheetTypes,
    sheetState: () -> Unit,
    cardList2: SnapshotStateList<HealthProperties>?,
    searchQuery: MutableState<String>,
) {
    val cardIndex = sheetLayout.cardIndex
    val state by viewModel.stateHp.collectAsStateWithLifecycle()
    when (state) {
        is HPropState.NoInternet -> {
            AppErrorScreen(onTryAgain = {})
        }

        is HPropState.Success -> {
            ItemSelectionLayout(
                cardList = (state as HPropState.Success).properties,
                cardIndex = cardIndex,
                composeIndex = ComposeIndex.First,
                cardList2 = cardList2,
                searchQuery = searchQuery
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
    onItemSelectFunctions: List<() -> Unit>,
    cardTypes: List<MultiRadioBtnKeys>,
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

        cardTypes.indices.forEach { index ->
            val (composeIndex, selectedOption) = selections[index]
            val cardType = cardTypes[index]
            val onItemSelect = onItemSelectFunctions[index]

            SelectionCardCreateProfile(
                cardType = cardType.getListName(),
                cardList = composeFirstData?.get(index),
                onItemsSelect = onItemSelect,
                selectedOption = selectedOption,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(cardType.key, state)
                },
                cardIndex = index,
                composeIndex = composeIndex,
                listName = cardType.getListName()
            )

            Spacer(modifier = Modifier.height(spacing.medium))
        }
        CreateProfileTwoButtonLayout(eventPrevious, eventNext)
        Spacer(modifier = Modifier.height(spacing.medium))
    }
}


sealed class HealthCreateBottomSheetTypes(val cardIndex: Int) {
    object HEALTHHISTORY : HealthCreateBottomSheetTypes(0)
    object INJURIES : HealthCreateBottomSheetTypes(1)
    object BODYPARTS : HealthCreateBottomSheetTypes(2)
    object AILMENTS : HealthCreateBottomSheetTypes(3)
    object MEDICATIONS : HealthCreateBottomSheetTypes(4)
    object HEALTHTARGETS : HealthCreateBottomSheetTypes(5)
    object ADDICTION : HealthCreateBottomSheetTypes(6)
}