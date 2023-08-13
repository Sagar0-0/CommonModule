package fit.asta.health.onboarding.data.remote.modal


import com.google.gson.annotations.SerializedName
import fit.asta.health.onboarding.data.model.OnboardingData

data class OnboardingDTO(
    @SerializedName("data")
    val `data`: List<OnboardingData> = listOf(),
    @SerializedName("status")
    val status: Status = Status()
) {
    data class Status(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("msg")
        val msg: String =""
    )
}