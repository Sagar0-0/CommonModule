package fit.asta.health.navigation.profile.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipsOnCards(
    textOnChip: String,
) {
    Chip(onClick = {},
        shape = RoundedCornerShape(32.dp),
        colors = ChipDefaults.chipColors(backgroundColor = Color(0x80D6D6D6))) {
        Text(text = textOnChip,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp,
            color = Color(0x99000000))
        Spacer(modifier = Modifier.width(4.dp))
        DeleteIcon()
    }
}