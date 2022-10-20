package fit.asta.health.testimonials.model.network

import com.google.gson.annotations.SerializedName

data class NetTestimonial(
    @SerializedName("dsc")
    val desc: String,
    @SerializedName("id")
    val id: String,
)
