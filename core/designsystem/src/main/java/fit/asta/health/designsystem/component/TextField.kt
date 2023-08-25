package fit.asta.health.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import fit.asta.health.designsystem.atomic.LocalShape

@Composable
fun AstaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @StringRes label: Int? = null,
    @StringRes placeholder: Int? = null,
    leadingIcon: ImageVector? = null,
    @StringRes leadingIconContentDesc: Int? = null,
    trailingIcon: ImageVector? = null,
    @StringRes trailingIconContentDesc: Int? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    shape: Shape = LocalShape.current.small.copy(
        bottomEnd = ZeroCornerSize,
        bottomStart = ZeroCornerSize
    ),
    colors: TextFieldColors = TextFieldDefaults.colors(),
    @StringRes supportingText: Int? = null
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        label = {
            label?.let { Text(text = stringResource(id = it)) }
        },
        placeholder = {
            placeholder?.let { Text(text = stringResource(id = it)) }
        },
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = leadingIconContentDesc?.let { desc ->
                        stringResource(id = desc)
                    }
                )
            }
        },
        trailingIcon = {
            trailingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = trailingIconContentDesc?.let { desc ->
                        stringResource(id = desc)
                    }
                )
            }
        },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        minLines = minLines,
        shape = shape,
        colors = colors,
        supportingText = {
            supportingText?.let { Text(text = stringResource(id = it)) }
        }
    )
}

@Composable
fun AstaOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @StringRes label: Int? = null,
    @StringRes placeholder: Int? = null,
    leadingIcon: ImageVector? = null,
    @StringRes leadingIconContentDesc: Int? = null,
    trailingIcon: ImageVector? = null,
    @StringRes trailingIconContentDesc: Int? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    shape: Shape = LocalShape.current.small.copy(
        bottomEnd = ZeroCornerSize,
        bottomStart = ZeroCornerSize
    ),
    colors: TextFieldColors = TextFieldDefaults.colors(),
    @StringRes supportingText: Int? = null
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        label = {
            label?.let { Text(text = stringResource(id = it)) }
        },
        placeholder = {
            placeholder?.let { Text(text = stringResource(id = it)) }
        },
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = leadingIconContentDesc?.let { desc ->
                        stringResource(id = desc)
                    }
                )
            }
        },
        trailingIcon = {
            trailingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = trailingIconContentDesc?.let { desc ->
                        stringResource(id = desc)
                    }
                )
            }
        },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        minLines = minLines,
        shape = shape,
        colors = colors,
        supportingText = {
            supportingText?.let { Text(text = stringResource(id = it)) }
        }
    )
}