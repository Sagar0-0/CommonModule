package fit.asta.health.navigation.tools.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.designsystem.molecular.AppNonInternetErrorScreen
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.navigation.tools.ui.viewmodel.HomeState
import fit.asta.health.navigation.tools.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun HomeContent(
    viewModel: HomeViewModel = hiltViewModel(),
    onNav: (String) -> Unit,
) {

    when (val state = viewModel.state.collectAsState().value) {
        is HomeState.Loading -> {
            AppDotTypingAnimation(modifier = Modifier.fillMaxSize())
        }

        is HomeState.Success -> {
            HomeScreenLayout(
                toolsHome = state.toolsHome, userId = viewModel.userId, onNav = onNav
            )
        }

        is HomeState.Error -> {
            AppNonInternetErrorScreen { viewModel.loadHomeData() }
        }

        is HomeState.NetworkError -> {
            AppInternetErrorDialog {
                viewModel.loadHomeData()
            }
        }
    }
}