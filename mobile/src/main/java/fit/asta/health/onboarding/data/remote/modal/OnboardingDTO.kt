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
        val dsc: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("ttl")
        val ttl: String,
        @SerializedName("type")
        val type: Int,
        @SerializedName("url")
        val url: String,
        @SerializedName("ver")
        val ver: Int,
        @SerializedName("vis")
        val vis: Boolean
    )

    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("msg")
        val msg: String
    )
}