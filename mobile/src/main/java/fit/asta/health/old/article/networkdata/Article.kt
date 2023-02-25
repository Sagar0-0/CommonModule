package fit.asta.health.old.article.networkdata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class PointType(val value: Int) {

    BULLETS_TITLE(0),
    BULLETS_TEXT(1),
    NUMERIC_TITLE(2),
    NUMERIC_TEXT(3),
    ALPHA_TITLE(4),
    ALPHA_TEXT(5),
    KEY_VALUE(6),
    URLS(7);

    companion object {

        fun valueOf(value: Int) = values().first { it.value == value }
    }
}

enum class MediaType(val value: Int) {

    IMAGE(0),
    VIDEO(1),
    AUDIO(2);

    companion object {

        fun valueOf(value: Int) = values().first { it.value == value }
    }
}

enum class ContentType(val value: Int) {

    HEADER(0),
    SUB_TOPIC(1),
    TEXT(2),
    QUOTE(3),
    NOTE(4),
    IMAGE(5),
    VIDEO(6),
    POINTS(7),
    SOURCE(8);

    companion object {

        fun valueOf(value: Int) = values().first { it.value == value }
    }
}

@Parcelize
data class Media(
    val type: MediaType,
    val url: String,
    val title: String?
) : Parcelable

@Parcelize
data class Topic(
    val title: String?,
    val text: String?,
    val media: Media?
) : Parcelable

@Parcelize
data class Points(
    val type: PointType,
    val topics: ArrayList<Topic>
) : Parcelable

@Parcelize
data class Content(
    val type: ContentType,
    val title: String?,
    val text: String?,
    val points: Points?
) : Parcelable

@Parcelize
data class Author(
    val name: String,
    val imgUrl: String?,
    val about: String?
) : Parcelable

@Parcelize
data class Header(
    val title: String,
    val subTitle: String?
) : Parcelable

@Parcelize
data class MetaData(
    val adoLoc: String,
    val imgLoc: String,
    val vdoLoc: String
) : Parcelable

@Parcelize
data class Article(
    val metaData: MetaData?,
    val pubDate: String?,
    val author: Author?,
    //val header: Header?,
    val content: ArrayList<Content>
) : Parcelable
