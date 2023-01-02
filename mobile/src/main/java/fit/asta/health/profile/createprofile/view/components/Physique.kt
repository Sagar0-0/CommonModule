@file:OptIn(ExperimentalMaterial3Api::class)

package fit.asta.health.profile.createprofile.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.profile.view.AddressType
import fit.asta.health.profile.view.Alpha
import fit.asta.health.profile.view.ButtonListTypes
import fit.asta.health.profile.view.NextButton


@Preview
@Composable
fun PhysiqueContent(eventSkip: (() -> Unit)? = null, eventNext: (() -> Unit)? = null) {

    val placeHolderDOB = listOf("DAY", "MONTH", "YEAR")

    val buttonTypeList = listOf(ButtonListTypes(buttonType = "Female"),
        ButtonListTypes(buttonType = "Male"),
        ButtonListTypes(buttonType = "Others"))

    val isPregnantList =
        listOf(ButtonListTypes(buttonType = "Yes"), ButtonListTypes(buttonType = "No"))

    Card(shape = RoundedCornerShape(16.dp)) {

        var text by remember { mutableStateOf(TextFieldValue("")) }
        val focusManager = LocalFocusManager.current

        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Date of Birth",
                    color = Color(0xff132839),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium)

                Text(text = "24yr",
                    color = Color(0x99000000),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(3),
                userScrollEnabled = false,
                modifier = Modifier
                    .height(60.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                placeHolderDOB.forEach {
                    item {
                        dOBTextField(text = text, focusManager = focusManager, placeholder = it)
                    }
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Weight",
                    color = Color(0xff132839),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium)
                Box {
                    Alpha(text1 = "kg", text2 = "lb")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(value = text,
                onValueChange = {
                    text = it
                },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Height",
                    color = Color(0xff132839),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium)
                Box {
                    Alpha(text1 = "in", text2 = "cm")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(value = text,
                onValueChange = {
                    text = it
                },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(20.dp))

            AddressType(selectionTypeText = "Gender", radioButtonList = buttonTypeList)

            Spacer(modifier = Modifier.height(20.dp))

            AddressType(selectionTypeText = "Are you Pregnant?", radioButtonList = isPregnantList)

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Please mention the week of Pregnancy",
                    color = Color(0x99000000),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold)
            }
            TextField(value = text,
                onValueChange = {
                    text = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent))

            Spacer(modifier = Modifier.height(20.dp))

            CreateProfileButtons(eventSkip, eventNext)

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}

@Composable
fun CreateProfileButtons(eventSkip: (() -> Unit)? = null, eventNext: (() -> Unit)? = null) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        NextButton(text = "Skip", modifier = Modifier.fillMaxWidth(0.5f), event = eventNext)
        NextButton(text = "Next", modifier = Modifier.fillMaxWidth(1f), event = eventNext)
    }
}

@Composable
private fun dOBTextField(
    text: TextFieldValue,
    focusManager: FocusManager,
    placeholder: String,
): TextFieldValue {
    var text1 = text
    OutlinedTextField(value = text,
        onValueChange = {
            text1 = it
        },
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        placeholder = { Text(placeholder) })
    return text1
}