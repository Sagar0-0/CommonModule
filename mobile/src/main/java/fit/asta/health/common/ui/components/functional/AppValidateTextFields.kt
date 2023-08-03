package fit.asta.health.common.ui.components.functional

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import fit.asta.health.common.ui.components.generic.AppDefOutlineTextField
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.utils.UiString

/**[ValidateTxtLength] is an object that defines the default text length used in the [AppValidateOutlineTxtField] composable.
 * */

object ValidateTxtLength {
    const val defLength = 256
}

/**[AppValidateOutlineTxtField] is a custom composable function in Jetpack Compose, designed to
 * create a text field with additional validation features. It includes an optional error message
 * and a character count indicator to ensure that the user's input adheres to a specified text length.
 * @param value the input text to be shown in the text field
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param modifier the [Modifier] to be applied to this text field
 * @param label the optional label to be displayed inside the text field container.
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called.
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines.
 * @param capitalization Options to request software keyboard to capitalize the text. Applies to
 * languages which has upper-case and lower-case letters.
 * @param keyboardType Values representing the different available Keyboard Types.
 * @param imeAction Signals the keyboard what type of action should be displayed. It is not guaranteed that
 * the keyboard will show the requested action.
 * @param isError indicates if the text field's current value is in error. If set to true, the
 * label, bottom indicator and trailing icon by default will be displayed in error color
 * @param stringLength The maximum allowed length for the text in the text field. The character count
 * will be displayed in the UI.
 * @param showLenErrorMsg  If set true it will display an error message based on text length [stringLength] .
 * */

@Composable
fun AppValidateOutlineTxtField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    errorMessage: UiString,
    keyboardActions: KeyboardActions,
    isError: Boolean = false,
    singleLine: Boolean = false,
    showLenErrorMsg: Boolean? = null,
    stringLength: Int = ValidateTxtLength.defLength,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        AppDefOutlineTextField(
            modifier = modifier,
            value = value,
            label = label,
            isError = isError,
            singleLine = singleLine,
            keyboardActions = keyboardActions,
            onValueChange = onValueChange,
            capitalization = capitalization,
            keyboardType = keyboardType,
            imeAction = imeAction,
        )
        TxtFieldErrorMsg(showError = isError, errorMessage = errorMessage.asString())
        if (showLenErrorMsg != null) {
            IsTxtLengthValid(
                showError = isError,
                value = value,
                showLenErrorMsg = showLenErrorMsg,
                stringLength = stringLength
            )
        }
    }
}

/**[TxtFieldErrorMsg] Displays an error message if showError is set to true.
 * @param showError: Boolean - Set to true to show the error message, false to hide it.
 * @param errorMessage: String - The error message to be displayed.
 * */

@Composable
fun TxtFieldErrorMsg(
    showError: Boolean,
    errorMessage: String,
) {
    if (showError) {
        AppTexts.TitleSmall(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/** [IsTxtLengthValid] Displays the character count of the text field if showErrorMsg is true and there are no validation errors.
 * @param showError Set to true if there is a validation error, false otherwise.
 * @param value: String The current value of the text field.
 * @param showLenErrorMsg Set to true to show the character count, false to hide it.
 * @param stringLength The maximum allowed length for the text in the text field. The character count will be compared against this value.
 * */

@Composable
fun IsTxtLengthValid(
    showError: Boolean,
    value: String,
    showLenErrorMsg: Boolean,
    stringLength: Int = ValidateTxtLength.defLength,
) {
    if (showLenErrorMsg && !showError) {
        AppTexts.LabelLarge(text = "${value.length}/${stringLength}")
    }
}