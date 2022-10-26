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
    @SerializedName("mda")
    val mda: List<Mda>,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("uid")
    val uid: String
)

data class Mda(
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("url")
    val url: String
)