package fit.asta.health.feature.water.nav


import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import fit.asta.health.common.utils.Constants.WATER_GRAPH_ROUTE
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.feature.water.WaterState
import fit.asta.health.feature.water.view.screen.ui.CustomBevBottomSheet
import fit.asta.health.feature.water.view.screen.ui.ErrorUi
import fit.asta.health.feature.water.viewmodel.WaterToolViewModel


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
            val context = LocalContext.current
            val viewModel: WaterToolViewModel = it.sharedViewModel(navController)
            val state = viewModel.state.collectAsState()
            when (state.value) {
                is WaterState.Loading -> AppDotTypingAnimation()
                is WaterState.Error -> {
                    ErrorUi(viewModel = viewModel, event = viewModel::event)
                    Toast.makeText(context,"Unknown Error, Try After SomeTime",Toast.LENGTH_SHORT).show()
                    Log.e("rishi", "Got Error + ${(state.value as WaterState.Error).error}")
                }

                else ->
                    CustomBevBottomSheet(
                        onBack = onBack,
                        event = viewModel::event
                    )
            }


        }

    }
}


