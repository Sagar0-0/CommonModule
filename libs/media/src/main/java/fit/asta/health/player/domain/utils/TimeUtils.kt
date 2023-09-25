package fit.asta.health.player.domain.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds
import fit.asta.health.resources.strings.R as StrR

@Composable
fun Long.asFormattedString() = milliseconds.toComponents { minutes, seconds, _ ->
    stringResource(
        id = StrR.string.player_timestamp_format,
        String.format(locale = Locale.US, format = "%02d", minutes),
        String.format(locale = Locale.US, format = "%02d", seconds)
    )
}

@Composable
fun Int.asFormattedString() = stringResource(
    id = StrR.string.player_timestamp_format,
    String.format(locale = Locale.US, format = "%02d", this / 60),
    String.format(locale = Locale.US, format = "%02d", this % 60)
)


fun convertToProgress(count: Long, total: Long) =
    ((count * ProgressDivider) / total / ProgressDivider).takeIf(Float::isFinite) ?: ZeroProgress

fun convertToPosition(value: Float, total: Long) = (value * total).toLong()

private const val ProgressDivider = 100f
private const val ZeroProgress = 0f
