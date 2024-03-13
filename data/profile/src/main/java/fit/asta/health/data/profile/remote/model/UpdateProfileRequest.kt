package fit.asta.health.data.profile.remote.model


import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("list")
    val list: List<UpdateObject>? = null
)

interface UpdateObject

data class UpdateObjectString(
    @SerializedName("obj")
    val screenName: String = "",
    @SerializedName("field")
    val fieldName: String = "",
    @SerializedName("val")
    val value: String = "",
) : UpdateObject

data class UpdateObjectInt(
    @SerializedName("obj")
    val screenName: String = "",
    @SerializedName("field")
    val fieldName: String = "",
    @SerializedName("val")
    val value: Int? = 0,
) : UpdateObject

data class UpdateObjectDouble(
    @SerializedName("obj")
    val screenName: String = "",
    @SerializedName("field")
    val fieldName: String = "",
    @SerializedName("val")
    val value: Double = 0.0,
) : UpdateObject

data class UpdateObjectTimeSchedule(
    @SerializedName("obj")
    val screenName: String = "",
    @SerializedName("field")
    val fieldName: String = "",
    @SerializedName("val")
    val value: TimeSchedule = TimeSchedule(),
) : UpdateObject

data class UpdateObjectPropertiesList(
    @SerializedName("obj")
    val screenName: String = "",
    @SerializedName("field")
    val fieldName: String = "",
    @SerializedName("val")
    val value: List<UserProperties> = listOf(),
) : UpdateObject