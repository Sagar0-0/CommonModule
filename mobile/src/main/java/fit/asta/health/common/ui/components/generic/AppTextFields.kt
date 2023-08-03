package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

/**[AppTextField] is a composable function that creates a custom outlined text field for the app.
 * @param value the input text to be shown in the text field
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param modifier the [Modifier] to be applied to this text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label the optional label to be displayed inside the text field container.
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called.
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines.
 * @param capitalization Options to request software keyboard to capitalize the text. Applies to languages which
 * has upper-case and lower-case letters.
 * @param keyboardType Values representing the different available Keyboard Types.
 * @param imeAction Signals the keyboard what type of action should be displayed. It is not guaranteed that
 * the keyboard will show the requested action.
 * @param isError indicates if the text field's current value is in error. If set to true, the
 * label, bottom indicator and trailing icon by default will be displayed in error color
 * */

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    isError: Boolean = false,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, textAlign = TextAlign.Center) },
        isError = isError,
        trailingIcon = {
            if (isError) Icon(
                imageVector = Icons.Filled.Error, contentDescription = "Show Error Icon"
            )
        },
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization, keyboardType = keyboardType, imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        enabled = enabled,
    )
}