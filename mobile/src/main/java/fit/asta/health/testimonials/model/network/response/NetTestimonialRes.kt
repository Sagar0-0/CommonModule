package fit.asta.health.testimonials.model.network.response

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.network.NetTestimonial

data class NetTestimonialRes(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val data: NetTestimonial
)

