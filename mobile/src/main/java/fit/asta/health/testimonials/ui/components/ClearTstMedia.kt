package fit.asta.health.testimonials.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.components.generic.AppDefaultIcon

@Composable
fun ClearTstMedia(onTstMediaClear: () -> Unit) {
    Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth(1f)) {
        IconButton(onClick = {
            onTstMediaClear()
        }) {
            AppDefaultIcon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete the Image",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}