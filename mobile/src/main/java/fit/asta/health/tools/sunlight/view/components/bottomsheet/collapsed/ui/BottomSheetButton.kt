package fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomSheetButton() {
    Button(onClick = { /*TODO*/ },
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff43A047)),
        modifier = Modifier.fillMaxWidth()) {
        Text(text = "SCHEDULE",
            color = Color.White,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            letterSpacing = 1.25.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center)
    }
}