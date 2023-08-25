package fit.asta.health.designsystem

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.atomic.AspectRatio
import fit.asta.health.designsystem.atomic.AstaTypography
import fit.asta.health.designsystem.atomic.BackgroundTheme
import fit.asta.health.designsystem.atomic.DarkDefaultColorScheme
import fit.asta.health.designsystem.atomic.Elevation
import fit.asta.health.designsystem.atomic.IconSize
import fit.asta.health.designsystem.atomic.LightDefaultColorScheme
import fit.asta.health.designsystem.atomic.LocalAspectRatio
import fit.asta.health.designsystem.atomic.LocalBackgroundTheme
import fit.asta.health.designsystem.atomic.LocalColors
import fit.asta.health.designsystem.atomic.LocalElevation
import fit.asta.health.designsystem.atomic.LocalIconSize
import fit.asta.health.designsystem.atomic.LocalShape
import fit.asta.health.designsystem.atomic.LocalSpacing
import fit.asta.health.designsystem.atomic.LocalTintTheme
import fit.asta.health.designsystem.atomic.LocalTypography
import fit.asta.health.designsystem.atomic.Shape
import fit.asta.health.designsystem.atomic.Spacing
import fit.asta.health.designsystem.atomic.TintTheme

@Composable
fun AstaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit
) {
    // Color scheme
    val colorScheme = when {
        !disableDynamicTheming && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
    }

    // Background theme
    val backgroundTheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = 2.dp,
    )
    val tintTheme = when {
        !disableDynamicTheming && supportsDynamicTheming() -> TintTheme(colorScheme.primary)
        else -> TintTheme()
    }
    CompositionLocalProvider(
        LocalAspectRatio provides AspectRatio(),
        LocalColors provides colorScheme,
        LocalTypography provides AstaTypography,
        LocalElevation provides Elevation(),
        LocalIconSize provides IconSize(),
        LocalShape provides Shape(),
        LocalSpacing provides Spacing(),
        LocalBackgroundTheme provides backgroundTheme,
        LocalTintTheme provides tintTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AstaTypography,
            content = content
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S