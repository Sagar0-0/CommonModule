package fit.asta.health.tools.water.view

import android.util.Log
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
    val state = viewModel.state.collectAsState()
    when (state.value) {
        is WaterState.Loading -> {
            Log.d("water", "WaterToolForm: loading")
            LoadingAnimation()
        }
        is WaterState.Error -> {
            Log.d("water", "WaterToolForm: error")
            ServerErrorLayout((state.value as WaterState.Error).error)
        }
        else -> {
            Log.d("water", "WaterToolForm: ok")
            WaterHomeScreen()
        }
    }
}