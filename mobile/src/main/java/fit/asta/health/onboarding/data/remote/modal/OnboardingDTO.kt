package fit.asta.health.onboarding.data.remote.modal


import com.google.gson.annotations.SerializedName

data class OnboardingDTO(
    @SerializedName("data")
    val `data`: List<OnboardingData>,
    @SerializedName("status")
    val status: Status
) {
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

    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("msg")
        val msg: String
    )
}