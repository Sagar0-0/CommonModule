package fit.asta.health.testimonials.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetTestimonialsRes(
    @SerializedName("data")
    val testimonials: List<NetTestimonial>,
    @SerializedName("status")
    val status: Status
)

data class NetTestimonial(
    @SerializedName("apv")
    val apv: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: TestimonialType,
    @SerializedName("mda")
    val media: List<Media>?,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("text")
    val testimonial: String,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("uid")
    val userId: String,
    @SerializedName("user")
    val user: User
)

enum class TestimonialType {
    TEXT, IMAGE, VIDEO
}

data class Media(
    @SerializedName("ttl")
    val title: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("url")
    val url: String
)

data class User(
    @SerializedName("name")
    val name: String,
    @SerializedName("org")
    val org: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("url")
    val url: String
)