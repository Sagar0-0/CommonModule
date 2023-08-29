package fit.asta.health.tools.water.nav

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import fit.asta.health.common.utils.Constants.WATER_GRAPH_ROUTE
import fit.asta.health.common.utils.Constants.deepLinkUrl
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.feature.testimonials.create.view.ServerErrorLayout
import fit.asta.health.tools.water.view.screen.WaterToolScreen
import fit.asta.health.tools.water.viewmodel.WaterState
import fit.asta.health.tools.water.viewmodel.WaterViewModel
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
        startDestination = WaterScreen.WaterToolHomeScreen.route,
        deepLinks = listOf(navDeepLink {
            uriPattern = "$deepLinkUrl/${WATER_GRAPH_ROUTE}"
            action = Intent.ACTION_VIEW
        })
    ) {
        composable(WaterScreen.WaterToolHomeScreen.route) {
            val viewModel:WaterViewModel = it.sharedViewModel(navController)
            val state = viewModel.state.collectAsState()
            val uiState = viewModel.uiState.value
            val waterTool by viewModel.modifiedWaterTool.collectAsStateWithLifecycle()
            val beverageList by viewModel.beverageList.collectAsStateWithLifecycle()
            val containerList by viewModel.containerList.collectAsStateWithLifecycle()
            val todayActivityData by viewModel.todayActivity.collectAsStateWithLifecycle()
            val selectedBeverage by viewModel.selectedBeverage.collectAsStateWithLifecycle()
            val containerIndex by viewModel.containerIndex.collectAsStateWithLifecycle()
            Log.d(
                "subhash",
                "WaterToolNavigation:${uiState} \n" + "${waterTool}\n" + "$containerIndex"
            )
            when (state.value) {
                is WaterState.Loading -> LoadingAnimation()
                is WaterState.Error -> ServerErrorLayout((state.value as WaterState.Error).error)
                else -> WaterToolScreen(
                    Event = viewModel::event,
                    beverageList = beverageList,
                    containerList = containerList,
                    selectedBeverage = selectedBeverage,
                    todayActivityData = todayActivityData,
                    containerIndex = containerIndex,
                    uiState = uiState,
                    onBack = onBack
                )
            }

        }

    }
}