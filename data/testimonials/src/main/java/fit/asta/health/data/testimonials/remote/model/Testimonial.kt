package fit.asta.health.data.testimonials.remote.model

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.UiString
import kotlinx.parcelize.Parcelize

@Parcelize
data class Testimonial(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("apv")
    val isApproved: Boolean = false,
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("rank")
    val rank: Int = -1,
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("text")
    val testimonial: String = "",
    @SerializedName("user")
    val user: TestimonialUser = TestimonialUser(),
    @SerializedName("beforeImg")
    val beforeImage: Media? = null,
    @SerializedName("afterImg")
    val afterImage: Media? = null,
    @SerializedName("video")
    val videoMedia: Media? = null
) : Parcelable

@Parcelize
data class TestimonialUser(
    @SerializedName("uid")
    val userId: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("org")
    val org: String = "",
    @SerializedName("role")
    val role: String = "",
    @SerializedName("url")
    val url: String = ""
) : Parcelable

@Parcelize
data class Media(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
    @Transient
    var localUrl: Uri? = null,
    @Transient
    val error: UiString = UiString.Empty
) : Parcelable