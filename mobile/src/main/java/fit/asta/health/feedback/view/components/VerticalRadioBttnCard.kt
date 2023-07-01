package fit.asta.health.feedback.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowColumn
import fit.asta.health.testimonials.view.create.MyTextField

data class ButtonListTypes(
    val buttonType: String,
)

@Preview
@Composable
fun VerticalRadioBttnCard() {

    val isConsentList =
        listOf(ButtonListTypes(buttonType = "First"), ButtonListTypes(buttonType = "Second"))


    VerticalRadioButton(
        selectionTypeText = "Will you Recommend our App?",
        radioButtonList = isConsentList)
}


@Composable
fun VerticalRadioButton(selectionTypeText: String, radioButtonList: List<ButtonListTypes>) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioButtonList[0]) }


    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth()) {
                androidx.compose.material.Text(text = selectionTypeText,
                    color = Color(0x99000000),
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp,
                    fontWeight = FontWeight.Bold)
            }
            FlowColumn(Modifier.fillMaxWidth()) {
                radioButtonList.forEach { text ->
                    Row(horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically) {
                        Box {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = (text == selectedOption), onClick = {
                                    onOptionSelected(text)
                                }, colors = RadioButtonDefaults.colors(Color(0xff2F80ED)))
                                Text(text = text.buttonType,
                                    fontSize = 16.sp,
                                    color = Color(0xff575757))
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            MyTextField(textFieldTitle = "Do you like to tell us to improve?")
        }
    }
}