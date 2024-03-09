package fit.asta.health.sunlight.remote.model

import com.google.gson.annotations.SerializedName

data class SunlightSessionData (
    var startTime:Long?=null,
    var endTime:Long?=null,
    val duration:Long?=null,
    val totalDs:Long?=null,
    @SerializedName("id"    ) var id    : String? = null,
    @SerializedName("uid"   ) var uid   : String? = null,
    @SerializedName("con"   ) var con   : Int?    = null,
    @SerializedName("dur"   ) var dur   : Long?    = null,
    @SerializedName("temp"  ) var temp  : Int?    = null,
    @SerializedName("uv"    ) var uv    : Int?    = null,
    @SerializedName("spf"   ) var spf   : String? = null,
    @SerializedName("exp"   ) var exp   : Int?    = null,
    @SerializedName("start" ) var start : String? = null,
    @SerializedName("end"   ) var end   : String? = null,
    @SerializedName("date") var date: Long? = null
){
    fun getDuration():Long{
        return (endTime?:0)-(startTime?:0)
    }
}