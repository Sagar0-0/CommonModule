package fit.asta.health.feature.spotify.utils


class SpotifyConstants {
    companion object {
        // Spotify Auth
        const val SPOTIFY_AUTH_REQUEST_CODE = 1337
        const val SPOTIFY_REDIRECT_URI = "fit.asta.health://callback"
        const val SPOTIFY_CLIENT_ID = "8f5ba8ca7b2a479aa6f766c931a6e8c4"
        const val SPOTIFY_SCOPES =
            "user-follow-read,user-read-recently-played,user-read-playback-position,user-top-read,playlist-read-private,app-remote-control,streaming,user-read-email,user-read-private,user-library-read"
    }
}