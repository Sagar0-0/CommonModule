package fit.asta.health.profile.data.userprofile


import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("email")
    var email: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("picUrl")
    var picUrl: String = ""
)