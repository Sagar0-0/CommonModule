package fit.asta.health.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fit.asta.health.R


// Most Commonly used TextStyles versions
val DisplayText = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_light)),
        fontWeight = FontWeight.Light,
        fontSize = 96.sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_light)),
        fontWeight = FontWeight.Light,
        fontSize = 60.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp
    )
)


val HeadingLine = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    )
)


// Use titles as Subtitles
val Subtitles = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)


// Body
val Body = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)

// Buttons
val Buttons = Typography(
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),

    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)

val IbarraNovaFont = FontFamily(
    Font(R.font.inter_regular),
    Font(R.font.inter_extrabold, weight = FontWeight.Bold),
    Font(R.font.inter_medium, weight = FontWeight.Medium),
    Font(R.font.inter_light, weight = FontWeight.SemiBold)
)

val IbarraNovaSemiBoldGraniteGray =
    TextStyle(
        fontFamily = IbarraNovaFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = ColorGraniteGray
    )

val IbarraNovaSemiBoldPlatinum16 =
    TextStyle(
        fontFamily = IbarraNovaFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = ColorPlatinum
    )

val IbarraNovaNormalGray14 =
    TextStyle(
        fontFamily = IbarraNovaFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = ColorGraniteGray
    )

val IbarraNovaNormalError13 = TextStyle(
    fontFamily = IbarraNovaFont,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    color = ColorRed
)