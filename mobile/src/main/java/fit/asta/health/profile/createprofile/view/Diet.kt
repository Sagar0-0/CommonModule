@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.createprofile.view

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.MainActivity
import fit.asta.health.navigation.home.view.component.ErrorScreenLayout
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.profile.MultiRadioBtnKeys
import fit.asta.health.profile.createprofile.view.DietCreateBottomSheetType.*
import fit.asta.health.profile.createprofile.view.components.ItemSelectionLayout
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.TwoRadioBtnSelections
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

    //Data
    val propertiesDataState by viewModel.propertiesData.collectAsStateWithLifecycle()

    // Get the data for ComposeIndex.Third (key = ComposeIndex.First)
    val composeThirdData: Map<Int, SnapshotStateList<HealthProperties>>? =
        propertiesDataState[ComposeIndex.Third]


    val event = viewModel.stateSubmit.collectAsState()
    val events = event.value

    var buttonClicked by remember { mutableStateOf(false) }

    //Radio Button Selection
    val radioButtonSelections by viewModel.radioButtonSelections.collectAsStateWithLifecycle()

    val selectedFoodResDemo =
        radioButtonSelections[MultiRadioBtnKeys.DIETREST] as TwoRadioBtnSelections?

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
                cardList = composeThirdData?.get(0),
                checkedState = checkedState,
                onItemsSelect = onDietaryPref,
                cardIndex = 0,
                composeIndex = ComposeIndex.Third
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Non-Veg Consumption Days?",
                cardList = composeThirdData?.get(1),
                checkedState = checkedState,
                onItemsSelect = onNonVegDays,
                cardIndex = 1,
                composeIndex = ComposeIndex.Third
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Food Allergies?",
                cardList = composeThirdData?.get(2),
                checkedState = checkedState,
                onItemsSelect = onFoodAllergies,
                cardIndex = 2,
                composeIndex = ComposeIndex.Third
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Cuisines?",
                cardList = composeThirdData?.get(3),
                checkedState = checkedState,
                onItemsSelect = onCuisines,
                cardIndex = 3,
                composeIndex = ComposeIndex.Third
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SelectionCardCreateProfile(
                cardType = "Food Restrictions?",
                cardList = composeThirdData?.get(4),
                checkedState = checkedState,
                onItemsSelect = onFoodRes,
                selectedOption = selectedFoodResDemo,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(MultiRadioBtnKeys.DIETREST, state)
                },
                enabled = selectedFoodResDemo == TwoRadioBtnSelections.First,
                cardIndex = 4,
                composeIndex = ComposeIndex.Third,
                listName = "Diet"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            CreateProfileButtons(
                eventPrevious, eventNext = {
                    buttonClicked = !buttonClicked
                    viewModel.onEvent(ProfileEvent.OnSubmit)
                }, text = "Submit", enableButton = true
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: (() -> Unit)? = null,
    open: Boolean = true
) {

    var openBottomSheet by rememberSaveable { mutableStateOf(open) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

    var currentBottomSheet: DietCreateBottomSheetType? by remember {
        mutableStateOf(null)
    }

    val openSheet = {
        scope.launch {
            bottomSheetState.partialExpand()
        }
    }

    val closeSheet = {
        scope.launch { bottomSheetState.hide() }
    }

    if (openBottomSheet) {

        val windowInsets = if (edgeToEdgeEnabled)
            WindowInsets(0) else BottomSheetDefaults.windowInsets

        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
            windowInsets = windowInsets,
            modifier = Modifier.fillMaxSize(),
            ) {

            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                DietCreateBottomSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
            }

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
