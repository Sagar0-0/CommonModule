package fit.asta.health.designsystem.components.generic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import fit.asta.health.designsystem.components.generic.AppButtons.AppFAB
import fit.asta.health.designsystem.components.generic.AppButtons.AppIconButton
import fit.asta.health.designsystem.components.generic.AppButtons.AppOutlinedButton
import fit.asta.health.designsystem.components.generic.AppButtons.AppRadioButton
import fit.asta.health.designsystem.components.generic.AppButtons.AppStandardButton
import fit.asta.health.designsystem.components.generic.AppButtons.AppTextButton
import fit.asta.health.designsystem.components.generic.AppButtons.AppToggleButton

/** [AppButtons] object contains several custom composable functions for creating different types
 * of buttons in Jetpack Compose.
 * List of all Methods in the object
 * [AppStandardButton]
 * [AppFAB]
 * [AppRadioButton]
 * [AppToggleButton]
 * [AppIconButton]
 * [AppTextButton]
 * [AppOutlinedButton]
 * */

object AppButtons {

    /** [AppStandardButton] is default button for the app.
     * @param modifier the [Modifier] to be applied to this button
     * @param shape defines the shape of this button's container
     * @param enabled controls the enabled state of this button. When `false`, this component will not
     * respond to user input, and it will appear visually disabled and disabled to accessibility
     * services.
     * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
     * states. See [ButtonDefaults.buttonColors].
     * @param contentPadding the spacing values to apply internally between the container and the
     * content
     * @param onClick called when this button is clicked
     */

    @Composable
    fun AppStandardButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
        shape: Shape = MaterialTheme.shapes.medium,
        content: @Composable RowScope.() -> Unit,
    ) {
        Button(
            modifier = modifier,
            enabled = enabled,
            colors = colors,
            contentPadding = contentPadding,
            shape = shape,
            onClick = onClick,
            content = content,
        )
    }

    /**[AppFAB] function is a custom composable function used to create a floating action button (FAB).
     * @param modifier the [Modifier] to be applied to this button
     * @param shape defines the shape of this FAB's container
     * @param containerColor the color used for the background of this FAB.
     * @param contentColor the preferred color for content inside this FAB.
     * @param onClick called when this FAB is clicked
     * @param content the content of this FAB, typically an [Icon]
     * */

    @Composable
    fun AppFAB(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        shape: Shape = CircleShape,
        containerColor: Color = MaterialTheme.colorScheme.primary,
        contentColor: Color = MaterialTheme.colorScheme.onPrimary,
        content: @Composable () -> Unit,
    ) {
        FloatingActionButton(
            modifier = modifier,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            onClick = onClick,
            content = content,
        )
    }


    /** [AppRadioButton] buttons allow users to select one option from a set.
     * @param modifier the [Modifier] to be applied to this radio button.
     * @param colors [RadioButtonColors] that will be used to resolve the color used for this radio
     * button.
     * @param selected whether this radio button is selected or not.
     * @param onClick called when this radio button is clicked.
     */

    @Composable
    fun AppRadioButton(
        selected: Boolean,
        modifier: Modifier = Modifier,
        colors: RadioButtonColors = RadioButtonDefaults.colors(),
        onClick: (() -> Unit)?,
    ) {
        RadioButton(
            modifier = modifier,
            selected = selected,
            colors = colors,
            onClick = onClick,
        )
    }

    /**[AppToggleButton] composable is a custom implementation of a toggle button.
     * @param checked whether or not this switch is checked
     * @param onCheckedChange called when this switch is clicked.
     * @param modifier the [Modifier] to be applied to this switch
     * @param thumbContent content that will be drawn inside the thumb, expected to measure
     * [SwitchDefaults.IconSize]
     * @param enabled controls the enabled state of this switch. When `false`, this component will not
     * respond to user input, and it will appear visually disabled and disabled to accessibility
     * services.
     * @param colors [SwitchColors] that will be used to resolve the colors used for this switch in
     * different states. See [SwitchDefaults.colors].
     * */

    @Composable
    fun AppToggleButton(
        checked: Boolean,
        modifier: Modifier = Modifier,
        onCheckedChange: ((Boolean) -> Unit)?,
        enabled: Boolean = true,
        colors: SwitchColors = SwitchDefaults.colors(),
        thumbContent: (@Composable () -> Unit)? = null,
    ) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier,
            thumbContent = thumbContent,
            enabled = enabled,
            colors = colors
        )
    }

    /** [AppIconButton] is a custom Composable function that provides a customized version of the IconButton.
     * @param onClick called when this icon button is clicked
     * @param modifier the [Modifier] to be applied to this icon button
     * @param enabled controls the enabled state of this icon button.
     * @param colors [IconButtonColors] that will be used to resolve the colors used for this icon
     * button.
     * @param content the content of this icon button
     * */

    @Composable
    fun AppIconButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        colors: IconButtonColors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface),
        content: @Composable () -> Unit,
    ) {
        IconButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = colors,
            content = content
        )
    }

    /** [AppTextButton] is a custom composable function that wraps the standard TextButton composable.
     * @param onClick called when this button is clicked
     * @param modifier the [Modifier] to be applied to this button
     * @param enabled controls the enabled state of this button.
     * @param shape defines the shape of this button's container, border (when [border] is not null),
     * and shadow (when using [elevation])
     * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
     * states. See [ButtonDefaults.textButtonColors].
     * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
     * states.
     * @param border the border to draw around the container of this button
     * @param contentPadding the spacing values to apply internally between the container and the
     * @param content Content inside the Text button
     * */

    @Composable
    fun AppTextButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        shape: Shape = ButtonDefaults.textShape,
        colors: ButtonColors = ButtonDefaults.textButtonColors(),
        elevation: ButtonElevation? = null,
        border: BorderStroke? = null,
        contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
        content: @Composable RowScope.() -> Unit,
    ) {
        TextButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            contentPadding = contentPadding,
            content = content
        )
    }

    /**
     * @param onClick called when this button is clicked
     * @param modifier the [Modifier] to be applied to this button
     * @param enabled controls the enabled state of this button.
     * @param shape defines the shape of this button's container
     * @param colors [ButtonColors] that will be used to resolve the colors for this button.
     * @param elevation [ButtonElevation] used to resolve the elevation for this button.
     * @param border the border to draw around the container of this button. Pass `null` for no border.
     * @param contentPadding the spacing values to apply internally between the container and the
     * @param content Content inside the Outlined button
     */

    @Composable
    fun AppOutlinedButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        shape: Shape = ButtonDefaults.outlinedShape,
        colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
        elevation: ButtonElevation? = null,
        border: BorderStroke? = ButtonDefaults.outlinedButtonBorder,
        contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
        content: @Composable RowScope.() -> Unit,
    ) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            contentPadding = contentPadding,
            content = content
        )
    }

    /** [AppCheckBox] allow users to select one or more items from a set. Checkboxes can turn an option on
     * or off.
     * @param checked whether this checkbox is checked or unchecked
     * @param onCheckedChange called when this checkbox is clicked.
     * @param modifier the [Modifier] to be applied to this checkbox
     * @param enabled controls the enabled state of this checkbox.
     * @param colors [CheckboxColors] that will be used to resolve the colors used for this checkbox in
     * different states.*/

    @Composable
    fun AppCheckBox(
        checked: Boolean,
        onCheckedChange: ((Boolean) -> Unit)?,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        colors: CheckboxColors = CheckboxDefaults.colors(),
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier,
            enabled = enabled,
            colors = colors
        )
    }
}