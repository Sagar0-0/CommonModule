package fit.asta.health.data.spotify.utils

class SpotifyConstants {
    companion object{
        const val SPOTIFY_SONG_KEY_URI = "bundle_spotify_song"
        const val SPOTIFY_SONG_KEY_TYPE = "bundle_spotify_song_type"
        const val SPOTIFY_AUTH_REQUEST_CODE = 1337
        const val SPOTIFY_SCOPES =
            "user-follow-read,user-read-recently-played,user-read-playback-position,user-top-read,playlist-read-private,app-remote-control,streaming,user-read-email,user-read-private,user-library-read"
    }
}