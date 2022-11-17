package fit.asta.health.scheduler.model.net.tag


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomTagData(
    @SerializedName("id")
    val id: String, // 6310c0782aeef2b4b3684a6d
    @SerializedName("name")
    val name: String, // updated
    @SerializedName("uid")
    val uid: String, // 6309a9379af54f142c65fbff
    @SerializedName("url")
    val url: String // url
) : Parcelable