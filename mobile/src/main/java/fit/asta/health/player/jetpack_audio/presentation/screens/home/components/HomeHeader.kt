package fit.asta.health.player.jetpack_audio.presentation.screens.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import fit.asta.health.R
import fit.asta.health.common.ui.components.AppTopBar
import fit.asta.health.player.jetpack_audio.presentation.ui.theme.LocalSpacing


@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    onSearchClick: () -> Unit,
    onProfileClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    AppTopBar(
        title = stringResource(id = R.string.app_name),
        onBack = {},
        actions = {
                IconButton(
                    onClick = { onSearchClick() },
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search_button)
                    )
                }
                IconButton(
                    onClick = { onProfileClicked() },
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = stringResource(id = R.string.profile_button)
                    )
                }
            }
    )
}