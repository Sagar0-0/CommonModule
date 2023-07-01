package fit.asta.health.feedback.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.testimonials.view.create.MyTextField

@Preview
@Composable
fun CheckBoxCard() {

    val checkBoxList = listOf("Friends and Family", "Social Media", "Ads and Promotion", "Third")

    val checkedState = remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(spacing.small),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
        ) {
            Text(
                text = "Where did you first hear about us?",
                fontSize = 16.sp,
                color = Color(0xff132839),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            Column(Modifier.fillMaxWidth()) {
                checkBoxList.forEach { title ->
                    Row(verticalAlignment = CenterVertically) {
                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it },
                            modifier = Modifier.size(spacing.extraMedium),
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xff0088FF))
                        )
                        Spacer(modifier = Modifier.width(spacing.small))
                        Text(text = title, fontSize = 14.sp, color = Color(0xff3E4955))
                    }
                    Spacer(modifier = Modifier.height(spacing.extraSmall))
                }
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            MyTextField(textFieldTitle = "Do you like to tell us to improve?")
        }
    }
}