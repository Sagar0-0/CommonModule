@file:OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class
)

package fit.asta.health.profile.createprofile.view.components

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.bottomsheets.ItemSelectionBtmSheetLayout
import fit.asta.health.profile.createprofile.view.components.HealthCreateBottomSheetTypes.*
import fit.asta.health.profile.model.domain.TwoToggleSelections
import fit.asta.health.profile.view.ButtonListTypes
import fit.asta.health.profile.view.SelectionCardCreateProfile
import fit.asta.health.profile.viewmodel.HPropState
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HealthContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    onSkipEvent: (Int) -> Unit,
    onHealthHistory: () -> Unit,
    onInjuries: () -> Unit,
    onAilments: () -> Unit,
    onMedications: () -> Unit,
    onHealthTargets: () -> Unit,
    onBodyInjurySelect: () -> Unit,
) {

    val checkedState = remember { mutableStateOf(true) }
    val radioButtonList =
        listOf(ButtonListTypes(buttonType = "First"), ButtonListTypes(buttonType = "Second"))

    val selectedHealthHis by viewModel.selectedHealthHisOption.collectAsStateWithLifecycle()
    val selectedAil by viewModel.selectedAilOption.collectAsStateWithLifecycle()
    val selectedMed by viewModel.selectedMedOption.collectAsStateWithLifecycle()
    val selectedHealthTar by viewModel.selectedHealthTarOption.collectAsStateWithLifecycle()
    val selectedInjury by viewModel.selectedInjOption.collectAsStateWithLifecycle()
//    val healthHisList by viewModel.healthHisList.collectAsStateWithLifecycle()
//    val injuriesList by viewModel.injuriesList.collectAsStateWithLifecycle()

    val healthHisList by viewModel.healthPropertiesData.collectAsStateWithLifecycle()


    Log.d("validate", "Health History List -> $healthHisList")

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

            healthHisList[0]?.let {
                SelectionCardCreateProfile(
                    cardType = "Any Significant Health History?",
                    cardList = it,
                    radioButtonList = radioButtonList,
                    checkedState = checkedState,
                    onItemsSelect = onHealthHistory,
                    selectedOption = selectedHealthHis,
                    onStateChange = { state ->
                        viewModel.onEvent(ProfileEvent.SetSelectHealthHisOption(state))
                    },
                    enabled = selectedHealthHis == TwoToggleSelections.First,
                    cardIndex = 0
                )
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            InjuriesLayout(cardType = "Any Injuries",
                cardType2 = "Body Part?",
                cardList = healthHisList[1]!!,
                cardList2 = healthHisList[2]!!,
                radioButtonList = radioButtonList,
                checkedState = checkedState,
                checkedState2 = checkedState,
                onItemsSelect = onInjuries,
                onItemsSelect2 = onBodyInjurySelect,
                selectedOption = selectedInjury,
                cardIndex1 = 1,
                cardIndex2 = 2,
                onStateChange = { state ->
                    viewModel.onEvent(ProfileEvent.SetSelectedInjOption(state))
                })

            Spacer(modifier = Modifier.height(spacing.medium))

            healthHisList[3]?.let {
                SelectionCardCreateProfile(
                    cardType = "Any Ailments?",
                    cardList = it,
                    radioButtonList = radioButtonList,
                    checkedState = checkedState,
                    onItemsSelect = onAilments,
                    selectedOption = selectedAil,
                    onStateChange = { state ->
                        viewModel.onEvent(ProfileEvent.SetSelectedAilOption(state))
                    },
                    enabled = selectedAil == TwoToggleSelections.First,
                    cardIndex = 3
                )
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            healthHisList[4]?.let {
                SelectionCardCreateProfile(
                    cardType = "Any Medications?",
                    cardList = it,
                    radioButtonList = radioButtonList,
                    checkedState = checkedState,
                    onItemsSelect = onMedications,
                    selectedOption = selectedMed,
                    onStateChange = { state ->
                        viewModel.onEvent(ProfileEvent.SetSelectedMedOption(state))
                    },
                    enabled = selectedMed == TwoToggleSelections.First
                )
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            healthHisList[5]?.let {
                SelectionCardCreateProfile(
                    cardType = "Any Health Targets?",
                    cardList = it,
                    radioButtonList = radioButtonList,
                    checkedState = checkedState,
                    onItemsSelect = onHealthTargets,
                    selectedOption = selectedHealthTar,
                    onStateChange = { state ->
                        viewModel.onEvent(ProfileEvent.SetSelectedHealthTarOption(state))
                    },
                    enabled = selectedHealthTar == TwoToggleSelections.First
                )
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            CreateProfileButtons(eventPrevious, eventNext, text = "Next")

            Spacer(modifier = Modifier.height(spacing.medium))

            SkipPage(onSkipEvent = onSkipEvent)

            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }

}


@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun HealthCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    onSkipEvent: (Int) -> Unit,
) {

    var currentBottomSheet: HealthCreateBottomSheetTypes? by remember {
        mutableStateOf(null)
    }

    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }

    val modalBottomSheetState = rememberModalBottomSheetState(modalBottomSheetValue)

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

    ModalBottomSheetLayout(modifier = Modifier
        .fillMaxSize()
        .wrapContentHeight(),
        sheetState = modalBottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                HealthCreateBtmSheetLayout(
                    sheetLayout = it, closeSheet = { closeSheet() }, viewModel = viewModel
                )
            }
        }) {

        HealthContent(eventPrevious = eventPrevious,
            eventNext = eventNext,
            onSkipEvent = onSkipEvent,
            onHealthHistory = {
                currentBottomSheet = HEALTHHISTORY
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "ailment"))
            },
            onInjuries = {
                currentBottomSheet = INJURIES
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "injury"))
            },
            onAilments = {
                currentBottomSheet = AILMENTS
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "ailment"))
            },
            onMedications = {
                currentBottomSheet = MEDICATIONS
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "injury"))
            },
            onHealthTargets = {
                currentBottomSheet = HEALTHTARGETS
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "goal"))
            },
            onBodyInjurySelect = {
                currentBottomSheet = BODYINJURIY
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "bp"))
            })

    }

}

enum class HealthCreateBottomSheetTypes {
    HEALTHHISTORY, INJURIES, AILMENTS, MEDICATIONS, HEALTHTARGETS, BODYINJURIY
}

@Composable
fun HealthCreateBtmSheetLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    sheetLayout: HealthCreateBottomSheetTypes,
    closeSheet: () -> Unit,
) {

    when (sheetLayout) {
        HEALTHHISTORY -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> {}
                is HPropState.NoInternet -> {}
                is HPropState.Success -> {
                    ItemSelectionBtmSheetLayout(cardList = state.properties, cardIndex = 0)
                }
            }
        }
        INJURIES -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> {}
                is HPropState.NoInternet -> {}
                is HPropState.Success -> {
                    ItemSelectionBtmSheetLayout(cardList = state.properties, cardIndex = 1)
                }
            }
        }
        AILMENTS -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> {}
                is HPropState.NoInternet -> {}
                is HPropState.Success -> {
                    ItemSelectionBtmSheetLayout(cardList = state.properties, cardIndex = 2)
                }
            }
        }
        MEDICATIONS -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> {}
                is HPropState.NoInternet -> {}
                is HPropState.Success -> {
                    ItemSelectionBtmSheetLayout(cardList = state.properties, cardIndex = 3)
                }
            }
        }
        HEALTHTARGETS -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> {}
                is HPropState.NoInternet -> {}
                is HPropState.Success -> {
                    ItemSelectionBtmSheetLayout(cardList = state.properties, cardIndex = 4)
                }
            }
        }
        BODYINJURIY -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> {}
                is HPropState.NoInternet -> {}
                is HPropState.Success -> {
                    ItemSelectionBtmSheetLayout(cardList = state.properties, cardIndex = 5)
                }
            }
        }
    }

}


