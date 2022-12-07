package fit.asta.health.tools.water.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.testimonials.view.ServerErrorLayout
import fit.asta.health.tools.water.viewmodel.WaterState
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun WaterToolForm(viewModel: WaterViewModel = hiltViewModel()) {

    when (val state = viewModel.state.collectAsState().value) {
        is WaterState.Loading -> LoadingAnimation()
        is WaterState.Error -> ServerErrorLayout(state.error)
        else -> {
            WaterHomeScreen()
        }
    }
}