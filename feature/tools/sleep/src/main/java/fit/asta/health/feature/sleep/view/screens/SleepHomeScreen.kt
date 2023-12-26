package fit.asta.health.feature.sleep.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import fit.asta.health.data.sleep.model.network.get.ProgressData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ProgressBarInt
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun SleepHomeScreen(
    progressData: ProgressData?
) {

    // State to hold the current time
    var currentTime by remember { mutableStateOf(LocalTime.now()) }

    // Format the time as "hh:mm a"
    val formattedTime = remember(currentTime) {
        DateTimeFormatter.ofPattern("hh:mm a").format(currentTime)
    }

    // Update the time using LaunchedEffect
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(1000) // Delay for 1 second
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val color = AppTheme.colors.onBackground

        // This is the Clock
        Box(
            modifier = Modifier
                .size(200.dp)
                .drawBehind {

                    val center = Offset(size.width / 2, size.height / 2)
                    val radius = size.minDimension / 2

                    // Draw the clock face
                    drawCircle(color.copy(alpha = .3f), radius, center)
                },
            contentAlignment = Alignment.Center
        ) {

            // Timing of the clock
            TitleTexts.Level1(
                text = formattedTime.uppercase()
            )
        }

        // These are the variables which are used to
        val recommended = progressData?.rcm?.toFloat() ?: 8f
        val goal = progressData?.tgt?.toFloat() ?: 7f
        val achieved = progressData?.ach?.toFloat() ?: 0f

        AppCard {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ProgressBarInt(
                    modifier = Modifier.weight(0.3f),
                    targetDistance = recommended,
                    progress = achieved / recommended,
                    name = "Recommended",
                    postfix = "Hrs"
                )
                ProgressBarInt(
                    modifier = Modifier.weight(0.3f),
                    targetDistance = goal,
                    progress = achieved / goal,
                    name = "Goal",
                    postfix = "Hrs"
                )
                ProgressBarInt(
                    modifier = Modifier.weight(0.3f),
                    targetDistance = goal,
                    progress = (goal - achieved) / goal,
                    name = "Remaining",
                    postfix = "Hrs"
                )
            }
        }
    }
}