package fit.asta.health.data.scheduler.remote.net.tag


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TagsListResponse(
    @SerializedName("CustomTagData")
    val customTagData: List<TagData>? = null,
    @SerializedName("TagData")
    val tagData: List<TagData> = emptyList(),
) : Parcelable