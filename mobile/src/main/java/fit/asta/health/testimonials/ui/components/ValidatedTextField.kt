package fit.asta.health.testimonials.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.common.utils.UiString
import fit.asta.health.designsystem.components.generic.AppTextField


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
    Column(Modifier.fillMaxWidth()) {
        AppTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = label,
            isError = showError,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
        )
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
    showError: Boolean = false,
    errorMessage: UiString = UiString.Empty,
) {
    Column(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            isError = showError,
            keyboardActions = keyboardActions,
            trailingIcon = {
                if (showError) Icon(
                    imageVector = Icons.Filled.Error, contentDescription = "Show Error Icon"
                )
            },
            keyboardOptions = keyboardOptions,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            },
            singleLine = singleLine,
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
        )
        Row(Modifier.fillMaxWidth()) {
            if (showError) {
                Text(
                    text = errorMessage.asString(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}