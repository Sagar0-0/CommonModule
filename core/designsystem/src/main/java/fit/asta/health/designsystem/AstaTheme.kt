package fit.asta.health.designsystem

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.designsystem.atomic.AspectRatio
import fit.asta.health.designsystem.atomic.AstaTypography
import fit.asta.health.designsystem.atomic.DarkColors
import fit.asta.health.designsystem.atomic.Elevation
import fit.asta.health.designsystem.atomic.IconSize
import fit.asta.health.designsystem.atomic.LightColors
import fit.asta.health.designsystem.atomic.LocalAspectRatio
import fit.asta.health.designsystem.atomic.LocalColors
import fit.asta.health.designsystem.atomic.LocalElevation
import fit.asta.health.designsystem.atomic.LocalIconSize
import fit.asta.health.designsystem.atomic.LocalShape
import fit.asta.health.designsystem.atomic.LocalSpacing
import fit.asta.health.designsystem.atomic.LocalTypography
import fit.asta.health.designsystem.atomic.Shape
import fit.asta.health.designsystem.atomic.Spacing

@Composable
fun AstaTheme(
    theme: String,
    content: @Composable () -> Unit
) {
    val usingDarkMode = theme == "dark"
    val useDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colors = when {
        useDynamicColors && usingDarkMode -> dynamicDarkColorScheme(LocalContext.current)
        useDynamicColors && !usingDarkMode -> dynamicLightColorScheme(LocalContext.current)
        usingDarkMode -> DarkColors
        else -> LightColors
    }
    CompositionLocalProvider(
        LocalAspectRatio provides AspectRatio(),
        LocalColors provides colors,
        LocalTypography provides AstaTypography,
        LocalElevation provides Elevation(),
        LocalIconSize provides IconSize(),
        LocalShape provides Shape(),
        LocalSpacing provides Spacing(),
    ) {
        MaterialTheme(
            colorScheme = colors,
            content = content
        )
    }
}