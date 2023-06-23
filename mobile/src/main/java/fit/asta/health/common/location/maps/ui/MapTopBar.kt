package fit.asta.health.common.location.maps.ui

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
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.ui.theme.cardElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapTopBar(text: String, onBackPressed: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
        }, navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    Icons.Outlined.NavigateBefore,
                    "back",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ), modifier = Modifier.shadow(elevation = cardElevation.medium)
    )
}

@Composable
@Preview(showBackground = true)
fun MapTopBarPreview() {
    MapTopBar(text = "Preview") {

    }
}