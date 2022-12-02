package fit.asta.health.profile.bottomsheets.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TextFieldListData(
    val placeHolderText: String,
)

data class ButtonListTypes(
    val buttonType: String,
)

@Composable
fun UserBasicDetailBottomSheetLayout(textFieldList: List<TextFieldListData>) {

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
            Text(text = "Please Enter Details", fontSize = 16.sp)
        }

        UserInputLayoutList(textFieldList = textFieldList)

        Spacer(modifier = Modifier.height(20.dp))
        DoneButton()
    }
}


@Composable
fun UserInputLayoutList(textFieldList: List<TextFieldListData>) {
    textFieldList.forEach {
        Spacer(modifier = Modifier.height(24.dp))
        InputFromUserLayout(placeHolderText = it.placeHolderText)
    }
}

@Composable
fun InputFromUserLayout(placeHolderText: String) {

    var exercise by remember {
        mutableStateOf("")
    }

    TextField(value = exercise,
        onValueChange = {
            exercise = it
        },
        placeholder = {
            Text(text = placeHolderText, fontSize = 16.sp, color = Color(0xff999999))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .border(width = 1.8.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color(0xffDFE6ED)),
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done),
        singleLine = true)
}