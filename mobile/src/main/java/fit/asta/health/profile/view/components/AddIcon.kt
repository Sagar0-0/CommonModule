package fit.asta.health.profile.view.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddIcon(onClick: (() -> Unit)? = null) {

    onClick?.let {
        IconButton(onClick = it,
            Modifier.size(24.dp),
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xff0088ff))) {
            Icon(imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color.White)
        }
    }

}