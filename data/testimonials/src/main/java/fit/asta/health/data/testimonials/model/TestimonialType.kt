package fit.asta.health.data.testimonials.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class TestimonialType(
    val value: Int,
    val title: String
) : Parcelable {

    object TEXT : TestimonialType(value = 0, title = "Text")
    object IMAGE : TestimonialType(value = 1, title = "Image")
    object VIDEO : TestimonialType(value = 2, title = "Video")

    companion object {
        fun from(code: Int): TestimonialType {
            return when (code) {
                0 -> TEXT
                1 -> IMAGE
                2 -> VIDEO
                else -> TEXT
            }
        }
    }
}

/*
enum class TestimonialType(val value: Int) {
    @SerializedName("0")
    TEXT(0),

    @SerializedName("1")
    IMAGE(1),

    @SerializedName("2")
    VIDEO(2);

    companion object {
        fun from(value: Int) = values().first { it.value == value }
    }
}

enum class MediaType(val value: Int) {
    @SerializedName("0")
    IMAGE(0),

    @SerializedName("1")
    AUDIO(1),

    @SerializedName("2")
    VIDEO(2);

    companion object {
        fun from(value: Int) = values().first { it.value == value }
    }
}
*/