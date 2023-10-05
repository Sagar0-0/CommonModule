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
import fit.asta.health.data.profile.remote.model.Session
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.molecular.texts.BodyTexts

@Composable
fun ProfileSessionCard(
    title: String,
    session: Session,
) {
    AppCard {
        Column(
            modifier = Modifier
                .padding(AppTheme.spacing.medium)
                .fillMaxWidth()
        ) {
            BodyTexts.Level3(text = title)
            Spacer(modifier = Modifier.height(AppTheme.spacing.medium))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                UserSleepCycles(columnType = "BED TIME", columnValue = session.from.toString())
                Spacer(modifier = Modifier.width(AppTheme.spacing.largeMedium))
                UserSleepCycles(columnType = "WAKE UP", columnValue = session.to.toString())
            }
        }
    }
}