package fit.asta.health.payment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderRequest(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("uid")
    val uId: String = "",
    @SerializedName("subType")
    val subType: String = "",
    @SerializedName("durType")
    val durType: String = "",
    @SerializedName("con")
    val country: String = "",
    @SerializedName("type")
    val type: Int = 0,
) : Parcelable
