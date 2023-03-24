package fit.asta.health.profile.view.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RemoveChipOnCard(
    textOnChip: String,
    checkedState: (MutableState<Boolean>)? = null,
    onClick: () -> Unit,
) {

    checkedState?.let {
        Chip(
            onClick = onClick,
            shape = RoundedCornerShape(32.dp),
            colors = ChipDefaults.chipColors(backgroundColor = Color(0x80D6D6D6)),
            enabled = it.value
        ) {
            Text(
                text = textOnChip,
                style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                color = Color(0x99000000)
            )

            if (checkedState.value) {
                Spacer(modifier = Modifier.width(4.dp))
                DeleteIcon()
            }

        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddChipOnCard(
    textOnChip: String,
    onClick: () -> Unit,
) {

    Chip(
        onClick = onClick,
        shape = RoundedCornerShape(32.dp),
        colors = ChipDefaults.chipColors(backgroundColor = Color(0x80D6D6D6)),
    ) {
        Text(
            text = textOnChip,
            style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
            color = Color(0x99000000)
        )

        Spacer(modifier = Modifier.width(4.dp))

        OnlyAddIcon()

    }

}