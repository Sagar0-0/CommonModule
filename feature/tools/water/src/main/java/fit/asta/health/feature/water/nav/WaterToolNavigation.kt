package fit.asta.health.feature.water.nav

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.common.utils.Constants.WATER_GRAPH_ROUTE
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.feature.water.view.screen.ui.CustomBevBottomSheet
import fit.asta.health.feature.water.viewmodel.WaterState
import fit.asta.health.feature.water.viewmodel.WaterToolViewModel
import fit.asta.health.feature.water.viewmodel.WaterViewModel

import kotlinx.coroutines.ExperimentalCoroutinesApi

fun NavController.navigateToWater(navOptions: NavOptions? = null) {
    this.navigate(WATER_GRAPH_ROUTE, navOptions)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.waterToolNavigation(
    navController: NavHostController, onBack: () -> Unit
) {

    navigation(
        route = WATER_GRAPH_ROUTE,
        startDestination = WaterScreen.WaterToolHomeScreen.route
    ) {
        composable(WaterScreen.WaterToolHomeScreen.route) {

            val NviewModel: WaterViewModel = it.sharedViewModel(navController)
            val viewModel : WaterToolViewModel = it.sharedViewModel(navController)
            val state = viewModel.state.collectAsState()
            val sliderValueWater by viewModel.waterQuantity.collectAsStateWithLifecycle()
            val sliderValueCoconut by viewModel.coconutQuantity.collectAsStateWithLifecycle()
            val sliderValueFirstPreference by viewModel.firstPrefQuantity.collectAsStateWithLifecycle()
            val sliderValueSecondPreference by viewModel.secondPrefQuantity.collectAsStateWithLifecycle()
            val sliderValueRecentAdd by viewModel.recentAddedQuantity.collectAsStateWithLifecycle()
            val uiState = NviewModel.uiState.value
            val waterTool by NviewModel.modifiedWaterTool.collectAsStateWithLifecycle()
            val beverageList by NviewModel.beverageList.collectAsStateWithLifecycle()
            val containerList by NviewModel.containerList.collectAsStateWithLifecycle()
            val todayActivityData by NviewModel.todayActivity.collectAsStateWithLifecycle()
            val selectedBeverage by NviewModel.selectedBeverage.collectAsStateWithLifecycle()
            val containerIndex by NviewModel.containerIndex.collectAsStateWithLifecycle()
            Log.d(
                "subhash",
                "WaterToolNavigation:${uiState} \n" + "${waterTool}\n" + "$containerIndex"
            )
            when (state.value) {
                is WaterState.Loading -> AppDotTypingAnimation()
                is WaterState.Error -> {
                    // TODO :-
                    Text(text = "Got Error + ${state.value}", color = Color.Red)
                }
                else ->
//                    WaterToolScreen(
//                    event = viewModel::event,
//                    beverageList = beverageList,
//                    containerList = containerList,
//                    selectedBeverage = selectedBeverage,
//                    todayActivityData = todayActivityData,
//                    containerIndex = containerIndex,
//                    uiState = uiState,
//                    onBack = onBack
//                )
                    CustomBevBottomSheet(onBack = onBack,
                        event = viewModel::event
//                        sliderValueWater = sliderValueWater,
//                        sliderValueCoconut = sliderValueCoconut.value,
//                        sliderValueFirstPreference = sliderValueFirstPreference.value,
//                        sliderValueSecondPreference = sliderValueSecondPreference.value,
//                        sliderValueRecentAdded = sliderValueRecentAdd.value
                    )
            }


        }

    }
}


