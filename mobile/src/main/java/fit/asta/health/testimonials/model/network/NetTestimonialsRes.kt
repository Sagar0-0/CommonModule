package fit.asta.health.testimonials.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetTestimonialsRes(
    @SerializedName("status") val status: Status,
    @SerializedName("data") val testimonials: List<NetTestimonial>,
)