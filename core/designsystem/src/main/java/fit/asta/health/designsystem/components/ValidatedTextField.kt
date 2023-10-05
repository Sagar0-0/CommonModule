package fit.asta.health.designsystem.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

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