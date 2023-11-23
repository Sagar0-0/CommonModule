package fit.asta.health.navigation.tools.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.navigation.tools.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun HomeContent(
    homeViewModel: HomeViewModel,
    refCode: String,
    onNav: (String) -> Unit,
) {
    val context = LocalContext.current
    when (val state = homeViewModel.toolsHomeDataState.collectAsStateWithLifecycle().value) {
        is UiState.Loading -> {
            AppDotTypingAnimation(modifier = Modifier.fillMaxSize())
        }

        is UiState.Success -> {
            HomeScreenLayout(
                toolsHome = state.data,
                refCode = refCode,
                userId = homeViewModel.userId,
                onNav = onNav
            )
        }

        is UiState.ErrorRetry -> {
            AppErrorScreen(text = state.resId.toStringFromResId(context)) { homeViewModel.loadHomeData() }
        }

        is UiState.ErrorMessage -> {
            LaunchedEffect(key1 = Unit, block = {
                Toast.makeText(context, state.resId.toStringFromResId(context), Toast.LENGTH_SHORT)
                    .show()
            })
        }

        is UiState.NoInternet -> {
            AppInternetErrorDialog {
                homeViewModel.loadHomeData()
            }
        }

        else -> {}
    }
}