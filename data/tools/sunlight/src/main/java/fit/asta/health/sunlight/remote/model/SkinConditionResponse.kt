package fit.asta.health.sunlight.remote.model

import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.Prc
import fit.asta.health.common.utils.Value



data class SkinConditionResponseData (
    @SerializedName("id"    ) var id    : String? = null,
    @SerializedName("code"  ) var code  : String? = null,
    @SerializedName("type"  ) var type  : Int?    = null,
    @SerializedName("dsc"   ) var dsc   : String? = null,
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("since" ) var since : Int?    = null,
    @SerializedName("url"   ) var url   : String? = null
){
    fun toCommonPrc(screenName:String,screenCode:String):Prc{
        return Prc(
            code=screenCode,
            dsc="",
            isMultiSel = false,
            ttl =screenName,
            type=1,
            values = listOf(
                Value(
                    dsc=dsc?:"",
                    url=url?:"",
                    code = code?:"",
                    ttl = name?:"",
                    id = id?:""
                )
            )
        )
    }
    fun toCommonValue(): Value {
        return Value(
            dsc=dsc?:"",
            url=url?:"",
            code = code?:"",
            ttl = name?:"",
            id = id?:""
        )
    }
}
