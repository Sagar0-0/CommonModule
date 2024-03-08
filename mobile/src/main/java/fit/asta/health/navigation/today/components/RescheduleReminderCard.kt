package fit.asta.health.navigation.today.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.navigation.today.ui.view.HomeEvent
import fit.asta.health.resources.drawables.R as DrawR


@Composable
fun RescheduleReminderCard(userEditMessage: Boolean=true, hSEvent: (HomeEvent) -> Unit) {
    AnimatedVisibility(visible = !userEditMessage) {
        AppElevatedCard {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(AppTheme.spacing.level2)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                    modifier = Modifier.weight(0.9f)
                ) {
                    AppIcon(
                        painter = painterResource(id = DrawR.drawable.ic_round_access_alarm_24),
                        modifier = Modifier.size(60.dp)
                    )
                    CaptionTexts.Level1(text = "Please schedule your alarms according to your flexibility!")
                }
                Box(
                    modifier = Modifier
                        .weight(0.1f)
                ) {
                    AppIcon(
                        imageVector = Icons.Default.Close,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clickable {
                                hSEvent(HomeEvent.SetUserEdit)
                            }
                    )
                }
            }
        }
    }


}
