package fit.asta.health.common.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.ui.theme.cardElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    text: String = "",
    containerColor: Color = MaterialTheme.colorScheme.onPrimary,
    titleContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    backIcon: ImageVector = Icons.Outlined.NavigateBefore,
    actionItems: @Composable RowScope.() -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = backIcon,
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleContentColor
        ),
        modifier = Modifier.shadow(elevation = cardElevation.medium),
        actions = actionItems
    )
}

@Composable
@Preview(showBackground = true)
fun CustomTopBarPreview() {
    CustomTopBar(text = "Preview")
}