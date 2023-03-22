@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.createprofile.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.bottomsheets.ItemSelectionBtmSheetLayout
import fit.asta.health.profile.createprofile.view.components.HealthCreateBottomSheetTypes.*
import fit.asta.health.profile.model.domain.UserSelection
import fit.asta.health.profile.view.ButtonListTypes
import fit.asta.health.profile.view.MultiToggleLayout
import fit.asta.health.profile.view.SelectionCardCreateProfile
import fit.asta.health.profile.view.components.AddIcon
import fit.asta.health.profile.view.components.RemoveChipOnCard
import fit.asta.health.profile.viewmodel.HPropState
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.testimonials.view.components.ValidateNumberField
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

    val healthHistoryList = listOf("Diabetes", "Heart Disease", "Stroke", "Depression")
    val checkedState = remember { mutableStateOf(true) }
    val radioButtonList =
        listOf(ButtonListTypes(buttonType = "Yes"), ButtonListTypes(buttonType = "No"))
    val healthHistoryList4 = listOf("Head", "Leg", "Hand", "Toe")

    val selectedHealthHis by viewModel.selectedHealthHisOption.collectAsStateWithLifecycle()
    val selectedAil by viewModel.selectedAilOption.collectAsStateWithLifecycle()
    val selectedMed by viewModel.selectedMedOption.collectAsStateWithLifecycle()
    val selectedHealthTar by viewModel.selectedHealthTarOption.collectAsStateWithLifecycle()

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
                cardType = "Any Significant Health history?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState = checkedState,
                onItemsSelect = onHealthHistory,
                selectedOption = selectedHealthHis,
                onStateChange = { state ->
                    viewModel.setSelectedHealthHisOption(state)
                },
                enabled = selectedHealthHis == UserSelection.Yes
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            InjuriesLayout(
                cardType = "Any Injuries",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState = checkedState,
                onItemsSelect = onInjuries,
                cardType2 = "Body Part?",
                cardList2 = healthHistoryList4,
                checkedState2 = checkedState,
                onItemsSelect2 = onBodyInjurySelect
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Any Ailments?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState = checkedState,
                onItemsSelect = onAilments,
                selectedOption = selectedAil,
                onStateChange = { state ->
                    viewModel.setSelectedAilOption(state)
                },
                enabled = selectedAil == UserSelection.Yes
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Any Medications?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState = checkedState,
                onItemsSelect = onMedications,
                selectedOption = selectedMed,
                onStateChange = { state ->
                    viewModel.setSelectedMedOption(state)
                },
                enabled = selectedMed == UserSelection.Yes
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Any Health Targets?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState = checkedState,
                onItemsSelect = onHealthTargets,
                selectedOption = selectedHealthTar,
                onStateChange = { state ->
                    viewModel.setSelectedHealthTarOption(state)
                },
                enabled = selectedHealthTar == UserSelection.Yes
            )

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

        HealthContent(
            eventPrevious = eventPrevious,
            eventNext = eventNext,
            onSkipEvent = onSkipEvent,
            onHealthHistory = {
                currentBottomSheet = HEALTHHISTORY
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "injury"))
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
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "injury"))
            },
        ) {
            currentBottomSheet = BODYINJURIY
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "injury"))
        }

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
                    ItemSelectionBtmSheetLayout(cardList = state.properties)
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
                    ItemSelectionBtmSheetLayout(cardList = state.properties)
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
                    ItemSelectionBtmSheetLayout(cardList = state.properties)
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
                    ItemSelectionBtmSheetLayout(cardList = state.properties)
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
                    ItemSelectionBtmSheetLayout(cardList = state.properties)
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
                    ItemSelectionBtmSheetLayout(cardList = state.properties)
                }
            }
        }
    }

}


@Composable
fun InjuriesLayout(
    cardType: String,
    cardType2: String,
    cardList: List<String>,
    cardList2: List<String>,
    radioButtonList: List<ButtonListTypes>,
    checkedState: (MutableState<Boolean>)? = null,
    checkedState2: (MutableState<Boolean>)? = null,
    onItemsSelect: () -> Unit,
    onItemsSelect2: () -> Unit,
) {


    val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }
    var text by remember { mutableStateOf(("")) }
    val focusManager = LocalFocusManager.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(spacing.small))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cardType,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                AddIcon(onClick = onItemsSelect)

            }

            MultiToggleLayout(
                selectionTypeText = null,
                radioButtonList = radioButtonList,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )

            if (selectedOption == radioButtonList[0].buttonType) {

                com.google.accompanist.flowlayout.FlowRow(
                    mainAxisSpacing = spacing.minSmall,
                    modifier = Modifier.padding(start = spacing.medium),
                ) {
                    cardList.forEach {
                        RemoveChipOnCard(textOnChip = it, checkedState = checkedState)
                    }
                }

                Spacer(modifier = Modifier.height(spacing.medium))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.medium),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        "Please Enter when you were Injured",
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(spacing.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.small)
                ) {

                    Box(Modifier.fillMaxWidth(0.5f)) {
                        ValidateNumberField(
                            value = text,
                            onValueChange = { text = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                        )
                    }

                    RowToggleButtonGroup(
                        buttonCount = 2,
                        onButtonClick = { index -> println(index) },
                        buttonTexts = arrayOf("Month", "Year"),
                        modifier = Modifier.size(width = 130.dp, height = 24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(spacing.small))

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.medium),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = cardType2,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    AddIcon(onClick = onItemsSelect2)
                }

                Spacer(modifier = Modifier.height(spacing.small))

                com.google.accompanist.flowlayout.FlowRow(
                    mainAxisSpacing = spacing.minSmall,
                    modifier = Modifier.padding(start = spacing.medium),
                ) {
                    cardList2.forEach {
                        RemoveChipOnCard(textOnChip = it, checkedState = checkedState2)
                    }
                }

                Spacer(modifier = Modifier.height(spacing.small))

            }

        }
    }

}
