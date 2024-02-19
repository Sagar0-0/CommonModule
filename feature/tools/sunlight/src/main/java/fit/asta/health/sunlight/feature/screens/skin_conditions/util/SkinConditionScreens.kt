package fit.asta.health.sunlight.feature.screens.skin_conditions.util

import android.util.Log
import androidx.annotation.StringRes
import fit.asta.health.resources.strings.R


data class SkinConditionScreen(
    @StringRes var title: Int,
    var index: Int
)

object SkinConditionScreenCode {
    const val EXPOSURE_SCREEN = "exp"
    const val SKIN_COLOR_SCREEN = "sc"
    const val SUNSCREEN_SPF_SCREEN = "spf"
    const val SUPPLEMENT_PERIOD_LIST = "prd"
    const val SUPPLEMENT_UNITS = "iu"
    const val SKIN_TYPE_SCREEN = "st"
    const val SUPPLEMENT_SCREEN = "sup"
}

fun String.getScreenIndex(): Int {
    Log.d("id", "getScreenIndex: $this")
    return when (this) {
        SkinConditionScreenCode.EXPOSURE_SCREEN -> {
            SkinConditionPager.SELECT_EXPOSURE
        }

        SkinConditionScreenCode.SKIN_COLOR_SCREEN -> {
            SkinConditionPager.SELECT_COLOR
        }

        SkinConditionScreenCode.SUNSCREEN_SPF_SCREEN -> {
            SkinConditionPager.SELECT_SPF
        }

        SkinConditionScreenCode.SKIN_TYPE_SCREEN -> {
            SkinConditionPager.SELECT_SKIN_TYPE
        }

        SkinConditionScreenCode.SUPPLEMENT_SCREEN -> {
            SkinConditionPager.SELECT_SUPPLEMENT
        }

        else -> {
            -1
        }
    }
}

object SkinConditionScreens {
    fun getSkinConditionPagerList(): List<SkinConditionScreen> {
        return listOf(
            SkinConditionScreen(
                title = R.string.skin_exposure,
                SkinConditionPager.SELECT_EXPOSURE
            ),
            SkinConditionScreen(
                title = R.string.skin_color,
                SkinConditionPager.SELECT_COLOR
            ),
            SkinConditionScreen(
                title = R.string.skin_type,
                SkinConditionPager.SELECT_COLOR
            ),
            SkinConditionScreen(
                title = R.string.sunscreen,
                SkinConditionPager.SELECT_SPF
            ),
            SkinConditionScreen(
                title = R.string.supplements,
                SkinConditionPager.SELECT_SUPPLEMENT
            )
        )
    }
}