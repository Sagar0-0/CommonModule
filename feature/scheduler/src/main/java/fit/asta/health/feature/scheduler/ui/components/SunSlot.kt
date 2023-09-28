package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import fit.asta.health.designsystem.components.functional.ScheduleIconLayout
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.AppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import fit.asta.health.resources.strings.R as StringR


@Composable
fun WeatherCard(weatherData: WeatherData, modifier: Modifier = Modifier, onSchedule: () -> Unit) {

    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppTheme.appSpacing.small)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = AppTheme.appSpacing.medium,
                vertical = AppTheme.appSpacing.small
            ),
            verticalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.small)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppTexts.BodyLarge(text = weatherData.title)
                Spacer(modifier = Modifier.width(AppTheme.appSpacing.small))
                ScheduleIconLayout(onButtonClick = onSchedule)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.small),
            ) {
                AppDefaultIcon(
                    imageVector = Icons.Default.Event,
                    contentDescription = stringResource(StringR.string.event_icon)
                )
                AppTexts.BodySmall(text = weatherData.time)
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.small),
            ) {
                AppDefaultIcon(
                    imageVector = Icons.Default.Thermostat,
                    contentDescription = stringResource(StringR.string.temperature_icon)
                )
                AppTexts.BodySmall(text = weatherData.temperature)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.small),
            ) {
                AppDefaultIcon(
                    imageVector = Icons.Default.Waves,
                    contentDescription = stringResource(StringR.string.event_icon)
                )
                AppTexts.BodySmall(text = weatherData.uvDetails)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.small),
            ) {
                AppDefaultIcon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = stringResource(StringR.string.temperature_icon)
                )
                AppTexts.BodySmall(text = weatherData.timeSlot)
            }
        }
    }
}

@Composable
fun WeatherCardList(weatherDataList: List<WeatherData>) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(weatherDataList) { weatherData ->
                WeatherCard(weatherData = weatherData) {}
            }
        }
    }
}


@Composable
fun WeatherCardPreview() {

    val currentDateTime = LocalDateTime.now()
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    val weatherDataList = listOf(
        WeatherData(
            time = timeFormatter.format(currentDateTime),
            temperature = "25°C",
            uvDetails = "Moderate",
            timeSlot = "Morning",
            title = "Tomorrow"

        ),
        WeatherData(
            time = timeFormatter.format(currentDateTime),
            temperature = "25°C",
            uvDetails = "Moderate",
            timeSlot = "Morning",
            title = "09th Aug"

        ),
        WeatherData(
            time = timeFormatter.format(currentDateTime),
            temperature = "25°C",
            uvDetails = "Moderate",
            timeSlot = "Morning",
            title = "Today"

        ),
    )

    WeatherCardList(weatherDataList = weatherDataList)
}