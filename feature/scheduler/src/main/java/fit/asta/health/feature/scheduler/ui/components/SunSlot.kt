package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Waves
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fit.asta.health.data.scheduler.remote.model.WeatherData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.functional.ScheduleIconLayout
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.resources.strings.R as StringR


@Composable
fun WeatherCard(weatherData: WeatherData, modifier: Modifier = Modifier, onSchedule: () -> Unit) {

    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level2)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = AppTheme.spacing.level3,
                vertical = AppTheme.spacing.level2
            ),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BodyTexts.Level1(text = weatherData.title)
                Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
                ScheduleIconLayout(onButtonClick = onSchedule)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            ) {
                AppIcon(
                    imageVector = Icons.Default.Event,
                    contentDescription = stringResource(StringR.string.event_icon)
                )
                BodyTexts.Level3(text = weatherData.time)
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            ) {
                AppIcon(
                    imageVector = Icons.Default.Thermostat,
                    contentDescription = stringResource(StringR.string.temperature_icon)
                )
                BodyTexts.Level3(text = weatherData.temperature)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            ) {
                AppIcon(
                    imageVector = Icons.Default.Waves,
                    contentDescription = stringResource(StringR.string.event_icon)
                )
                BodyTexts.Level3(text = weatherData.uvDetails)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            ) {
                AppIcon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = stringResource(StringR.string.temperature_icon)
                )
                BodyTexts.Level3(text = weatherData.timeSlot)
            }
        }
    }
}