package fit.asta.health.designsystemx.atomic

import androidx.compose.material3.Typography
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fit.asta.health.core.designsystem.R


data class Typo(
    val large: Large = Large(),
    val heading: Heading = Heading(),
    val titleBody: Title = Title(),
    val caption: Caption = Caption()
) {
    data class Large(
        val level1: TextStyle = TextStyle(
            fontSize = 72.sp,
            fontWeight = FontWeight.Normal
        ),
        val level2: TextStyle = TextStyle(
            fontSize = 48.sp,
            fontWeight = FontWeight.SemiBold
        ),
        val level3: TextStyle = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    )

    data class Heading(
        val level1: TextStyle = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        ),
        val level2: TextStyle = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        ),
        val level3: TextStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        ),
        val level4: TextStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    )

    data class Title(
        val level1: TextStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        ),
        val level2: TextStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        ),
        val level3: TextStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        ),
        val level4: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    )

    data class Body(
        val level1: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        val level2: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        ),
        val level3: TextStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    )

    data class Caption(
        val level1: TextStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        ),
        val level2: TextStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        ),
        val level3: TextStyle = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        ),
        val level4: TextStyle = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        ),
        val level5: TextStyle = TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

internal val LocalCustomAppTypo = compositionLocalOf { Typo() }

/**
 * This object is the default Typography of the App
 */
internal val AppTypography = Typography(

    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.Light,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 25.2.sp,
        letterSpacing = 0.sp
    ),

    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 25.2.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 22.4.sp,
        letterSpacing = 0.1.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),

    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
//        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
//        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
internal val LocalAppTypography = compositionLocalOf { AppTypography }