package fit.asta.health.payment.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class OrderRequest(
    @SerializedName("amtDtls")
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
        @SerializedName("dct")
        val discountCode: String = "",
        @SerializedName("cpn")
        val couponCode: String = "",
        @SerializedName("ofr")
        val offerCode: String = "",
        @SerializedName("wMny")
        val walletMoney: Double = 0.0,
        @SerializedName("wPts")
        val walletPoints: Double = 0.0
    ) : Parcelable

    @Parcelize
    data class SubscriptionDetail(
        @SerializedName("cat")
        val productCategoryId: String = "",
        @SerializedName("prod")
        val productId: String = ""
    ) : Parcelable
}

sealed class OrderRequestType(val code: Int) {
    data object Subscription : OrderRequestType(1)
    data object AddInWallet : OrderRequestType(2)
}