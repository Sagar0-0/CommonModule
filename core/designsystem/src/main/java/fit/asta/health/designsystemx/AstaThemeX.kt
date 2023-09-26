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
import fit.asta.health.designsystemx.atomic.AppAspectRatio
import fit.asta.health.designsystemx.atomic.AppTypography
import fit.asta.health.designsystemx.atomic.AppBoxSize
import fit.asta.health.designsystemx.atomic.AppButtonSize
import fit.asta.health.designsystemx.atomic.AppCardHeight
import fit.asta.health.designsystemx.atomic.AppCustomSize
import fit.asta.health.designsystemx.atomic.DarkAppColors
import fit.asta.health.designsystemx.atomic.AppElevation
import fit.asta.health.designsystemx.atomic.AppIconButtonSize
import fit.asta.health.designsystemx.atomic.AppIconSize
import fit.asta.health.designsystemx.atomic.AppImageHeight
import fit.asta.health.designsystemx.atomic.AppImageSize
import fit.asta.health.designsystemx.atomic.LightAppColors
import fit.asta.health.designsystemx.atomic.LocalAppAspectRatio
import fit.asta.health.designsystemx.atomic.LocalAppBoxSize
import fit.asta.health.designsystemx.atomic.LocalAppButtonSize
import fit.asta.health.designsystemx.atomic.LocalAppCardHeight
import fit.asta.health.designsystemx.atomic.LocalAppColors
import fit.asta.health.designsystemx.atomic.LocalAppCustomSize
import fit.asta.health.designsystemx.atomic.LocalAppElevation
import fit.asta.health.designsystemx.atomic.LocalAppIconButtonSize
import fit.asta.health.designsystemx.atomic.LocalAppIconSize
import fit.asta.health.designsystemx.atomic.LocalAppImageHeight
import fit.asta.health.designsystemx.atomic.LocalAppImageSize
import fit.asta.health.designsystemx.atomic.LocalAppShape
import fit.asta.health.designsystemx.atomic.LocalAppSpacing
import fit.asta.health.designsystemx.atomic.LocalAppTintTheme
import fit.asta.health.designsystemx.atomic.LocalAppTypography
import fit.asta.health.designsystemx.atomic.AppShape
import fit.asta.health.designsystemx.atomic.AppSpacing
import fit.asta.health.designsystemx.atomic.AppTintTheme

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
            DarkAppColors
        else
            LightAppColors
    }

    val appTintTheme = when {
        !disableDynamicTheming && supportsDynamicTheming() -> AppTintTheme(colorScheme.primary)
        else -> AppTintTheme()
    }
    CompositionLocalProvider(
        LocalAppAspectRatio provides AppAspectRatio(),
        LocalAppColors provides colorScheme,
        LocalAppTypography provides AppTypography,
        LocalAppElevation provides AppElevation(),
        LocalAppIconSize provides AppIconSize(),
        LocalAppShape provides AppShape(),
        LocalAppSpacing provides AppSpacing(),
        LocalAppTintTheme provides appTintTheme,
        LocalAppBoxSize provides AppBoxSize()
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
object AstaThemeX {

    /**
     * Aspect Ratio of the App
     */
    val appAspectRatio: AppAspectRatio
        @Composable
        @ReadOnlyComposable
        get() = LocalAppAspectRatio.current

    /**
     * Colors of the app which are used by default
     */
    val colorsX: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    /**
     * Default Typography of the app
     */
    val typographyX: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    /**
     * Default elevations of the app
     */
    val appElevation: AppElevation
        @Composable
        @ReadOnlyComposable
        get() = LocalAppElevation.current

    /**
     * Default icon sizes of the App
     */
    val appIconSize: AppIconSize
        @Composable
        @ReadOnlyComposable
        get() = LocalAppIconSize.current

    /**
     * Default Shapes of the App
     */
    val appShape: AppShape
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShape.current

    /**
     * Default Spacing for the App
     */
    val appSpacing: AppSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalAppSpacing.current

    /**
     * Default Tint Theme for the app
     */
    val appTintTheme: AppTintTheme
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTintTheme.current

    /**
     * Default Box Sizes for the App
     */
    val appBoxSize: AppBoxSize
        @Composable
        @ReadOnlyComposable
        get() = LocalAppBoxSize.current

    /**
     * Default Button sizes for the App
     */
    val appButtonSize: AppButtonSize
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
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S