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

/** [AppDefBtn] is default button for the app. Buttons help people initiate actions,
 * from sending an email, to sharing a document, to liking a post.
 * @param onClick called when this button is clicked
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 */

@Composable
fun AppDefBtn(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: ButtonColors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {


    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = color,
        contentPadding = contentPadding,
        enabled = enabled,
        content = content,
    )

}

/**[AppDefFAB] function is a custom composable function used to create a floating action button (FAB).
 * @param onClick called when this FAB is clicked
 * @param modifier the [Modifier] to be applied to this button
 * @param content the content of this FAB, typically an [Icon]
 * */

@Composable
fun AppDefFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        content = content,
        containerColor = MaterialTheme.colorScheme.primary,
        shape = CircleShape,
        modifier = modifier,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    )
}


/** [AppDefRadioButton] buttons allow users to select one option from a set.

 * @param selected whether this radio button is selected or not
 * @param onClick called when this radio button is clicked.
 * @param modifier the [Modifier] to be applied to this radio button
 * @param colors [RadioButtonColors] that will be used to resolve the color used for this radio
 * button in different states. See [RadioButtonDefaults.colors].
 */

@Composable
fun AppDefRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    colors: RadioButtonColors = RadioButtonDefaults.colors(),
) {
    RadioButton(selected = selected, onClick = onClick, modifier = modifier, colors = colors)
}