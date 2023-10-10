package fit.asta.health.common.utils

import fit.asta.health.resources.drawables.R

fun String.toDraw() = when (this) {
    "msc" -> R.drawable.baseline_music_note_24
    "ins" -> R.drawable.baseline_merge_type_24
    "lvl" -> R.drawable.round_filter_vintage_24
    "ln" -> R.drawable.baseline_language_24
    "tgt" -> R.drawable.baseline_merge_type_24
    else -> R.drawable.baseline_merge_type_24
}