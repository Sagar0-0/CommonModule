package fit.asta.health.scheduler.data.api.net.tag


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AstaGetTagsListResponse(
    @SerializedName("CustomTagData")
    val customTagData: List<Data>,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("status")
    val status: Status
) : Parcelable