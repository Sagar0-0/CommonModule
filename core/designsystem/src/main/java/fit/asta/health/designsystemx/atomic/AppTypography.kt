package fit.asta.health.designsystemx.atomic

import androidx.compose.material3.Typography
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import fit.asta.health.designsystemx.atomic.token.DefaultTypographyTokens


data class AppCustomTypography(
    val large: Large = Large(),
    val heading: Heading = Heading(),
    val title: Title = Title(),
    val body: Body = Body(),
    val caption: Caption = Caption()
) {
    data class Large(
        val level1: TextStyle = DefaultTypographyTokens.largeLevel1,
        val level2: TextStyle = DefaultTypographyTokens.largeLevel2,
        val level3: TextStyle = DefaultTypographyTokens.largeLevel3
    )

    data class Heading(
        val level1: TextStyle = DefaultTypographyTokens.headingLevel1,
        val level2: TextStyle = DefaultTypographyTokens.headingLevel2,
        val level3: TextStyle = DefaultTypographyTokens.headingLevel3,
        val level4: TextStyle = DefaultTypographyTokens.headingLevel4
    )

    data class Title(
        val level1: TextStyle = DefaultTypographyTokens.titleLevel1,
        val level2: TextStyle = DefaultTypographyTokens.titleLevel2,
        val level3: TextStyle = DefaultTypographyTokens.titleLevel3,
        val level4: TextStyle = DefaultTypographyTokens.titleLevel4
    )

    data class Body(
        val level1: TextStyle = DefaultTypographyTokens.bodyLevel1,
        val level2: TextStyle = DefaultTypographyTokens.bodyLevel2,
        val level3: TextStyle = DefaultTypographyTokens.bodyLevel3
    )

    data class Caption(
        val level1: TextStyle = DefaultTypographyTokens.captionLevel1,
        val level2: TextStyle = DefaultTypographyTokens.captionLevel2,
        val level3: TextStyle = DefaultTypographyTokens.captionLevel3,
        val level4: TextStyle = DefaultTypographyTokens.captionLevel4,
        val level5: TextStyle = DefaultTypographyTokens.captionLevel5
    )
}

internal val LocalCustomAppTypography = compositionLocalOf { AppCustomTypography() }

/**
 * This object is the default Typography of the App
 */
internal val AppTypography = Typography(

    displayLarge = DefaultTypographyTokens.largeLevel1,
    displayMedium = DefaultTypographyTokens.largeLevel2,
    displaySmall = DefaultTypographyTokens.largeLevel3,

    headlineLarge = DefaultTypographyTokens.headingLevel1,
    headlineMedium = DefaultTypographyTokens.headingLevel2,
    headlineSmall = DefaultTypographyTokens.headingLevel4,

    titleLarge = DefaultTypographyTokens.titleLevel1,
    titleMedium = DefaultTypographyTokens.titleLevel2,
    titleSmall = DefaultTypographyTokens.titleLevel3,

    bodyLarge = DefaultTypographyTokens.bodyLevel1,
    bodyMedium = DefaultTypographyTokens.bodyLevel2,
    bodySmall = DefaultTypographyTokens.bodyLevel3,

    labelLarge = DefaultTypographyTokens.captionLevel1,
    labelMedium = DefaultTypographyTokens.captionLevel3,
    labelSmall = DefaultTypographyTokens.captionLevel5
)
internal val LocalAppTypography = compositionLocalOf { AppTypography }