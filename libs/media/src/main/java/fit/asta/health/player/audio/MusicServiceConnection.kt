package fit.asta.health.player.audio

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.EVENT_MEDIA_METADATA_CHANGED
import androidx.media3.common.Player.EVENT_PLAYBACK_STATE_CHANGED
import androidx.media3.common.Player.EVENT_PLAY_WHEN_READY_CHANGED
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import androidx.work.await
import dagger.hilt.android.qualifiers.ApplicationContext
import fit.asta.health.player.audio.common.MusicState
import fit.asta.health.player.di.AppDispatchers
import fit.asta.health.player.di.Dispatcher
import fit.asta.health.player.domain.mapper.asMediaItem
import fit.asta.health.player.domain.mapper.asSong
import fit.asta.health.player.domain.model.Song
import fit.asta.health.player.domain.utils.Constants.POSITION_UPDATE_INTERVAL_MS
import fit.asta.health.player.domain.utils.MediaConstants.DEFAULT_INDEX
import fit.asta.health.player.domain.utils.MediaConstants.DEFAULT_POSITION_MS
import fit.asta.health.player.domain.utils.asPlaybackState
import fit.asta.health.player.domain.utils.orDefaultTimestamp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    @Dispatcher(AppDispatchers.MAIN) mainDispatcher: CoroutineDispatcher,
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
//    val currentProgress = flow {
//        while (currentCoroutineContext().isActive) {
//            val process= (mediaBrowser?.contentPosition?.div(1000) ?: 1) /60
//            emit(process)
//            delay(60000)
//        }
//    }
    private val _currentProgress = MutableSharedFlow<Long>()
    val currentProgress =_currentProgress.asSharedFlow()

    init {
        coroutineScope.launch {
            mediaBrowser = MediaBrowser.Builder(
                context,
                SessionToken(context, ComponentName(context, MusicService::class.java))
            ).buildAsync().await().apply { addListener(PlayerListener()) }
            while (currentCoroutineContext().isActive) {
            val process= (mediaBrowser?.contentPosition?.div(1000) ?: 1) /60
            _currentProgress.emit(process)
            delay(60000)
        }
        }
    }

    fun release( context: Context){
        mediaBrowser?.release()
        val stopIntent = Intent(context, MusicService::class.java)
        context.stopService(stopIntent)
    }
    fun skipPrevious() = mediaBrowser?.run {
        seekToPrevious()
        play()
    }
     fun forward()= mediaBrowser?.run {
         seekForward()
     }
    fun backward()=mediaBrowser?.run {
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
//        mediaBrowser?.release()
    }

    fun changeTrack(mediaItem: MediaItem) {
        mediaBrowser?.run {
            replaceMediaItem(currentMediaItemIndex, mediaItem)
        }
    }

    fun playIndex(index: Int) {
        mediaBrowser?.seekTo(index, DEFAULT_POSITION_MS)
    }

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
