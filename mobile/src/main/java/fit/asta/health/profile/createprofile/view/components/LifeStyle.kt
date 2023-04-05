@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.createprofile.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.NoInternetLayout
import fit.asta.health.profile.bottomsheets.ItemSelectionBtmSheetLayout
import fit.asta.health.profile.createprofile.view.components.LifeStyleCreateBottomSheetType.*
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.view.*
import fit.asta.health.profile.viewmodel.HPropState
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LifeStyleContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    onSkipEvent: (Int) -> Unit,
    onCurrentActivity: () -> Unit,
    onPreferredActivity: () -> Unit,
    onLifeStyleTargets: () -> Unit,
) {

    val checkedState = remember { mutableStateOf(true) }

    val selectedPhyActiveOption by viewModel.selectedPhyAct.collectAsStateWithLifecycle()
    val selectedWorkingEnvOption by viewModel.selectedWorkingEnv.collectAsStateWithLifecycle()
    val selectedWorkingStyOption by viewModel.selectedWorkStyle.collectAsStateWithLifecycle()
    val selectedWorkingHrsOption by viewModel.selectedWorkingHrs.collectAsStateWithLifecycle()


    val areLSValidInput by viewModel.areLSValid.collectAsStateWithLifecycle()

    val lfList by viewModel.lfPropertiesData.collectAsState()


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

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
            ) {
                ThreeTogglesGroups(
                    selectionTypeText = "Are you Physically Active",
                    selectedOption = selectedPhyActiveOption,
                    onStateChange = { state ->
                        viewModel.onEvent(ProfileEvent.SetSelectedPhyActOption(state))
                    },
                    firstOption = "Less",
                    secondOption = "Moderate",
                    thirdOption = "Very"
                )
            }


            Spacer(modifier = Modifier.height(spacing.medium))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
            ) {
                TwoTogglesGroup(
                    selectionTypeText = "Current Working Environment",
                    selectedOption = selectedWorkingEnvOption,
                    onStateChange = { state ->
                        viewModel.onEvent(ProfileEvent.SetSelectedWorkingEnvOption(state))
                    },
                    firstOption = "Standing",
                    secondOption = "Sitting"
                )
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
            ) {
                TwoTogglesGroup(
                    selectionTypeText = "Current WorkStyle",
                    selectedOption = selectedWorkingStyOption,
                    onStateChange = { state ->
                        viewModel.onEvent(ProfileEvent.SetSelectedWorkingStyleOption(state))
                    },
                    firstOption = "Indoor",
                    secondOption = "Outdoor"
                )
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
            ) {
                ThreeTogglesGroups(
                    selectionTypeText = "What are your working hours",
                    selectedOption = selectedWorkingHrsOption,
                    onStateChange = { state ->
                        viewModel.onEvent(ProfileEvent.SetSelectedWorkingHrsOption(state))
                    },
                    firstOption = "Morning",
                    secondOption = "Afternoon",
                    thirdOption = "Night"
                )
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Current Activities?",
                cardList = lfList.getValue(0),
                checkedState = checkedState,
                onItemsSelect = onCurrentActivity,
                cardIndex = 0,
                composeIndex = ComposeIndex.Second
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "Preferred Activities?",
                cardList = lfList.getValue(1),
                checkedState = checkedState,
                onItemsSelect = onPreferredActivity,
                cardIndex = 1,
                composeIndex = ComposeIndex.Second
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyChipSelectionCard(
                cardType = "LifeStyleTargets?",
                cardList = lfList.getValue(2),
                checkedState = checkedState,
                onItemsSelect = onLifeStyleTargets,
                cardIndex = 2,
                composeIndex = ComposeIndex.Second
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            CreateProfileButtons(
                eventPrevious, eventNext, text = "Next", enableButton = areLSValidInput
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            SkipPage(onSkipEvent = onSkipEvent)

            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LifeStyleCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
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

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = modalBottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                LifeStyleCreateBottomSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
            }
        }) {
        LifeStyleContent(
            eventPrevious = eventPrevious,
            eventNext = eventNext,
            onSkipEvent = onSkipEvent,
            onCurrentActivity = {
                currentBottomSheet = CURRENTACTIVITIES
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "activity"))
            },
            onPreferredActivity = {
                currentBottomSheet = PREFERREDACTIVITIES
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "activity"))
            },
            onLifeStyleTargets = {
                currentBottomSheet = LIFESTYLETARGETS
                openSheet()
                viewModel.onEvent(ProfileEvent.GetHealthProperties(propertyType = "goal"))
            })
    }


}


enum class LifeStyleCreateBottomSheetType {
    CURRENTACTIVITIES, PREFERREDACTIVITIES, LIFESTYLETARGETS
}


@Composable
fun LifeStyleCreateBottomSheetLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    sheetLayout: LifeStyleCreateBottomSheetType,
    closeSheet: () -> Unit,
) {
    when (sheetLayout) {
        CURRENTACTIVITIES -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    NoInternetLayout(onTryAgain = {})
                }
                is HPropState.Success -> {
                    ItemSelectionBtmSheetLayout(
                        cardList = state.properties,
                        cardIndex = 0,
                        composeIndex = ComposeIndex.Second
                    )
                }
            }
        }
        PREFERREDACTIVITIES -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    NoInternetLayout(onTryAgain = {})
                }
                is HPropState.Success -> {
                    ItemSelectionBtmSheetLayout(
                        cardList = state.properties,
                        cardIndex = 1,
                        composeIndex = ComposeIndex.Second
                    )
                }
            }
        }
        LIFESTYLETARGETS -> {
            when (val state = viewModel.stateHp.collectAsState().value) {
                is HPropState.Empty -> {}
                is HPropState.Error -> {}
                is HPropState.Loading -> LoadingAnimation()
                is HPropState.NoInternet -> {
                    NoInternetLayout(onTryAgain = {})
                }
                is HPropState.Success -> {
                    ItemSelectionBtmSheetLayout(
                        cardList = state.properties,
                        cardIndex = 2,
                        composeIndex = ComposeIndex.Second
                    )
                }
            }
        }
    }
}