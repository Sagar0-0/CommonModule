package fit.asta.health.profile.bottomsheets.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow


@Composable
fun UserAddressBottomSheetLayout(
    textFieldList: List<TextFieldListData>,
    radioButtonList: List<ButtonListTypes>,
) {


    Column(Modifier
        .fillMaxWidth()
        .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            DividerLine()
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Please Enter Address", fontSize = 16.sp)
        }

        UserInputLayoutList(textFieldList = textFieldList)

        Spacer(modifier = Modifier.height(8.dp))

        AddressType(selectionTypeText = "Enter Your Address", radioButtonList = radioButtonList)

        Spacer(modifier = Modifier.height(16.dp))

        DoneButton()
    }
}

@Composable
fun AddressType(selectionTypeText: String, radioButtonList: List<ButtonListTypes>) {

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioButtonList[0]) }

    Column(Modifier
        .fillMaxWidth()
        .padding(start = 16.dp)) {
        Row(Modifier.fillMaxWidth()) {
            Text(text = selectionTypeText,
                color = Color(0x99000000),
                fontSize = 14.sp,
                lineHeight = 19.6.sp,
                fontWeight = FontWeight.Bold)
        }
        FlowRow {
            radioButtonList.forEach { text ->
                Box(modifier = Modifier.padding(top = 9.5.dp, bottom = 9.5.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = (text == selectedOption), onClick = {
                            onOptionSelected(text)
                        }, colors = RadioButtonDefaults.colors(Color(0xff2F80ED)))
                        Text(text = text.buttonType,
                            fontSize = 16.sp,
                            lineHeight = 22.4.sp,
                            color = Color(0xff575757))
                    }
                }
            }
        }
    }
}


@Composable
fun ToggleRectangularButton() {

    var selected by remember { mutableStateOf(false) }

    val color = if (selected) Color.Transparent else Color(0xff0277BD)


    Box(modifier = Modifier.padding(start = 16.dp, top = 9.5.dp, bottom = 9.5.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(onClick = { selected = !selected },
                content = { },
                border = BorderStroke(width = 1.dp, color = Color(0xff0277BD)),
                colors = ButtonDefaults.buttonColors(backgroundColor = color),
                modifier = Modifier.size(20.dp))
            Text(text = "Mark as Default Address",
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color(0xff575757),
                modifier = Modifier.padding(start = 4.dp))
        }
    }
}