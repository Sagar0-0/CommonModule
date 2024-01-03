package fit.asta.health.designsystem.atomic.token

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fit.asta.health.core.designsystem.R


val astaFontFamily = FontFamily(
    Font(R.font.montserrat_black, FontWeight.Light),
    Font(R.font.montserrat_black_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.montserrat_extra_bold, FontWeight.ExtraBold),
    Font(R.font.montserrat_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.montserrat_extra_light, FontWeight.ExtraLight),
    Font(R.font.montserrat_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.montserrat_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_semi_bold, FontWeight.SemiBold),
    Font(R.font.montserrat_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.montserrat_thin, FontWeight.Thin),
    Font(R.font.montserrat_thin_italic, FontWeight.Thin, FontStyle.Italic),
)

object DefaultTypographyTokens {

    /**
     * Large Texts constants
     */
    val largeLevel1: TextStyle = TextStyle(
        fontSize = 72.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = astaFontFamily
    )
    val largeLevel2: TextStyle = TextStyle(
        fontSize = 48.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = astaFontFamily
    )
    val largeLevel3: TextStyle = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = astaFontFamily
    )

    /**
     * Headline Texts constants
     */
    val headingLevel1: TextStyle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = astaFontFamily
    )
    val headingLevel2: TextStyle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = astaFontFamily
    )
    val headingLevel3: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = astaFontFamily
    )
    val headingLevel4: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = astaFontFamily
    )

    /**
     * Title Texts Constants
     */
    val titleLevel1: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = astaFontFamily
    )
    val titleLevel2: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = astaFontFamily
    )
    val titleLevel3: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = astaFontFamily
    )
    val titleLevel4: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = astaFontFamily
    )

    /**
     * Body Texts Constants
     */
    val bodyLevel1: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = astaFontFamily
    )
    val bodyLevel2: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = astaFontFamily
    )
    val bodyLevel3: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = astaFontFamily
    )

    /**
     * Caption Texts Constants
     */
    val captionLevel1: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = astaFontFamily
    )
    val captionLevel2: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = astaFontFamily
    )
    val captionLevel3: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = astaFontFamily
    )
    val captionLevel4: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = astaFontFamily
    )
    val captionLevel5: TextStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = astaFontFamily
    )
}