package fit.asta.health.common.utils

import android.net.Uri
import androidx.compose.runtime.Composable

@Composable
fun getOneUrl(localUrl: Uri?, remoteUrl: String): String {
    return localUrl?.toString() ?: getImgUrl(remoteUrl)
}