package fit.asta.health.sunlight.feature.screens.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.sunlight.remote.model.WeatherDetails
import fit.asta.health.resources.strings.R as StrR


@Composable
fun SlotCard(weatherDetails: WeatherDetails) {
    val spacing = AppTheme.spacing
    AppCard(
        modifier = Modifier
            .width(160.dp)
            .padding(spacing.level0)
    ) {
        Box {
            Column(
                modifier = Modifier.padding(horizontal = spacing.level2)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.level0)
                ) {
                    Image(
                        painter = painterResource(id = weatherDetails.weatherIcon),
                        contentDescription = stringResource(id = StrR.string.weather_icon),
                        modifier = Modifier.size(spacing.level4)
                    )
                    Column(
                        modifier = Modifier.padding(start = spacing.level1),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BodyTexts.Level3(
                            text = weatherDetails.title,
                        )
                        BodyTexts.Level3(
                            text = weatherDetails.getTemDegreeSymbol(),
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BodyTexts.Level3(
                        text = weatherDetails.day,
                    )
                    BodyTexts.Level3(
                        text = weatherDetails.time,
                    )
                }
            }
            AppIcon(
                painter = painterResource(id = weatherDetails.timeIcon),
                contentDescription = stringResource(id = StrR.string.time_icon),
                modifier = Modifier
                    .padding(spacing.level1)
                    .size(spacing.level3)
                    .align(Alignment.TopEnd),
            )
        }
    }
}

@Composable
fun SlotCardItem(
    modifier: Modifier = Modifier,
    name: String,
    type: String,
    @DrawableRes id: Int,
) {
    AppCard(modifier) {
        Row(
            modifier = modifier.padding(AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)) {
                AppLocalImage(
                    painter = painterResource(id), contentDescription = null,
                    modifier = Modifier.size(AppTheme.customSize.level3)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            ) {
                BodyTexts.Level2(
                    text = name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                TitleTexts.Level3(
                    text = type,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}