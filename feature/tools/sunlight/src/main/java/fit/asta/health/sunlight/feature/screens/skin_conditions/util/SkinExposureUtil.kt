package fit.asta.health.sunlight.feature.screens.skin_conditions.util

import androidx.annotation.DrawableRes
import fit.asta.health.resources.drawables.R

object SkinExposureUtil {
    fun getExposureDataList(): List<ExposureData> {
        return listOf(
            ExposureData(
                icon = R.drawable.ic_exposure_g4,
                title = "40%"
            ),
            ExposureData(
                icon = R.drawable.ic_exposure_b2,
                title = "30%"
            ),
            ExposureData(
                icon = R.drawable.ic_exposure2,
                title = "45%"
            ),
            ExposureData(
                icon = R.drawable.ic_exposure_b3,
                title = "50%"
            ),
            ExposureData(
                icon = R.drawable.ic_exposure_sari,
                title = "25%"
            ),
            ExposureData(
                icon = R.drawable.ic_exposure_salwar,
                title = "20%"
            ),
            ExposureData(
                icon = R.drawable.ic_exposure_dhoti,
                title = "20%"
            )
        )
    }
}

data class ExposureData(
    @DrawableRes val icon: Int,
    val title: String,
    val value: Double? = null
)