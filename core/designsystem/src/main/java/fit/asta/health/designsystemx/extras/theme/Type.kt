package fit.asta.health.designsystemx.extras.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import fit.asta.health.core.designsystem.R


val MyTypography = Typography(

    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_light)),
        fontWeight = FontWeight.Light,
        fontSize = 96.sp
    ), displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_light)),
        fontWeight = FontWeight.Light,
        fontSize = 60.sp
    ), displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp
    ), headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ), headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),

    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ), titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ), titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ), bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ), bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ), labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ), labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )

)


data class Ts(
    val quote: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Thin,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    val quoteBold: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    ),
    val successfulCardText: TextStyle = TextStyle(
        fontSize = 34.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center
    ),
)

val LocalTextStyle = compositionLocalOf { Ts() }

val ts: Ts
    @Composable @ReadOnlyComposable get() = LocalTextStyle.current
