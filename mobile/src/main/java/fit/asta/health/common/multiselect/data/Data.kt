package fit.asta.health.common.multiselect.data


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("tag")
    val tag: String = "",
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("values")
    val values: ArrayList<Value> = arrayListOf()
)