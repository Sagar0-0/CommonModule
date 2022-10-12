package fit.asta.health.navigation.home.view

import android.annotation.SuppressLint
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.navigation.home.intent.HomeState
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.NoInternetLayout

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeContent(state: HomeState) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        when (state) {
            is HomeState.Loading -> LoadingAnimation()
            is HomeState.Success -> ReadyScreen(toolsHome = state.toolsHome)
            is HomeState.Error -> NoInternetLayout()
        }
    }
}