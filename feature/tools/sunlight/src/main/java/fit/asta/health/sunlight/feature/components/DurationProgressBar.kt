package fit.asta.health.sunlight.feature.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.sunlight.feature.rainbowColors
import fit.asta.health.sunlight.feature.screens.home.components.DurationRecommendationCard
import fit.asta.health.sunlight.feature.screens.home.homeScreen.SunlightHomeState
import fit.asta.health.resources.drawables.R as DrawR

fun sunGradient(): Brush {
    val startColor = Color.Yellow
    val endColor = Color(255, 204, 0)
    val midColor = Color(233, 169, 72, 255)
    return Brush.linearGradient(
        colors = listOf(startColor, midColor, endColor)
    )
}

@Composable
fun DurationProgressBar(
    homeState: State<SunlightHomeState>
) {

    val spacing = AppTheme.spacing
    AppCard {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = sunGradient()),
            contentAlignment = Alignment.Center
        ) {
            AppLocalImage(
                painter = painterResource(id = DrawR.drawable.ic_sun_nature),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .alpha(0.03f)
            )
            Column(
                modifier = Modifier.padding(spacing.level1),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BodyTexts.Level2(
                        text = "Altitude: 210m",
                    )
                    BodyTexts.Level2(
                        text = "UVI 4",
                    )
                }
                Box(contentAlignment = Alignment.Center) {
                    DrawArc(progress = homeState.value.remainingTime)
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CaptionTexts.Level2(text = homeState.value.sunlightProgress.value)
                        BodyTexts.Level1(
                            text = "${homeState.value.totalTime.value} min",
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = AppTheme.spacing.level1)
                        .clip(RectangleShape),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DurationRecommendationCard(
                        constTime = "${(homeState.value.sunlightHomeResponse?.sunLightProgressData?.rcmIu ?: 0)} IU",
                        tool = "Vitamin D",
                        title = "Recommended"
                    )
                    DurationRecommendationCard(
                        constTime = "${(homeState.value.sunlightHomeResponse?.sunLightProgressData?.tgtIu ?: 0)} IU",
                        tool = "Vitamin D",
                        title = "Daily Goal"
                    )
                    DurationRecommendationCard(
                        time = homeState.value.sunlightProgress,
                        tool = "Sunlight",
                        title = "Time Remaining"
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RectangleShape),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DurationRecommendationCard(
                        time = homeState.value.totalDConsumed,
                        tool = "Vitamin D",
                        title = "Achieved"
                    )
                    DurationRecommendationCard(
                        constTime = "${(homeState.value.sunlightHomeResponse?.sunLightProgressData?.remIu ?: 0)} IU",
                        tool = "Vitamin D",
                        title = "Remaining"
                    )
                    DurationRecommendationCard(
                        constTime = "${(homeState.value.sunlightHomeResponse?.sunLightProgressData?.remIu ?: 0)} IU",
                        tool = "Vitamin D",
                        title = "Target"
                    )
                }
            }
        }
    }
}

@Composable
fun DrawArc(progress: MutableState<Float>) {
    val startAngle = 140f
    val sweepAngle = 260f
    val useCenter by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .size(220.dp)
            .padding(AppTheme.spacing.level2)
    ) {
        Canvas(modifier = Modifier.size(220.dp)) {
            drawArc(
                color = Color.LightGray,
                startAngle,
                sweepAngle,
                useCenter,
                style = Stroke(width = 30f, cap = StrokeCap.Round, miter = 40f)
            )
            drawArc(
                brush = Brush.sweepGradient(
                    colors = rainbowColors,
                    center = Offset(size.width / 2, size.width)
                ),
                startAngle,
                progress.value,
                useCenter,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
        }
    }
}