package fit.asta.health.sunlight.remote.model

import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.Prc
import fit.asta.health.sunlight.utils.ChartUtils


data class SunlightHomeData(

    @SerializedName("sunLightData") var sunLightData: SunLightData? = SunLightData(),
    @SerializedName("sunSlotData") var sunSlotData: SunSlotData? = SunSlotData(),
    @SerializedName("SunLightProgressData") var sunLightProgressData: SunLightProgressData? = SunLightProgressData(),
    @SerializedName("tips") var tips: Tips? = Tips()

)

data class Tips(

    @SerializedName("id") var id: String? = null,
    @SerializedName("tips") var tips: ArrayList<String> = arrayListOf()

)

data class SunLightProgressData(

    @SerializedName("id") var id: String? = null,
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("tgt") var tgt: Int? = null,
    @SerializedName("ach") var ach: Int? = null,
    @SerializedName("rem") var rem: Long? = null,
    @SerializedName("rcm") var rcm: Long? = null,
    @SerializedName("tgtIu") var tgtIu: Int? = null,
    @SerializedName("rcmIu") var rcmIu: Int? = null,
    @SerializedName("achIu") var achIu: Long? = null,
    @SerializedName("remIu") var remIu: Int? = null,
    @SerializedName("sup") var sup: Int? = null,
    @SerializedName("supIu") var supIu: Int? = null,
    @SerializedName("metIu") var metIu: Int? = null,
    @SerializedName("meta") var meta: Meta? = Meta()

)

data class Meta(

    @SerializedName("min") var min: String? = null,
    @SerializedName("max") var max: String? = null

)

data class SunSlotData(

    @SerializedName("id") var id: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("lat") var lat: Float? = null,
    @SerializedName("lon") var lon: Float? = null,
    @SerializedName("loc") var loc: String? = null,
    @SerializedName("currUv") var currUv: Double? = 0.0,
    @SerializedName("currTemp") var currTemp: Double? = 0.0,
    @SerializedName("day1") var slot: ArrayList<Slot>? = null,


    ) {
    fun toChartData(): List<PieChartInput> {
        val list = ArrayList<PieChartInput>()
        slot?.sortBy {
            it.time ?: ""
        }
        slot?.forEach { slotData ->
            list.add(
                PieChartInput(
                    1, "", slotData.time,
                    uv = try {
                        (slotData.uv?.toInt() ?: 0).toString()
                    } catch (e: Exception) {
                        "0"
                    },
                    temp = try {
                        (slotData.uv ?: 0.0).toString()
                    } catch (e: Exception) {
                        "0"
                    }
                )
            )
            if (ChartUtils.isHourMatchingWithCurrentTime(slotData.time ?: "")) {
                currUv = slotData.uv
                currTemp = slotData.temp
            }
        }
        return list
    }
}

data class Slot(

    @SerializedName("time") var time: String? = null,
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("uv") var uv: Double? = null

)

data class SunLightData(

    @SerializedName("id") var id: String? = null,
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("type") var type: Int? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("prc") var prc: ArrayList<Prc> = arrayListOf(),
    @SerializedName("sup") var sup: Sup? = Sup(),
    @SerializedName("iu/min") var iuPerMin: Long? = null

)

data class Sup(

    @SerializedName("prd") var prd: String? = null,
    @SerializedName("iu") var iu: Int? = null,
    @SerializedName("unit") var unit: String? = null,
    @SerializedName("intake") var intake: Boolean? = null

) {
    fun supWithUnit(): String {
        return "${iu ?: "0"} ${unit ?: "IU"}"
    }
}
