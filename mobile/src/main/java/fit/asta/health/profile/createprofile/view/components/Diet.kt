@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.bottomsheets.ItemSelectionBtmSheetLayout
import fit.asta.health.profile.createprofile.view.components.DietCreateBottomSheetType.*
import fit.asta.health.profile.model.domain.TwoToggleSelections
import fit.asta.health.profile.view.*
import fit.asta.health.profile.viewmodel.HPropState
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DietContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    onNonVegDays: () -> Unit,
    onFoodAllergies: () -> Unit,
    onCuisines: () -> Unit,
    onFoodRes: () -> Unit,
    onDietaryPref: () -> Unit,
) {
    val checkedState = remember { mutableStateOf(true) }
    val radioButtonList =
        listOf(ButtonListTypes(buttonType = "First"), ButtonListTypes(buttonType = "Second"))

    val selectedFoodRes by viewModel.selectedFoodResOption.collectAsStateWithLifecycle()
    val dietList by viewModel.healthHisList.collectAsStateWithLifecycle()

    val dietData by viewModel.healthPropertiesData.collectAsStateWithLifecycle()

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
                cardList = dietList,
                checkedState = checkedState,
                onItemsSelect = onDietaryPref
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Non-Veg Consumption Days?",
                cardList = dietList,
                checkedState = checkedState,
                onItemsSelect = onNonVegDays
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Food Allergies?",
                cardList = dietList,
                checkedState = checkedState,
                onItemsSelect = onFoodAllergies
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Cuisines?",
                cardList = dietList,
                checkedState = checkedState,
                onItemsSelect = onCuisines
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Food Restrictions?",
                cardList = dietList,
                radioButtonList = radioButtonList,
                checkedState = checkedState,
                onItemsSelect = onFoodRes,
                selectedOption = selectedFoodRes,
                onStateChange = { state ->
                    viewModel.onEvent(
                        ProfileEvent.SetSelectedFoodResOption(
                            state
                        )
                    )
                },
                enabled = selectedFoodRes == TwoToggleSelections.First
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
    viewModel: ProfileViewModel = hiltViewModel(),
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

        DietContent(eventPrevious = eventPrevious, eventNext = eventDone, onNonVegDays = {
            currentBottomSheet = NONVEGDAYS
            openSheet()
        }, onFoodAllergies = {
            currentBottomSheet = FOODALLERGIES
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "food"))
        }, onCuisines = {
            currentBottomSheet = CUISINES
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "cu"))
        }, onFoodRes = {
            currentBottomSheet = FOODRES
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "food"))
        }, onDietaryPref = {
            currentBottomSheet = DIETARYPREF
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "dp"))
        })

    }

}


enum class DietCreateBottomSheetType {
    DIETARYPREF, NONVEGDAYS, FOODALLERGIES, CUISINES, FOODRES
}

@Composable
fun DietCreateBottomSheetLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    sheetLayout: DietCreateBottomSheetType,
    closeSheet: () -> Unit,
) {
    when (sheetLayout) {
        DIETARYPREF -> {
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
        NONVEGDAYS -> {
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
        FOODALLERGIES -> {
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
        CUISINES -> {
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
        FOODRES -> {
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
