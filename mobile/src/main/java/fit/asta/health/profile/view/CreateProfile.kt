package fit.asta.health.profile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.R

data class ButtonListTypes(
    val buttonType: String,
)

@Preview(showSystemUi = true)
@ExperimentalMaterial3Api
@Composable
fun CreateProfile() {

    val buttonTypeList = listOf(ButtonListTypes(buttonType = "Female"),
        ButtonListTypes(buttonType = "Male"),
        ButtonListTypes(buttonType = "Others"))

    val isPregnantList =
        listOf(ButtonListTypes(buttonType = "Yes"), ButtonListTypes(buttonType = "No"))


    var text by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(16.dp))

            UserCircleImage()

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Name",
                    color = Color(0xff132839),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(value = text,
                onValueChange = {
                    text = it
                },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(20.dp))

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

            Row(modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedTextField(value = text,
                    onValueChange = {
                        text = it
                    },
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    placeholder = { Text("DAY") },
                    modifier = Modifier.fillMaxWidth(0.3f))


                OutlinedTextField(value = text,
                    onValueChange = {
                        text = it
                    },
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    placeholder = { Text("MONTH") },
                    modifier = Modifier.fillMaxWidth(0.5f))


                OutlinedTextField(value = text,
                    onValueChange = {
                        text = it
                    },
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    placeholder = { Text("YEAR") },
                    modifier = Modifier.fillMaxWidth(0.8f))
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

            PrivacyStatement()

            Spacer(modifier = Modifier.height(20.dp))

            UserConsent()

            Spacer(modifier = Modifier.height(20.dp))

            NextButton(text = "Next")
        }
    }
}

@Composable
fun UserCircleImage() {

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.padding(start = 7.5.dp, end = 7.5.dp)) {
        Image(painter = painterResource(id = R.drawable.userphoto),
            contentDescription = null,
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(160.dp),
            contentScale = ContentScale.Crop)

        Row(horizontalArrangement = Arrangement.End,
            modifier = Modifier.align(alignment = Alignment.BottomEnd)) {
            Box {
                IconButton(onClick = { /*TODO*/ }) {
                    Image(painter = painterResource(id = R.drawable.profileediticon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = CircleShape))
                }

            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Alpha(
    text1: String,
    text2: String,
) {

    CompositionLocalProvider(
        LocalMinimumTouchTargetEnforcement provides false,
    ) {
        Row(Modifier
            .height(30.dp)
            .width(121.dp)) {
            Button(onClick = { /*TODO*/ },
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)) {
                Text(text = text1, fontSize = 10.sp)
            }
            Button(onClick = { /*TODO*/ },
                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)) {
                Text(text = text2, fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun AddressType(
    selectionTypeText: String,
    radioButtonList: List<ButtonListTypes>,
    modifier: Modifier = Modifier,
) {

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioButtonList[0]) }

    Card(modifier = modifier
        .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth()) {
                androidx.compose.material.Text(text = selectionTypeText,
                    color = Color(0x99000000),
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp,
                    fontWeight = FontWeight.Bold)
            }
            FlowRow(Modifier.fillMaxWidth()) {
                radioButtonList.forEach { text ->
                    Row(horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.padding(top = 9.5.dp, bottom = 9.5.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                androidx.compose.material.RadioButton(selected = (text == selectedOption),
                                    onClick = {
                                        onOptionSelected(text)
                                    },
                                    colors = RadioButtonDefaults.colors(Color(0xff2F80ED)))
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
    }
}

@Composable
fun PrivacyStatement() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Box {
            Image(painter = painterResource(id = R.drawable.privacy),
                contentDescription = null,
                modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "Privacy Statement", fontSize = 14.sp, color = Color(0xDE000000))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "We value your privacy. We are committed to protecting your privacy and ask for your consent for the use of your personal health information as required during you health care.",
                fontSize = 12.sp,
                color = Color(0xDE000000),
                fontWeight = FontWeight.Light,
                softWrap = true)
        }
    }
}

@Composable
fun UserConsent() {

    val checkedState = remember { mutableStateOf(false) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Box {
            Checkbox(checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "I CONSENT TO THE USE OF MY PERSONAL HEALTH INFORMATION AS REQUIRED DURING YOUR HEALTH CARE.",
                fontSize = 12.sp,
                color = Color(0xFF375369),
                fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun NextButton(text: String, modifier: Modifier = Modifier, event: (() -> Unit)? = null) {
    event?.let {
        Button(modifier = modifier
            .padding(horizontal = 16.dp)
            .clip(shape = RectangleShape),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff0088FF)),
            onClick = it) {
            Text(text = text,
                fontFamily = FontFamily.Default,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                lineHeight = 16.sp,
                letterSpacing = 1.25.sp,
                textAlign = TextAlign.Center)
        }
    }
}