package fit.asta.health.designsystem.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
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
 * @param containerColor the color used for the background of this FAB. Use [Color.Transparent] to
 * have no color.
 * @param contentColor the preferred color for content inside this FAB. Defaults to either the
 * matching content color for [containerColor], or to the current LocalContentColor if
 * [containerColor] is not a color from the theme.
 * @param elevation this variable contains the elevation of the FAB
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this FAB. You can create and pass in your own `remember`ed instance to observe [Interaction]s
 * and customize the appearance / behavior of this FAB in different states.
 * @param onClick called when this FAB is clicked
 * @param content the content of this FAB, typically an [Icon]
 * */
@Composable
fun AppFloatingActionButton(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    containerColor: Color = AppTheme.colors.primary,
    contentColor: Color = AppTheme.colors.onPrimary,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(
        defaultElevation = AppTheme.elevation.level3,
        pressedElevation = AppTheme.elevation.level3,
        focusedElevation = AppTheme.elevation.level3,
        hoveredElevation = AppTheme.elevation.level4
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = interactionSource,
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
 * @param imageVector This is the image Vector which needs to be drawn in the UI
 * @param containerColor the color used for the background of this FAB. Use [Color.Transparent] to
 * have no color.
 * @param contentColor the preferred color for content inside this FAB. Defaults to either the
 * matching content color for [containerColor], or to the current LocalContentColor if
 * [containerColor] is not a color from the theme.
 * @param elevation this variable contains the elevation of the FAB
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this FAB. You can create and pass in your own `remember`ed instance to observe [Interaction]s
 * and customize the appearance / behavior of this FAB in different states.
 * @param contentDescription This is the content description of the Icon
 * @param iconTint This is the tint of the icon in the FAB
 * @param onClick called when this FAB is clicked
 * */
@Composable
fun AppFloatingActionButton(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    imageVector: ImageVector,
    containerColor: Color = AppTheme.colors.primary,
    contentColor: Color = AppTheme.colors.onPrimary,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(
        defaultElevation = AppTheme.elevation.level3,
        pressedElevation = AppTheme.elevation.level3,
        focusedElevation = AppTheme.elevation.level3,
        hoveredElevation = AppTheme.elevation.level4
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentDescription: String? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    onClick: () -> Unit
) {

    FloatingActionButton(
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        onClick = onClick,
        elevation = elevation,
        interactionSource = interactionSource
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
 * @param painter This is the painter which needs to be drawn in the UI
 * @param containerColor the color used for the background of this FAB. Use [Color.Transparent] to
 * have no color.
 * @param contentColor the preferred color for content inside this FAB. Defaults to either the
 * matching content color for [containerColor], or to the current LocalContentColor if
 * [containerColor] is not a color from the theme.
 * @param elevation this variable contains the elevation of the FAB
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this FAB. You can create and pass in your own `remember`ed instance to observe [Interaction]s
 * and customize the appearance / behavior of this FAB in different states.
 * @param contentDescription This is the content description of the Icon
 * @param iconTint This is the tint of the icon in the FAB
 * @param onClick called when this FAB is clicked
 * */
@Composable
fun AppFloatingActionButton(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    painter: Painter,
    containerColor: Color = AppTheme.colors.primary,
    contentColor: Color = AppTheme.colors.onPrimary,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(
        defaultElevation = AppTheme.elevation.level3,
        pressedElevation = AppTheme.elevation.level3,
        focusedElevation = AppTheme.elevation.level3,
        hoveredElevation = AppTheme.elevation.level4
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentDescription: String? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    onClick: () -> Unit,
) {

    FloatingActionButton(
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        onClick = onClick,
        elevation = elevation,
        interactionSource = interactionSource
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = iconTint
        )
    }
}