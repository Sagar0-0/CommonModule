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
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
                AppElevatedButton(
                    onClick = {},
                    textToShow = "Enabled Button",
                    leadingIcon = Icons.Default.Person
                )

                AppElevatedButton(
                    enabled = false,
                    onClick = {},
                    textToShow = "Disabled Button",
                    leadingIcon = Icons.Default.Person
                )
            }
        }
    }
}


/**
 * ![Elevated button image](https://developer.android.com/images/reference/androidx/compose/material3/elevated-button.png)
 *
 * Elevated buttons are high-emphasis buttons that are essentially [FilledTonalButton]s with a
 * shadow. To prevent shadow creep, only use them when absolutely necessary, such as when the button
 * requires visual separation from patterned container.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.elevatedButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonDefaults.elevatedButtonElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 * @param content This contains the Composable to be shown inside the Button
 */
@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.elevatedShape,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        disabledContainerColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level1),
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(
        defaultElevation = AppTheme.elevation.level1,
        pressedElevation = AppTheme.elevation.level1,
        focusedElevation = AppTheme.elevation.level1,
        hoveredElevation = AppTheme.elevation.level2,
        disabledElevation = AppTheme.elevation.level0
    ),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level4,
        top = AppTheme.spacing.level2,
        end = AppTheme.spacing.level4,
        bottom = AppTheme.spacing.level2
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    ElevatedButton(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource,
        content
    )
}


/**
 * ![Elevated button image](https://developer.android.com/images/reference/androidx/compose/material3/elevated-button.png)
 *
 * Elevated buttons are high-emphasis buttons that are essentially [FilledTonalButton]s with a
 * shadow. To prevent shadow creep, only use them when absolutely necessary, such as when the button
 * requires visual separation from patterned container.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow This is the text which needs to be shown inside the Elevated Button
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.elevatedButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonDefaults.elevatedButtonElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    shape: Shape = ButtonDefaults.elevatedShape,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        disabledContainerColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level1),
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(
        defaultElevation = AppTheme.elevation.level1,
        pressedElevation = AppTheme.elevation.level1,
        focusedElevation = AppTheme.elevation.level1,
        hoveredElevation = AppTheme.elevation.level2,
        disabledElevation = AppTheme.elevation.level0
    ),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level4,
        top = AppTheme.spacing.level2,
        end = AppTheme.spacing.level4,
        bottom = AppTheme.spacing.level2
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        border = border,
        shape = shape
    ) {
        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.onPrimary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )
    }
}


/**
 * ![Elevated button image](https://developer.android.com/images/reference/androidx/compose/material3/elevated-button.png)
 *
 * Elevated buttons are high-emphasis buttons that are essentially [FilledTonalButton]s with a
 * shadow. To prevent shadow creep, only use them when absolutely necessary, such as when the button
 * requires visual separation from patterned container.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow The button text content to be shown content.
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param iconTint This is the tint of the Icon.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.elevatedButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonDefaults.elevatedButtonElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    shape: Shape = ButtonDefaults.elevatedShape,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        disabledContainerColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level1),
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(
        defaultElevation = AppTheme.elevation.level1,
        pressedElevation = AppTheme.elevation.level1,
        focusedElevation = AppTheme.elevation.level1,
        hoveredElevation = AppTheme.elevation.level2,
        disabledElevation = AppTheme.elevation.level0
    ),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level4,
        top = AppTheme.spacing.level2,
        end = AppTheme.spacing.level4,
        bottom = AppTheme.spacing.level2
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        border = border,
        shape = shape
    ) {

        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.onPrimary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )
    }
}


/**
 * ![Elevated button image](https://developer.android.com/images/reference/androidx/compose/material3/elevated-button.png)
 *
 * Elevated buttons are high-emphasis buttons that are essentially [FilledTonalButton]s with a
 * shadow. To prevent shadow creep, only use them when absolutely necessary, such as when the button
 * requires visual separation from patterned container.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow The button text content to be shown content.
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param iconTint This is the tint of the Icon.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.elevatedButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonDefaults.elevatedButtonElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */

@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    leadingIcon: Painter? = null,
    leadingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    shape: Shape = ButtonDefaults.elevatedShape,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        disabledContainerColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level1),
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(
        defaultElevation = AppTheme.elevation.level1,
        pressedElevation = AppTheme.elevation.level1,
        focusedElevation = AppTheme.elevation.level1,
        hoveredElevation = AppTheme.elevation.level2,
        disabledElevation = AppTheme.elevation.level0
    ),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level4,
        top = AppTheme.spacing.level2,
        end = AppTheme.spacing.level4,
        bottom = AppTheme.spacing.level2
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        border = border,
        shape = shape
    ) {

        if (leadingIcon != null) {
            Icon(
                painter = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.onPrimary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )
    }
}


/**
 * ![Elevated button image](https://developer.android.com/images/reference/androidx/compose/material3/elevated-button.png)
 *
 * Elevated buttons are high-emphasis buttons that are essentially [FilledTonalButton]s with a
 * shadow. To prevent shadow creep, only use them when absolutely necessary, such as when the button
 * requires visual separation from patterned container.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow The button text content to be shown content.
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param iconTint This is the tint of the Icon.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.elevatedButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonDefaults.elevatedButtonElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    trailingIconDes: String? = null,
    trailingIcon: ImageVector? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    shape: Shape = ButtonDefaults.elevatedShape,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        disabledContainerColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level1),
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(
        defaultElevation = AppTheme.elevation.level1,
        pressedElevation = AppTheme.elevation.level1,
        focusedElevation = AppTheme.elevation.level1,
        hoveredElevation = AppTheme.elevation.level2,
        disabledElevation = AppTheme.elevation.level0
    ),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level4,
        top = AppTheme.spacing.level2,
        end = AppTheme.spacing.level4,
        bottom = AppTheme.spacing.level2
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        border = border,
        shape = shape
    ) {

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.onPrimary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }
}


/**
 * ![Elevated button image](https://developer.android.com/images/reference/androidx/compose/material3/elevated-button.png)
 *
 * Elevated buttons are high-emphasis buttons that are essentially [FilledTonalButton]s with a
 * shadow. To prevent shadow creep, only use them when absolutely necessary, such as when the button
 * requires visual separation from patterned container.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow The button text content to be shown content.
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param iconTint This is the tint of the Icon.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.elevatedButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonDefaults.elevatedButtonElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    trailingIconDes: String? = null,
    trailingIcon: Painter? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    shape: Shape = ButtonDefaults.elevatedShape,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        disabledContainerColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level1),
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(
        defaultElevation = AppTheme.elevation.level1,
        pressedElevation = AppTheme.elevation.level1,
        focusedElevation = AppTheme.elevation.level1,
        hoveredElevation = AppTheme.elevation.level2,
        disabledElevation = AppTheme.elevation.level0
    ),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level4,
        top = AppTheme.spacing.level2,
        end = AppTheme.spacing.level4,
        bottom = AppTheme.spacing.level2
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        border = border,
        shape = shape
    ) {

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.onPrimary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                painter = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }
}


/**
 * ![Elevated button image](https://developer.android.com/images/reference/androidx/compose/material3/elevated-button.png)
 *
 * Elevated buttons are high-emphasis buttons that are essentially [FilledTonalButton]s with a
 * shadow. To prevent shadow creep, only use them when absolutely necessary, such as when the button
 * requires visual separation from patterned container.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow The button text content to be shown content.
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param iconTint This is the tint of the Icon.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.elevatedButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonDefaults.elevatedButtonElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    leadingIcon: Painter? = null,
    leadingIconDes: String? = null,
    trailingIcon: Painter? = null,
    trailingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    shape: Shape = ButtonDefaults.elevatedShape,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        disabledContainerColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level1),
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(
        defaultElevation = AppTheme.elevation.level1,
        pressedElevation = AppTheme.elevation.level1,
        focusedElevation = AppTheme.elevation.level1,
        hoveredElevation = AppTheme.elevation.level2,
        disabledElevation = AppTheme.elevation.level0
    ),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level4,
        top = AppTheme.spacing.level2,
        end = AppTheme.spacing.level4,
        bottom = AppTheme.spacing.level2
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        border = border,
        shape = shape
    ) {

        if (leadingIcon != null) {
            Icon(
                painter = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.onPrimary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                painter = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }
}


/**
 * ![Elevated button image](https://developer.android.com/images/reference/androidx/compose/material3/elevated-button.png)
 *
 * Elevated buttons are high-emphasis buttons that are essentially [FilledTonalButton]s with a
 * shadow. To prevent shadow creep, only use them when absolutely necessary, such as when the button
 * requires visual separation from patterned container.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow The button text content to be shown content.
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param iconTint This is the tint of the Icon.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.elevatedButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonDefaults.elevatedButtonElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    trailingIcon: ImageVector? = null,
    trailingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    shape: Shape = ButtonDefaults.elevatedShape,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        disabledContainerColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level1),
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(
        defaultElevation = AppTheme.elevation.level1,
        pressedElevation = AppTheme.elevation.level1,
        focusedElevation = AppTheme.elevation.level1,
        hoveredElevation = AppTheme.elevation.level2,
        disabledElevation = AppTheme.elevation.level0
    ),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level4,
        top = AppTheme.spacing.level2,
        end = AppTheme.spacing.level4,
        bottom = AppTheme.spacing.level2
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        border = border,
        shape = shape
    ) {

        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.onPrimary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }
}


/**
 * ![Elevated button image](https://developer.android.com/images/reference/androidx/compose/material3/elevated-button.png)
 *
 * Elevated buttons are high-emphasis buttons that are essentially [FilledTonalButton]s with a
 * shadow. To prevent shadow creep, only use them when absolutely necessary, such as when the button
 * requires visual separation from patterned container.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow The button text content to be shown content.
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param iconTint This is the tint of the Icon.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.elevatedButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonDefaults.elevatedButtonElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    trailingIcon: Painter? = null,
    trailingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    shape: Shape = ButtonDefaults.elevatedShape,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        disabledContainerColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level1),
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(
        defaultElevation = AppTheme.elevation.level1,
        pressedElevation = AppTheme.elevation.level1,
        focusedElevation = AppTheme.elevation.level1,
        hoveredElevation = AppTheme.elevation.level2,
        disabledElevation = AppTheme.elevation.level0
    ),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level4,
        top = AppTheme.spacing.level2,
        end = AppTheme.spacing.level4,
        bottom = AppTheme.spacing.level2
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        border = border,
        shape = shape
    ) {

        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.onPrimary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                painter = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }
}

/**
 * ![Elevated button image](https://developer.android.com/images/reference/androidx/compose/material3/elevated-button.png)
 *
 * Elevated buttons are high-emphasis buttons that are essentially [FilledTonalButton]s with a
 * shadow. To prevent shadow creep, only use them when absolutely necessary, such as when the button
 * requires visual separation from patterned container.
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow The button text content to be shown content.
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param iconTint This is the tint of the Icon.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.elevatedButtonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonDefaults.elevatedButtonElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param onClick called when this button is clicked
 */
@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    leadingIcon: Painter? = null,
    leadingIconDes: String? = null,
    trailingIcon: ImageVector? = null,
    trailingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    shape: Shape = ButtonDefaults.elevatedShape,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        disabledContainerColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level1),
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(
        defaultElevation = AppTheme.elevation.level1,
        pressedElevation = AppTheme.elevation.level1,
        focusedElevation = AppTheme.elevation.level1,
        hoveredElevation = AppTheme.elevation.level2,
        disabledElevation = AppTheme.elevation.level0
    ),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = AppTheme.spacing.level4,
        top = AppTheme.spacing.level2,
        end = AppTheme.spacing.level4,
        bottom = AppTheme.spacing.level2
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        border = border,
        shape = shape
    ) {

        if (leadingIcon != null) {
            Icon(
                painter = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.onPrimary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }
}