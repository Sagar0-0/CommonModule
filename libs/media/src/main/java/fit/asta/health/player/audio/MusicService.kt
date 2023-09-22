package fit.asta.health.player.audio

import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.player.domain.utils.common.MusicNotificationProvider
import javax.inject.Inject

@UnstableApi @AndroidEntryPoint
class MusicService : MediaLibraryService() {
    private var mediaLibrarySession: MediaLibrarySession? = null

    @Inject
    lateinit var musicSessionCallback: MusicSessionCallback

    @Inject
    lateinit var musicNotificationProvider: MusicNotificationProvider

    @Inject
    lateinit var player: Player

    override fun onCreate() {
        super.onCreate()

        mediaLibrarySession =
            MediaLibrarySession.Builder(this, player, musicSessionCallback).build()

        setMediaNotificationProvider(musicNotificationProvider)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = mediaLibrarySession
    override fun onDestroy() {
        super.onDestroy()
        mediaLibrarySession?.run {
            player.release()
            release()
            mediaLibrarySession = null
        }
        musicSessionCallback.cancelCoroutineScope()
        musicNotificationProvider.cancelCoroutineScope()
    }
}
