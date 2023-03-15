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
import fit.asta.health.profile.createprofile.view.components.DietCreateBottomSheetType.*
import fit.asta.health.profile.view.*
import fit.asta.health.ui.theme.spacing
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DietContent(
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    onNonVegDays: () -> Unit,
    onFoodAllergies: () -> Unit,
    onCuisines: () -> Unit,
    onFoodRes: () -> Unit,
    onDietaryPref: () -> Unit,
) {

    val healthHistoryList6 = listOf("Veg", "Non-Veg", "Vegan")
    val healthHistoryList5 = listOf("Cycling", "Walking", "Swimming", "Gym", "Dancing", "Bowling")
    val checkedState = remember { mutableStateOf(true) }
    val radioButtonList =
        listOf(ButtonListTypes(buttonType = "Yes"), ButtonListTypes(buttonType = "No"))

    val healthHistoryList = listOf("Diabetes", "Heart Disease", "Stroke", "Depression")


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

            OnlyChipSelectionCard(
                cardType = "Dietary Preferences",
                cardList = healthHistoryList6,
                checkedState,
                onDietaryPref
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Non-Veg Consumption Days?",
                cardList = healthHistoryList5,
                checkedState,
                onNonVegDays
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Food Allergies?",
                cardList = healthHistoryList5,
                checkedState,
                onFoodAllergies
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Cuisines?", cardList = healthHistoryList5, checkedState, onCuisines
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Food Restrictions?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState,
                onFoodRes
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            CreateProfileButtons(eventPrevious, eventNext, text = "Done")

            Spacer(modifier = Modifier.height(spacing.medium))

        }
    }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DietCreateScreen(
    eventPrevious: (() -> Unit)? = null,
    eventDone: (() -> Unit)? = null,
) {
    var currentBottomSheet: DietCreateBottomSheetType? by remember {
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


    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = modalBottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                DietCreateBottomSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
            }
        }) {

        DietContent(eventPrevious, eventDone, onNonVegDays = {
            currentBottomSheet = NONVEGDAYS
            openSheet()
        }, onFoodAllergies = {
            currentBottomSheet = FOODALLERGIES
            openSheet()
        }, onCuisines = {
            currentBottomSheet = CUISINES
            openSheet()
        }, onFoodRes = {
            currentBottomSheet = FOODRES
            openSheet()
        }, onDietaryPref = {
            currentBottomSheet = DIETARYPREF
            openSheet()
        })

    }

}


enum class DietCreateBottomSheetType {
    DIETARYPREF, NONVEGDAYS, FOODALLERGIES, CUISINES, FOODRES
}

@Composable
fun DietCreateBottomSheetLayout(
    sheetLayout: DietCreateBottomSheetType,
    closeSheet: () -> Unit,
) {
    when (sheetLayout) {
        DIETARYPREF -> Screen1(closeSheet)
        NONVEGDAYS -> Screen1(closeSheet)
        FOODALLERGIES -> Screen1(closeSheet)
        CUISINES -> Screen1(closeSheet)
        FOODRES -> Screen1(closeSheet)
    }
}
