package fit.asta.health.profile.view.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fit.asta.health.common.ui.theme.customSize

@Composable
fun AddIcon(onClick: (() -> Unit)? = null) {

    onClick?.let {
        IconButton(
            onClick = it,
            Modifier.size(customSize.extraMedium),
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(customSize.medium),
                tint = Color.White
            )
        }
    }

}