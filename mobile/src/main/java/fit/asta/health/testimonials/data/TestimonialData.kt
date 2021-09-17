package fit.asta.health.testimonials.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestimonialData(
    var uid: String = "",
    var userId: String = "",
    var imageURL: String = "",
    var testimonial: String = "",
    var name: String = "",
    var designation: String = "",
    var organization: String = ""
) : Parcelable