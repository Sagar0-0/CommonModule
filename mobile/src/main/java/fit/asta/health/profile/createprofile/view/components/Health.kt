package fit.asta.health.profile.createprofile.view.components

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.createprofile.view.components.HealthCreateBottomSheetTypes.*
import fit.asta.health.profile.view.*
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
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    val healthHistoryList = listOf("Diabetes", "Heart Disease", "Stroke", "Depression")
    val checkedState = remember { mutableStateOf(true) }
    val radioButtonList =
        listOf(ButtonListTypes(buttonType = "Yes"), ButtonListTypes(buttonType = "No"))
    val healthHistoryList3 = listOf("6 Months Ago") // Convert it to TextField

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

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = modalBottomSheetValue
    )

    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch { modalBottomSheetState.hide() }
    }

    val openSheet = {
        scope.launch {
            modalBottomSheetState.show()
            modalBottomSheetValue = ModalBottomSheetValue.Expanded
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        sheetState = modalBottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                HealthCreateBtmSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
            }
        }
    ) {

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
        })

    }

}


enum class HealthCreateBottomSheetTypes {
    HEALTHHISTORY, INJURIES, AILMENTS, MEDICATIONS, HEALTHTARGETS
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
    }

}
