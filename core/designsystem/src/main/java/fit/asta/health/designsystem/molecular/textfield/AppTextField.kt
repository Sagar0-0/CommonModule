package fit.asta.health.designsystem.molecular.textfield

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts


/**
 * Text fields allow users to enter text into a UI. They typically appear in forms and dialogs.
 * Filled text fields have more visual emphasis than outlined text fields, making them stand out
 * when surrounded by other content and components.
 *
 * ![Filled text field image](https://developer.android.com/images/reference/androidx/compose/material3/filled-text-field.png)
 *
 * @param modifier the [Modifier] to be applied to this text field
 * @param value the input text to be shown in the text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label This is the text that will be shown on the top of the Text Field
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 * container
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 * container
 * @param visualTransformation transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines. The keyboard will be informed to not show the return key
 * as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines attribute will
 * be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this text field. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this text field in different states.
 * @param shape defines the shape of this text field's container
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.colors].
 * @param appTextFieldType This is the Type of the TextField refer to [AppTextFieldValidator] for reference
 * and to get all the relevant type go at [AppTextFieldType].
 * @param isValidText This sends the parent function if the text is valid or not
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 */
@Composable
fun AppTextField(
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = AppTheme.shape.level1,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = AppTheme.colors.tertiary,
        unfocusedTextColor = AppTheme.colors.secondary,
        disabledTextColor = AppTheme.colors.onSecondary,
        errorTextColor = AppTheme.colors.error,
        focusedContainerColor  = AppTheme.colors.onSecondary,
        unfocusedContainerColor = AppTheme.colors.secondaryContainer,
        disabledContainerColor = AppTheme.colors.secondary,
        errorContainerColor = AppTheme.colors.errorContainer,
        cursorColor = AppTheme.colors.secondary,
        errorCursorColor = AppTheme.colors.onErrorContainer,
        errorLabelColor = AppTheme.colors.error,
        focusedPlaceholderColor = AppTheme.colors.onSecondary,
        focusedLabelColor = AppTheme.colors.onSecondary,
        unfocusedLabelColor = AppTheme.colors.onSecondaryContainer,
        unfocusedPlaceholderColor = AppTheme.colors.onSecondaryContainer
    ),
    appTextFieldType: AppTextFieldValidator = AppTextFieldValidator(AppTextFieldType.Custom()),
    isValidText: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {

    // This variable keeps if the user is typing or not
    val isTyping = remember { mutableStateOf(false) }
    isTyping.value = value.isNotEmpty()

    // This formulates if the user Input is valid or not
    val isError = appTextFieldType.isInvalid(value, isTyping.value)

    // Formulating what will be the error Message of the text Field Type
    val errorMessage = appTextFieldType.getErrorMessage(value)

    // This variable contains the Counter for the String
    val stringCounter = appTextFieldType.getStringCounter(value)


    // Text Field Layout from Material 3
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isValidText(appTextFieldType.isTextValid(it))
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = AppTheme.customTypography.caption.level2,
        label = { BodyTexts.Level3(text = label) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon?.let {
            {
                if (!isError) {
                    trailingIcon()
                } else {
                    Icon(
                        imageVector = Icons.Filled.Error, contentDescription = "ErrorMessage Icon"
                    )
                }
            }
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        supportingText = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level1),
                horizontalArrangement = if (isError) Arrangement.SpaceBetween else Arrangement.End
            ) {
                if (isError) CaptionTexts.Level3(errorMessage)

                CaptionTexts.Level3(text = stringCounter)
            }
        }
    )
}


/**
 * Text fields allow users to enter text into a UI. They typically appear in forms and dialogs.
 * Filled text fields have more visual emphasis than outlined text fields, making them stand out
 * when surrounded by other content and components.
 *
 * ![Filled text field image](https://developer.android.com/images/reference/androidx/compose/material3/filled-text-field.png)
 *
 * @param modifier the [Modifier] to be applied to this text field
 * @param value the input text to be shown in the text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label This is the text that will be shown on the top of the Text Field
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 * container
 * @param leadingIconDes This is the description of the Leading Icon
 * @param onLeadingIconClicked This is the function which will be called when the leading Icon is
 * clicked.
 * @param visualTransformation transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines. The keyboard will be informed to not show the return key
 * as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines attribute will
 * be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this text field. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this text field in different states.
 * @param shape defines the shape of this text field's container
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.colors].
 * @param appTextFieldType This is the Type of the TextField refer to [AppTextFieldValidator] for reference
 * and to get all the relevant type go at [AppTextFieldType].
 * @param isValidText This sends the parent function if the text is valid or not
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 */
@Composable
fun AppTextField(
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    onLeadingIconClicked: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = AppTheme.shape.level1,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    appTextFieldType: AppTextFieldValidator = AppTextFieldValidator(AppTextFieldType.Custom()),
    isValidText: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {

    // This variable keeps if the user is typing or not
    val isTyping = remember { mutableStateOf(false) }
    isTyping.value = value.isNotEmpty()

    // This formulates if the user Input is valid or not
    val isError = appTextFieldType.isInvalid(value, isTyping.value)

    // Formulating what will be the error Message of the text Field Type
    val errorMessage = appTextFieldType.getErrorMessage(value)

    // This variable contains the Counter for the String
    val stringCounter = appTextFieldType.getStringCounter(value)


    // Text Field Layout from Material 3
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isValidText(appTextFieldType.isTextValid(it))
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = AppTheme.customTypography.caption.level2,
        label = { BodyTexts.Level3(text = label) },
        leadingIcon = {
            if (leadingIcon != null) {
                AppIconButton(imageVector = leadingIcon, iconDesc = leadingIconDes) {
                    onLeadingIconClicked()
                }
            }
        },
        trailingIcon = {
            if (isError) Icon(
                imageVector = Icons.Filled.Error, contentDescription = "ErrorMessage Icon"
            )
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        supportingText = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level1),
                horizontalArrangement = if (isError) Arrangement.SpaceBetween else Arrangement.End
            ) {
                if (isError) CaptionTexts.Level3(errorMessage)

                CaptionTexts.Level3(text = stringCounter)
            }
        }
    )
}


/**
 * Text fields allow users to enter text into a UI. They typically appear in forms and dialogs.
 * Filled text fields have more visual emphasis than outlined text fields, making them stand out
 * when surrounded by other content and components.
 *
 * ![Filled text field image](https://developer.android.com/images/reference/androidx/compose/material3/filled-text-field.png)
 *
 * @param modifier the [Modifier] to be applied to this text field
 * @param value the input text to be shown in the text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label This is the text that will be shown on the top of the Text Field
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 * container
 * @param leadingIconDes This is the description of the Leading Icon
 * @param onLeadingIconClicked This is the function which will be called when the leading Icon is
 * clicked.
 * @param visualTransformation transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines. The keyboard will be informed to not show the return key
 * as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines attribute will
 * be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this text field. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this text field in different states.
 * @param shape defines the shape of this text field's container
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.colors].
 * @param appTextFieldType This is the Type of the TextField refer to [AppTextFieldValidator] for reference
 * and to get all the relevant type go at [AppTextFieldType].
 * @param isValidText This sends the parent function if the text is valid or not
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 */
@Composable
fun AppTextField(
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    leadingIcon: Painter? = null,
    leadingIconDes: String? = null,
    onLeadingIconClicked: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = AppTheme.shape.level1,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    appTextFieldType: AppTextFieldValidator = AppTextFieldValidator(AppTextFieldType.Custom()),
    isValidText: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {

    // This variable keeps if the user is typing or not
    val isTyping = remember { mutableStateOf(false) }
    isTyping.value = value.isNotEmpty()

    // This formulates if the user Input is valid or not
    val isError = appTextFieldType.isInvalid(value, isTyping.value)

    // Formulating what will be the error Message of the text Field Type
    val errorMessage = appTextFieldType.getErrorMessage(value)

    // This variable contains the Counter for the String
    val stringCounter = appTextFieldType.getStringCounter(value)


    // Text Field Layout from Material 3
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isValidText(appTextFieldType.isTextValid(it))
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = AppTheme.customTypography.caption.level2,
        label = { BodyTexts.Level3(text = label) },
        leadingIcon = {
            if (leadingIcon != null) {
                AppIconButton(painter = leadingIcon, iconDesc = leadingIconDes) {
                    onLeadingIconClicked()
                }
            }
        },
        trailingIcon = {
            if (isError) Icon(
                imageVector = Icons.Filled.Error, contentDescription = "ErrorMessage Icon"
            )
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        supportingText = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level1),
                horizontalArrangement = if (isError) Arrangement.SpaceBetween else Arrangement.End
            ) {
                if (isError) CaptionTexts.Level3(errorMessage)

                CaptionTexts.Level3(text = stringCounter)
            }
        }
    )
}


/**
 * Text fields allow users to enter text into a UI. They typically appear in forms and dialogs.
 * Filled text fields have more visual emphasis than outlined text fields, making them stand out
 * when surrounded by other content and components.
 *
 * ![Filled text field image](https://developer.android.com/images/reference/androidx/compose/material3/filled-text-field.png)
 *
 * @param modifier the [Modifier] to be applied to this text field
 * @param value the input text to be shown in the text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label This is the text that will be shown on the top of the Text Field
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 * container
 * @param trailingIconDes This is the description of the Trailing Icon
 * @param onTrailingIconClicked This is the function which will be called when the trailing icon is
 * clicked
 * @param visualTransformation transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines. The keyboard will be informed to not show the return key
 * as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines attribute will
 * be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this text field. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this text field in different states.
 * @param shape defines the shape of this text field's container
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.colors].
 * @param appTextFieldType This is the Type of the TextField refer to [AppTextFieldValidator] for reference
 * and to get all the relevant type go at [AppTextFieldType].
 * @param isValidText This sends the parent function if the text is valid or not
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 */
@Composable
fun AppTextField(
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    trailingIconDes: String? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClicked: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = AppTheme.shape.level1,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    appTextFieldType: AppTextFieldValidator = AppTextFieldValidator(AppTextFieldType.Custom()),
    isValidText: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {

    // This variable keeps if the user is typing or not
    val isTyping = remember { mutableStateOf(false) }
    isTyping.value = value.isNotEmpty()

    // This formulates if the user Input is valid or not
    val isError = appTextFieldType.isInvalid(value, isTyping.value)

    // Formulating what will be the error Message of the text Field Type
    val errorMessage = appTextFieldType.getErrorMessage(value)

    // This variable contains the Counter for the String
    val stringCounter = appTextFieldType.getStringCounter(value)


    // Text Field Layout from Material 3
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isValidText(appTextFieldType.isTextValid(it))
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = AppTheme.customTypography.caption.level2,
        label = { BodyTexts.Level3(text = label) },
        trailingIcon = {
            if (trailingIcon != null && !isError) {
                AppIconButton(imageVector = trailingIcon, iconDesc = trailingIconDes) {
                    onTrailingIconClicked()
                }
            }
            if (isError) Icon(
                imageVector = Icons.Filled.Error, contentDescription = "ErrorMessage Icon"
            )
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        supportingText = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level1),
                horizontalArrangement = if (isError) Arrangement.SpaceBetween else Arrangement.End
            ) {
                if (isError) CaptionTexts.Level3(errorMessage)

                CaptionTexts.Level3(text = stringCounter)
            }
        }
    )
}


/**
 * Text fields allow users to enter text into a UI. They typically appear in forms and dialogs.
 * Filled text fields have more visual emphasis than outlined text fields, making them stand out
 * when surrounded by other content and components.
 *
 * ![Filled text field image](https://developer.android.com/images/reference/androidx/compose/material3/filled-text-field.png)
 *
 * @param modifier the [Modifier] to be applied to this text field
 * @param value the input text to be shown in the text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label This is the text that will be shown on the top of the Text Field
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 * container
 * @param trailingIconDes This is the description of the Trailing Icon
 * @param onTrailingIconClicked This is the function which will be called when the trailing icon is
 * clicked
 * @param visualTransformation transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines. The keyboard will be informed to not show the return key
 * as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines attribute will
 * be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this text field. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this text field in different states.
 * @param shape defines the shape of this text field's container
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.colors].
 * @param appTextFieldType This is the Type of the TextField refer to [AppTextFieldValidator] for reference
 * and to get all the relevant type go at [AppTextFieldType].
 * @param isValidText This sends the parent function if the text is valid or not
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 */
@Composable
fun AppTextField(
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    trailingIconDes: String? = null,
    trailingIcon: Painter? = null,
    onTrailingIconClicked: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = AppTheme.shape.level1,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    appTextFieldType: AppTextFieldValidator = AppTextFieldValidator(AppTextFieldType.Custom()),
    isValidText: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {

    // This variable keeps if the user is typing or not
    val isTyping = remember { mutableStateOf(false) }
    isTyping.value = value.isNotEmpty()

    // This formulates if the user Input is valid or not
    val isError = appTextFieldType.isInvalid(value, isTyping.value)

    // Formulating what will be the error Message of the text Field Type
    val errorMessage = appTextFieldType.getErrorMessage(value)

    // This variable contains the Counter for the String
    val stringCounter = appTextFieldType.getStringCounter(value)


    // Text Field Layout from Material 3
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isValidText(appTextFieldType.isTextValid(it))
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = AppTheme.customTypography.caption.level2,
        label = { BodyTexts.Level3(text = label) },
        trailingIcon = {
            if (trailingIcon != null && !isError) {
                AppIconButton(painter = trailingIcon, iconDesc = trailingIconDes) {
                    onTrailingIconClicked()
                }
            }
            if (isError) Icon(
                imageVector = Icons.Filled.Error, contentDescription = "ErrorMessage Icon"
            )
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        supportingText = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level1),
                horizontalArrangement = if (isError) Arrangement.SpaceBetween else Arrangement.End
            ) {
                if (isError) CaptionTexts.Level3(errorMessage)

                CaptionTexts.Level3(text = stringCounter)
            }
        }
    )
}


/**
 * Text fields allow users to enter text into a UI. They typically appear in forms and dialogs.
 * Filled text fields have more visual emphasis than outlined text fields, making them stand out
 * when surrounded by other content and components.
 *
 * ![Filled text field image](https://developer.android.com/images/reference/androidx/compose/material3/filled-text-field.png)
 *
 * @param modifier the [Modifier] to be applied to this text field
 * @param value the input text to be shown in the text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label This is the text that will be shown on the top of the Text Field
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 * container
 * @param leadingIconDes This is the description of the Leading Icon
 * @param onLeadingIconClicked This is the function which will be called when the leading Icon is
 * clicked.
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 * container
 * @param trailingIconDes This is the description of the Trailing Icon
 * @param onTrailingIconClicked This is the function which will be called when the trailing icon is
 * clicked
 * @param visualTransformation transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines. The keyboard will be informed to not show the return key
 * as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines attribute will
 * be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this text field. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this text field in different states.
 * @param shape defines the shape of this text field's container
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.colors].
 * @param appTextFieldType This is the Type of the TextField refer to [AppTextFieldValidator] for reference
 * and to get all the relevant type go at [AppTextFieldType].
 * @param isValidText This sends the parent function if the text is valid or not
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 */
@Composable
fun AppTextField(
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    onLeadingIconClicked: () -> Unit = {},
    trailingIcon: ImageVector? = null,
    trailingIconDes: String? = null,
    onTrailingIconClicked: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = AppTheme.shape.level1,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    appTextFieldType: AppTextFieldValidator = AppTextFieldValidator(AppTextFieldType.Custom()),
    isValidText: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {

    // This variable keeps if the user is typing or not
    val isTyping = remember { mutableStateOf(false) }
    isTyping.value = value.isNotEmpty()

    // This formulates if the user Input is valid or not
    val isError = appTextFieldType.isInvalid(value, isTyping.value)

    // Formulating what will be the error Message of the text Field Type
    val errorMessage = appTextFieldType.getErrorMessage(value)

    // This variable contains the Counter for the String
    val stringCounter = appTextFieldType.getStringCounter(value)


    // Text Field Layout from Material 3
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isValidText(appTextFieldType.isTextValid(it))
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = AppTheme.customTypography.caption.level2,
        label = { BodyTexts.Level3(text = label) },
        leadingIcon = {
            if (leadingIcon != null) {
                AppIconButton(imageVector = leadingIcon, iconDesc = leadingIconDes) {
                    onLeadingIconClicked()
                }
            }
        },
        trailingIcon = {
            if (trailingIcon != null && !isError) {
                AppIconButton(imageVector = trailingIcon, iconDesc = trailingIconDes) {
                    onTrailingIconClicked()
                }
            }
            if (isError) Icon(
                imageVector = Icons.Filled.Error, contentDescription = "ErrorMessage Icon"
            )
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        supportingText = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level1),
                horizontalArrangement = if (isError) Arrangement.SpaceBetween else Arrangement.End
            ) {
                if (isError) CaptionTexts.Level3(errorMessage)

                CaptionTexts.Level3(text = stringCounter)
            }
        }
    )
}


/**
 * Text fields allow users to enter text into a UI. They typically appear in forms and dialogs.
 * Filled text fields have more visual emphasis than outlined text fields, making them stand out
 * when surrounded by other content and components.
 *
 * ![Filled text field image](https://developer.android.com/images/reference/androidx/compose/material3/filled-text-field.png)
 *
 * @param modifier the [Modifier] to be applied to this text field
 * @param value the input text to be shown in the text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label This is the text that will be shown on the top of the Text Field
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 * container
 * @param leadingIconDes This is the description of the Leading Icon
 * @param onLeadingIconClicked This is the function which will be called when the leading Icon is
 * clicked.
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 * container
 * @param trailingIconDes This is the description of the Trailing Icon
 * @param onTrailingIconClicked This is the function which will be called when the trailing icon is
 * clicked
 * @param visualTransformation transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines. The keyboard will be informed to not show the return key
 * as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines attribute will
 * be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this text field. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this text field in different states.
 * @param shape defines the shape of this text field's container
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.colors].
 * @param appTextFieldType This is the Type of the TextField refer to [AppTextFieldValidator] for reference
 * and to get all the relevant type go at [AppTextFieldType].
 * @param isValidText This sends the parent function if the text is valid or not
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 */
@Composable
fun AppTextField(
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    leadingIcon: Painter? = null,
    leadingIconDes: String? = null,
    onLeadingIconClicked: () -> Unit = {},
    trailingIcon: Painter? = null,
    trailingIconDes: String? = null,
    onTrailingIconClicked: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = AppTheme.shape.level1,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    appTextFieldType: AppTextFieldValidator = AppTextFieldValidator(AppTextFieldType.Custom()),
    isValidText: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {

    // This variable keeps if the user is typing or not
    val isTyping = remember { mutableStateOf(false) }
    isTyping.value = value.isNotEmpty()

    // This formulates if the user Input is valid or not
    val isError = appTextFieldType.isInvalid(value, isTyping.value)

    // Formulating what will be the error Message of the text Field Type
    val errorMessage = appTextFieldType.getErrorMessage(value)

    // This variable contains the Counter for the String
    val stringCounter = appTextFieldType.getStringCounter(value)


    // Text Field Layout from Material 3
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isValidText(appTextFieldType.isTextValid(it))
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = AppTheme.customTypography.caption.level2,
        label = { BodyTexts.Level3(text = label) },
        leadingIcon = {
            if (leadingIcon != null) {
                AppIconButton(painter = leadingIcon, iconDesc = leadingIconDes) {
                    onLeadingIconClicked()
                }
            }
        },
        trailingIcon = {
            if (trailingIcon != null && !isError) {
                AppIconButton(painter = trailingIcon, iconDesc = trailingIconDes) {
                    onTrailingIconClicked()
                }
            }
            if (isError) Icon(
                imageVector = Icons.Filled.Error, contentDescription = "ErrorMessage Icon"
            )
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        supportingText = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level1),
                horizontalArrangement = if (isError) Arrangement.SpaceBetween else Arrangement.End
            ) {
                if (isError) CaptionTexts.Level3(errorMessage)

                CaptionTexts.Level3(text = stringCounter)
            }
        }
    )
}


/**
 * Text fields allow users to enter text into a UI. They typically appear in forms and dialogs.
 * Filled text fields have more visual emphasis than outlined text fields, making them stand out
 * when surrounded by other content and components.
 *
 * ![Filled text field image](https://developer.android.com/images/reference/androidx/compose/material3/filled-text-field.png)
 *
 * @param modifier the [Modifier] to be applied to this text field
 * @param value the input text to be shown in the text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label This is the text that will be shown on the top of the Text Field
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 * container
 * @param leadingIconDes This is the description of the Leading Icon
 * @param onLeadingIconClicked This is the function which will be called when the leading Icon is
 * clicked.
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 * container
 * @param trailingIconDes This is the description of the Trailing Icon
 * @param onTrailingIconClicked This is the function which will be called when the trailing icon is
 * clicked
 * @param visualTransformation transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines. The keyboard will be informed to not show the return key
 * as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines attribute will
 * be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this text field. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this text field in different states.
 * @param shape defines the shape of this text field's container
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.colors].
 * @param appTextFieldType This is the Type of the TextField refer to [AppTextFieldValidator] for reference
 * and to get all the relevant type go at [AppTextFieldType].
 * @param isValidText This sends the parent function if the text is valid or not
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 */
@Composable
fun AppTextField(
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    leadingIcon: Painter? = null,
    leadingIconDes: String? = null,
    onLeadingIconClicked: () -> Unit = {},
    trailingIcon: ImageVector? = null,
    trailingIconDes: String? = null,
    onTrailingIconClicked: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = AppTheme.shape.level1,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    appTextFieldType: AppTextFieldValidator = AppTextFieldValidator(AppTextFieldType.Custom()),
    isValidText: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {

    // This variable keeps if the user is typing or not
    val isTyping = remember { mutableStateOf(false) }
    isTyping.value = value.isNotEmpty()

    // This formulates if the user Input is valid or not
    val isError = appTextFieldType.isInvalid(value, isTyping.value)

    // Formulating what will be the error Message of the text Field Type
    val errorMessage = appTextFieldType.getErrorMessage(value)

    // This variable contains the Counter for the String
    val stringCounter = appTextFieldType.getStringCounter(value)


    // Text Field Layout from Material 3
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isValidText(appTextFieldType.isTextValid(it))
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = AppTheme.customTypography.caption.level2,
        label = { BodyTexts.Level3(text = label) },
        leadingIcon = {
            if (leadingIcon != null) {
                AppIconButton(painter = leadingIcon, iconDesc = leadingIconDes) {
                    onLeadingIconClicked()
                }
            }
        },
        trailingIcon = {
            if (trailingIcon != null && !isError) {
                AppIconButton(imageVector = trailingIcon, iconDesc = trailingIconDes) {
                    onTrailingIconClicked()
                }
            }
            if (isError) Icon(
                imageVector = Icons.Filled.Error, contentDescription = "ErrorMessage Icon"
            )
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        supportingText = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level1),
                horizontalArrangement = if (isError) Arrangement.SpaceBetween else Arrangement.End
            ) {
                if (isError) CaptionTexts.Level3(errorMessage)

                CaptionTexts.Level3(text = stringCounter)
            }
        }
    )
}


/**
 * Text fields allow users to enter text into a UI. They typically appear in forms and dialogs.
 * Filled text fields have more visual emphasis than outlined text fields, making them stand out
 * when surrounded by other content and components.
 *
 * ![Filled text field image](https://developer.android.com/images/reference/androidx/compose/material3/filled-text-field.png)
 *
 * @param modifier the [Modifier] to be applied to this text field
 * @param value the input text to be shown in the text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label This is the text that will be shown on the top of the Text Field
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 * container
 * @param leadingIconDes This is the description of the Leading Icon
 * @param onLeadingIconClicked This is the function which will be called when the leading Icon is
 * clicked.
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 * container
 * @param trailingIconDes This is the description of the Trailing Icon
 * @param onTrailingIconClicked This is the function which will be called when the trailing icon is
 * clicked
 * @param visualTransformation transforms the visual representation of the input [value]
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines. The keyboard will be informed to not show the return key
 * as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines attribute will
 * be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this text field. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this text field in different states.
 * @param shape defines the shape of this text field's container
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.colors].
 * @param appTextFieldType This is the Type of the TextField refer to [AppTextFieldValidator] for reference
 * and to get all the relevant type go at [AppTextFieldType].
 * @param isValidText This sends the parent function if the text is valid or not
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 */
@Composable
fun AppTextField(
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    onLeadingIconClicked: () -> Unit = {},
    trailingIcon: Painter? = null,
    trailingIconDes: String? = null,
    onTrailingIconClicked: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = AppTheme.shape.level1,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    appTextFieldType: AppTextFieldValidator = AppTextFieldValidator(AppTextFieldType.Custom()),
    isValidText: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {

    // This variable keeps if the user is typing or not
    val isTyping = remember { mutableStateOf(false) }
    isTyping.value = value.isNotEmpty()

    // This formulates if the user Input is valid or not
    val isError = appTextFieldType.isInvalid(value, isTyping.value)

    // Formulating what will be the error Message of the text Field Type
    val errorMessage = appTextFieldType.getErrorMessage(value)

    // This variable contains the Counter for the String
    val stringCounter = appTextFieldType.getStringCounter(value)


    // Text Field Layout from Material 3
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isValidText(appTextFieldType.isTextValid(it))
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = AppTheme.customTypography.caption.level2,
        label = { BodyTexts.Level3(text = label) },
        leadingIcon = {
            if (leadingIcon != null) {
                AppIconButton(imageVector = leadingIcon, iconDesc = leadingIconDes) {
                    onLeadingIconClicked()
                }
            }
        },
        trailingIcon = {
            if (trailingIcon != null && !isError) {
                AppIconButton(painter = trailingIcon, iconDesc = trailingIconDes) {
                    onTrailingIconClicked()
                }
            }
            if (isError) Icon(
                imageVector = Icons.Filled.Error, contentDescription = "ErrorMessage Icon"
            )
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        supportingText = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level1),
                horizontalArrangement = if (isError) Arrangement.SpaceBetween else Arrangement.End
            ) {
                if (isError) CaptionTexts.Level3(errorMessage)

                CaptionTexts.Level3(text = stringCounter)
            }
        }
    )
}