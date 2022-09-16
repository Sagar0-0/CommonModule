package fit.asta.health.home.model

import fit.asta.health.R

data class HomeScreenImageSliderData(
    val description: String,
    val imageID: Int,
)

val sliderDataList = listOf(
    HomeScreenImageSliderData("“Physical fitness is the first requisite of happiness.”",
        R.drawable.first_image),
    HomeScreenImageSliderData("“Good health and good sense are two of life’s greatest blessings.”",
        R.drawable.second_image),
    HomeScreenImageSliderData("“The first wealth is health.”",
        R.drawable.third_image)
)
