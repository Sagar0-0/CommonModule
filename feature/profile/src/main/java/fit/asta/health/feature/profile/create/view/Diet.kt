@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.feature.profile.create.view

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.AppModalBottomSheetLayout
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystemx.AppTheme
import fit.asta.health.feature.profile.create.MultiRadioBtnKeys
import fit.asta.health.feature.profile.create.view.DietCreateBottomSheetType.*
import fit.asta.health.feature.profile.create.view.components.CreateProfileTwoButtonLayout
import fit.asta.health.feature.profile.create.view.components.ItemSelectionLayout
import fit.asta.health.feature.profile.create.vm.ComposeIndex
import fit.asta.health.feature.profile.create.vm.HPropState
import fit.asta.health.feature.profile.create.vm.ProfileEvent
import fit.asta.health.feature.profile.create.vm.ProfileSubmitState
import fit.asta.health.feature.profile.create.vm.TwoRadioBtnSelections
import fit.asta.health.feature.profile.show.view.*
import fit.asta.health.feature.profile.show.vm.ProfileViewModel
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class
)
@Composable
fun DietCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit,
    navigateBack: () -> Unit,
) {

    //Data
    val propertiesDataState by viewModel.propertiesData.collectAsStateWithLifecycle()
    val composeThirdData: Map<Int, SnapshotStateList<HealthProperties>>? =
        propertiesDataState[ComposeIndex.Third]

    val searchQuery = remember { mutableStateOf("") }
    var currentBottomSheet: DietCreateBottomSheetType? by remember { mutableStateOf(null) }
    var modalBottomSheetValue by remember { mutableStateOf(ModalBottomSheetValue.Hidden) }
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = modalBottomSheetValue)

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

    val onBottomSheetItemClick: (String) -> Unit = { propertyType ->
        currentBottomSheet?.let {
            openSheet()
            searchQuery.value = ""
            viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = propertyType))
        }
    }

    val onItemClick: (DietCreateBottomSheetType, String) -> Unit = { sheetType, propertyType ->
        currentBottomSheet = sheetType
        onBottomSheetItemClick(propertyType)
    }

    val cardList = listOf(
        OnlySelectionCardData(
            stringResource(R.string.dietaryPref_profile_creation),
            composeThirdData?.get(DIETARYPREF.cardIndex),
            {
                onItemClick(DIETARYPREF, DIETARYPREF.propertyType)
            },
            DIETARYPREF.cardIndex
        ), OnlySelectionCardData(
            stringResource(R.string.nonVegDays_profile_creation),
            composeThirdData?.get(NONVEGDAYS.cardIndex),
            {
                /* TODO non-veg days property type?*/
                onItemClick(NONVEGDAYS, NONVEGDAYS.propertyType)
            },
            NONVEGDAYS.cardIndex
        ), OnlySelectionCardData(
            stringResource(R.string.foodAllergies_profile_creation),
            composeThirdData?.get(FOODALLERGIES.cardIndex),
            {
                onItemClick(FOODALLERGIES, FOODALLERGIES.propertyType)
            },
            FOODALLERGIES.cardIndex
        ), OnlySelectionCardData(
            stringResource(R.string.cuisines_profile_creation),
            composeThirdData?.get(CUISINES.cardIndex),
            {
                onItemClick(CUISINES, CUISINES.propertyType)
            },
            CUISINES.cardIndex
        )
    )

    AppModalBottomSheetLayout(sheetContent = {
        Spacer(modifier = Modifier.height(1.dp))
        currentBottomSheet?.let {
            DietCreateBottomSheetLayout(
                sheetLayout = it,
                closeSheet = { closeSheet() },
                cardList2 = composeThirdData?.get(it.cardIndex),
                searchQuery = searchQuery
            )
        }
    }, sheetState = modalBottomSheetState, content = {
        DietContent(eventPrevious = eventPrevious, onFoodRes = {
            onItemClick(FOODRES, FOODRES.propertyType)
        }, cardList = cardList, composeThirdData = composeThirdData, navigateBack = navigateBack)
    })
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun DietContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit,
    onFoodRes: () -> Unit,
    cardList: List<OnlySelectionCardData>,
    composeThirdData: Map<Int, SnapshotStateList<HealthProperties>>?,
    navigateBack: () -> Unit,
) {

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
                .padding(horizontal = AppTheme.appSpacing.medium)
                .verticalScroll(rememberScrollState())
                .background(color = AppTheme.colorsX.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))

            cardList.forEach { cardData ->
                OnlyChipSelectionCard(
                    cardType = cardData.cardType,
                    cardList = cardData.cardList,
                    onItemsSelect = cardData.onItemsSelect,
                    cardIndex = cardData.cardIndex,
                    composeIndex = ComposeIndex.Second,
                )
                Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))
            }

            SelectionCardCreateProfile(
                cardType = MultiRadioBtnKeys.DIETREST.getListName(),
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

            Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))

            CreateProfileTwoButtonLayout(
                eventPrevious = eventPrevious, eventNext = {
                    buttonClicked = !buttonClicked
                    viewModel.onEvent(ProfileEvent.OnSubmit)
                }, titleButton2 = stringResource(R.string.submit)
            )

            if (buttonClicked) {
                when (events) {
                    is ProfileSubmitState.Empty -> {}
                    is ProfileSubmitState.Error -> {
                        Log.d(
                            "validate",
                            "ErrorMessage -> ${events.error} and message -> ${events.error.message} and ${events.error.localizedMessage}"
                        )
                    }

                    is ProfileSubmitState.Loading -> {
                        LoadingAnimation()
                    }

                    is ProfileSubmitState.NoInternet -> AppErrorScreen(onTryAgain = {})
                    is ProfileSubmitState.Success -> {
                        navigateBack()
                    }
                }
            }
            Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun DietCreateBottomSheetLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    sheetLayout: DietCreateBottomSheetType,
    closeSheet: () -> Unit,
    cardList2: SnapshotStateList<HealthProperties>?,
    searchQuery: MutableState<String>,
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
            cardList2 = cardList2,
            cardIndex = cardIndex,
            composeIndex = ComposeIndex.Third,
            searchQuery = searchQuery
        )
    }
}

sealed class DietCreateBottomSheetType(val cardIndex: Int, val propertyType: String) {
    data object DIETARYPREF : DietCreateBottomSheetType(0, "dp")
    data object NONVEGDAYS : DietCreateBottomSheetType(1, "dp")
    data object FOODALLERGIES : DietCreateBottomSheetType(2, "food")
    data object CUISINES : DietCreateBottomSheetType(3, "cu")
    data object FOODRES : DietCreateBottomSheetType(4, "food")
}