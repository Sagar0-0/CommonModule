package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.utils.UiString


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

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label, textAlign = TextAlign.Center) },
            isError = showError,
            trailingIcon = {
                if (showError) Icon(
                    imageVector = Icons.Filled.Error, contentDescription = "Show Error Icon"
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
                    style = MaterialTheme.typography.titleSmall
                )
            }

            if (label == "Testimonials") {
                Text(text = "${value.length}/256")
            }
        }

    }
}


@Composable
fun ValidateNumberField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeholder: String = "",
    singleLine: Boolean = false,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        },
        singleLine = singleLine,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    )

}