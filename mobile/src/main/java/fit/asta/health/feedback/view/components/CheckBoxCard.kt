package fit.asta.health.feedback.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.testimonials.view.create.MyTextField

@Preview
@Composable
fun CheckBoxCard() {

    val checkBoxList = listOf("Friends and Family", "Social Media", "Ads and Promotion", "Third")

    val checkedState = remember { mutableStateOf(true) }

    Card(modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Text(text = "Where did you first hear about us?",
                fontSize = 16.sp,
                color = Color(0xff132839),
                fontWeight = FontWeight.Medium)

            Spacer(modifier = Modifier.height(16.dp))

            Column(Modifier.fillMaxWidth()) {
                checkBoxList.forEach { title ->
                    Row(verticalAlignment = CenterVertically) {
                        Checkbox(checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it },
                            modifier = Modifier.size(24.dp),
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xff0088FF)))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = title, fontSize = 14.sp, color = Color(0xff3E4955))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            MyTextField(textFieldTitle = "Do you like to tell us to improve?")
        }
    }
}