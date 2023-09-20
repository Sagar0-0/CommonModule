package fit.asta.health.data.feedback.remote.modal


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostFeedbackDTO(
    @SerializedName("flag")
    val flag: Boolean = false,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("msg")
    val msg: String = ""
) : Parcelable
