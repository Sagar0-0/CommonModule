package fit.asta.health.designsystem.molecular.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import fit.asta.health.designsystem.AppTheme

/**
 * To learn more about icons, see [Material Design icons](https://m3.material.io/styles/icons/overview)
 *
 * @param modifier the [Modifier] to be applied to this icon
 * @param imageVector [ImageVector] to draw inside this icon
 * @param contentDescription text used by accessibility services to describe what this icon
 * represents. This should always be provided unless this icon is used for decorative purposes, and
 * does not represent a meaningful action that a user can take. This text should be localized, such
 * as by using [androidx.compose.ui.res.stringResource] or similar
 * @param tint tint to be applied to [imageVector]. If [Color.Unspecified] is provided, then no tint
 * is applied.
 */
@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
    tint: Color = AppTheme.colors.onSurface,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier.size(AppTheme.imageSize.level3)
    )
}


/**
 * [Material Design icons](https://m3.material.io/styles/icons/overview)
 *
 * @param modifier the [Modifier] to be applied to this icon
 * @param bitmap [ImageBitmap] to draw inside this icon
 * @param contentDescription text used by accessibility services to describe what this icon
 * represents. This should always be provided unless this icon is used for decorative purposes, and
 * does not represent a meaningful action that a user can take. This text should be localized, such
 * as by using [androidx.compose.ui.res.stringResource] or similar
 * @param tint tint to be applied to [bitmap]. If [Color.Unspecified] is provided, then no tint is
 * applied.
 */
@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    bitmap: ImageBitmap,
    contentDescription: String? = null,
    tint: Color = AppTheme.colors.onSurface
) {
    Icon(
        bitmap = bitmap,
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier.size(AppTheme.imageSize.level3)
    )
}


/**
 * To learn more about icons, see [Material Design icons](https://m3.material.io/styles/icons/overview)
 *
 * @param modifier the [Modifier] to be applied to this icon
 * @param painter [Painter] to draw inside this icon
 * @param contentDescription text used by accessibility services to describe what this icon
 * represents. This should always be provided unless this icon is used for decorative purposes, and
 * does not represent a meaningful action that a user can take. This text should be localized, such
 * as by using [androidx.compose.ui.res.stringResource] or similar
 * @param tint tint to be applied to [painter]. If [Color.Unspecified] is provided, then no tint is
 * applied.
 */
@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null,
    tint: Color = AppTheme.colors.onSurface
) {
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier.size(AppTheme.imageSize.level3)
    )
}