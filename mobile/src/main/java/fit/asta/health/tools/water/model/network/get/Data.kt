package fit.asta.health.tools.water.model.network.get


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("beverageInfo")
    val beverageInfo: BeverageInfo,
    @SerializedName("beverageList")
    val beverageList: List<Beverage>,
    @SerializedName("progressData")
    val progressData: ProgressData,
    @SerializedName("quantityData")
    val quantityData: Any,
    @SerializedName("waterToolData")
    val waterToolData: WaterToolData
)