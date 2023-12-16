package fit.asta.health.referral.remote.model

import com.google.gson.annotations.SerializedName

typealias SubscriptionState = Int

data class UserDetails(
    @SerializedName("mail")
    val mail: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("pic")
    val pic: String = "",
    val phone: String = "",
    @SerializedName("prime")
    val subscriptionState: SubscriptionState = SubscriptionStateType.INACTIVE.code,
)

fun SubscriptionState.getSubscriptionStateType() =
    SubscriptionStateType.entries.first { this == it.code }

enum class SubscriptionStateType(val code: Int) {
    ACTIVE(1),
    INACTIVE(2),
    TEMPORARY_INACTIVE(3)
}