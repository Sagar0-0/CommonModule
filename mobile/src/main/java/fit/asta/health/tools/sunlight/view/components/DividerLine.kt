package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DividerLine() {
    Divider(color = Color(0xff0277BD),
        modifier = Modifier
            .size(width = 80.dp, height = 8.dp)
            .clip(shape = RoundedCornerShape(4.dp)))
}