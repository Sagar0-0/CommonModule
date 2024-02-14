package fit.asta.health.designsystem.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.CaptionTexts


// Preview Function
@Preview("Light Button")
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        Surface {
            Column {
                AppTextButton(
                    textToShow = "Enabled Button",
                    leadingIcon = Icons.Default.Person,
                    trailingIcon = Icons.Default.Person
                ) {}

                AppTextButton(
                    textToShow = "Disabled Button",
                    enabled = false,
                    leadingIcon = Icons.Default.Person,
                    trailingIcon = Icons.Default.Person
                ) {}
            }
        }
    }
}


/**
 * ![Text button image](https://developer.android.com/images/reference/androidx/compose/material3/text-button.png)
 *
 * Text buttons are typically used for less-pronounced actions, including those located in dialogs
 * and cards. In cards, text buttons help maintain an emphasis on card content. Text buttons are
 * used for the lowest priority actions, especially when presenting multiple options.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.textButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * A TextButton typically has no elevation, and the default value is `null`. See [ElevatedButton]
 * for a button with elevation.
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 * @param content This is the content inside the Button
 */
@Composable
fun AppTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = AppTheme.shape.level1,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.primary,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level3,
        top = AppTheme.spacing.level1,
        end = AppTheme.spacing.level3,
        bottom = AppTheme.spacing.level1
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable (RowScope.() -> Unit)
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        shape = shape,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
        content = content
    )
}


/**
 * ![Text button image](https://developer.android.com/images/reference/androidx/compose/material3/text-button.png)
 *
 * Text buttons are typically used for less-pronounced actions, including those located in dialogs
 * and cards. In cards, text buttons help maintain an emphasis on card content. Text buttons are
 * used for the lowest priority actions, especially when presenting multiple options.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow This is the text to be shown in the Button
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.textButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * A TextButton typically has no elevation, and the default value is `null`. See [ElevatedButton]
 * for a button with elevation.
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppTextButton(
    textToShow: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = AppTheme.shape.level1,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.primary,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level3,
        top = AppTheme.spacing.level1,
        end = AppTheme.spacing.level3,
        bottom = AppTheme.spacing.level1
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    AppOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        shape = shape,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    ) {
        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.primary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )
    }
}


/**
 * ![Text button image](https://developer.android.com/images/reference/androidx/compose/material3/text-button.png)
 *
 * Text buttons are typically used for less-pronounced actions, including those located in dialogs
 * and cards. In cards, text buttons help maintain an emphasis on card content. Text buttons are
 * used for the lowest priority actions, especially when presenting multiple options.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow This is the text to be shown in the Button
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.textButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * A TextButton typically has no elevation, and the default value is `null`. See [ElevatedButton]
 * for a button with elevation.
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppTextButton(
    textToShow: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.primary,
    shape: Shape = AppTheme.shape.level1,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.primary,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level3,
        top = AppTheme.spacing.level1,
        end = AppTheme.spacing.level3,
        bottom = AppTheme.spacing.level1
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        shape = shape,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    ) {

        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level0),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.primary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )
    }
}


/**
 * ![Text button image](https://developer.android.com/images/reference/androidx/compose/material3/text-button.png)
 *
 * Text buttons are typically used for less-pronounced actions, including those located in dialogs
 * and cards. In cards, text buttons help maintain an emphasis on card content. Text buttons are
 * used for the lowest priority actions, especially when presenting multiple options.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow This is the text to be shown in the Button
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.textButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * A TextButton typically has no elevation, and the default value is `null`. See [ElevatedButton]
 * for a button with elevation.
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppTextButton(
    textToShow: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: Painter? = null,
    leadingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.primary,
    shape: Shape = AppTheme.shape.level1,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.primary,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level3,
        top = AppTheme.spacing.level1,
        end = AppTheme.spacing.level3,
        bottom = AppTheme.spacing.level1
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        shape = shape,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    ) {

        if (leadingIcon != null) {
            Icon(
                painter = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level0),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.primary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )
    }
}


/**
 * ![Text button image](https://developer.android.com/images/reference/androidx/compose/material3/text-button.png)
 *
 * Text buttons are typically used for less-pronounced actions, including those located in dialogs
 * and cards. In cards, text buttons help maintain an emphasis on card content. Text buttons are
 * used for the lowest priority actions, especially when presenting multiple options.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow This is the text to be shown in the Button
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.textButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * A TextButton typically has no elevation, and the default value is `null`. See [ElevatedButton]
 * for a button with elevation.
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppTextButton(
    textToShow: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    trailingIconDes: String? = null,
    trailingIcon: Painter? = null,
    iconTint: Color = AppTheme.colors.primary,
    shape: Shape = AppTheme.shape.level1,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.primary,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level3,
        top = AppTheme.spacing.level1,
        end = AppTheme.spacing.level3,
        bottom = AppTheme.spacing.level1
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        shape = shape,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    ) {

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.primary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                painter = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.level0),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }
}


/**
 * ![Text button image](https://developer.android.com/images/reference/androidx/compose/material3/text-button.png)
 *
 * Text buttons are typically used for less-pronounced actions, including those located in dialogs
 * and cards. In cards, text buttons help maintain an emphasis on card content. Text buttons are
 * used for the lowest priority actions, especially when presenting multiple options.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow This is the text to be shown in the Button
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.textButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * A TextButton typically has no elevation, and the default value is `null`. See [ElevatedButton]
 * for a button with elevation.
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppTextButton(
    textToShow: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    trailingIconDes: String? = null,
    trailingIcon: ImageVector? = null,
    iconTint: Color = AppTheme.colors.primary,
    shape: Shape = AppTheme.shape.level1,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.primary,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level3,
        top = AppTheme.spacing.level1,
        end = AppTheme.spacing.level3,
        bottom = AppTheme.spacing.level1
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        shape = shape,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    ) {

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.primary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.level0),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }
}


/**
 * ![Text button image](https://developer.android.com/images/reference/androidx/compose/material3/text-button.png)
 *
 * Text buttons are typically used for less-pronounced actions, including those located in dialogs
 * and cards. In cards, text buttons help maintain an emphasis on card content. Text buttons are
 * used for the lowest priority actions, especially when presenting multiple options.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow This is the text to be shown in the Button
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.textButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * A TextButton typically has no elevation, and the default value is `null`. See [ElevatedButton]
 * for a button with elevation.
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppTextButton(
    textToShow: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    trailingIcon: ImageVector? = null,
    trailingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.primary,
    shape: Shape = AppTheme.shape.level1,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.primary,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level3,
        top = AppTheme.spacing.level1,
        end = AppTheme.spacing.level3,
        bottom = AppTheme.spacing.level1
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        shape = shape,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    ) {

        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level0),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.primary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.level0),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }
}


/**
 * ![Text button image](https://developer.android.com/images/reference/androidx/compose/material3/text-button.png)
 *
 * Text buttons are typically used for less-pronounced actions, including those located in dialogs
 * and cards. In cards, text buttons help maintain an emphasis on card content. Text buttons are
 * used for the lowest priority actions, especially when presenting multiple options.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow This is the text to be shown in the Button
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.textButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * A TextButton typically has no elevation, and the default value is `null`. See [ElevatedButton]
 * for a button with elevation.
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppTextButton(
    textToShow: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: Painter? = null,
    leadingIconDes: String? = null,
    trailingIcon: Painter? = null,
    trailingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.primary,
    shape: Shape = AppTheme.shape.level1,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.primary,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level3,
        top = AppTheme.spacing.level1,
        end = AppTheme.spacing.level3,
        bottom = AppTheme.spacing.level1
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        shape = shape,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    ) {

        if (leadingIcon != null) {
            Icon(
                painter = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level0),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.primary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                painter = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.level0),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }
}


/**
 * ![Text button image](https://developer.android.com/images/reference/androidx/compose/material3/text-button.png)
 *
 * Text buttons are typically used for less-pronounced actions, including those located in dialogs
 * and cards. In cards, text buttons help maintain an emphasis on card content. Text buttons are
 * used for the lowest priority actions, especially when presenting multiple options.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow This is the text to be shown in the Button
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.textButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * A TextButton typically has no elevation, and the default value is `null`. See [ElevatedButton]
 * for a button with elevation.
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppTextButton(
    textToShow: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: Painter? = null,
    leadingIconDes: String? = null,
    trailingIcon: ImageVector? = null,
    trailingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.primary,
    shape: Shape = AppTheme.shape.level1,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.primary,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level3,
        top = AppTheme.spacing.level1,
        end = AppTheme.spacing.level3,
        bottom = AppTheme.spacing.level1
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        shape = shape,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    ) {

        if (leadingIcon != null) {
            Icon(
                painter = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level0),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.primary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.level0),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }
}


/**
 * ![Text button image](https://developer.android.com/images/reference/androidx/compose/material3/text-button.png)
 *
 * Text buttons are typically used for less-pronounced actions, including those located in dialogs
 * and cards. In cards, text buttons help maintain an emphasis on card content. Text buttons are
 * used for the lowest priority actions, especially when presenting multiple options.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow This is the text to be shown in the Button
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.textButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * A TextButton typically has no elevation, and the default value is `null`. See [ElevatedButton]
 * for a button with elevation.
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppTextButton(
    textToShow: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    trailingIcon: Painter? = null,
    trailingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.primary,
    shape: Shape = AppTheme.shape.level1,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.primary,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level3,
        top = AppTheme.spacing.level1,
        end = AppTheme.spacing.level3,
        bottom = AppTheme.spacing.level1
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        shape = shape,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    ) {

        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level0),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.primary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                painter = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.level0),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }
}