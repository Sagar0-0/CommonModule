package fit.asta.health.profile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import fit.asta.health.R
import fit.asta.health.testimonials.view.theme.imageSize
import fit.asta.health.ui.spacing

data class ButtonListTypes(
    val buttonType: String,
)

@Preview(showSystemUi = true)
@ExperimentalMaterial3Api
@Composable
fun CreateProfile() {

    val buttonTypeList = listOf(
        ButtonListTypes(buttonType = "Female"),
        ButtonListTypes(buttonType = "Male"),
        ButtonListTypes(buttonType = "Others")
    )

    val isPregnantList =
        listOf(ButtonListTypes(buttonType = "Yes"), ButtonListTypes(buttonType = "No"))


    var text by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            UserCircleImage()

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Name",
                    color = Color(0xff132839),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Date of Birth",
                    color = Color(0xff132839),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "24yr",
                    color = Color(0x99000000),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    placeholder = { Text("DAY") },
                    modifier = Modifier.fillMaxWidth(0.3f)
                )


                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    placeholder = { Text("MONTH") },
                    modifier = Modifier.fillMaxWidth(0.5f)
                )


                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    placeholder = { Text("YEAR") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(
                    text = "Weight",
                    color = Color(0xff132839),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Box {
                    Alpha(text1 = "kg", text2 = "lb")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(
                    text = "Height",
                    color = Color(0xff132839),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Box {
                    Alpha(text1 = "in", text2 = "cm")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            val (selectedOption, onOptionSelected) = remember { mutableStateOf(buttonTypeList[0]) }

            Spacer(modifier = Modifier.height(20.dp))

            AddressType(
                selectionTypeText = "Gender",
                radioButtonList = buttonTypeList,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )

            Spacer(modifier = Modifier.height(20.dp))

            AddressType(
                selectionTypeText = "Are you Pregnant?",
                radioButtonList = isPregnantList,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(
                    text = "Please mention the week of Pregnancy",
                    color = Color(0x99000000),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )

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

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(start = 7.5.dp, end = 7.5.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.userphoto),
            contentDescription = null,
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(160.dp),
            contentScale = ContentScale.Crop
        )

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        ) {
            Box {
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.profileediticon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = CircleShape)
                    )
                }

            }
        }

    }
}

@Composable
fun Alpha(
    text1: String,
    text2: String,
) {

    Row(
        Modifier
            .height(30.dp)
            .width(121.dp)
    ) {
        Button(
            onClick = { /*TODO*/ }, shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
        ) {
            Text(text = text1, fontSize = 10.sp)
        }
        Button(
            onClick = { /*TODO*/ }, shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
        ) {
            Text(text = text2, fontSize = 10.sp)
        }
    }
}

@Composable
fun AddressType(
    selectionTypeText: String,
    radioButtonList: List<ButtonListTypes>,
    modifier: Modifier = Modifier,
    selectedOption: ButtonListTypes,
    onOptionSelected: (ButtonListTypes) -> Unit,
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(Modifier.fillMaxWidth()) {

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium)
            ) {
                Text(
                    text = selectionTypeText,
                    color = Color(0x99000000),
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(radioButtonList.size),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                userScrollEnabled = false
            ) {
                radioButtonList.forEach { text ->
                    item {
                        Row(verticalAlignment = CenterVertically, modifier = Modifier.weight(1f)) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                modifier = Modifier.size(40.dp)
                            )
                            Text(
                                text = text.buttonType,
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Right
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PrivacyStatement() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.medium),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.privacy),
                contentDescription = null,
                modifier = Modifier.size(imageSize.extraMedium)
            )
        }
        Spacer(modifier = Modifier.width(spacing.medium))
        Column {
            Text(text = "Privacy Statement", fontSize = 14.sp, color = Color(0xDE000000))
            Spacer(modifier = Modifier.height(spacing.extraSmall))
            Text(
                text = "We value your privacy. We are committed to protecting your privacy and ask for your consent for the use of your personal health information as required during you health care.",
                fontSize = 12.sp,
                color = Color(0xDE000000),
                fontWeight = FontWeight.Light,
                softWrap = true
            )
        }
    }
}

@Composable
fun UserConsent() {

    val checkedState = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                modifier = Modifier.size(imageSize.extraMedium)
            )
        }
        Spacer(modifier = Modifier.width(spacing.medium))
        Column {
            Text(
                text = "I CONSENT TO THE USE OF MY PERSONAL HEALTH INFORMATION AS REQUIRED DURING YOUR HEALTH CARE.",
                fontSize = 12.sp,
                color = Color(0xFF375369),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun NextButton(text: String, modifier: Modifier = Modifier, event: (() -> Unit)? = null) {
    event?.let {
        Button(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .clip(shape = RectangleShape),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff0088FF)),
            onClick = it
        ) {
            Text(
                text = text,
                fontFamily = FontFamily.Default,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                lineHeight = 16.sp,
                letterSpacing = 1.25.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}