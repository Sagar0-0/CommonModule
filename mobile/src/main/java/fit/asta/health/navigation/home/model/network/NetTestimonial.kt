package fit.asta.health.navigation.home.model.network

import com.google.gson.annotations.SerializedName

data class NetTestimonial(
    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val userId: String,
    @SerializedName("apv")
    val approve: Boolean,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("mda")
    val media: List<NetMedia>,
    @SerializedName("user")
    val user: NetTestimonialUser
)

data class NetMedia(
    @SerializedName("type")
    val type: Int,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("url")
    val url: String
)

data class NetTestimonialUser(
    @SerializedName("name")
    val name: String,
    @SerializedName("org")
    val org: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("url")
    val url: String
)