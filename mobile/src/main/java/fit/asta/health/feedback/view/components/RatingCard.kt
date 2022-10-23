package fit.asta.health.feedback.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.testimonials.view.components.MyTextField


@Composable
fun RatingCard(
    cardTitle: String,
    textFieldTitle: String,
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(5.dp)) {
        Column(Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(text = cardTitle,
                fontSize = 16.sp,
                color = Color(0xff132839),
                fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(16.dp))
            Rating()
            Spacer(modifier = Modifier.height(16.dp))
            MyTextField(textFieldTitle = textFieldTitle)
        }
    }
}