package fit.asta.health.old.course.session.networkdata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status
import kotlinx.parcelize.Parcelize

data class SessionResponse(
    @SerializedName("statusDTO")
    val status: Status = Status(),
    @SerializedName("data")
    val `data`: SessionNetData = SessionNetData()
)

@Parcelize
data class SessionNetData(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("sty")
    val sty: String = "",
    @SerializedName("dur")
    val dur: Long = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("cal")
    val cal: Int = 0,
    @SerializedName("lvl")
    val lvl: String = "",
    @SerializedName("int")
    val int: Float = 0.0F,
    @SerializedName("auth")
    val auth: String = "",
    @SerializedName("day")
    val day: Int = 0,
    @SerializedName("totalDays")
    val totalDays: Int = 0,
    @SerializedName("prg")
    val prg: Int = 0,
    @SerializedName("exercises")
    val exercises: List<ExerciseNetData> = listOf()
) : Parcelable

@Parcelize
data class ExerciseNetData(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("sub")
    val sub: String = "",
    @SerializedName("sty")
    val sty: String = "",
    @SerializedName("dur")
    val dur: Long = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("cal")
    val cal: Int = 0,
    @SerializedName("lvl")
    val lvl: String = "",
    @SerializedName("int")
    val int: String = ""
) : Parcelable