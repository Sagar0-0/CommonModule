package fit.asta.health.navigation.home.view

import android.app.Activity
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
    activity: Activity,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    when (val state = viewModel.state.collectAsState().value) {
        is HomeState.Loading -> {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        is HomeState.Success -> {
            HomeScreenLayout(
                activity = activity,
                toolsHome = state.toolsHome,
                userId = viewModel.userId
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