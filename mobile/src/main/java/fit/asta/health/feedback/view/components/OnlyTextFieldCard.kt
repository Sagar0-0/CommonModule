package fit.asta.health.feedback.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.testimonials.view.create.MyTextField

@Composable
fun OnlyTextFieldCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.medium),
        shape = RoundedCornerShape(spacing.small),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = spacing.extraSmall)
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
        ) {
            Text(
                text = "Any suggestions or complaints please feel free to write below",
                fontSize = 16.sp,
                color = Color(0xff132839),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            MyTextField(textFieldTitle = "Do you like to tell us to improve?")
        }

    }
}