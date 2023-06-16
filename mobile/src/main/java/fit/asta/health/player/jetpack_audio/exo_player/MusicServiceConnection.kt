package fit.asta.health.player.jetpack_audio.exo_player

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.common.Player.EVENT_MEDIA_METADATA_CHANGED
import androidx.media3.common.Player.EVENT_PLAYBACK_STATE_CHANGED
import androidx.media3.common.Player.EVENT_PLAY_WHEN_READY_CHANGED
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import androidx.work.await
import dagger.hilt.android.qualifiers.ApplicationContext
import fit.asta.health.player.jetpack_audio.di.Dispatcher
import fit.asta.health.player.jetpack_audio.di.LoulaDispatchers
import fit.asta.health.player.jetpack_audio.domain.data.Song
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants.DEFAULT_INDEX
import fit.asta.health.player.jetpack_audio.domain.utils.MediaConstants.DEFAULT_POSITION_MS
import fit.asta.health.player.jetpack_audio.exo_player.common.MusicState
import fit.asta.health.player.jetpack_audio.exo_player.mapper.asMediaItem
import fit.asta.health.player.jetpack_audio.exo_player.mapper.asSong
import fit.asta.health.player.jetpack_audio.exo_player.util.Constants.POSITION_UPDATE_INTERVAL_MS
import fit.asta.health.player.jetpack_audio.exo_player.util.asPlaybackState
import fit.asta.health.player.jetpack_audio.exo_player.util.orDefaultTimestamp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@UnstableApi @Singleton
class MusicServiceConnection @Inject constructor(
    @ApplicationContext context: Context,
    @Dispatcher(LoulaDispatchers.MAIN) mainDispatcher: CoroutineDispatcher,
) {
    private var mediaBrowser: MediaBrowser? = null
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    private val _musicState = MutableStateFlow(MusicState())
    val musicState = _musicState.asStateFlow()

    val currentPosition = flow {
        while (currentCoroutineContext().isActive) {
            val currentPosition = mediaBrowser?.currentPosition ?: DEFAULT_POSITION_MS
            emit(currentPosition)
            delay(POSITION_UPDATE_INTERVAL_MS)
        }
    }

    init {
        coroutineScope.launch {
            mediaBrowser = MediaBrowser.Builder(
                context,
                SessionToken(context, ComponentName(context, MusicService::class.java))
            ).buildAsync().await().apply { addListener(PlayerListener()) }
        }
    }

    fun skipPrevious() = mediaBrowser?.run {
        seekToPrevious()
        play()
    }
     fun forword()= mediaBrowser?.run {
         seekForward()
     }
    fun backword()=mediaBrowser?.run {
        seekBack()
    }
    fun play() = mediaBrowser?.play()
    fun pause() = mediaBrowser?.pause()

    fun skipNext() = mediaBrowser?.run {
        seekToNext()
        play()
    }

    fun skipTo(position: Long) = mediaBrowser?.run {
        seekTo(position)
        play()
    }

    fun playSongs(
        songs: List<Song>,
        startIndex: Int = DEFAULT_INDEX,
        startPositionMs: Long = DEFAULT_POSITION_MS
    ) {
        mediaBrowser?.run {
            setMediaItems(songs.map(Song::asMediaItem), startIndex, startPositionMs)
            prepare()
            play()
        }
    }

    fun shuffleSongs(
        songs: List<Song>,
        startIndex: Int = DEFAULT_INDEX,
        startPositionMs: Long = DEFAULT_POSITION_MS
    ) = playSongs(
        songs = songs.shuffled(),
        startIndex = startIndex,
        startPositionMs = startPositionMs
    )

    private inner class PlayerListener : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            if (events.containsAny(
                    EVENT_PLAYBACK_STATE_CHANGED,
                    EVENT_MEDIA_METADATA_CHANGED,
                    EVENT_PLAY_WHEN_READY_CHANGED
                )
            ) {
                updateMusicState(player)
            }

        }

        override fun onTracksChanged(tracks: Tracks) {
            super.onTracksChanged(tracks)

        }
    }

    private fun updateMusicState(player: Player) = with(player) {
        _musicState.update {
            it.copy(
                currentSong = currentMediaItem.asSong(),
                playbackState = playbackState.asPlaybackState(),
                playWhenReady = playWhenReady,
                duration = duration.orDefaultTimestamp()
            )
        }
    }

}
