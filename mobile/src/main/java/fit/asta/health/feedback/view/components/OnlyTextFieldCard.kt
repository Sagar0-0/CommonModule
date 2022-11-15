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
import fit.asta.health.testimonials.view.create.MyTextField

@Composable
fun OnlyTextFieldCard() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Text(text = "Any suggestions or complaints please feel free to write below",
                fontSize = 16.sp,
                color = Color(0xff132839),
                fontWeight = FontWeight.Medium)

            Spacer(modifier = Modifier.height(16.dp))

            MyTextField(textFieldTitle = "Do you like to tell us to improve?")
        }
    }
}