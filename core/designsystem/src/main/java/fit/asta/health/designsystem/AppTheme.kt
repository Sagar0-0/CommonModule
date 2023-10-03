package fit.asta.health.designsystem

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.designsystem.atomic.AppAlphaValues
import fit.asta.health.designsystem.atomic.AppAspectRatio
import fit.asta.health.designsystem.atomic.AppTypography
import fit.asta.health.designsystem.atomic.AppCustomTypography
import fit.asta.health.designsystem.atomic.AppBoxSize
import fit.asta.health.designsystem.atomic.AppButtonSize
import fit.asta.health.designsystem.atomic.AppCardHeight
import fit.asta.health.designsystem.atomic.AppCustomSize
import fit.asta.health.designsystem.atomic.DarkAppColors
import fit.asta.health.designsystem.atomic.AppElevation
import fit.asta.health.designsystem.atomic.AppIconButtonSize
import fit.asta.health.designsystem.atomic.AppIconSize
import fit.asta.health.designsystem.atomic.AppImageHeight
import fit.asta.health.designsystem.atomic.AppImageSize
import fit.asta.health.designsystem.atomic.LightAppColors
import fit.asta.health.designsystem.atomic.LocalAppAspectRatio
import fit.asta.health.designsystem.atomic.LocalAppBoxSize
import fit.asta.health.designsystem.atomic.LocalAppButtonSize
import fit.asta.health.designsystem.atomic.LocalAppCardHeight
import fit.asta.health.designsystem.atomic.LocalAppColors
import fit.asta.health.designsystem.atomic.LocalAppCustomSize
import fit.asta.health.designsystem.atomic.LocalAppElevation
import fit.asta.health.designsystem.atomic.LocalAppIconButtonSize
import fit.asta.health.designsystem.atomic.LocalAppIconSize
import fit.asta.health.designsystem.atomic.LocalAppImageHeight
import fit.asta.health.designsystem.atomic.LocalAppImageSize
import fit.asta.health.designsystem.atomic.LocalAppShape
import fit.asta.health.designsystem.atomic.LocalAppSpacing
import fit.asta.health.designsystem.atomic.LocalAppTintTheme
import fit.asta.health.designsystem.atomic.LocalAppTypography
import fit.asta.health.designsystem.atomic.AppShape
import fit.asta.health.designsystem.atomic.AppSpacing
import fit.asta.health.designsystem.atomic.AppTintTheme
import fit.asta.health.designsystem.atomic.LocalAppAlphaValues
import fit.asta.health.designsystem.atomic.LocalCustomAppTypography

/**
 * This is the default theme of the App which would be used as a Wrapper Theme over the
 * [MaterialTheme] theme
 */
@Composable
fun AppTheme(
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

        else -> if (darkTheme) DarkAppColors else LightAppColors
    }

    val appTintTheme = when {
        !disableDynamicTheming && supportsDynamicTheming() -> AppTintTheme(colorScheme.primary)
        else -> AppTintTheme()
    }
    CompositionLocalProvider(
        LocalAppAspectRatio provides AppAspectRatio(),
        LocalAppColors provides colorScheme,
        LocalAppTypography provides AppTypography,
        LocalCustomAppTypography provides AppCustomTypography(),
        LocalAppElevation provides AppElevation(),
        LocalAppIconSize provides AppIconSize(),
        LocalAppShape provides AppShape(),
        LocalAppSpacing provides AppSpacing(),
        LocalAppTintTheme provides appTintTheme,
        LocalAppBoxSize provides AppBoxSize(),
        LocalAppAlphaValues provides AppAlphaValues()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}

/**
 * The Wrapper Theme objects can be accessed using this object class from anywhere in the App
 */
object AppTheme {

    /**
     * Aspect Ratio of the App
     */
    val aspectRatio: AppAspectRatio
        @Composable
        @ReadOnlyComposable
        get() = LocalAppAspectRatio.current

    /**
     * Colors of the app which are used by default
     */
    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    /**
     * Custom Typography of the App
     */
    val customTypography: AppCustomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalCustomAppTypography.current

    /**
     * Default elevations of the app
     */
    val elevation: AppElevation
        @Composable
        @ReadOnlyComposable
        get() = LocalAppElevation.current

    /**
     * Default icon sizes of the App
     */
    val iconSize: AppIconSize
        @Composable
        @ReadOnlyComposable
        get() = LocalAppIconSize.current

    /**
     * Default Shapes of the App
     */
    val shape: AppShape
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShape.current

    /**
     * Default Spacing for the App
     */
    val spacing: AppSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalAppSpacing.current

    /**
     * Default Tint Theme for the app
     */
    val tintTheme: AppTintTheme
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTintTheme.current

    /**
     * Default Box Sizes for the App
     */
    val boxSize: AppBoxSize
        @Composable
        @ReadOnlyComposable
        get() = LocalAppBoxSize.current

    /**
     * Default Button sizes for the App
     */
    val buttonSize: AppButtonSize
        @Composable
        @ReadOnlyComposable
        get() = LocalAppButtonSize.current

    /**
     * Card Heights for the whole App
     */
    val cardHeight: AppCardHeight
        @Composable
        @ReadOnlyComposable
        get() = LocalAppCardHeight.current

    /**
     * Icon Button Sizes for the whole App
     */
    val iconButtonSize: AppIconButtonSize
        @Composable
        @ReadOnlyComposable
        get() = LocalAppIconButtonSize.current

    /**
     * Image Height for the whole Apps
     */
    val imageHeight: AppImageHeight
        @Composable
        @ReadOnlyComposable
        get() = LocalAppImageHeight.current

    /**
     * Default Image Sizes for the whole App
     */
    val imageSize: AppImageSize
        @Composable
        @ReadOnlyComposable
        get() = LocalAppImageSize.current

    /**
     * Default Custom Sizes for the Whole App
     */
    val customSize: AppCustomSize
        @Composable
        @ReadOnlyComposable
        get() = LocalAppCustomSize.current

    /**
     * Default Custom App Alpha Values
     */
    val alphaValues: AppAlphaValues
        @Composable
        @ReadOnlyComposable
        get() = LocalAppAlphaValues.current
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S