package fit.asta.health.testimonials.model.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
sealed class TestimonialType(
    val value: Int,
    val title: String,
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
    TEXT(0),
    IMAGE(1),
    VIDEO(2);

    companion object {
        fun from(value: Int) = values().first { it.value == value }
    }
}

enum class MediaType(val value: Int) {
    IMAGE(0),
    AUDIO(1),
    VIDEO(2);

    companion object {
        fun from(value: Int) = values().first { it.value == value }
    }
}*/