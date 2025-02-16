package fit.asta.health.designsystem.atomic

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.compositionLocalOf
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_background
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_error
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_errorContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_inverseOnSurface
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_inversePrimary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_inverseSurface
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_onBackground
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_onError
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_onErrorContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_onPrimary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_onPrimaryContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_onSecondary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_onSecondaryContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_onSurface
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_onSurfaceVariant
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_onTertiary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_onTertiaryContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_outline
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_primary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_primaryContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_secondary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_secondaryContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_surface
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_surfaceTint
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_surfaceVariant
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_tertiary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_dark_tertiaryContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_background
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_error
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_errorContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_inverseOnSurface
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_inversePrimary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_inverseSurface
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_onBackground
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_onError
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_onErrorContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_onPrimary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_onPrimaryContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_onSecondary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_onSecondaryContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_onSurface
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_onSurfaceVariant
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_onTertiary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_onTertiaryContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_outline
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_primary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_primaryContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_secondary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_secondaryContainer
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_surface
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_surfaceTint
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_surfaceVariant
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_tertiary
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens.md_theme_light_tertiaryContainer

/**
 * This is the object containing hte Light Default Colors of The app
 */
internal val LightAppColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint
)

/**
 * This object contains the default colors of the Dark theme
 */
internal val DarkAppColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint
)

internal val LocalAppColors = compositionLocalOf { LightAppColors }