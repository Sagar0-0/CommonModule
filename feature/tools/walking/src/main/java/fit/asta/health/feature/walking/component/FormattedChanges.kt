package fit.asta.health.feature.walking.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.changes.Change
import androidx.health.connect.client.changes.DeletionChange
import androidx.health.connect.client.changes.UpsertionChange
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SpeedRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.WeightRecord
import fit.asta.health.common.health_data.dateTimeWithOffsetOrDefault
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import java.time.ZonedDateTime
import fit.asta.health.resources.strings.R as StrR

/**
 * Composables for formatting [Change] objects returned from Health Connect.
 */
@Composable
fun FormattedChange(change: Change) {
    when (change) {
        is UpsertionChange -> FormattedUpsertionChange(change)
        is DeletionChange -> FormattedDeletionChange(change)
    }
}

@Composable
fun FormattedUpsertionChange(change: UpsertionChange) {
    when (change.record) {
        is ExerciseSessionRecord -> {
            val activity = change.record as ExerciseSessionRecord
            FormattedChangeRow(
                startTime = dateTimeWithOffsetOrDefault(
                    activity.startTime,
                    activity.startZoneOffset
                ),
                recordType = stringResource(StrR.string.differential_changes_type_exercise_session),
                dataSource = change.record.metadata.dataOrigin.packageName
            )
        }

        is StepsRecord -> {
            val steps = change.record as StepsRecord
            FormattedChangeRow(
                startTime = dateTimeWithOffsetOrDefault(steps.startTime, steps.startZoneOffset),
                recordType = stringResource(StrR.string.differential_changes_type_steps),
                dataSource = change.record.metadata.dataOrigin.packageName
            )
        }

        is SpeedRecord -> {
            val speed = change.record as SpeedRecord
            FormattedChangeRow(
                startTime = dateTimeWithOffsetOrDefault(speed.startTime, speed.startZoneOffset),
                recordType = stringResource(StrR.string.differential_changes_type_speed_series),
                dataSource = change.record.metadata.dataOrigin.packageName
            )
        }

        is HeartRateRecord -> {
            val hr = change.record as HeartRateRecord
            FormattedChangeRow(
                startTime = dateTimeWithOffsetOrDefault(hr.startTime, hr.startZoneOffset),
                recordType = stringResource(StrR.string.differential_changes_type_heart_rate_series),
                dataSource = change.record.metadata.dataOrigin.packageName
            )
        }

        is TotalCaloriesBurnedRecord -> {
            val calories = change.record as TotalCaloriesBurnedRecord
            FormattedChangeRow(
                startTime = dateTimeWithOffsetOrDefault(
                    calories.startTime,
                    calories.startZoneOffset
                ),
                recordType = stringResource(StrR.string.differential_changes_type_total_calories),
                dataSource = change.record.metadata.dataOrigin.packageName
            )
        }

        is SleepSessionRecord -> {
            val sleep = change.record as SleepSessionRecord
            FormattedChangeRow(
                startTime = dateTimeWithOffsetOrDefault(sleep.startTime, sleep.startZoneOffset),
                recordType = stringResource(StrR.string.differential_changes_type_sleep_session),
                dataSource = change.record.metadata.dataOrigin.packageName
            )
        }

        is WeightRecord -> {
            val weight = change.record as WeightRecord
            FormattedChangeRow(
                startTime = dateTimeWithOffsetOrDefault(weight.time, weight.zoneOffset),
                recordType = stringResource(StrR.string.differential_changes_type_weight),
                dataSource = change.record.metadata.dataOrigin.packageName
            )
        }

        is DistanceRecord -> {
            val distance = change.record as DistanceRecord
            FormattedChangeRow(
                startTime = dateTimeWithOffsetOrDefault(
                    distance.startTime,
                    distance.startZoneOffset
                ),
                recordType = stringResource(StrR.string.differential_changes_type_distance),
                dataSource = change.record.metadata.dataOrigin.packageName
            )
        }

        else -> {
            Log.w("TAG", "Unknown record type: ${change.record}")
        }
    }
}

@Composable
fun FormattedDeletionChange(change: DeletionChange) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            BodyTexts.Level2(
                text = stringResource(StrR.string.differential_changes_deleted),

                )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BodyTexts.Level2("UID: ${change.recordId}")
        }
    }
}

@Composable
fun FormattedChangeRow(
    startTime: ZonedDateTime,
    recordType: String,
    dataSource: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            BodyTexts.Level2(
                text = stringResource(StrR.string.differential_changes_upserted),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BodyTexts.Level2("${startTime.toLocalTime()}")
            BodyTexts.Level2(recordType)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            BodyTexts.Level2(
                text = dataSource,
            )
        }
    }
}
