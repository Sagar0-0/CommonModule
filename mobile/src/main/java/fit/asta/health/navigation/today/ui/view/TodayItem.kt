package fit.asta.health.navigation.today.ui.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.cards.AppOutlinedCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
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
    var expandedState by remember { mutableStateOf(false) }
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        onClick = { expandedState = !expandedState }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                    CaptionTexts.Level2(text = item.info.tag)
                    TitleTexts.Level2(text = item.info.name)

                    if (expandedState) {
                        BodyTexts.Level2(text = item.info.description)
                    }
                    TitleTexts.Level2(
                        text = "${if (time.hours < 10) "0" else ""}${time.hours}:${
                            if (time.minutes < 10) "0" else ""
                        }${time.minutes} ${time.dayTime.name}"
                    )
                }
            }
            if (expandedState) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AppOutlinedCard(shape = CircleShape) {
                        AppIcon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = Icons.Default.Delete
                        )
                    }
                    AppOutlinedCard(shape = CircleShape) {
                        AppIcon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = Icons.Default.Alarm
                        )
                    }
                    AppOutlinedCard(shape = CircleShape) {
                        AppIcon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = Icons.Default.SkipNext
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TodayItem1(item: AlarmEntity) {
    val time = AMPMHoursMin(
        hours = if (item.time.hours > 12) {
            item.time.hours - 12
        } else if (item.time.hours == 0) 12
        else item.time.hours,
        minutes = item.time.minutes,
        dayTime = if (item.time.hours >= 12) AMPMHoursMin.DayTime.PM else AMPMHoursMin.DayTime.AM
    )
    var expandedState by remember { mutableStateOf(false) }
    val imgSize: Dp by animateDpAsState(
        if (expandedState) 180.dp else 110.dp,
        tween(800, easing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f)), label = ""
    )
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        onClick = { expandedState = !expandedState }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AppNetworkImage(
                errorImage = painterResource(R.drawable.placeholder_tag),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getImgUrl(url = item.info.url))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(AppTheme.shape.level2)
                    .height(imgSize)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imgSize)
                    .padding(AppTheme.spacing.level2)
            ) {

                CaptionTexts.Level2(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = item.info.tag,
                    color = Color.White
                )

                TitleTexts.Level2(
                    modifier = Modifier.align(Alignment.TopEnd),
                    text = "${if (time.hours < 10) "0" else ""}${time.hours}:${
                        if (time.minutes < 10) "0" else ""
                    }${time.minutes} ${time.dayTime.name}",
                    color = Color.White
                )
                if (expandedState) {
                    TitleTexts.Level2(
                        modifier = Modifier.align(Alignment.TopCenter),
                        text = item.info.name,
                        color = Color.White
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level0),
                    ) {
                        AppOutlinedCard(shape = CircleShape) {
                            AppIcon(
                                modifier = Modifier.padding(8.dp),
                                imageVector = Icons.Default.Delete
                            )
                        }
                        AppOutlinedCard(shape = CircleShape) {
                            AppIcon(
                                modifier = Modifier.padding(8.dp),
                                imageVector = Icons.Default.Alarm
                            )
                        }
                        AppOutlinedCard(shape = CircleShape) {
                            AppIcon(
                                modifier = Modifier.padding(8.dp),
                                imageVector = Icons.Default.SkipNext
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        BodyTexts.Level2(
                            text = item.info.description,
                            color = Color.White
                        )

                    }

                } else {
                    TitleTexts.Level2(
                        modifier = Modifier.align(Alignment.BottomStart),
                        text = item.info.name,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun TodayItem2(item: AlarmEntity) {
    val time = AMPMHoursMin(
        hours = if (item.time.hours > 12) {
            item.time.hours - 12
        } else if (item.time.hours == 0) 12
        else item.time.hours,
        minutes = item.time.minutes,
        dayTime = if (item.time.hours >= 12) AMPMHoursMin.DayTime.PM else AMPMHoursMin.DayTime.AM
    )
    var expandedState by remember { mutableStateOf(false) }
    val imgSize: Dp by animateDpAsState(
        if (expandedState) 110.dp else 70.dp,
        tween(800, easing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f)), label = ""
    )
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        onClick = { expandedState = !expandedState }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
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
                        .size(imgSize)
                )
                if (expandedState) {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                        horizontalAlignment = Alignment.Start
                    ) {
                        CaptionTexts.Level2(text = item.info.tag)
                        TitleTexts.Level2(text = item.info.name)
                        BodyTexts.Level2(text = item.info.description)
                        TitleTexts.Level2(
                            text = "${if (time.hours < 10) "0" else ""}${time.hours}:${
                                if (time.minutes < 10) "0" else ""
                            }${time.minutes} ${time.dayTime.name}"
                        )
                    }
                } else {
                    TitleTexts.Level2(text = item.info.name)
                    TitleTexts.Level2(
                        text = "${if (time.hours < 10) "0" else ""}${time.hours}:${
                            if (time.minutes < 10) "0" else ""
                        }${time.minutes} ${time.dayTime.name}"
                    )
                }

            }
            if (expandedState) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AppOutlinedCard(shape = CircleShape) {
                        AppIcon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = Icons.Default.Delete
                        )
                    }
                    AppOutlinedCard(shape = CircleShape) {
                        AppIcon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = Icons.Default.Alarm
                        )
                    }
                    AppOutlinedCard(shape = CircleShape) {
                        AppIcon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = Icons.Default.SkipNext
                        )
                    }
                }
            }
        }
    }
}