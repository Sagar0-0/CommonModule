package fit.asta.health.thirdparty.spotify.utils

import android.content.Context
import android.util.DisplayMetrics


class SpotifyConstants {
    companion object {
        const val SHARED_PREF_SPOTIFY = "SPOTIFY_MUSIC"

        // Spotify Auth
        const val SHARED_PREF_SPOTIFY_TOKEN = "SHARED_PREF_SPOTIFY_TOKEN"
        const val SPOTIFY_AUTH_REQUEST_CODE = 1337
        const val SPOTIFY_REDIRECT_URI = "fit.asta.health://callback"
        const val SPOTIFY_CLIENT_ID = "8f5ba8ca7b2a479aa6f766c931a6e8c4"
        const val SPOTIFY_SCOPES =
            "user-follow-read,user-read-recently-played,user-read-playback-position,user-top-read,playlist-read-private,app-remote-control,streaming,user-read-email,user-read-private,user-library-read"

        // Spotify Web API
        const val SPOTIFY_BASE_URL = "https://api.spotify.com/v1/"
        const val TAG = "debug_spotify"

        var SPOTIFY_USER_ACCESS_TOKEN = ""
        const val SPOTIFY_USER_DETAILS = "SPOTIFY_USER_DETAILS"
        const val SPOTIFY_USER_TOKEN = "SPOTIFY_USER_TOKEN"
        const val SPOTIFY_TRACK_ID = "SPOTIFY_TRACK_ID"
        const val SPOTIFY_ALBUM_ID = "SPOTIFY_ALBUM_ID"


        fun convertPixelsToDp(px: Float, context: Context): Float {
            return px / (context.resources
                .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }

        fun convertDpToPixel(dp: Float, context: Context): Float {
            return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }
}