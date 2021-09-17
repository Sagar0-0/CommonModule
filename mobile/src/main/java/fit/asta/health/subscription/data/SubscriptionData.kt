package fit.asta.health.subscription.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class SubscriptionItemType(val value: Int) {

    UnselectedSubscription(0),
    SelectedSubscription(1);

    companion object {
        fun valueOf(value: Int) = values().first { it.value == value }
    }
}

class FeatureItem(
    var title: String = "",
    var description: String = "",
    var url: String = ""
)

class SubscriptionItem(
    var uid: String = "",
    var tag: String = "",
    var title: String = "",
    var subTitle: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var selected: SubscriptionItemType = SubscriptionItemType.UnselectedSubscription
)

data class SubscriptionPlanData(
    var uid: String = "",
    var type: Int = 1,
    var title: String = "",
    var desc: String = "",
    var url: String = "",
    var features: List<FeatureItem> = emptyList(),
    var subscriptions: List<SubscriptionItem> = emptyList()
)

data class SubscriptionData(
    var title: String = "",
    var desc: String = "",
    var plans: List<SubscriptionPlanData> = emptyList()
)

@Parcelize
data class SubscriptionStatus(
    var uid: String = "",
    var userId: String = "",
    var plan: Int = 0,
    var offer: OfferData? = null,
) : Parcelable

@Parcelize
data class OfferData(
    var code: String = "",
    var desc: String = "",
    var imgUrl: String = "",
    var startDate: String = "",
    var ttl: String = "",
    var uid: String = "",
    var validTill: String = ""
) : Parcelable