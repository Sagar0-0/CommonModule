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

    val checkedState = remember { mutableStateOf(true) }

    val radioButtonList = listOf(
        ButtonListTypes(buttonType = "Morning"),
        ButtonListTypes(buttonType = "Afternoon"),
        ButtonListTypes(buttonType = "Night")
    )

    val radioButtonList2 = listOf(
        ButtonListTypes(buttonType = "Less"),
        ButtonListTypes(buttonType = "Moderate"),
        ButtonListTypes(buttonType = "Very")
    )

    val radioButtonList3 = listOf(
        ButtonListTypes(buttonType = "Standing"),
        ButtonListTypes(buttonType = "Sitting"),
    )

    val radioButtonList4 = listOf(
        ButtonListTypes(buttonType = "Indoor"),
        ButtonListTypes(buttonType = "Outdoor"),
    )

    val healthHistoryList5 = listOf("Cycling", "Walking", "Swimming", "Gym", "Dancing", "Bowling")

    val (selectedWorkingOption, onWorkingOptionSelected) = remember { mutableStateOf("") }
    val (selectedActiveOption, onActiveOptionSelected) = remember { mutableStateOf("") }
    val (selectedEnvOption, onEnvOptionSelected) = remember { mutableStateOf("") }
    val (selectedWorkStyleOption, onWorkStyleOptionSelected) = remember { mutableStateOf("") }

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

            MultiToggleWithTitle(
                selectionTypeText = "Are you physically active ? ",
                radioButtonList = radioButtonList2,
                selectedOption = selectedActiveOption,
                onOptionSelected = onActiveOptionSelected
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            MultiToggleWithTitle(
                selectionTypeText = "Current Working Environment?",
                radioButtonList = radioButtonList3,
                selectedOption = selectedEnvOption,
                onOptionSelected = onEnvOptionSelected
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            MultiToggleWithTitle(
                selectionTypeText = "Current WorkStyle?",
                radioButtonList = radioButtonList4,
                selectedOption = selectedWorkStyleOption,
                onOptionSelected = onWorkStyleOptionSelected
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            MultiToggleWithTitle(
                selectionTypeText = "What are your working hours?",
                radioButtonList = radioButtonList,
                selectedOption = selectedWorkingOption,
                onOptionSelected = onWorkingOptionSelected
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
            if (modalBottomSheetValue == ModalBottomSheetValue.HalfExpanded) {
                modalBottomSheetValue = ModalBottomSheetValue.Expanded
            }
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