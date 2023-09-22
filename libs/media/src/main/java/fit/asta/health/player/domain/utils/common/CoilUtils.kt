package fit.asta.health.player.domain.utils.common

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.size.Scale
import fit.asta.health.resources.drawables.R

internal suspend fun Uri.asArtworkBitmap(context: Context): Bitmap? {
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(this)
        .placeholder(R.drawable.music_cover)
        .error(R.drawable.music_cover)
        .scale(Scale.FILL)
        .build()

    val drawable = loader.execute(request).drawable
    return drawable?.toBitmap()
}
