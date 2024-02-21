package fit.asta.health.sunlight.remote.model

import com.google.gson.annotations.SerializedName


data class SessionDetailBody(
    @SerializedName("id") var id: String? = null,
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("con") var con: Int? = null,
    @SerializedName("dur") var dur: Long? = null,
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("uv") var uv: Int? = null,
    @SerializedName("spf") var spf: String? = null,
    @SerializedName("start") var start: String? = null,
    @SerializedName("end") var end: String? = null,
    @SerializedName("exp") var exp: Int? = null
)