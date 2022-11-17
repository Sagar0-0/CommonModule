package fit.asta.health.thirdparty.spotify.view.bindingAdapters

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.Display
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.card.MaterialCardView
import fit.asta.health.thirdparty.spotify.model.net.categories.Icon
import fit.asta.health.thirdparty.spotify.model.net.common.ArtistX
import fit.asta.health.thirdparty.spotify.model.net.common.Image
import fit.asta.health.thirdparty.spotify.model.net.top.ItemTopTrack
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants
import fit.asta.health.thirdparty.spotify.view.activity.SpotifyAlbumDetailsActivity
import fit.asta.health.thirdparty.spotify.view.activity.SpotifyTracksDetailsActivity


class SpotifyBindingAdapter {
    companion object {

        @BindingAdapter("spotify:updateWidthOfUI")
        @JvmStatic
        fun updateWidthOfUI(materialCardView: MaterialCardView, itemCount: Int) {
            val context = materialCardView.context
            val display: Display = (context as Activity).windowManager.defaultDisplay
            val width: Int = display.width
            val height: Int = display.height
            materialCardView.layoutParams = ViewGroup.LayoutParams(
                width / itemCount, materialCardView.height
            )
            materialCardView.requestLayout()//It is necesary to refresh the screen
        }

        @BindingAdapter("spotify:loadImageFromURL")
        @JvmStatic
        fun loadImageFromURL(imagePlaceholder: ImageView, images: List<Image>?) {
            if (images?.isNotEmpty() == true) {
                Glide
                    .with(imagePlaceholder.context)
                    .load(images[0].url)
                    .transform(
                        RoundedCorners(
                            SpotifyConstants.convertDpToPixel(
                                8f,
                                imagePlaceholder.context
                            ).toInt()
                        )
                    )
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imagePlaceholder)
            }
        }

        @BindingAdapter("spotify:loadTrackDetailsCollection")
        @JvmStatic
        fun loadTrackDetailsCollection(textView: TextView, trackItemTopTrack: ItemTopTrack) {
            var text: String = ""
            trackItemTopTrack.artists.forEach {
                text = text + it.name + ", "
            }
            text = text.removeRange(text.length - 2, text.length - 1)
            textView.text = text
        }

        @BindingAdapter("spotify:loadArtistsCollection")
        @JvmStatic
        fun loadArtistsCollection(textView: TextView, item: List<ArtistX>) {
            var text: String = ""
            item.forEach {
                text = text + it.name + ", "
            }
            text = text.removeRange(text.length - 2, text.length - 1)
            textView.text = text
        }

        @BindingAdapter("spotify:loadCircularImageFromURL")
        @JvmStatic
        fun loadCircularImageFromURL(imagePlaceholder: ImageView, images: List<Image>) {
            if (images.isNotEmpty()) {
                Glide
                    .with(imagePlaceholder.context)
                    .load(images[0].url)
                    .centerCrop()
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imagePlaceholder)
            }
        }

        @BindingAdapter("spotify:openInSpotify")
        @JvmStatic
        fun openInSpotify(container: LinearLayout, uri: String) {
            val context = container.context
            container.setOnClickListener {
                val defaultBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(context, defaultBrowser, null)
            }
        }

        @BindingAdapter("spotify:openTrackDetails")
        @JvmStatic
        fun openTrackDetails(container: LinearLayout, id: String) {
            val context = container.context
            container.setOnClickListener {
                val intent = Intent(context, SpotifyTracksDetailsActivity::class.java)
                intent.putExtra(SpotifyConstants.SPOTIFY_TRACK_ID, id)
                intent.putExtra(
                    SpotifyConstants.SPOTIFY_USER_TOKEN,
                    SpotifyConstants.SPOTIFY_USER_ACCESS_TOKEN
                )
                context.startActivity(intent)
            }
        }

        @BindingAdapter("spotify:openAlbumDetails")
        @JvmStatic
        fun openAlbumDetails(container: LinearLayout, id: String) {
            val context = container.context
            container.setOnClickListener {
                val intent = Intent(context, SpotifyAlbumDetailsActivity::class.java)
                intent.putExtra(SpotifyConstants.SPOTIFY_ALBUM_ID, id)
                intent.putExtra(
                    SpotifyConstants.SPOTIFY_USER_TOKEN,
                    SpotifyConstants.SPOTIFY_USER_ACCESS_TOKEN
                )
                context.startActivity(intent)
            }
        }

        @BindingAdapter("spotify:loadIconFromURL")
        @JvmStatic
        fun loadIconFromURL(imagePlaceholder: ImageView, images: List<Icon>) {
            if (images.isNotEmpty()) {
                Glide
                    .with(imagePlaceholder.context)
                    .load(images[0].url)
                    .transform(
                        RoundedCorners(
                            SpotifyConstants.convertDpToPixel(
                                8f,
                                imagePlaceholder.context
                            ).toInt()
                        )
                    )
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imagePlaceholder)
            }
        }
    }
}