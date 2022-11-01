package fit.asta.health.old_subscription.networkdata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status
import kotlinx.parcelize.Parcelize

@Parcelize
data class OffersNetData(
    @SerializedName("uid")
    val uid: String = ""
) : Parcelable

@Parcelize
data class SubscriptionPlanNetData(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("tag")
    val tag: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("sub")
    val sub: String = "",
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("price")
    val price: Double = 0.0,
    @SerializedName("default")
    val default: Int = 0
) : Parcelable

@Parcelize
data class FeaturesNetData(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("url")
    val url: String = ""
) : Parcelable

@Parcelize
data class SubscriptionTypeNetData(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("type")
    val type: Int = 1,
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("features")
    val features: List<FeaturesNetData> = listOf(),
    @SerializedName("plans")
    val plans: List<SubscriptionPlanNetData> = listOf()
) : Parcelable

@Parcelize
data class SubscriptionNetData(
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("offers")
    val offers: OffersNetData = OffersNetData(),
    @SerializedName("types")
    val types: List<SubscriptionTypeNetData> = listOf()
) : Parcelable

data class SubscriptionDataResponse(
    @SerializedName("statusDTO")
    val status: Status = Status(),
    @SerializedName("data")
    val `data`: SubscriptionNetData = SubscriptionNetData()
)

@Parcelize
data class SubscriptionStatusNetData(
    @SerializedName("offer")
    val offer: OfferNetData? = null,
    @SerializedName("plan")
    val plan: Int = 0,
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("userId")
    val userId: String = ""
) : Parcelable

@Parcelize
data class OfferNetData(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("imgUrl")
    val imgUrl: String = "",
    @SerializedName("startDate")
    val startDate: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("validTill")
    val validTill: String = ""
) : Parcelable

data class SubscriptionStatusResponse(
    @SerializedName("statusDTO")
    val status: Status = Status(),
    @SerializedName("data")
    val `data`: SubscriptionStatusNetData = SubscriptionStatusNetData()
)