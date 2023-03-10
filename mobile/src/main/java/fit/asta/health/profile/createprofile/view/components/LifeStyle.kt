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
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.createprofile.view.components.LifeStyleCreateBottomSheetType.*
import fit.asta.health.profile.view.*
import fit.asta.health.ui.spacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LifeStyleContent(
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    onSkipEvent: (Int) -> Unit,
    onCurrentActivity: () -> Unit,
    onPreferredActivity: () -> Unit,
    onLifeStyleTargets: () -> Unit,
) {

    val healthHistoryList4 = listOf("Less", "Moderate", "Very")
    val healthHistoryList6 = listOf("Standing", "Sitting")
    val healthHistoryList7 = listOf("Indoor", "Outdoor")
    val checkedState = remember { mutableStateOf(true) }
    val radioButtonList3 = listOf(
        ButtonListTypes(buttonType = "Morning"),
        ButtonListTypes(buttonType = "Afternoon"),
        ButtonListTypes(buttonType = "Night")
    )
    val healthHistoryList5 = listOf("Cycling", "Walking", "Swimming", "Gym", "Dancing", "Bowling")

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

            SelectionOutlineButton(
                cardType = "Are you physically active ?", cardList = healthHistoryList4
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionOutlineButton(
                cardType = "Current Working Environment?", cardList = healthHistoryList6
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionOutlineButton(
                cardType = "Current WorkStyle?", cardList = healthHistoryList7
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioButtonList3[0]) }

            MultiToggleWithTitle(
                selectionTypeText = "What are your working hours?",
                radioButtonList = radioButtonList3,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Current Activities?",
                cardList = healthHistoryList5,
                checkedState,
                onCurrentActivity
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Preferred Activities?",
                cardList = healthHistoryList5,
                checkedState,
                onPreferredActivity
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "LifeStyleTargets?",
                cardList = healthHistoryList5,
                checkedState,
                onLifeStyleTargets
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
fun LifeStyleCreateScreen(
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    onSkipEvent: (Int) -> Unit,
) {

    var currentBottomSheet: LifeStyleCreateBottomSheetType? by remember {
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


    ModalBottomSheetLayout(modifier = Modifier.fillMaxSize(),
        sheetState = modalBottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                LifeStyleCreateBottomSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
            }
        }) {
        LifeStyleContent(eventPrevious, eventNext, onSkipEvent, onCurrentActivity = {
            currentBottomSheet = CURRENTACTIVITIES
            openSheet()
        }, onPreferredActivity = {
            currentBottomSheet = PREFERREDACTIVITIES
            openSheet()
        }, onLifeStyleTargets = {
            currentBottomSheet = LIFESTYLETARGETS
            openSheet()
        })
    }


}


enum class LifeStyleCreateBottomSheetType {
    CURRENTACTIVITIES, PREFERREDACTIVITIES, LIFESTYLETARGETS
}

@Composable
fun LifeStyleCreateBottomSheetLayout(
    sheetLayout: LifeStyleCreateBottomSheetType,
    closeSheet: () -> Unit,
) {
    when (sheetLayout) {
        CURRENTACTIVITIES -> Screen1(closeSheet)
        PREFERREDACTIVITIES -> Screen1(closeSheet)
        LIFESTYLETARGETS -> Screen1(closeSheet)
    }
}