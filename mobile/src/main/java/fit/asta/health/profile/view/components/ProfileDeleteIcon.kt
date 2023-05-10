package fit.asta.health.profile.view.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.common.ui.theme.customSize

@Composable
fun ProfileDeleteIcon() {

    Icon(
        imageVector = Icons.Filled.RemoveCircle,
        contentDescription = null,
        Modifier.size(20.dp),
        tint = MaterialTheme.colorScheme.error
    )

}

@Composable
fun ProfileAddIcon(
    onClick: (() -> Unit)? = null,
) {


    onClick?.let {
        IconButton(
            onClick = it,
            Modifier.size(customSize.extraMedium),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.onTertiaryContainer
            ),
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


@Composable
fun ProfileOnlyAddIcon() {

    Icon(
        imageVector = Icons.Filled.AddCircle,
        contentDescription = null,
        Modifier.size(20.dp),
        tint = MaterialTheme.colorScheme.primary
    )

}