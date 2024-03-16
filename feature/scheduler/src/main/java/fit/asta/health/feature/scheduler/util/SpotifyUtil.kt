package fit.asta.health.feature.scheduler.util

import android.content.Context
import android.content.pm.PackageManager
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import fit.asta.health.core.network.BuildConfig
import fit.asta.health.data.spotify.utils.SpotifyConstants

object SpotifyUtil {
    fun getSpotifyRequest(): AuthorizationRequest? {
        return AuthorizationRequest.Builder(
            BuildConfig.SPOTIFY_CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            BuildConfig.SPOTIFY_REDIRECT_URI
        )
            .setShowDialog(false)
            .setScopes(arrayOf(SpotifyConstants.SPOTIFY_SCOPES))
            .build()
    }

    fun getConnectionParams(): ConnectionParams? {
       return ConnectionParams.Builder(BuildConfig.SPOTIFY_CLIENT_ID)
            .setRedirectUri(BuildConfig.SPOTIFY_REDIRECT_URI)
            .showAuthView(false)
            .build()
    }

    fun isSpotifyInstalled(context: Context): Boolean {
        val packageName = "com.spotify.music"
        return try {
            context.packageManager?.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}