package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.common.ui.theme.spacing

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
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {


    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(spacing.small),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        contentPadding = contentPadding,
        enabled = enabled,
        content = content
    )

}