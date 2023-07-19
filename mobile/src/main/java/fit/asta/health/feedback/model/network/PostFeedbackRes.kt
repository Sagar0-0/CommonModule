package fit.asta.health.feedback.model.network


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostFeedbackRes(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("status")
    val status: Status = Status()
) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("flag")
        val flag: Boolean = false,
        @SerializedName("id")
        val id: String = "",
        @SerializedName("msg")
        val msg: String = ""
    ) : Parcelable

    @Parcelize
    data class Status(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("msg")
        val msg: String = ""
    ) : Parcelable
}