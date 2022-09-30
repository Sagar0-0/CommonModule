package fit.asta.health.navigation.home.model.network

import com.google.gson.annotations.SerializedName

data class Testimonial(
    @SerializedName("id")
    val id: String,
    @SerializedName("apv")
    val approve: Boolean,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("mda")
    val media: List<Media>,
    @SerializedName("user")
    val user: User
)

data class Media(
    @SerializedName("type")
    val type: Int,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("url")
    val url: String
)

data class User(
    @SerializedName("uid")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("org")
    val org: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("url")
    val url: String
)