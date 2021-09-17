package fit.asta.health.player

import android.content.Context
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import org.json.JSONException


class MediaSourceAdapter(val context: Context, private val videos: ArrayList<Video>) {

    fun build(): MediaSource {

        // These factories are used to construct two media sources below
        /*val dataSource1: DataSource.Factory = DefaultDataSourceFactory(context, context.getString(R.string.app_name))*/
        val dataSource =
            VideoCacheFactory.getInstance(context, 2040 * 1024 * 1024, 50 * 1024 * 1024)

        /*val dashMediaSource = DashMediaSource.Factory(dataSource)*/
        val proMediaSource = ProgressiveMediaSource.Factory(dataSource)
        val concatenatingMediaSource = ConcatenatingMediaSource()

        try {

            for (inx in 0 until videos.size) {

                val mediaItem: MediaItem = MediaItem.fromUri(videos[inx].uri!!)
                val mediaSource: MediaSource = proMediaSource.createMediaSource(mediaItem)
                concatenatingMediaSource.addMediaSource(mediaSource)
            }

        } catch (e: JSONException) {

            e.printStackTrace()
        }

        return concatenatingMediaSource
    }
}