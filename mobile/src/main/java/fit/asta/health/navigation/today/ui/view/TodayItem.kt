package fit.asta.health.navigation.today.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R

@Composable
fun TodayItem(item: AlarmEntity) {
    val time = AMPMHoursMin(
        hours = if (item.time.hours > 12) {
            item.time.hours - 12
        } else if (item.time.hours == 0) 12
        else item.time.hours,
        minutes = item.time.minutes,
        dayTime = if (item.time.hours >= 12) AMPMHoursMin.DayTime.PM else AMPMHoursMin.DayTime.AM
    )
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AppNetworkImage(
                errorImage = painterResource(R.drawable.placeholder_tag),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getImgUrl(url = item.info.url))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(AppTheme.shape.level2)
                    .weight(.35f)
                    .height(110.dp)
            )
            Column(
                modifier = Modifier
                    .weight(.65f)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                horizontalAlignment = Alignment.Start
            ) {
                CaptionTexts.Level2(text = item.info.name)
                TitleTexts.Level2(text = item.info.tag)
                BodyTexts.Level2(
                    text = "${if (time.hours < 10) "0" else ""}${time.hours}:${
                        if (time.minutes < 10) "0" else ""
                    }${time.minutes} ${time.dayTime.name}"
                )
            }
        }
    }
}