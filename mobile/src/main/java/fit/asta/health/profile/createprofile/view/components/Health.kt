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
import fit.asta.health.profile.createprofile.view.components.HealthCreateBottomSheetTypes.*
import fit.asta.health.profile.view.*
import fit.asta.health.profile.view.components.AddIcon
import fit.asta.health.profile.view.components.ChipsOnCards
import fit.asta.health.testimonials.view.components.ValidateNumberField
import fit.asta.health.testimonials.view.theme.cardElevation
import fit.asta.health.ui.spacing
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HealthContent(
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
    val healthHistoryList3 = listOf("6 Months Ago") // Convert it to TextField
    val healthHistoryList4 = listOf("Head", "Leg", "Hand", "Toe")

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
                checkedState,
                onItemsSelect = onHealthHistory
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "When were you recently Injured?",
                cardList = healthHistoryList3,
                checkedState,
                onItemsSelect = onHealthHistory
            ) // Convert it to Text field

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Any Injuries?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState,
                onItemsSelect = onInjuries
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
                checkedState,
                onItemsSelect = onAilments
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Any Medications?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState,
                onItemsSelect = onMedications
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Any Health Targets?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState,
                onItemsSelect = onHealthTargets
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            CreateProfileButtons(eventPrevious, eventNext, text = "Next")

            Spacer(modifier = Modifier.height(spacing.medium))

            SkipPage(onSkipEvent = onSkipEvent)

            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HealthCreateScreen(
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

    val modalBottomSheetState =
        rememberModalBottomSheetState(modalBottomSheetValue)

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
                HealthCreateBtmSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
            }
        }) {

        HealthContent(eventPrevious, eventNext, onSkipEvent, onHealthHistory = {
            currentBottomSheet = HEALTHHISTORY
            openSheet()
        }, onInjuries = {
            currentBottomSheet = INJURIES
            openSheet()
        }, onAilments = {
            currentBottomSheet = AILMENTS
            openSheet()
        }, onMedications = {
            currentBottomSheet = MEDICATIONS
            openSheet()
        }, onHealthTargets = {
            currentBottomSheet = HEALTHTARGETS
            openSheet()
        }, onBodyInjurySelect = {
            currentBottomSheet = BODYINJURIY
            openSheet()
        })

    }

}


enum class HealthCreateBottomSheetTypes {
    HEALTHHISTORY, INJURIES, AILMENTS, MEDICATIONS, HEALTHTARGETS, BODYINJURIY
}

@Composable
fun HealthCreateBtmSheetLayout(
    sheetLayout: HealthCreateBottomSheetTypes,
    closeSheet: () -> Unit,
) {

    when (sheetLayout) {
        HEALTHHISTORY -> Screen1(closeSheet)
        INJURIES -> Screen1(closeSheet)
        AILMENTS -> Screen1(closeSheet)
        MEDICATIONS -> Screen1(closeSheet)
        HEALTHTARGETS -> Screen1(closeSheet)
        BODYINJURIY -> Screen1(closeSheet)
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
                        ChipsOnCards(textOnChip = it, checkedState = checkedState)
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
                        ChipsOnCards(textOnChip = it, checkedState = checkedState2)
                    }
                }

                Spacer(modifier = Modifier.height(spacing.small))

            }

        }
    }

}
