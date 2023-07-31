package fit.asta.health.common.ui.components.generic


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    containerColor: Color = Color.Transparent,
    titleContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    backIcon: ImageVector? = Icons.Outlined.NavigateBefore,
    onBack: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            backIcon?.let {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = it,
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor, titleContentColor = titleContentColor
        ),
        modifier = modifier,
        actions = actions
    )
}

@Composable
fun AppTopBarWithHelp(
    title: String,
    onBack: () -> Unit,
    onHelp: () -> Unit,
) {
    AppTopBar(
        title = title,
        onBack = onBack
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = onHelp) {
                Icon(
                    imageVector = Icons.Default.Help,
                    contentDescription = "leadingIcon"
                )
            }
        }
    }
}