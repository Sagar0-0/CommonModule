package fit.asta.health.testimonials.view.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.R
import fit.asta.health.common.ui.theme.boxSize
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.imageSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.testimonials.model.domain.TestimonialType


@Composable
fun MyTextField(
    textFieldTitle: String,
) {

    val maxChar = 50
    var text by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text, onValueChange = {
                if (it.length <= maxChar) text = it
            }, modifier = Modifier
                .fillMaxWidth()
                .height(boxSize.medium), placeholder = {
                Text(
                    text = textFieldTitle,
                    lineHeight = 19.6.sp,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }, colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.background
            ), shape = MaterialTheme.shapes.medium
        )
        Text(
            text = "${text.length} / $maxChar",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TestimonialsRadioButton(
    selectionTypeText: String,
    radioButtonList: List<TestimonialType>,
    selectedOption: TestimonialType,
    content: @Composable (() -> Unit)? = null,
    titleTestimonial: @Composable (() -> Unit)? = null,
    onOptionSelected: (TestimonialType) -> Unit,
) {

    Column(Modifier.fillMaxWidth()) {

        androidx.compose.material3.Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = cardElevation.medium)
        ) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = spacing.medium)
            ) {
                Spacer(modifier = Modifier.height(spacing.medium))
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = selectionTypeText,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        lineHeight = 19.6.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                FlowRow(Modifier.fillMaxWidth()) {
                    radioButtonList.forEach { item ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.padding(
                                    top = spacing.small, bottom = spacing.small
                                )
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = (item == selectedOption), onClick = {
                                            onOptionSelected(item)
                                        },
                                        // colors = RadioButtonDefaults.colors(Color(0xff2F80ED))
                                        colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                                    )
                                    Text(
                                        text = item.name,
                                        lineHeight = 22.4.sp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(spacing.medium))

        titleTestimonial?.let { it() }
        if (selectedOption == radioButtonList[0] || selectedOption == radioButtonList[1]) {
            Spacer(modifier = Modifier.height(spacing.medium))
            content?.let { it() }
        }
    }
}

@Composable
fun UploadFiles(modifier: Modifier = Modifier) {

    Column(modifier = modifier) {
        Text(
            text = "Add a Photo or Video",
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        Box(
            modifier = Modifier.dashedBorder(
                width = 1.dp, radius = 8.dp, color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium, vertical = spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Upload Image or Video",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 19.6.sp,
                    style = MaterialTheme.typography.bodyMedium
                )

                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Text(
                        text = "Upload",
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.width(spacing.small))
                    Image(
                        painter = painterResource(id = R.drawable.upload),
                        contentDescription = null,
                        modifier = Modifier.size(imageSize.extraMedium)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(spacing.medium))

        Text(
            text = "You can upload maximum 5 images and 1 video *",
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall
        )
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
        it.drawRoundRect(
            width.toPx(),
            width.toPx(),
            size.width - width.toPx(),
            size.height - width.toPx(),
            radius.toPx(),
            radius.toPx(),
            paint
        )
    }
}