package fit.asta.health.onboarding.modal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnboardingData(
    val title: String = "",
    val desc: String = "",
    val imgUrl: String = "",
    val type: Int = 0
) : Parcelable
