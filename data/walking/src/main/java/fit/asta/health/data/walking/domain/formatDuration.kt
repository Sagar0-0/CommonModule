package fit.asta.health.data.walking.domain

fun formatDuration(minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60

    return "%02d:%02d".format(hours, remainingMinutes)
}