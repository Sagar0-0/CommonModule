package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Stable
fun BottomSheetListItemPicker(
    name: String,
    list: List<UserProperties>,
    onOpenClick: () -> Unit
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onOpenClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TitleTexts.Level3(text = name)
                AppIcon(imageVector = Icons.Default.AddCircle)
            }
            AnimatedVisibility(list.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.padding(top = AppTheme.spacing.level2),
                ) {
                    list.forEach {
                        AppCard(
                            modifier = Modifier.padding(AppTheme.spacing.level1),
                        ) {
                            BodyTexts.Level3(
                                text = it.name,
                                modifier = Modifier.padding(AppTheme.spacing.level1),
                            )
                        }
                    }
                }
            }
        }
    }
}