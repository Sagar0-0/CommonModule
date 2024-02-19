package fit.asta.health.sunlight.feature.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.sunlight.feature.components.AutoScrollingLazyRow
import fit.asta.health.sunlight.feature.utils.toAmPmFormat
import fit.asta.health.sunlight.remote.model.SunSlotData
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StrR


@Composable
fun WeatherContent(sunSlotData: SunSlotData?) {
    if (!sunSlotData?.slot.isNullOrEmpty()) {
        Column {
            HeadingTexts.Level3(
                text = stringResource(id = StrR.string.upcoming_slots),
                modifier = Modifier.padding(vertical = AppTheme.spacing.level2)
            )
            AutoScrollingLazyRow(list = sunSlotData?.slot ?: emptyList()) { slot ->
                SlotCardItem(
                    name = slot.time?.toAmPmFormat() ?: "-",
                    type = "${slot.temp ?: 0}°C",
                    id = DrawR.drawable.image_sun,
                    modifier = Modifier.padding(horizontal = AppTheme.spacing.level1)
                )
            }
        }
    }
}
