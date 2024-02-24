package fit.asta.health.sunlight.feature.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.sunlight.feature.components.PieChart
import fit.asta.health.sunlight.feature.components.UvIndexColorIndicator
import fit.asta.health.sunlight.feature.utils.DateUtil
import fit.asta.health.sunlight.remote.model.SunSlotData


@Composable
fun UvIndexLocationCard(
    sunSlotData: SunSlotData?,
    onHighUv: () -> Unit
) {
//        AppCard {
    if (sunSlotData?.slot != null) {
        LaunchedEffect(Unit) {
            sunSlotData.toChartData()
            if ((sunSlotData.currUv ?: 0.0) > 4.0) {
                onHighUv.invoke()
            }
        }
        Column(
            modifier = Modifier
                .padding(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(240.dp)
            ) {
                PieChart(
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .offset(0.dp, (30).dp),
                    input = sunSlotData.toChartData(),
                    currUv = (sunSlotData.currUv ?: 0.0).toString()
                )
                UvIndexColorIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = AppTheme.spacing.level2)
                )
                /*         Box(
                         modifier = Modifier
                             .fillMaxWidth(0.7f)
                             .fillMaxHeight(0.4f)
                             .border(
                                 BorderStroke(1.dp, AppTheme.colors.onSurface),
                                 shape = AppTheme.shape.level2
                             )
                             .padding(AppTheme.spacing.level2)
                     ) {
                         AppLocalImage(painter = painterResource(id = DrawR.drawable.ic_weather_))
                         HeadingTexts.Level1(
                             text = "24°C",
                             modifier = Modifier.align(Alignment.TopEnd)
                         )
                         Column(
                             modifier = Modifier
         //                        .padding(AppTheme.spacing.level2)
                                 .align(Alignment.BottomCenter)
                         ) {
                             BodyTexts.Level1(text = DateUtil.getDayNameForToday())
                             BodyTexts.Level3(text = "banglore" ?: "")
                         }
                     }*/

            }
            CaptionTexts.Level3(text = "UV index based on time slots")
            if (DateUtil.isCurrentTimeLaterThan(
                    sunSlotData.toChartData().lastOrNull()?.time ?: ""
                )
            ) {
                CaptionTexts.Level3(
                    text = "The tendency to sun to provide D is over you next slot starts tomorrow",
                    textAlign = TextAlign.Center,
                    color = AppTheme.colors.primary
                )
            }
        }
    }else{
        CaptionTexts.Level4(
            text = sunSlotData?.message?:"",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
//    }