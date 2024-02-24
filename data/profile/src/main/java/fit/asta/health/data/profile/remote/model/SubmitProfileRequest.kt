package fit.asta.health.data.profile.remote.model


import com.google.gson.annotations.SerializedName

data class SubmitProfileRequest(
    @SerializedName("field")
    val fieldName: String = "",
    @SerializedName("obj")
    val screenName: String = "",
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("val")
    val value: Any? = null
)