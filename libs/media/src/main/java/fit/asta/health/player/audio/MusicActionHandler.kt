package fit.asta.health.player.audio

import android.content.Context
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.media3.common.Player
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import dagger.hilt.android.qualifiers.ApplicationContext
import fit.asta.health.player.di.AppDispatchers
import fit.asta.health.player.di.Dispatcher
import fit.asta.health.player.domain.utils.AppIcons
import fit.asta.health.player.domain.utils.Constants.UNHANDLED_STATE_ERROR_MESSAGE
import fit.asta.health.player.domain.utils.Constants.UNKNOWN_CUSTOM_ACTION_ERROR_MESSAGE
import fit.asta.health.player.domain.utils.common.MusicCommands.REPEAT
import fit.asta.health.player.domain.utils.common.MusicCommands.REPEAT_ONE
import fit.asta.health.player.domain.utils.common.MusicCommands.REPEAT_SHUFFLE
import fit.asta.health.player.domain.utils.common.MusicCommands.SHUFFLE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject
import fit.asta.health.resources.strings.R as StrR

class MusicActionHandler @Inject constructor(
    @Dispatcher(AppDispatchers.MAIN) mainDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) {
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    private val customLayoutMap = mutableMapOf<String, CommandButton>()
    val customLayout: List<CommandButton> get() = customLayoutMap.values.toList()
    val customCommands = getAvailableCustomCommands()

    init {
        loadCustomLayout()
    }

    fun onCustomCommand(mediaSession: MediaSession, customCommand: SessionCommand) =
        when (customCommand.customAction) {
            REPEAT, REPEAT_ONE, SHUFFLE -> {
                handleRepeatShuffleCommand(
                    action = customCommand.customAction,
                    player = mediaSession.player
                )
            }
            else -> error("$UNKNOWN_CUSTOM_ACTION_ERROR_MESSAGE ${customCommand.customAction}")
        }

    fun cancelCoroutineScope() = coroutineScope.cancel()

    private fun handleRepeatShuffleCommand(action: String, player: Player) = when (action) {
        REPEAT -> {
            player.repeatMode = Player.REPEAT_MODE_ONE
            setRepeatShuffleCommand(REPEAT_ONE)
        }
        REPEAT_ONE -> {
            player.repeatMode = Player.REPEAT_MODE_ALL
            player.shuffleModeEnabled = true
            setRepeatShuffleCommand(SHUFFLE)
        }
        SHUFFLE -> {
            player.shuffleModeEnabled = false
            player.repeatMode = Player.REPEAT_MODE_ALL
            setRepeatShuffleCommand(REPEAT)
        }
        else -> error(UNHANDLED_STATE_ERROR_MESSAGE)
    }

    private fun setRepeatShuffleCommand(action: String) =
        customLayoutMap.set(REPEAT_SHUFFLE, customCommands.getValue(action))

    private fun loadCustomLayout() = customLayoutMap.run {
        this[REPEAT_SHUFFLE] = customCommands.getValue(REPEAT)
    }

    private fun getAvailableCustomCommands() = mapOf(
        REPEAT to buildCustomCommand(
            action = REPEAT,
            displayName = context.getString(StrR.string.repeat),
            iconResource = AppIcons.Repeat.resourceId
        ),
        REPEAT_ONE to buildCustomCommand(
            action = REPEAT_ONE,
            displayName = context.getString(StrR.string.repeat_one),
            iconResource = AppIcons.RepeatOne.resourceId
        ),
        SHUFFLE to buildCustomCommand(
            action = SHUFFLE,
            displayName = context.getString(StrR.string.shuffle),
            iconResource = AppIcons.Shuffle.resourceId
        )
    )
}

private fun buildCustomCommand(
    action: String,
    displayName: String,
    @DrawableRes iconResource: Int,
) = CommandButton.Builder()
    .setSessionCommand(SessionCommand(action, Bundle.EMPTY))
    .setDisplayName(displayName)
    .setIconResId(iconResource)
    .build()
