package fit.asta.health.player.domain.utils

import androidx.annotation.DrawableRes
import fit.asta.health.player.domain.utils.Icon.DrawableResourceIcon
import fit.asta.health.resources.drawables.R

object AppIcons {
    val Music = DrawableResourceIcon(R.drawable.ic_music)
    val Repeat = DrawableResourceIcon(R.drawable.ic_repeat)
    val RepeatOne = DrawableResourceIcon(R.drawable.ic_repeat_one)
    val Shuffle = DrawableResourceIcon(R.drawable.ic_shuffle)
    val SkipPrevious = DrawableResourceIcon(R.drawable.ic_skip_previous)
    val Play = DrawableResourceIcon(R.drawable.ic_play)
    val Pause = DrawableResourceIcon(R.drawable.ic_pause)
    val SkipNext = DrawableResourceIcon(R.drawable.ic_skip_next)
    val SkipForward = DrawableResourceIcon(R.drawable.fordward)
    val SkipBack = DrawableResourceIcon(R.drawable.fordward)
}

sealed interface Icon {
    data class DrawableResourceIcon(@DrawableRes val resourceId: Int) : Icon
}
