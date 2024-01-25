package fit.asta.health.subscription.remote.model

import com.google.gson.annotations.SerializedName

data class SubscriptionPlansResponse(
    @SerializedName("plans")
    val plans: List<SubscriptionCategoryData>? = null,
    @SerializedName("userPlan")
    val userPlan: UserSubscribedPlan? = null
)
