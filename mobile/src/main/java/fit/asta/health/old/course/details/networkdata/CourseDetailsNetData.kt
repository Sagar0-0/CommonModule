package fit.asta.health.old.course.details.networkdata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseHeaderNetData(
    @SerializedName("date")
    val date: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("sub")
    val sub: String = "",
    @SerializedName("urlType")
    val urlType: Int = 1,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("lvl")
    val lvl: String = "",
    @SerializedName("dur")
    val dur: String = ""
) : Parcelable

@Parcelize
data class IntroNetData(
    @SerializedName("url")
    val url: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("dsc")
    val dsc: String = ""
) : Parcelable

@Parcelize
data class KeysNetData(
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("pts")
    val pts: List<String> = listOf()
) : Parcelable

@Parcelize
data class AgeNetData(
    @SerializedName("frm")
    val frm: Int = 0,
    @SerializedName("to")
    val to: Int = 0
) : Parcelable

@Parcelize
data class AudienceNetData(
    @SerializedName("age")
    val age: AgeNetData = AgeNetData(),
    @SerializedName("gen")
    val gen: String = "",
    @SerializedName("lvl")
    val lvl: String = "",
    @SerializedName("preReq")
    val preReq: List<String> = listOf(),
    @SerializedName("notFor")
    val notFor: List<String> = listOf()
) : Parcelable

@Parcelize
data class ExpertNetData(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("exp")
    val exp: String = "",
    @SerializedName("prof")
    val prof: String = "",
    @SerializedName("qlf")
    val qlf: String = "",
    @SerializedName("abt")
    val abt: String = "",
    @SerializedName("url")
    val url: String = ""
) : Parcelable

@Parcelize
data class OverViewNetData(
    @SerializedName("intro")
    val intro: IntroNetData = IntroNetData(),
    @SerializedName("keys")
    val keys: KeysNetData = KeysNetData(),
    @SerializedName("audience")
    val audience: AudienceNetData = AudienceNetData(),
    @SerializedName("experts")
    val experts: ArrayList<ExpertNetData> = arrayListOf()
) : Parcelable

@Parcelize
data class SessionNetData(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("sub")
    val sub: String = "",
    @SerializedName("dsc")
    val dsc: String = "",
    @SerializedName("lvl")
    val lvl: String = "",
    @SerializedName("dur")
    val dur: Int = 0,
    @SerializedName("int")
    val int: Float = 0.0F,
    @SerializedName("cal")
    val cal: Int = 0,
    @SerializedName("pct")
    var pct: List<String> = listOf()
) : Parcelable

@Parcelize
data class CourseDetailsNetData(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("header")
    val header: CourseHeaderNetData = CourseHeaderNetData(),
    @SerializedName("overview")
    val overview: OverViewNetData = OverViewNetData(),
    @SerializedName("sessions")
    val sessions: ArrayList<SessionNetData> = arrayListOf()
) : Parcelable
