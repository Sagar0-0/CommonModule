package fit.asta.health.sunlight.feature.screens.skin_conditions.util

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import fit.asta.health.resources.strings.R


object SkinColorUtil {
    fun getSkinColorData(): List<SkinColorData> {
        return listOf(
            SkinColorData(
                color = SkinColor.Fair,
                title = R.string.fair
            ),
            SkinColorData(
                color = SkinColor.Light,
                title = R.string.light
            ),
            SkinColorData(
                color = SkinColor.MediumLight,
                title = R.string.medium_light
            ),
            SkinColorData(
                color = SkinColor.Medium,
                title = R.string.medium
            ),
            SkinColorData(
                color = SkinColor.MediumDark,
                title = R.string.medium_dark
            ),
            SkinColorData(
                color = SkinColor.Dark,
                title = R.string.dark
            )
        )
    }
}

object SkinColor {
    val Fair = Color(0xFFC8AC99)
    val Light = Color(0xFFB1886C)
    val MediumLight = Color(0xFFA0795F)
    val Medium = Color(0xFF8C624E)
    val MediumDark = Color(0xFF634B3F)
    val Dark = Color(0xFF544239)
}

data class SkinColorData(
    val color: Color,
    @StringRes val title: Int
)