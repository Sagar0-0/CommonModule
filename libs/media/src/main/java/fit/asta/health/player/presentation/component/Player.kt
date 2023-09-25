package fit.asta.health.player.presentation.component

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.Player
import androidx.media3.common.Timeline

@Composable
fun RememberPlayer(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    onPause: (Context) -> Unit,
    onStart: (Context) -> Unit
) {
    val currentContext = LocalContext.current.applicationContext
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when {
                (event == Lifecycle.Event.ON_START)
                        || (event == Lifecycle.Event.ON_RESUME) -> {
                    onStart(currentContext)
                }

                (event == Lifecycle.Event.ON_PAUSE)
                        || (event == Lifecycle.Event.ON_STOP) -> {
                    onPause(currentContext)
                }
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun rememberManagedPlayer(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    factory: (Context) -> Player
): State<Player?> {
    val currentContext = LocalContext.current.applicationContext
    val playerManager = remember { PlayerManager { factory(currentContext) } }
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when {
                (event == Lifecycle.Event.ON_START)
                        || (event == Lifecycle.Event.ON_RESUME) -> {
                    playerManager.initialize()
                    Log.d("devil", "rememberManagedPlayer:ON_RESUME ${playerManager.player}")
                }

                (event == Lifecycle.Event.ON_PAUSE)
                        || (event == Lifecycle.Event.ON_STOP) -> {
                    playerManager.release()
                    Log.d("devil", "rememberManagedPlayer:ON_PAUSE ${playerManager.player}")
                }
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
    return playerManager.player
}

@Stable
internal class PlayerManager(
    private val factory: () -> Player,
) : RememberObserver {
    var player = mutableStateOf<Player?>(null)
    private var rememberedState: Triple<String, Int, Long>? = null
    private val window: Timeline.Window = Timeline.Window()

    internal fun initialize() {
        if (player.value != null) return
        Log.d("devil", "initialize: ${player.value}")
        player.value = factory().also { player ->
            player.addListener(object : Player.Listener {
                override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                    // recover the remembered state if media id matched
                    rememberedState
                        ?.let { (id, index, position) ->
                            if (!timeline.isEmpty
                                && timeline.windowCount > index
                                && id == timeline.getWindow(index, window).mediaItem.mediaId
                            ) {
                                player.seekTo(index, position)
                            }
                        }
                        ?.also { rememberedState = null }
                }
            })
        }
    }

    internal fun release() {
        player.value?.let { player ->
            // remember the current state before release
            player.currentMediaItem?.let { mediaItem ->
                rememberedState = Triple(
                    mediaItem.mediaId,
                    player.currentMediaItemIndex,
                    player.currentPosition
                )
            }
            player.release()
        }
        player.value = null
    }

    override fun onAbandoned() {
        release()
    }

    override fun onForgotten() {
        release()
    }

    override fun onRemembered() {
    }
}
