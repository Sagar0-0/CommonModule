package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.data.scheduler.remote.model.WeatherData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.cards.AppOutlinedCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.resources.drawables.R as DrawR

@Composable
fun WeatherCardHome(
    temperature: String,
    modifier: Modifier = Modifier,
) {
    AppCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            Column(
                modifier = Modifier.weight(.5f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
            ) {
                HeadingTexts.Level1(text = "$temperature °C")
                BodyTexts.Level2(text = "Cloudy")
            }
            Box(modifier = Modifier.weight(.5f), contentAlignment = Alignment.Center) {
                AppLocalImage(
                    painter = painterResource(id = DrawR.drawable.weather_sun_cloud_angled_rain),
                    contentDescription = null,
                    modifier = Modifier.size(70.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}

@Composable
fun SunSlot(weatherData: WeatherData, modifier: Modifier = Modifier, onSchedule: () -> Unit) {

    AppOutlinedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onSchedule
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = AppTheme.spacing.level1,
                vertical = AppTheme.spacing.level1
            ),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CaptionTexts.Level1(text = weatherData.title)
                CaptionTexts.Level3(text = weatherData.time)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            ) {
                BodyTexts.Level3(text = weatherData.temperature)
                BodyTexts.Level3(text = weatherData.uvDetails)
            }
        }
    }
}

@Composable
fun SunSlotsProgressCard(
    modifier: Modifier = Modifier,
    title: String,
    titleValue: String,
    onSchedule: () -> Unit
) {

    AppElevatedCard(modifier = modifier, onClick = onSchedule) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppIcon(
                imageVector = Icons.Default.Schedule,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            BodyTexts.Level2(text = titleValue)
            Spacer(modifier = Modifier.height(8.dp))
            CaptionTexts.Level2(text = title)
        }
    }
}

