package fit.asta.health.designsystemx

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.designsystemx.atomic.AspectRatioX
import fit.asta.health.designsystemx.atomic.AstaTypographyX
import fit.asta.health.designsystemx.atomic.DarkDefaultColorSchemeX
import fit.asta.health.designsystemx.atomic.ElevationX
import fit.asta.health.designsystemx.atomic.IconSizeX
import fit.asta.health.designsystemx.atomic.LightDefaultColorSchemeX
import fit.asta.health.designsystemx.atomic.LocalAspectRatioX
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

/**
 * This is the default theme of the App which would be used as a Wrapper Theme over the
 * [MaterialTheme] theme
 */
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
        LocalTintThemeX provides tintThemeX
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AstaTypographyX,
            content = content
        )
    }
}

/**
 * The Wrapper Theme objects can be accessed using this object class from anywhere in the App
 */
object AstaThemeX {

    /**
     * Aspect Ratio of the App
     */
    val aspectRatioX: AspectRatioX
        @Composable
        @ReadOnlyComposable
        get() = LocalAspectRatioX.current

    /**
     * Colors of the app which are used by default
     */
    val colorsX: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorsX.current

    /**
     * Default Typography of the app
     */
    val typographyX: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypographyX.current

    /**
     * Default elevations of the app
     */
    val elevationX: ElevationX
        @Composable
        @ReadOnlyComposable
        get() = LocalElevationX.current

    /**
     * Default icon sizes of the App
     */
    val iconSizeX: IconSizeX
        @Composable
        @ReadOnlyComposable
        get() = LocalIconSizeX.current

    /**
     * Default Shapes of the App
     */
    val shapeX: ShapeX
        @Composable
        @ReadOnlyComposable
        get() = LocalShapeX.current

    /**
     * Default Spacing for the App
     */
    val spacingX: SpacingX
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacingX.current

    /**
     * Default Tint Theme for the app
     */
    val tintThemeX: TintThemeX
        @Composable
        @ReadOnlyComposable
        get() = LocalTintThemeX.current
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S