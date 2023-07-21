@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.createprofile.view

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.main.ui.MainActivity
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.navigation.home.view.component.ErrorScreenLayout
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.profile.createprofile.view.DietCreateBottomSheetType.*
import fit.asta.health.profile.createprofile.view.components.ItemSelectionLayout
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.TwoToggleSelections
import fit.asta.health.profile.view.*
import fit.asta.health.profile.viewmodel.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun DietContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: (() -> Unit)? = null,
    onNonVegDays: () -> Unit,
    onFoodAllergies: () -> Unit,
    onCuisines: () -> Unit,
    onFoodRes: () -> Unit,
    onDietaryPref: () -> Unit,
) {

    val context = LocalContext.current

    val checkedState = remember { mutableStateOf(true) }

    val dietList by viewModel.dpData.collectAsState()

    val event = viewModel.stateSubmit.collectAsState()
    val events = event.value

    var buttonClicked by remember { mutableStateOf(false) }

    val selectedFoodRes by viewModel.selectedFoodResOption.collectAsStateWithLifecycle()
    val doAllInputsValid by viewModel.doAllDataInputsValid.collectAsStateWithLifecycle()

    val isFoodResValid = when (selectedFoodRes) {
        TwoToggleSelections.First -> dietList.getValue(4).isNotEmpty()
        TwoToggleSelections.Second -> true
        null -> false
    }

    if (isFoodResValid) {
        viewModel.onEvent(ProfileEvent.IsDietValid(true))
    } else {
        viewModel.onEvent(ProfileEvent.IsDietValid(false))
    }


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
                cardList = dietList.getValue(0),
                checkedState = checkedState,
                onItemsSelect = onDietaryPref,
                cardIndex = 0,
                composeIndex = ComposeIndex.Third
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Non-Veg Consumption Days?",
                cardList = dietList.getValue(1),
                checkedState = checkedState,
                onItemsSelect = onNonVegDays,
                cardIndex = 1,
                composeIndex = ComposeIndex.Third
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Food Allergies?",
                cardList = dietList.getValue(2),
                checkedState = checkedState,
                onItemsSelect = onFoodAllergies,
                cardIndex = 2,
                composeIndex = ComposeIndex.Third
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Cuisines?",
                cardList = dietList.getValue(3),
                checkedState = checkedState,
                onItemsSelect = onCuisines,
                cardIndex = 3,
                composeIndex = ComposeIndex.Third
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Food Restrictions?",
                cardList = dietList.getValue(4),
                checkedState = checkedState,
                onItemsSelect = onFoodRes,
                selectedOption = selectedFoodRes,
                onStateChange = { state ->
                    viewModel.onEvent(
                        ProfileEvent.SetSelectedFoodResOption(
                            state, 11
                        )
                    )
                },
                enabled = selectedFoodRes == TwoToggleSelections.First,
                cardIndex = 4,
                composeIndex = ComposeIndex.Third,
                listName = "Diet"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            CreateProfileButtons(
                eventPrevious, eventNext = {
                    buttonClicked = !buttonClicked
                    viewModel.onEvent(ProfileEvent.OnSubmit)
                }, text = "Submit", enableButton = doAllInputsValid
            )

            if (buttonClicked) {
                when (events) {
                    is ProfileSubmitState.Empty -> {}
                    is ProfileSubmitState.Error -> {
                        Log.d(
                            "validate",
                            "Error -> ${events.error} and message -> ${events.error.message} and ${events.error.localizedMessage}"
                        )
                    }

                    is ProfileSubmitState.Loading -> LoadingAnimation()
                    is ProfileSubmitState.NoInternet -> ErrorScreenLayout(onTryAgain = {})
                    is ProfileSubmitState.Success -> {
                        (context as MainActivity).loadAppScreen()
                        Log.d("validate", "Success -> ${events.userProfile}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(spacing.medium))

        }
    }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DietCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: (() -> Unit)? = null,
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


    ModalBottomSheetLayout(modifier = Modifier.fillMaxSize(),
        sheetState = modalBottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                DietCreateBottomSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
            }
        }) {

        DietContent(eventPrevious = eventPrevious, onNonVegDays = {
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
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    ErrorScreenLayout(onTryAgain = {})
                }

                is HPropState.Success -> {
                    ItemSelectionLayout(
                        cardList = state.properties,
                        cardIndex = 0,
                        composeIndex = ComposeIndex.Third
                    )
                }
            }
        }

        NONVEGDAYS -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    ErrorScreenLayout(onTryAgain = {})
                }

                is HPropState.Success -> {
                    ItemSelectionLayout(
                        cardList = state.properties,
                        cardIndex = 1,
                        composeIndex = ComposeIndex.Third
                    )
                }
            }
        }

        FOODALLERGIES -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    ErrorScreenLayout(onTryAgain = {})
                }

                is HPropState.Success -> {
                    ItemSelectionLayout(
                        cardList = state.properties,
                        cardIndex = 2,
                        composeIndex = ComposeIndex.Third
                    )
                }
            }
        }

        CUISINES -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    ErrorScreenLayout(onTryAgain = {})
                }

                is HPropState.Success -> {
                    ItemSelectionLayout(
                        cardList = state.properties,
                        cardIndex = 3,
                        composeIndex = ComposeIndex.Third
                    )
                }
            }
        }

        FOODRES -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    ErrorScreenLayout(onTryAgain = {})
                }

                is HPropState.Success -> {
                    ItemSelectionLayout(
                        cardList = state.properties,
                        cardIndex = 4,
                        composeIndex = ComposeIndex.Third
                    )
                }
            }
        }
    }
}
