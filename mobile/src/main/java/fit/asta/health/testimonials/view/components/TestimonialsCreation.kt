package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.R
import fit.asta.health.profile.view.components.ButtonListTypes
import fit.asta.health.profile.view.components.UpdateButton


@Composable
fun MyTextField(
    textFieldTitle: String,
) {

    val maxChar = 50

    var text by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth()) {
        OutlinedTextField(value = text,
            onValueChange = {
                if (it.length <= maxChar) text = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            placeholder = {
                Text(text = textFieldTitle,
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp,
                    color = Color(0xff999999))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White,
                focusedBorderColor = Color(0xff0088FF),
                unfocusedBorderColor = Color(0xffDFE6ED)),
            shape = RoundedCornerShape(8.dp))
        Text(text = "${text.length} / $maxChar",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun TestimonialTitle(
    placeHolder: String,
) {

    var text by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth()) {
        OutlinedTextField(value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = placeHolder,
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp,
                    color = Color(0xff999999))
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White,
                focusedBorderColor = Color(0xff0088FF),
                unfocusedBorderColor = Color(0xffDFE6ED)),
            shape = RoundedCornerShape(8.dp))
    }
}

@Preview
@Composable
fun TestimonialType(contentTestType: @Composable() (() -> Unit)? = null) {

    val radioButtonList = listOf(ButtonListTypes(buttonType = "Written"),
        ButtonListTypes(buttonType = "Video"),
        ButtonListTypes(buttonType = "Image"))

    TestimonialsRadioButton(selectionTypeText = "Type of Testimonials",
        radioButtonList = radioButtonList, content = contentTestType)

}


@Composable
fun TestimonialsRadioButton(
    selectionTypeText: String,
    radioButtonList: List<ButtonListTypes>,
    content: @Composable() (() -> Unit)? = null,
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioButtonList[0]) }

    Column(Modifier.fillMaxWidth()) {
        androidx.compose.material3.Card(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(width = 1.dp, color = Color(0xffDFE6ED))) {
            Column(Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(text = selectionTypeText,
                        color = Color(0x99000000),
                        fontSize = 14.sp,
                        lineHeight = 19.6.sp,
                        fontWeight = FontWeight.Bold)
                }
                FlowRow(Modifier.fillMaxWidth()) {
                    radioButtonList.forEach { index ->
                        Row(horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.padding(top = 9.5.dp, bottom = 9.5.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(selected = (index == selectedOption), onClick = {
                                        onOptionSelected(index)
                                    }, colors = RadioButtonDefaults.colors(Color(0xff2F80ED)))
                                    androidx.compose.material3.Text(text = index.buttonType,
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

        if (selectedOption == radioButtonList[0] || selectedOption == radioButtonList[2]) {
            Spacer(modifier = Modifier.height(16.dp))

            content?.let { it() }
        }

    }

}


@Composable
fun TestimonialLayoutDemo(onNavigateTstCreate: () -> Unit) {
    Column(Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            androidx.compose.material3.IconButton(onClick = onNavigateTstCreate) {
                androidx.compose.material3.Icon(painter = painterResource(id = R.drawable.removeicon),
                    contentDescription = null,
                    Modifier.size(24.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialTitle(placeHolder = "Title")

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialType()

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialTitle(placeHolder = "Lisa")

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialTitle(placeHolder = "Role")

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialTitle(placeHolder = "Organisation")

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialTitle(placeHolder = "Subtitle")

        Spacer(modifier = Modifier.height(16.dp))

        UploadFiles()

        Spacer(modifier = Modifier.height(16.dp))

        UpdateButton()

    }
}

@Composable
fun UploadFiles(modifier: Modifier = Modifier) {

    Column(modifier = modifier) {
        Text(text = "Add a Photo or Video",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.dashedBorder(width = 1.dp,
            radius = 8.dp,
            color = Color(0xff8694A9))) {
            Row(Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Upload Image or Video",
                    color = Color(0xff585D64),
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp)

                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff0075FF)),
                    shape = RoundedCornerShape(20.dp)) {
                    Text(text = "Upload",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.width(7.dp))
                    Image(painter = painterResource(id = R.drawable.upload),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "You can upload maximum 5 images and 1 video *",
            fontSize = 12.sp,
            color = Color(0x99000000))
    }
}

fun Modifier.dashedBorder(width: Dp, radius: Dp, color: Color) = drawBehind {
    drawIntoCanvas {
        val paint = Paint().apply {
            strokeWidth = width.toPx()
            this.color = color
            style = PaintingStyle.Stroke
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        }
        it.drawRoundRect(width.toPx(),
            width.toPx(),
            size.width - width.toPx(),
            size.height - width.toPx(),
            radius.toPx(),
            radius.toPx(),
            paint)
    }
}


@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    showError: Boolean = false,
    errorMessage: String = "",
) {
    Column(Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        OutlinedTextField(value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            isError = showError,
            trailingIcon = {
                if (showError) Icon(imageVector = Icons.Filled.Error,
                    contentDescription = "Show Error Icon")
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true,
            modifier = Modifier.fillMaxWidth())
        if (showError) {
            Text(text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .fillMaxWidth())
        }
    }
}