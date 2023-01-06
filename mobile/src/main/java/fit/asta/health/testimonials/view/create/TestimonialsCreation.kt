package fit.asta.health.testimonials.view.create

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.R
import fit.asta.health.testimonials.model.domain.ButtonListTypes
import fit.asta.health.ui.theme.ColorGraniteGray
import fit.asta.health.ui.theme.Dark02
import fit.asta.health.ui.theme.FocusedBorderColor
import fit.asta.health.ui.theme.TextLight04


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
                    color = ColorGraniteGray)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White,
                focusedBorderColor = FocusedBorderColor,
                unfocusedBorderColor = TextLight04),
            shape = RoundedCornerShape(8.dp))
        Text(text = "${text.length} / $maxChar",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.fillMaxWidth())
    }
}


@Composable
fun TestimonialsRadioButton(
    selectionTypeText: String,
    radioButtonList: List<ButtonListTypes>,
    selectedOption: ButtonListTypes,
    content: @Composable (() -> Unit)? = null,
    titleTestimonial: @Composable (() -> Unit)? = null,
    onOptionSelected: (ButtonListTypes) -> Unit,
) {

    Column(Modifier.fillMaxWidth()) {

        androidx.compose.material3.Card(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(width = 1.dp, color = TextLight04)) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(text = selectionTypeText,
                        color = Color.Black,
                        fontSize = 14.sp,
                        lineHeight = 19.6.sp,
                        fontWeight = FontWeight.Bold)
                }
                FlowRow(Modifier.fillMaxWidth()) {
                    radioButtonList.forEach { item ->
                        Row(horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.padding(top = 9.5.dp, bottom = 9.5.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(selected = (item.type == selectedOption.type),
                                        onClick = {
                                            onOptionSelected(item)
                                        },
                                       // colors = RadioButtonDefaults.colors(Color(0xff2F80ED))
                                        colors = RadioButtonDefaults.colors(androidx.compose.material3.MaterialTheme.colorScheme.primary)
                                    )
                                    androidx.compose.material3.Text(text = item.title,
                                        fontSize = 16.sp,
                                        lineHeight = 22.4.sp,
                                        color = Dark02)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        titleTestimonial?.let { it() }
        if (selectedOption == radioButtonList[0] || selectedOption == radioButtonList[1]) {
            Spacer(modifier = Modifier.height(16.dp))
            content?.let { it() }
        }
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
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Upload Image or Video",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp)

                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary),
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
            color = Color.Black)
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