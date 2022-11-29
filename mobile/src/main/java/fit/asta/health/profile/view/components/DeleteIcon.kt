package fit.asta.health.profile.view.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DeleteIcon() {
    Icon(imageVector = Icons.Filled.RemoveCircle,
        contentDescription = null,
        Modifier.size(20.dp),
        tint = MaterialTheme.colorScheme.error)
}