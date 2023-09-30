package fit.asta.health.designsystem.molecular.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import fit.asta.health.designsystem.AppTheme

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
    shape: Shape = AppTheme.shape.small.copy(
        bottomEnd = ZeroCornerSize,
        bottomStart = ZeroCornerSize
    ),
    colors: TextFieldColors = TextFieldDefaults.colors(),
    supportingText: @Composable (() -> Unit)? = null,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        label = label?.let {
             { Text(text = stringResource(id = it)) }
        },
        placeholder = placeholder?.let {
             { Text(text = stringResource(id = it)) }
        },
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = leadingIconContentDesc?.let { desc ->
                        stringResource(id = desc)
                    }
                )
            }
        },
        trailingIcon = trailingIcon?.let {
             {
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
        supportingText = supportingText
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
    shape: Shape = AppTheme.shape.small.copy(
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

sealed interface AstaValidatedTextFieldType {
    data object Phone : AstaValidatedTextFieldType
    data object Mail : AstaValidatedTextFieldType
    data class Default(val minLength: Int = 1, val maxLength: Int = 256) :
        AstaValidatedTextFieldType
}

@Composable
fun AstaValidatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    type: AstaValidatedTextFieldType = AstaValidatedTextFieldType.Default(),
    enabled: Boolean = true,
    @StringRes label: Int? = null,
    @StringRes placeholder: Int? = null,
    leadingIcon: ImageVector? = null,
    @StringRes leadingIconContentDesc: Int? = null,
    trailingIcon: ImageVector? = null,
    @StringRes trailingIconContentDesc: Int? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    var startedTyping by rememberSaveable {
        mutableStateOf(false)
    }
    if (value.isNotEmpty()) {
        startedTyping = true
    }
    val isError: Boolean = if (startedTyping) {
        when (type) {
            is AstaValidatedTextFieldType.Mail -> {
                value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()
            }

            is AstaValidatedTextFieldType.Phone -> {
                value.length != 10
            }

            is AstaValidatedTextFieldType.Default -> {
                value.length < type.minLength || value.length > type.maxLength
            }
        }
    } else {
        false
    }

    val errorMessage = when (type) {
        is AstaValidatedTextFieldType.Mail -> {
            if (value.isEmpty()) {
                "E-mail can not be empty"
            } else {
                "Enter a valid e-mail"
            }
        }

        is AstaValidatedTextFieldType.Phone -> {
            "Enter a valid phone number"
        }

        is AstaValidatedTextFieldType.Default -> {
            if (value.length < type.minLength) {
                "Enter text more than ${type.minLength} char"
            } else if (value.length > type.maxLength) {
                "Text should be less than ${type.maxLength} char"
            } else {
                ""
            }
        }
    }

    val maxChars = when (type) {
        is AstaValidatedTextFieldType.Mail -> {
            50
        }

        is AstaValidatedTextFieldType.Phone -> {
            10
        }

        is AstaValidatedTextFieldType.Default -> {
            type.maxLength
        }
    }

    AstaTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        leadingIconContentDesc = leadingIconContentDesc,
        trailingIcon = if(isError) {
                Icons.Default.Error
        }else{
            trailingIcon
        },
        trailingIconContentDesc = trailingIconContentDesc,
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        supportingText = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.small),
                horizontalArrangement =
                if (isError) {
                    Arrangement.SpaceBetween
                } else {
                    Arrangement.End
                }
            ) {
                if (isError) Text(errorMessage)
                Text(text = "${value.length}/$maxChars")
            }
        }
    )
}