package fit.asta.otpfield

import android.view.KeyEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.otpfield.configuration.OTPConfigurations
import fit.asta.otpfield.utils.conditional

private val MIN_HEIGHT_CELL_SIZE = 48.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OTPCell(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    configurations: OTPConfigurations,
    placeHolder: String,
    visualTransformation: VisualTransformation,
    isErrorOccurred: Boolean,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }

    val cellConfiguration by remember(
        key1 = value, key2 = isFocused, key3 = isErrorOccurred
    ) {
        val config = when {
            isErrorOccurred -> configurations.errorCellConfig
            isFocused -> configurations.activeCellConfig
            value.isNotEmpty() -> configurations.filledCellConfig
            else -> configurations.emptyCellConfig
        }
        mutableStateOf(config)
    }

    val textStyle = remember(cellConfiguration.textStyle) {
        cellConfiguration.textStyle.copy(textAlign = TextAlign.Center)
    }

    val textFieldValue = remember(value) {
        TextFieldValue(
            text = value,
            selection = TextRange(value.length),
        )
    }

    AppSurface(
        modifier = modifier
            .defaultMinSize(
                minHeight = MIN_HEIGHT_CELL_SIZE
            )
            .conditional(configurations.enableBottomLine) {
                drawBehind {
                    if (configurations.enableBottomLine) {
                        val y = size.height

                        drawLine(
                            color = cellConfiguration.borderColor,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = cellConfiguration.borderWidth.toPx()
                        )
                    }
                }
            }
            .conditional(configurations.enableBottomLine.not()) {
                border(
                    border = BorderStroke(
                        width = cellConfiguration.borderWidth, color = cellConfiguration.borderColor
                    ), shape = cellConfiguration.shape
                )
            },
        shadowElevation = configurations.elevation,
        shape = if (configurations.enableBottomLine) RoundedCornerShape(0.dp) else cellConfiguration.shape,
    ) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = {
                if (it.text == value) return@BasicTextField
                onValueChange(it.text)
            },
            visualTransformation = visualTransformation,
            textStyle = textStyle,
            modifier = Modifier
                .conditional(value.isEmpty()) {
                    onPreviewKeyEvent {
                        val isDeleteKey = it.key == Key.Backspace || it.key == Key.Delete

                        if (isDeleteKey && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                            onValueChange("")
                            return@onPreviewKeyEvent true
                        }
                        false
                    }
                }
                .onFocusEvent { isFocused = it.isFocused }
                .background(cellConfiguration.backgroundColor),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType, imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {}, onDone = {}),
            singleLine = true,
            enabled = enabled,
            cursorBrush = SolidColor(configurations.cursorColor),
        ) { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                placeholder = {
                    CellPlaceHolder(placeHolder = placeHolder)
                },
                contentPadding = PaddingValues(0.dp),
            )
        }
    }
}

@Composable
private fun CellPlaceHolder(
    placeHolder: String,
    modifier: Modifier = Modifier,
) {
    BodyTexts.Level3(
        text = placeHolder,
        modifier = modifier.fillMaxWidth()
    )
}