package fit.asta.health.designsystemx

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
import fit.asta.health.designsystemx.atomic.AspectRatioX
import fit.asta.health.designsystemx.atomic.AstaTypographyX
import fit.asta.health.designsystemx.atomic.BackgroundThemeX
import fit.asta.health.designsystemx.atomic.DarkDefaultColorSchemeX
import fit.asta.health.designsystemx.atomic.ElevationX
import fit.asta.health.designsystemx.atomic.IconSizeX
import fit.asta.health.designsystemx.atomic.LightDefaultColorSchemeX
import fit.asta.health.designsystemx.atomic.LocalAspectRatioX
import fit.asta.health.designsystemx.atomic.LocalBackgroundThemeX
import fit.asta.health.designsystemx.atomic.LocalColorsX
import fit.asta.health.designsystemx.atomic.LocalElevationX
import fit.asta.health.designsystemx.atomic.LocalIconSizeX
import fit.asta.health.designsystemx.atomic.LocalShapeX
import fit.asta.health.designsystemx.atomic.LocalSpacingX
import fit.asta.health.designsystemx.atomic.LocalTintThemeX
import fit.asta.health.designsystemx.atomic.LocalTypographyX
import fit.asta.health.designsystemx.atomic.ShapeX
import fit.asta.health.designsystemx.atomic.SpacingX
import fit.asta.health.designsystemx.atomic.TintThemeX

@Composable
fun AstaThemeX(
    darkTheme: Boolean = isSystemInDarkTheme(),
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit
) {
    // Color scheme
    val colorScheme = when {

        !disableDynamicTheming && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme)
                dynamicDarkColorScheme(context)
            else
                dynamicLightColorScheme(context)
        }

        else -> if (darkTheme)
            DarkDefaultColorSchemeX
        else
            LightDefaultColorSchemeX
    }

    // Background theme
    val backgroundThemeX = BackgroundThemeX(
        color = colorScheme.surface,
        tonalElevation = 2.dp,
    )
    val tintThemeX = when {
        !disableDynamicTheming && supportsDynamicTheming() -> TintThemeX(colorScheme.primary)
        else -> TintThemeX()
    }
    CompositionLocalProvider(
        LocalAspectRatioX provides AspectRatioX(),
        LocalColorsX provides colorScheme,
        LocalTypographyX provides AstaTypographyX,
        LocalElevationX provides ElevationX(),
        LocalIconSizeX provides IconSizeX(),
        LocalShapeX provides ShapeX(),
        LocalSpacingX provides SpacingX(),
        LocalBackgroundThemeX provides backgroundThemeX,
        LocalTintThemeX provides tintThemeX,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AstaTypographyX,
            content = content
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S