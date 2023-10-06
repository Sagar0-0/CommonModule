package fit.asta.health.designsystem.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme

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
                AppFloatingActionButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                }
            }
        }
    }
}


/**
 * [AppFloatingActionButton] function is a custom composable function used to create a
 * floating action button (FAB).
 *
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param shape defines the shape of this Floating Action Button's container
 * @param onClick called when this FAB is clicked
 * @param content the content of this FAB, typically an [Icon]
 * */
@Composable
fun AppFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    content: @Composable () -> Unit
) {

    FloatingActionButton(
        modifier = modifier,
        shape = shape,
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        onClick = onClick,
        content = content
    )
}


/**
 * [AppFloatingActionButton] function is a custom composable function used to create a
 * floating action button (FAB).
 *
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param shape defines the shape of this Floating Action Button's container
 * @param imageVector the content of this FAB, typically an [Icon]
 * @param contentDescription This is the content description for the Icon
 * @param iconTint This contains the Tint of the Icon Inside
 * @param onClick called when this FAB is clicked
 * */
@Composable
fun AppFloatingActionButton(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    imageVector: ImageVector,
    contentDescription: String? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    onClick: () -> Unit
) {

    FloatingActionButton(
        modifier = modifier,
        shape = shape,
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = iconTint
        )
    }
}

/**
 * [AppFloatingActionButton] function is a custom composable function used to create a
 * floating action button (FAB).
 *
 *
 * @param modifier the [Modifier] to be applied to this button
 * @param shape defines the shape of this Floating Action Button's container
 * @param painter the content of this FAB, typically an [Icon]
 * @param contentDescription This is the content description for the Icon
 * @param iconTint This contains the Tint of the Icon Inside
 * @param onClick called when this FAB is clicked
 * */
@Composable
fun AppFloatingActionButton(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    painter: Painter,
    contentDescription: String? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    onClick: () -> Unit
) {

    FloatingActionButton(
        modifier = modifier,
        shape = shape,
        containerColor = AppTheme.colors.primary,
        contentColor = AppTheme.colors.onPrimary,
        onClick = onClick
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = iconTint
        )
    }
}