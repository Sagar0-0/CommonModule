package fit.asta.health.testimonials.model.network

import com.google.gson.annotations.SerializedName

data class NetTestimonial(
    @SerializedName("apv")
    val apv: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("mda")
    val media: List<NetMedia>?,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("text")
    val testimonial: String,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("uid")
    val userId: String,
    @SerializedName("user")
    val user: NetTestimonialUser
)

data class NetMedia(
    @SerializedName("name")
    val name: String,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("url")
    var url: String
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