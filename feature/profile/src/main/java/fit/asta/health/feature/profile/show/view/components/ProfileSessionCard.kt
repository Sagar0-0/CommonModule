package fit.asta.health.feature.profile.show.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.data.profile.remote.model.TimeSchedule
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.BodyTexts

@Composable
fun ProfileSessionCard(
    title: String,
    sleepSchedule: TimeSchedule,
) {
    AppCard {
        Column(
            modifier = Modifier
                .padding(AppTheme.spacing.level2)
                .fillMaxWidth()
        ) {
            BodyTexts.Level3(text = title)
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                UserSleepCycles(
                    columnType = "BED TIME",
                    columnValue = sleepSchedule.from.toString()
                )
                Spacer(modifier = Modifier.width(AppTheme.spacing.level5))
                UserSleepCycles(columnType = "WAKE UP", columnValue = sleepSchedule.to.toString())
            }
        }
    }
}