package fit.asta.otpfield

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import fit.asta.otpfield.configuration.OTPConfigurations
import fit.asta.otpfield.utils.EMPTY
import fit.asta.otpfield.utils.requestFocusSafely

private const val NOT_ENTERED_VALUE = '₺'

/**
 * OhTeePeeInput is a composable that can be used to get OTP/Pin from user.
 *
 * Whenever the user edits the text, [onValueChange] is called with the most up to date state
 * including the empty values that represented by [OTPConfigurations.placeHolder].
 *
 * When the user fills all the cells, [onValueChange]'s isValid parameter will be `true`,
 * otherwise it will be `false`.
 *
 * To customize the appearance of cells you can pass [configurations] parameter with
 * a lot of options like , [OTPConfigurations.cellModifier], [OTPConfigurations.errorCellConfig] and more.
 *
 * If you don't want to pass all the configurations, you can use [OTPConfigurations.withDefaults] to customize
 * only the configurations you want.
 *
 * @param value will be split to chars and shown in the [OTPInput].
 * @param onValueChange Called when user enters or deletes any cell.
 * @param configurations [OTPConfigurations] to customize the appearance of cells.
 *
 * @param modifier optional [Modifier] for the whole [OTPInput].
 * You can use [OTPConfigurations.cellModifier] to customize each single cell.
 *
 * @param isValueInvalid when set to true, all cells will use [OTPConfigurations.errorCellConfig] and
 * focus will be requested on first cell. you should set this to false when user starts editing the text.
 *
 * @param keyboardType The keyboard type to be used for this text field.
 *
 * @param enabled when enabled is false, user will not be able to interact with the text fields.
 * you can set it to false when you are waiting for a response from server.
 *
 * @param autoFocusByDefault when set to true, first cell will be focused by default.
 *
 */
@Composable
fun OTPInput(
    value: String,
    onValueChange: (newValue: String, isValid: Boolean) -> Unit,
    configurations: OTPConfigurations,
    modifier: Modifier = Modifier,
    isValueInvalid: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Number,
    enabled: Boolean = true,
    autoFocusByDefault: Boolean = true,
) {
    require(configurations.placeHolder.length <= 1) {
        "placeHolder can't be more then 1 characters"
    }
    require(configurations.obscureText.length <= 1) {
        "obscureText can't be more then 1 characters"
    }

    val obscureText = configurations.obscureText
    val placeHolder = configurations.placeHolder
    val cellsCount = configurations.cellsCount
    val placeHolderAsChar = placeHolder.first()
    val otpValue by remember(value, isValueInvalid) {
        val charList = CharArray(cellsCount) { index ->
            if (isValueInvalid) {
                NOT_ENTERED_VALUE
            } else {
                value.getOrNull(index)?.takeIf { it.isWhitespace().not() } ?: NOT_ENTERED_VALUE
            }
        }
        mutableStateOf(charList)
    }
    val focusRequester = remember(cellsCount) { List(cellsCount) { FocusRequester() } }
    val transparentTextSelectionColors: TextSelectionColors = remember {
        TextSelectionColors(
            handleColor = Transparent, backgroundColor = Transparent
        )
    }

    val visualTransformation = remember(obscureText) {
        if (obscureText.isEmpty()) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation(obscureText.first())
        }
    }

    fun requestFocus(index: Int) {
        val nextIndex = index.coerceIn(0, cellsCount - 1)
        focusRequester[nextIndex].requestFocusSafely()
    }

    LaunchedEffect(autoFocusByDefault) {
        if (autoFocusByDefault) focusRequester.first().requestFocus()
    }

    LaunchedEffect(isValueInvalid) {
        if (isValueInvalid) focusRequester.first().requestFocus()
    }

    OTPInput(
        modifier = modifier,
        textSelectionColors = transparentTextSelectionColors,
        cellsCount = cellsCount,
        otpValue = otpValue,
        placeHolder = placeHolder,
        isErrorOccurred = isValueInvalid,
        keyboardType = keyboardType,
        OTPConfigurations = configurations,
        focusRequesters = focusRequester,
        enabled = enabled,
        visualTransformation = visualTransformation,
        onCellInputChange = onCellInputChange@{ currentCellIndex, newValue ->
            val currentCellText = otpValue[currentCellIndex].toString()
            val formattedNewValue =
                newValue.replace(placeHolder, String.EMPTY).replace(obscureText, String.EMPTY)

            if (formattedNewValue == currentCellText) {
                requestFocus(currentCellIndex + 1)
                return@onCellInputChange
            }

            if (formattedNewValue.length == cellsCount) {
                onValueChange(formattedNewValue, true)
                focusRequester.last().requestFocusSafely()
                return@onCellInputChange
            }

            if (formattedNewValue.isNotEmpty()) {
                otpValue[currentCellIndex] = formattedNewValue.last()
                requestFocus(currentCellIndex + 1)
            } else if (currentCellText != NOT_ENTERED_VALUE.toString()) {
                otpValue[currentCellIndex] = NOT_ENTERED_VALUE
            } else {
                val previousIndex = (currentCellIndex - 1).coerceIn(0, cellsCount)
                otpValue[previousIndex] = NOT_ENTERED_VALUE
                requestFocus(previousIndex)
            }
            val otpValueAsString = otpValue.joinToString(String.EMPTY) {
                if (it == NOT_ENTERED_VALUE) " " else it.toString()
            }
            onValueChange(otpValueAsString, otpValueAsString.none { it == placeHolderAsChar })
        },
    )
}

@Composable
private fun OTPInput(
    modifier: Modifier = Modifier,
    textSelectionColors: TextSelectionColors,
    cellsCount: Int,
    otpValue: CharArray,
    placeHolder: String,
    isErrorOccurred: Boolean,
    keyboardType: KeyboardType,
    OTPConfigurations: OTPConfigurations,
    focusRequesters: List<FocusRequester>,
    enabled: Boolean,
    visualTransformation: VisualTransformation,
    onCellInputChange: (index: Int, value: String) -> Unit,
) {
    CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
        Row(
            modifier = modifier, verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(cellsCount) { index ->
                val displayValue = getCellDisplayCharacter(
                    currentChar = otpValue[index],
                )
                OTPCell(
                    value = displayValue,
                    isErrorOccurred = isErrorOccurred,
                    keyboardType = keyboardType,
                    modifier = OTPConfigurations.cellModifier.focusRequester(focusRequester = focusRequesters[index]),
                    enabled = enabled,
                    configurations = OTPConfigurations,
                    onValueChange = {
                        onCellInputChange(index, it)
                    },
                    placeHolder = placeHolder,
                    visualTransformation = visualTransformation
                )
            }
        }
    }
}

@Composable
private fun getCellDisplayCharacter(
    currentChar: Char,
): String = currentChar.toString().replace(NOT_ENTERED_VALUE.toString(), String.EMPTY)