package fit.asta.health.feature.walking.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SpeedRecord
import fit.asta.health.common.health_data.dateTimeWithOffsetOrDefault
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Displays a list of [SpeedRecord] data in the [LazyColumn].
 */
fun LazyListScope.speedSeries(
    @StringRes labelId: Int,
    series: List<SpeedRecord>
) {
    seriesHeading(labelId)
    series.forEach { serie ->
        seriesDateTimeHeading(
            start = serie.startTime,
            startZoneOffset = serie.startZoneOffset,
            end = serie.endTime,
            endZoneOffset = serie.endZoneOffset
        )
        items(serie.samples) { SeriesRow(String.format("%.1f", it.speed.inMetersPerSecond)) }
    }
}

/**
 * Displays a list of [HeartRateRecord] data in the [LazyColumn].
 */
fun LazyListScope.heartRateSeries(
    @StringRes labelId: Int,
    series: List<HeartRateRecord>
) {
    seriesHeading(labelId)
    series.forEach { serie ->
        seriesDateTimeHeading(
            start = serie.startTime,
            startZoneOffset = serie.startZoneOffset,
            end = serie.endTime,
            endZoneOffset = serie.endZoneOffset
        )
        items(serie.samples) { SeriesRow(it.beatsPerMinute.toString()) }
    }
}

fun LazyListScope.seriesHeading(labelId: Int) {
    item {
        BodyTexts.Level2(
            text = stringResource(id = labelId),
        )
    }
}

fun LazyListScope.seriesDateTimeHeading(
    start: Instant,
    startZoneOffset: ZoneOffset?,
    end: Instant,
    endZoneOffset: ZoneOffset?
) {
    item {
        val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
        val startTime = dateTimeWithOffsetOrDefault(start, startZoneOffset)
        val endTime = dateTimeWithOffsetOrDefault(end, endZoneOffset)
        val dateLabel = dateFormatter.format(startTime)
        val startLabel = timeFormatter.format(startTime)
        val endLabel = timeFormatter.format(endTime)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            BodyTexts.Level2(
                color = AppTheme.colors.secondary,
                text = "$dateLabel: $startLabel - $endLabel",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SeriesRow(value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BodyTexts.Level2("Sample: $value")
    }
}
