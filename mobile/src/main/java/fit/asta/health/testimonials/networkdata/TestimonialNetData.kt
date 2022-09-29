package fit.asta.health.testimonials.networkdata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestimonialNetData(
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("userId")
    var userId: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("tml")
    var tml: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("des")
    var des: String = "",
    @SerializedName("org")
    var org: String = ""
) : Parcelable

data class TestimonialResponse(
    @SerializedName("data")
    val `data`: TestimonialNetData = TestimonialNetData(),
    @SerializedName("statusDTO")
    val status: Status = Status()
)

data class TestimonialListResponse(
    @SerializedName("data")
    val `data`: List<TestimonialNetData> = listOf(),
    @SerializedName("statusDTO")
    val status: Status = Status()
)