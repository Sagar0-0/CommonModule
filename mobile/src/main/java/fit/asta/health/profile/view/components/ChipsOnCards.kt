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
fun ChipsOnCards(
    textOnChip: String,
    checkedState: (MutableState<Boolean>)? = null,
) {

    checkedState?.let {
        Chip(
            onClick = { /*TODO*/ },
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