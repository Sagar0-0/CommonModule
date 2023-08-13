package fit.asta.health.onboarding.data.remote.modal


import com.google.gson.annotations.SerializedName
import fit.asta.health.onboarding.data.model.OnboardingData

data class OnboardingDTO(
    @SerializedName("data")
    val `data`: List<OnboardingData>,
    @SerializedName("status")
    val status: Status
) {
    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("msg")
        val msg: String
    )
}