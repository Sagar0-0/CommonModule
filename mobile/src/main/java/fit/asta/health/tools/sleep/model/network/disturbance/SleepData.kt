package fit.asta.health.tools.sleep.model.network.disturbance

import com.google.gson.annotations.SerializedName

data class SleepData(
    @SerializedName("customPropertyData")
    val customPropertyData: List<PropertyData>?,
    @SerializedName("propertyData")
    val propertyData: List<PropertyData>?
)