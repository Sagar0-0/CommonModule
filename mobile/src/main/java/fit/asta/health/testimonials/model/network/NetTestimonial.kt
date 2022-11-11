package fit.asta.health.testimonials.model.network

import com.google.gson.annotations.SerializedName

data class NetTestimonial(
    @SerializedName("apv")
    val apv: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: TestimonialType,
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

enum class TestimonialType {
    @SerializedName("0")
    TEXT,

    @SerializedName("1")
    IMAGE,

    @SerializedName("2")
    VIDEO
}

data class NetMedia(
    @SerializedName("ttl")
    val title: String,
    @SerializedName("type")
    val type: Int,
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