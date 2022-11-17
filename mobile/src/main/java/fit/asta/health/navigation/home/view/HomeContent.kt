package fit.asta.health.navigation.home.view

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import fit.asta.health.navigation.home.view.component.HomeScreenLayout
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.NoInternetLayout
import fit.asta.health.navigation.home.viewmodel.HomeState
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeContent(state: HomeState) {
    Scaffold {
        when (state) {
            is HomeState.Loading -> LoadingAnimation()
            is HomeState.Success -> HomeScreenLayout(toolsHome = state.toolsHome)
            is HomeState.Error -> NoInternetLayout(onTryAgain = {

            })
        }
    }
}