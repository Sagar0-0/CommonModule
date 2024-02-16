package fit.asta.health.feature.profile.profile.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.chip.AppAssistChip
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthScreen(
    userProfileState: UserProfileState
) {
    val bottomSheetState = rememberModalBottomSheetState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier)
        userProfileState.healthBottomSheetTypes.forEach { type ->
            AppCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2)
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
                        TitleTexts.Level3(text = type.name)
                        AppIconButton(
                            onClick = {
                                userProfileState.openHealthBottomSheet(bottomSheetState, type)
                            }
                        ) {
                            AppIcon(imageVector = Icons.Default.AddCircle)
                        }
                    }
                    AnimatedVisibility(type.list.isNotEmpty()) {
                        FlowRow(
                            mainAxisSpacing = AppTheme.spacing.level0,
                            modifier = Modifier.padding(top = AppTheme.spacing.level2),
                        ) {
                            type.list.forEach {
                                AppAssistChip(
                                    textToShow = it.name,
                                    trailingIcon = Icons.Default.RemoveCircle
                                ) {
                                    type.remove(it)
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier)
        AppModalBottomSheet(
            sheetVisible = userProfileState.bottomSheetVisible,
            sheetState = bottomSheetState,
            dragHandle = null,
            onDismissRequest = { userProfileState.closeBottomSheet(bottomSheetState) },
        ) {
            HealthBottomSheetLayout(
                userProfileState = userProfileState
            )
        }
    }
}