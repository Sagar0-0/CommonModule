package fit.asta.health.common.jetpack

import android.net.Uri
import androidx.compose.runtime.Composable
import fit.asta.health.common.utils.getImgUrl

@Composable
fun getOneUrl(localUrl: Uri?, remoteUrl: String): String {
    return localUrl?.toString() ?: getImgUrl(remoteUrl)
}