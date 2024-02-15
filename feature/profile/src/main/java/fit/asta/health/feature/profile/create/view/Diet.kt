@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.feature.profile.create.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheetLayout
import fit.asta.health.feature.profile.create.MultiRadioBtnKeys
import fit.asta.health.feature.profile.create.view.DietCreateBottomSheetType.CUISINES
import fit.asta.health.feature.profile.create.view.DietCreateBottomSheetType.DIETARYPREF
import fit.asta.health.feature.profile.create.view.DietCreateBottomSheetType.FOODALLERGIES
import fit.asta.health.feature.profile.create.view.DietCreateBottomSheetType.FOODRES
import fit.asta.health.feature.profile.create.view.DietCreateBottomSheetType.NONVEGDAYS
import fit.asta.health.feature.profile.create.view.components.CreateProfileTwoButtonLayout
import fit.asta.health.feature.profile.create.view.components.ItemSelectionLayout
import fit.asta.health.feature.profile.create.vm.ComposeIndex
import fit.asta.health.feature.profile.create.vm.ProfileEvent
import fit.asta.health.feature.profile.create.vm.TwoRadioBtnSelections
import fit.asta.health.feature.profile.profile.ui.UserProfileState
import fit.asta.health.feature.profile.show.view.OnlyChipSelectionCard
import fit.asta.health.feature.profile.show.view.SelectionCardCreateProfile
import fit.asta.health.feature.profile.show.vm.ProfileViewModel
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun DietCreateScreen(
    userProfileState: UserProfileState,
    viewModel: ProfileViewModel = hiltViewModel(),
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

    AppModalBottomSheetLayout(
        sheetContent = {
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
            DietContent(
                userProfileState = userProfileState,
                onFoodRes = {
                    onItemClick(FOODRES, FOODRES.propertyType)
                },
                cardList = cardList,
                composeThirdData = composeThirdData
            )
        })
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun DietContent(
    userProfileState: UserProfileState,
    viewModel: ProfileViewModel = hiltViewModel(),
    onFoodRes: () -> Unit,
    cardList: List<OnlySelectionCardData>,
    composeThirdData: Map<Int, SnapshotStateList<HealthProperties>>?,
) {

    val events = viewModel.submitProfileState.collectAsStateWithLifecycle().value

    val buttonClicked by remember { mutableStateOf(false) }

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
                .padding(horizontal = AppTheme.spacing.level2)
                .verticalScroll(rememberScrollState())
                .background(color = AppTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            cardList.forEach { cardData ->
                OnlyChipSelectionCard(
                    cardType = cardData.cardType,
                    cardList = cardData.cardList,
                    onItemsSelect = cardData.onItemsSelect,
                    cardIndex = cardData.cardIndex,
                    composeIndex = ComposeIndex.Second,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
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

            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            CreateProfileTwoButtonLayout(
                userProfileState = userProfileState,
                titleButton2 = stringResource(R.string.submit)
            )
            if (buttonClicked) {
                AppUiStateHandler(uiState = events) {}
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
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
    val state by viewModel.healthPropState.collectAsStateWithLifecycle()

    when (state) {
        is UiState.Loading -> AppDotTypingAnimation()
        is UiState.NoInternet -> AppInternetErrorDialog {}
        is UiState.Success -> ItemSelectionLayout(
            cardList = (state as UiState.Success).data,
            cardList2 = cardList2,
            cardIndex = cardIndex,
            composeIndex = ComposeIndex.Third,
            searchQuery = searchQuery
        )

        else -> {}
    }
}

sealed class DietCreateBottomSheetType(val cardIndex: Int, val propertyType: String) {
    data object DIETARYPREF : DietCreateBottomSheetType(0, "dp")
    data object NONVEGDAYS : DietCreateBottomSheetType(1, "dp")
    data object FOODALLERGIES : DietCreateBottomSheetType(2, "food")
    data object CUISINES : DietCreateBottomSheetType(3, "cu")
    data object FOODRES : DietCreateBottomSheetType(4, "food")
}