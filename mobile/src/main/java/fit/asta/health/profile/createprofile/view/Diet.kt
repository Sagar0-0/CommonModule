@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.createprofile.view

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.MainActivity
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.AppModalBottomSheetLayout
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.MultiRadioBtnKeys
import fit.asta.health.profile.createprofile.view.DietCreateBottomSheetType.*
import fit.asta.health.profile.createprofile.view.components.CreateProfileTwoButtonLayout
import fit.asta.health.profile.createprofile.view.components.ItemSelectionLayout
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.TwoRadioBtnSelections
import fit.asta.health.profile.view.*
import fit.asta.health.profile.viewmodel.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DietCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit,
) {
    var currentBottomSheet: DietCreateBottomSheetType? by remember {
        mutableStateOf(null)
    }

    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }

    val modalBottomSheetState = androidx.compose.material.rememberModalBottomSheetState(
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

    AppModalBottomSheetLayout(sheetContent = {
        Spacer(modifier = Modifier.height(1.dp))
        currentBottomSheet?.let {
            DietCreateBottomSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
        }
    }, sheetState = modalBottomSheetState, content = {
        DietContent(eventPrevious = eventPrevious, onNonVegDays = {
            currentBottomSheet = NONVEGDAYS
            openSheet()/* TODO non-veg days property type?*/
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = NONVEGDAYS.propertyType))
        }, onFoodAllergies = {
            currentBottomSheet = FOODALLERGIES
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = FOODALLERGIES.propertyType))
        }, onCuisines = {
            currentBottomSheet = CUISINES
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = CUISINES.propertyType))
        }, onFoodRes = {
            currentBottomSheet = FOODRES
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = FOODRES.propertyType))
        }, onDietaryPref = {
            currentBottomSheet = DIETARYPREF
            openSheet()
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = DIETARYPREF.propertyType))
        })
    })
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun DietContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit,
    onNonVegDays: () -> Unit,
    onFoodAllergies: () -> Unit,
    onCuisines: () -> Unit,
    onFoodRes: () -> Unit,
    onDietaryPref: () -> Unit,
) {

    val context = LocalContext.current

    //Data
    val propertiesDataState by viewModel.propertiesData.collectAsStateWithLifecycle()
    val composeThirdData: Map<Int, SnapshotStateList<HealthProperties>>? =
        propertiesDataState[ComposeIndex.Third]

    val cardList = listOf(
        OnlySelectionCardData(
            "Dietary Preferences",
            composeThirdData?.get(DIETARYPREF.cardIndex),
            onDietaryPref,
            DIETARYPREF.cardIndex
        ), OnlySelectionCardData(
            "Non Veg Days",
            composeThirdData?.get(NONVEGDAYS.cardIndex),
            onNonVegDays,
            NONVEGDAYS.cardIndex
        ), OnlySelectionCardData(
            "Food Allergies?",
            composeThirdData?.get(FOODALLERGIES.cardIndex),
            onFoodAllergies,
            FOODALLERGIES.cardIndex
        ), OnlySelectionCardData(
            "Cuisines?", composeThirdData?.get(CUISINES.cardIndex), onCuisines, CUISINES.cardIndex
        )
    )

    val event = viewModel.stateSubmit.collectAsState()
    val events = event.value

    var buttonClicked by remember { mutableStateOf(false) }

    //Radio Button Selection
    val radioButtonSelections by viewModel.radioButtonSelections.collectAsStateWithLifecycle()

    val selectedFoodResDemo =
        radioButtonSelections[MultiRadioBtnKeys.DIETREST.key] as TwoRadioBtnSelections?

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

            cardList.forEach { cardData ->
                OnlyChipSelectionCard(
                    cardType = cardData.cardType,
                    cardList = cardData.cardList,
                    onItemsSelect = cardData.onItemsSelect,
                    cardIndex = cardData.cardIndex,
                    composeIndex = ComposeIndex.Second,
                )
                Spacer(modifier = Modifier.height(spacing.medium))
            }

            SelectionCardCreateProfile(
                cardType = "Food Restrictions?",
                cardList = composeThirdData?.get(4),
                onItemsSelect = onFoodRes,
                selectedOption = selectedFoodResDemo,
                onStateChange = { state ->
                    viewModel.updateRadioButtonSelection(MultiRadioBtnKeys.DIETREST.key, state)
                },
                cardIndex = 4,
                composeIndex = ComposeIndex.Third,
                listName = "Diet"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            CreateProfileTwoButtonLayout(
                eventPrevious = eventPrevious, eventNext = {
                    buttonClicked = !buttonClicked
                    viewModel.onEvent(ProfileEvent.OnSubmit)
                }, titleButton2 = "Submit"
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
                    is ProfileSubmitState.NoInternet -> AppErrorScreen(onTryAgain = {})
                    is ProfileSubmitState.Success -> {
                        (context as MainActivity).startMainNavHost()
                        Log.d("validate", "Success -> ${events.userProfile}")
                    }
                }
            }
            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }
}


sealed class DietCreateBottomSheetType(val cardIndex: Int, val propertyType: String) {
    object DIETARYPREF : DietCreateBottomSheetType(0, "dp")
    object NONVEGDAYS : DietCreateBottomSheetType(1, "dp")
    object FOODALLERGIES : DietCreateBottomSheetType(2, "food")
    object CUISINES : DietCreateBottomSheetType(3, "cu")
    object FOODRES : DietCreateBottomSheetType(4, "food")
}

@Composable
fun DietCreateBottomSheetLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    sheetLayout: DietCreateBottomSheetType,
    closeSheet: () -> Unit,
) {

    val cardIndex = sheetLayout.cardIndex
    val state by viewModel.stateHp.collectAsStateWithLifecycle()

    when (state) {
        is HPropState.Empty -> TODO()
        is HPropState.Error -> TODO()
        is HPropState.Loading -> LoadingAnimation()
        is HPropState.NoInternet -> AppErrorScreen(onTryAgain = {})
        is HPropState.Success -> ItemSelectionLayout(
            cardList = (state as HPropState.Success).properties,
            cardIndex = cardIndex,
            composeIndex = ComposeIndex.Third, cardList2 = null
        )
    }
}
