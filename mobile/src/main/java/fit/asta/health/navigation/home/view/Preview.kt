package fit.asta.health.navigation.home.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import fit.asta.health.navigation.home.intent.HomeState
import fit.asta.health.navigation.home.model.domain.ToolsHome

internal class AlbumStateProvider : PreviewParameterProvider<HomeState> {
    override val values: Sequence<HomeState> = sequenceOf(
        HomeState.Loading, HomeState.Success(ToolsHome()), HomeState.Error(error = Throwable())
    )
}

@Composable
fun AlbumScreenPreview(
    @PreviewParameter(AlbumStateProvider::class) state: HomeState
) {
    Content(state)
}