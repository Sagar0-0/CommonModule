package fit.asta.health.designsystem.atomic

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

/**
 * This Model Class contains all the different values for the default Spaces in the app
 */
@Immutable
data class AppShape(
    val level1: CornerBasedShape = RoundedCornerShape(4.dp),
    val level2: CornerBasedShape = RoundedCornerShape(8.dp),
    val level3: CornerBasedShape = RoundedCornerShape(16.dp),
    val level4: CornerBasedShape = RoundedCornerShape(24.dp)
)

internal val LocalAppShape = compositionLocalOf { AppShape() }