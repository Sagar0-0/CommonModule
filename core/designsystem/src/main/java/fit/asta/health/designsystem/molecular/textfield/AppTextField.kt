package fit.asta.health.designsystem.molecular.textfield

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
    TextField(value = value,
        onValueChange = {
            onValueChange(it)
            isValidText(appTextFieldType.isTextValid(it))
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = AppTheme.customTypography.caption.level2,
        label = { BodyTexts.Level3(text = label) },
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (trailingIcon != null && !isError) {
                trailingIcon()
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
        })
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
    TextField(value = value,
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
        })
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
    TextField(value = value,
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
        })
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
    TextField(value = value,
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
        })
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
    TextField(value = value,
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
        })
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
    TextField(value = value,
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
        })
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
    TextField(value = value,
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
        })
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
    TextField(value = value,
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
        })
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
    TextField(value = value,
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
        })
}


/**
 * Basic composable that enables users to edit text via hardware or software keyboard, but
 * provides no decorations like hint or placeholder.
 *
 * Whenever the user edits the text, [onValueChange] is called with the most up to date state
 * represented by [String] with which developer is expected to update their state.
 *
 * Unlike [TextFieldValue] overload, this composable does not let the developer to control
 * selection, cursor and text composition information. Please check [TextFieldValue] and
 * corresponding [BasicTextField] overload for more information.
 *
 * It is crucial that the value provided in the [onValueChange] is fed back into [BasicTextField] in
 * order to have the final state of the text being displayed.
 *
 * Example usage:
 *
 * Please keep in mind that [onValueChange] is useful to be informed about the latest state of the
 * text input by users, however it is generally not recommended to modify the value that you get
 * via [onValueChange] callback. Any change to this value may result in a context reset and end
 * up with input session restart. Such a scenario would cause glitches in the UI or text input
 * experience for users.
 *
 * This composable provides basic text editing functionality, however does not include any
 * decorations such as borders, hints/placeholder. A design system based implementation such as
 * Material Design Filled text field is typically what is needed to cover most of the needs. This
 * composable is designed to be used when a custom implementation for different design system is
 * needed.
 *
 * For example, if you need to include a placeholder in your TextField, you can write a composable
 * using the decoration box like this:
 *
 * If you want to add decorations to your text field, such as icon or similar, and increase the
 * hit target area, use the decoration box:
 *
 * In order to create formatted text field, for example for entering a phone number or a social
 * security number, use a [visualTransformation] parameter. Below is the example of the text field
 * for entering a credit card number:
 *
 * @param value the input [String] text to be shown in the text field
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param modifier optional [Modifier] for this text field.
 * @param enabled controls the enabled state of the [BasicTextField]. When `false`, the text
 * field will be neither editable nor focusable, the input of the text field will not be selectable
 * @param readOnly controls the editable state of the [BasicTextField]. When `true`, the text
 * field can not be modified, however, a user can focus it and copy text from it. Read-only text
 * fields are usually used to display pre-filled forms that user can not edit
 * @param textStyle Style configuration that applies at character level such as color, font etc.
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when set to true, this text field becomes a single horizontally scrolling
 * text field instead of wrapping onto multiple lines. The keyboard will be informed to not show
 * the return key as the [ImeAction]. [maxLines] and [minLines] are ignored as both are
 * automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param visualTransformation The visual transformation filter for changing the visual
 * representation of the input. By default no visual transformation is applied.
 * @param onTextLayout Callback that is executed when a new text layout is calculated. A
 * [TextLayoutResult] object that callback provides contains paragraph information, size of the
 * text, baselines and other details. The callback can be used to add additional decoration or
 * functionality to the text. For example, to draw a cursor or selection around the text.
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this TextField. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this TextField in different [Interaction]s.
 * @param cursorBrush [Brush] to paint cursor with. If [SolidColor] with [Color.Unspecified]
 * provided, there will be no cursor drawn
 * @param decorationBox Composable lambda that allows to add decorations around text field, such
 * as icon, placeholder, helper messages or similar, and automatically increase the hit target area
 * of the text field. To allow you to control the placement of the inner text field relative to your
 * decorations, the text field implementation will pass in a framework-controlled composable
 * parameter "innerTextField" to the decorationBox lambda you provide. You must call
 * innerTextField exactly once.
 */
@Composable
fun AppBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit = @Composable { innerTextField -> innerTextField() },
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = decorationBox
    )
}