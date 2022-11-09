package fit.asta.health.profile.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Bottom Update Button
@Composable
fun UpdateButton() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clip(shape = RectangleShape),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(Color(0xff0088FF))) {
        Text(text = "SUBMIT",
            fontFamily = FontFamily.Default,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            lineHeight = 16.sp,
            letterSpacing = 1.25.sp,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(vertical = 17.dp))
    }
}