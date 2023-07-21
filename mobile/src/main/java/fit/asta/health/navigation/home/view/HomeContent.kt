package fit.asta.health.navigation.home.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.navigation.home.view.component.ErrorScreenLayout
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.viewmodel.HomeState
import fit.asta.health.navigation.home.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun HomeContent(
    viewModel: HomeViewModel = hiltViewModel(),
    onBreathing :()->Unit,
    onWater: () -> Unit,
    onMeditation: () -> Unit,
    onSunlight: () -> Unit,
    onSleep: () -> Unit,
    onDance: () -> Unit,
    onYoga: () -> Unit,
    onWorkout: () -> Unit,
    onHiit: () -> Unit,
) {

    when (val state = viewModel.state.collectAsState().value) {
        is HomeState.Loading -> {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        is HomeState.Success -> {
            HomeScreenLayout(
                toolsHome = state.toolsHome,
                userId = viewModel.userId,
                onBreathing=onBreathing,
                onWater=onWater,
                onMeditation=onMeditation,
                onDance=onDance,
                onHiit = onHiit,
                onSleep = onSleep,
                onSunlight = onSunlight,
                onWorkout = onWorkout,
                onYoga = onYoga
            )
        }

        is HomeState.Error -> {
            ErrorScreenLayout(
                onTryAgain = {
                    viewModel.loadHomeData()
                }, isInternetError = false
            )
        }

        is HomeState.NetworkError -> {
            ErrorScreenLayout(onTryAgain = {
                viewModel.loadHomeData()
            })
        }
    }

}