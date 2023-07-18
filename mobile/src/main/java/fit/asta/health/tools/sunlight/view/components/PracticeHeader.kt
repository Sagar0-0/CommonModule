package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun PracticeHeader() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            Text(text = "PRACTICE",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 22.4.sp,
                color = Color.White)
        }
    }
}