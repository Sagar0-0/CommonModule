package fit.asta.health.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.common.utils.UiString

@Composable
fun ValidatedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    showError: Boolean = false,
    errorMessage: UiString,
    singleLine: Boolean = false,
) {

    /*Todo Text fields theming*/

    Column(Modifier.fillMaxWidth()) {

        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label, textAlign = TextAlign.Center) },
            isError = showError,
            trailingIcon = {
                if (showError) Icon(
                    imageVector = Icons.Filled.Error, contentDescription = "ErrorMessage"
                )
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        )

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            if (showError) {
                Text(
                    text = errorMessage.asString(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            if (label == "Testimonials") {
                Text(text = "${value.length}/256")
            }
        }

    }
}


@Composable
fun ValidatedNumberField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeholder: String = "",
    supportingText: String = "",
    supportingTextModifier: Modifier = Modifier,
    supportingTextAlign: TextAlign = TextAlign.Start,
    singleLine: Boolean = false,
) {

    TextField(
        enabled = enabled,
        value = value,
        onValueChange = onValueChange,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start
            )
        },
        supportingText = {
            Text(
                text = supportingText,
                modifier = supportingTextModifier,
                textAlign = supportingTextAlign
            )
        },
        singleLine = singleLine,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    )

}