package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

//TODO Toggle Btn, Icon Btn, Btn, FAB, Radio, Text Btn, Outline Btn(with Icon), --> Container Format

/** [AppButton] is default button for the app.
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
fun AppButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit,
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
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
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
    modifier: Modifier = Modifier,
    colors: RadioButtonColors = RadioButtonDefaults.colors(),
    selected: Boolean,
    onClick: (() -> Unit)?,
) {
    RadioButton(
        modifier = modifier,
        selected = selected,
        colors = colors,
        onClick = onClick,
    )
}