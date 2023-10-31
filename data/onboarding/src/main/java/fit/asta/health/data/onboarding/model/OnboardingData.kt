package fit.asta.health.data.onboarding.model

import com.google.gson.annotations.SerializedName

data class OnboardingData(
    @SerializedName("dsc")
    val desc: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("ver")
    val ver: Int = 0,
    @SerializedName("vis")
    val vis: Boolean = true
)

sealed class OnBoardingDataType(val type: Int) {
    data object Image : OnBoardingDataType(1)
    data object GIF : OnBoardingDataType(2)
    data object Video : OnBoardingDataType(3)
}