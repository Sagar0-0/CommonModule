package fit.asta.health.designsystemx.extras.textfield

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystemx.extras.theme.ColorAmericanPurple
import fit.asta.health.designsystemx.extras.validation.state.ValidationState
import fit.asta.health.designsystemx.extras.validation.util.TextFieldType

@Composable
fun AuthenticationTextField(
    modifier: Modifier,
    state: ValidationState,
    @StringRes hint: Int,
    onValueChange: (String) -> Unit,
    type: TextFieldType,

    ) {

    CustomTextField(
        modifier = modifier,
        state = state,
        hint = hint,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.labelLarge,
        hintTextStyle = MaterialTheme.typography.labelLarge,
        color = ColorAmericanPurple,
        cornerRadius = 15.dp,
        type = type
    )
}