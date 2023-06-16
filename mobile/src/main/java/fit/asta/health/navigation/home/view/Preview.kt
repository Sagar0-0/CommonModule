package fit.asta.health.navigation.home.view

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import fit.asta.health.navigation.home.model.domain.ToolsHome
import fit.asta.health.navigation.home.viewmodel.HomeState
import kotlinx.coroutines.ExperimentalCoroutinesApi

internal class AlbumStateProvider : PreviewParameterProvider<HomeState> {
    override val values: Sequence<HomeState> = sequenceOf(HomeState.Loading,
        HomeState.Success(ToolsHome()),
        HomeState.Error(error = Throwable()))
}

@ExperimentalCoroutinesApi
@Preview
@Composable
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
fun AlbumScreenPreview(@PreviewParameter(AlbumStateProvider::class) activity: Activity) {
    HomeContent(activity)
}