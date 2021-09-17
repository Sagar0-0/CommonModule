package fit.asta.health.profile.data.userprofile


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("contact")
    var contact: Contact = Contact(),
    @SerializedName("health")
    var health: List<Health> = listOf(),
    @SerializedName("lifestyle")
    var lifestyle: Lifestyle = Lifestyle(),
    @SerializedName("physique")
    var physique: List<Physique> = listOf(),
    @SerializedName("userId")
    var userId: String = ""
)