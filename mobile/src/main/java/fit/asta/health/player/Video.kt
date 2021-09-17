package fit.asta.health.player

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    var uid: String = "",
    var title: String = "",
    var subTitle: String = "",
    var uri: Uri? = null
) : Parcelable


@Parcelize
data class VideoListState(
    val uid: String?,
    val statePlayedAll: Boolean,
    val listSize: Int,
    val currentIndex: Int,
    val currentPosition: Long
) : Parcelable