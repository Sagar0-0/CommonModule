@file:OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalMaterialApi::class
)


package fit.asta.health.profile.createprofile.view


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import fit.asta.health.profile.createprofile.view.HealthCreateBottomSheetTypes.*
import fit.asta.health.profile.createprofile.view.components.ItemSelectionLayout
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.TwoRadioBtnSelections
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
    onAddictionSelect: () -> Unit,
) {

    val checkedState = remember { mutableStateOf(true) }

    val radioButtonList =
        listOf(ButtonListTypes(buttonType = "First"), ButtonListTypes(buttonType = "Second"))

    //Inputs
    val injurySince by viewModel.injuriesSince.collectAsStateWithLifecycle()

    //Selection Inputs

    val radioButtonSelections by viewModel.radioButtonSelections.collectAsStateWithLifecycle()

    val selectedHealthHisDemo =
        radioButtonSelections[MultiRadioBtnKeys.HEALTHHIS] as TwoRadioBtnSelections?
    val selectedInjDemo =
        radioButtonSelections[MultiRadioBtnKeys.INJURIES] as TwoRadioBtnSelections?
    val selectedAilDemo =
        radioButtonSelections[MultiRadioBtnKeys.AILMENTS] as TwoRadioBtnSelections?
    val selectedMedDemo =
        radioButtonSelections[MultiRadioBtnKeys.MEDICATIONS] as TwoRadioBtnSelections?
    val selectedHealthTarDemo =
        radioButtonSelections[MultiRadioBtnKeys.HEALTHTAR] as TwoRadioBtnSelections?
    val selectedAddDemo =
        radioButtonSelections[MultiRadioBtnKeys.ADDICTION] as TwoRadioBtnSelections?

    //Data
    val propertiesDataState by viewModel.propertiesData.collectAsStateWithLifecycle()

    // Get the data for ComposeIndex.Third (key = ComposeIndex.First)
    val composeFirstData: Map<Int, SnapshotStateList<HealthProperties>>? =
        propertiesDataState[ComposeIndex.First]


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


            SelectionCardCreateProfile(
                cardType = "Any Significant Health History?",
                cardList = composeFirstData?.get(0),
                checkedState = checkedState,
                onItemsSelect = onHealthHistory,
                selectedOption = selectedHealthHisDemo,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(MultiRadioBtnKeys.HEALTHHIS, state)
                },
                cardIndex = 0,
                composeIndex = ComposeIndex.First,
                listName = "Health History"
            )



            Spacer(modifier = Modifier.height(spacing.medium))

            InjuriesLayout(
                cardType = "Any Injuries",
                cardType2 = "Body Part?",
                cardList = composeFirstData?.get(1),
                cardList2 = composeFirstData?.get(2),
                radioButtonList = radioButtonList,
                checkedState = checkedState,
                checkedState2 = checkedState,
                onItemsSelect = onInjuries,
                onItemsSelect2 = onBodyInjurySelect,
                selectedOption = selectedInjDemo,
                cardIndex1 = 1,
                cardIndex2 = 2,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(MultiRadioBtnKeys.INJURIES, state)
                },
                time = injurySince.value,
                listName = "Injuries",
                listName2 = "Body Part"
            )


            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Any Ailments?",
                cardList = composeFirstData?.get(3),
                checkedState = checkedState,
                onItemsSelect = onAilments,
                selectedOption = selectedAilDemo,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(MultiRadioBtnKeys.AILMENTS, state)
                },
                cardIndex = 3,
                composeIndex = ComposeIndex.First,
                listName = "Ailments"
            )


            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Any Medications?",
                cardList = composeFirstData?.get(4),
                checkedState = checkedState,
                onItemsSelect = onMedications,
                selectedOption = selectedMedDemo,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(MultiRadioBtnKeys.MEDICATIONS, state)
                },
                cardIndex = 4,
                composeIndex = ComposeIndex.First,
                listName = "Medication"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Any Health Targets?",
                cardList = composeFirstData?.get(5),
                checkedState = checkedState,
                onItemsSelect = onHealthTargets,
                selectedOption = selectedHealthTarDemo,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(MultiRadioBtnKeys.HEALTHTAR, state)
                },
                cardIndex = 5,
                composeIndex = ComposeIndex.First,
                listName = "Health Targets"
            )


            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Any Addiction?",
                cardList = composeFirstData?.get(6),
                checkedState = checkedState,
                onItemsSelect = onAddictionSelect,
                selectedOption = selectedAddDemo,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(MultiRadioBtnKeys.ADDICTION, state)
                },
                cardIndex = 6,
                composeIndex = ComposeIndex.First,
                listName = "Addictions"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            CreateProfileButtons(
                eventPrevious, eventNext, text = "Next", enableButton = true
            )

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


    AppModalBottomSheetLayout(sheetContent = {
        Spacer(modifier = Modifier.height(1.dp))
        currentBottomSheet?.let {
            HealthCreateBtmSheetLayout(
                sheetLayout = it, sheetState = { closeSheet() }, viewModel = viewModel
            )
        }
    }, sheetState = modalBottomSheetState, content = {
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
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "med"))
            },
            onHealthTargets = {
                currentBottomSheet = HEALTHTARGETS
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "tgt"))
            },
            onBodyInjurySelect = {
                currentBottomSheet = BODYPARTS
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "bp"))
            },
            onAddictionSelect = {
                currentBottomSheet = ADDICTION
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "add"))
            })
    })

}

enum class HealthCreateBottomSheetTypes {
    HEALTHHISTORY, INJURIES, AILMENTS, MEDICATIONS, HEALTHTARGETS, BODYPARTS, ADDICTION
}

@Composable
fun HealthCreateBtmSheetLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    sheetLayout: HealthCreateBottomSheetTypes,
    sheetState: () -> Unit,
) {

    when (sheetLayout) {
        HEALTHHISTORY -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    AppErrorScreen(onTryAgain = {})
                }

                is HPropState.Success -> {
                    ItemSelectionLayout(
                        cardList = state.properties,
                        cardIndex = 0,
                        composeIndex = ComposeIndex.First
                    )
                }
            }
        }

        INJURIES -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    AppErrorScreen(onTryAgain = {})
                }

                is HPropState.Success -> {
                    ItemSelectionLayout(
                        cardList = state.properties,
                        cardIndex = 1,
                        composeIndex = ComposeIndex.First
                    )
                }
            }
        }

        BODYPARTS -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    AppErrorScreen(onTryAgain = {})
                }

                is HPropState.Success -> {
                    ItemSelectionLayout(
                        cardList = state.properties,
                        cardIndex = 2,
                        composeIndex = ComposeIndex.First
                    )
                }
            }
        }

        AILMENTS -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    AppErrorScreen(onTryAgain = {})
                }

                is HPropState.Success -> {
                    ItemSelectionLayout(
                        cardList = state.properties,
                        cardIndex = 3,
                        composeIndex = ComposeIndex.First
                    )
                }
            }
        }

        MEDICATIONS -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    AppErrorScreen(onTryAgain = {})
                }

                is HPropState.Success -> {
                    ItemSelectionLayout(
                        cardList = state.properties,
                        cardIndex = 4,
                        composeIndex = ComposeIndex.First
                    )
                }
            }
        }

        HEALTHTARGETS -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    AppErrorScreen(onTryAgain = {})
                }

                is HPropState.Success -> {
                    ItemSelectionLayout(
                        cardList = state.properties,
                        cardIndex = 5,
                        composeIndex = ComposeIndex.First
                    )
                }
            }
        }

        ADDICTION -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    AppErrorScreen(onTryAgain = {})
                }

                is HPropState.Success -> {
                    ItemSelectionLayout(
                        cardList = state.properties,
                        cardIndex = 6,
                        composeIndex = ComposeIndex.First
                    )
                }
            }
        }
    }

}


