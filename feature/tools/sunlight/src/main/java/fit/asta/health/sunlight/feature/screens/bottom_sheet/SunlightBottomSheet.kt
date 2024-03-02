package fit.asta.health.sunlight.feature.screens.bottom_sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.Prc
import fit.asta.health.common.utils.toDraw
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.CardItem
import fit.asta.health.designsystem.molecular.button.AppSwitch
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.sunlight.remote.model.Sup
import fit.asta.health.resources.drawables.R as DrawR


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SunlightBottomSheet(
    selectedData: SnapshotStateList<Prc>,
    timerState: MutableState<Boolean>,
    timerPauseState: MutableState<Boolean>,
    supplementData: MutableState<Sup?>,
    animatedState : Boolean,
    goToList: (Int, String) -> Unit,
    onTarget: () -> Unit,
    onStart: () -> Unit,
    onPause:()->Unit,
    onScheduler: () -> Unit
) {

    if (selectedData.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            AnimatedVisibility(visible = animatedState) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
                ) {
                    TitleTexts.Level3(
                        text = "Skin Conditions",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    LazyVerticalGrid(
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                        columns = GridCells.Fixed(2)
                    ) {
                        selectedData.forEachIndexed { index, prc ->
                            item {
                                CardItem(name = prc.ttl,
                                    type = prc.values.first().ttl,
                                    id = prc.code.toDraw(),
                                    onClick = {
                                        goToList(index, prc.code)
                                    })
                            }

                        }
                        if (supplementData.value != null) {
                            item {
                                CardItem(name = "Supplement",
                                    type = supplementData.value!!.supWithUnit(),
                                    id = "".toDraw(),
                                    onClick = {
                                        onTarget.invoke()
                                    })
                            }
                        }

                    }
                    SunlightCard(modifier = Modifier.fillMaxWidth())
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)) {
                if (!timerState.value){
                    ButtonWithColor(
                        modifier = Modifier.weight(0.5f),
                        color = AppTheme.colors.primary,
                        text = "SCHEDULE",
                        onClick = onScheduler
                    )
                }else{
                    ButtonWithColor(
                        modifier = Modifier.weight(0.5f),
                        color = if (timerPauseState.value) {
                            AppTheme.colors.surfaceVariant
                        } else AppTheme.colors.primary,
                        text =  if (timerPauseState.value) "RESUME" else "PAUSE",
                        onClick = onPause
                    )
                }
                ButtonWithColor(
                    modifier = Modifier.weight(0.5f),
                    color = if (timerState.value) {
                        AppTheme.colors.error
                    } else AppTheme.colors.secondary,
                    text = if (timerState.value) "STOP" else "START",
                    onClick = onStart
                )
            }
        }
    }
}

@Composable
fun SunlightCard(modifier: Modifier) {
    val checked = remember { mutableStateOf(true) }
    AppCard(
        modifier = modifier,
//        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(
                    painter = painterResource(id = DrawR.drawable.ic_ic24_notification),
                    contentDescription = null,
                )
                BodyTexts.Level2(text = "Sunlight")
                AppSwitch(
                    checked = checked.value,
                    modifier = Modifier.weight(0.5f),
                ) { checked.value = it }
            }
            CaptionTexts.Level3(
                maxLines = 3,
                text = "There will be addition of 500 ml to 1 Litre of water to your daily intake based on the weather temperature.",
            )
        }
    }
}