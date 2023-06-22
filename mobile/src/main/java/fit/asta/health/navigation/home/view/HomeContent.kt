package fit.asta.health.navigation.home.view

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.NoInternetLayout
import fit.asta.health.navigation.home.viewmodel.HomeState
import fit.asta.health.navigation.home.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeContent(activity: Activity, viewModel: HomeViewModel = hiltViewModel()) {

    Scaffold {
        when (val state = viewModel.state.collectAsState().value) {
            is HomeState.Loading -> LoadingAnimation()
            is HomeState.Success -> {
                HomeScreenLayout(activity = activity, toolsHome = state.toolsHome)
                Log.d("HomeScreen", "Home Screen Data -> ${state.toolsHome}")
            }
            is HomeState.Error -> NoInternetLayout(onTryAgain = {

            })
        }
    }
}