package fit.asta.health.tools.water.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.testimonials.view.ServerErrorLayout
import fit.asta.health.tools.water.view.screen.WaterToolScreen
import fit.asta.health.tools.water.viewmodel.WaterState
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun WaterToolNavigation(navController: NavHostController,viewModel: WaterViewModel) {
    NavHost(navController = navController, startDestination =WaterScreen.WaterToolHomeScreen.route ){

        composable(WaterScreen.WaterToolHomeScreen.route){
            val state = viewModel.state.collectAsState()
            val uiState=viewModel.uiState.value
            val waterTool by viewModel.modifiedWaterTool.collectAsStateWithLifecycle()
            val beverageList by viewModel.beverageList.collectAsStateWithLifecycle()
            val containerList by viewModel.containerList.collectAsStateWithLifecycle()
            val todayActivityData by viewModel.todayActivity.collectAsStateWithLifecycle()
            val selectedBeverage by viewModel.selectedBeverage.collectAsStateWithLifecycle()
            val containerIndex by viewModel.containerIndex.collectAsStateWithLifecycle()
            Log.d("subhash", "WaterToolNavigation:${uiState} \n"+"${waterTool}\n"+"$containerIndex")
            when (state.value) {
                is WaterState.Loading -> LoadingAnimation()
                is WaterState.Error -> ServerErrorLayout((state.value as WaterState.Error).error)
                else -> WaterToolScreen( Event = viewModel::event,
                    beverageList =beverageList ,
                    containerList =containerList ,
                    selectedBeverage = selectedBeverage,
                    todayActivityData = todayActivityData,
                    containerIndex = containerIndex,
                    uiState = uiState
                )
            }

        }

    }
}