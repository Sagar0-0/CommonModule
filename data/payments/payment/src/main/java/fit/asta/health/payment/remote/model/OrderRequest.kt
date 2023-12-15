package fit.asta.health.payment.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderRequest(
    @SerializedName("amtDetails")
    val amtDetails: AmtDetails = AmtDetails(),
    @SerializedName("con")
    val country: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("sub")
    val subscriptionDetail: SubscriptionDetail = SubscriptionDetail(),
    @SerializedName("type")
    val type: Int = OrderRequestType.Subscription.code,
    @SerializedName("uid")
    val uId: String = ""
) : Parcelable {
    @Parcelize
    data class AmtDetails(
        @SerializedName("amt")
        val amt: Double = 0.0,
        @SerializedName("discountCode")
        val discountCode: String = "",
        @SerializedName("offerCode")
        val offerCode: String = "",
        @SerializedName("walletMoney")
        val walletMoney: Int = 0,
        @SerializedName("walletPoints")
        val walletPoints: Int = 0
    ) : Parcelable

    @Parcelize
    data class SubscriptionDetail(
        @SerializedName("durType")
        val durType: String = "",
        @SerializedName("subType")
        val subType: String = ""
    ) : Parcelable
}

sealed class OrderRequestType(val code: Int) {
    data object Subscription : OrderRequestType(1)
    data object AddInWallet : OrderRequestType(2)
}