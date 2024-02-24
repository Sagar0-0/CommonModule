package fit.asta.health.sunlight.feature.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import fit.asta.chart.linear.LinearChart.Companion.BarChart
import fit.asta.chart.linear.data.LinearStringData
import fit.asta.chart.linear.decoration.LinearDecoration
import fit.asta.chart.linear.plots.LinearBarPlot
import fit.asta.chart.util.ChartPoint
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.sunlight.feature.utils.DateUtil
import fit.asta.health.sunlight.feature.utils.splitAmPm
import fit.asta.health.sunlight.feature.utils.toAmPmFormat
import fit.asta.health.sunlight.remote.model.SunSlotData

@Composable
fun UvBarChartCard(sunSlotData: SunSlotData?) {
    if (sunSlotData != null) {
        val yAxis = sunSlotData.slot?.map { ChartPoint((it.temp ?: 0).toFloat()) } ?: emptyList()
        val xAxis = sunSlotData.slot?.map { ChartPoint((it.time.splitAmPm() ?: "")) }
            ?: emptyList()
        val yAxisReadings = listOf(
            yAxis,
            yAxis
        )

//    val xAxisReadings = ChartPoint()

        // Creating LinearStringData object with the dummy data
        if (yAxis.isNotEmpty() && xAxis.isNotEmpty()) {
            val linearStringData = LinearStringData(
                yAxisReadings = yAxisReadings,
                xAxisReadings = xAxis
            )

            // Passing the LinearStringData object to the BarChart composable
            AppCard {
                Column(
                    modifier = Modifier.padding(
                        top = AppTheme.spacing.level2,
                        bottom = AppTheme.spacing.level2
                    )
                ) {
                    /*HeadingTexts.Level3(
                    text = stringResource(id = R.string.upcoming_slots),
                    modifier = Modifier.padding(vertical = AppTheme.spacing.level2)
                )*/
                    BarChart(
                        linearData = linearStringData,
                        plot = LinearBarPlot(barWidth = 60f),
                        decoration = LinearDecoration.barDecorationColors(
                            plotPrimaryColor = listOf(Color.Red, Color.Yellow)
                        )
                    )
                    CaptionTexts.Level3(
                        text = "Temperature based on sun slots",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}