package fit.asta.health.common.utils

import android.net.Uri
import androidx.compose.runtime.Composable

@Composable
fun getImageModel(uri: Uri?, remoteUrl: String?): String? {
    return uri?.toString() ?: remoteUrl?.let { getImageUrl(remoteUrl) }
}