package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import fit.asta.health.designsystem.components.ButtonWithColor
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.tools.sunlight.nav.SunlightScreen
import fit.asta.health.tools.sunlight.view.home.SunlightHomeScreenEvents
import fit.asta.health.tools.sunlight.viewmodel.SunlightViewModel
import fit.asta.health.tools.view.PracticeGridView
import fit.asta.health.tools.view.components.AddMoreWater
import fit.asta.health.tools.view.components.PracticeExpandedCard

@Composable

fun SunlightBottomSheetGridView(
    cardList: List<PracticeGridView>,
    startState: MutableState<Boolean>,
    navController: NavController,
    homeViewModel: SunlightViewModel
) {


    Box(contentAlignment = Alignment.BottomCenter) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                DividerLineCenter()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    "PRACTICE",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.4.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                val itemSize: Dp = LocalConfiguration.current.screenWidthDp.dp / 2

                FlowRow(
                    mainAxisSize = SizeMode.Expand,
                    mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
                ) {
                    cardList.forEach { value ->
                        PracticeExpandedCard(
                            cardTitle = value.cardTitle,
                            cardImg = value.cardImg,
                            cardValue = value.cardValue,
                            modifier = Modifier.size(width = itemSize, height = 100.dp),
                            onclick = value.onClick
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                AddMoreWater()
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                BottomSheetButtonLayout()
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(spacing.medium)
                ) {
                    ButtonWithColor(
                        modifier = Modifier.weight(0.5f),
                        color = Color.Green,
                        text = "SCHEDULE"
                    ) {}
                    ButtonWithColor(
                        modifier = Modifier.weight(0.5f),
                        color = if (!startState.value) Color.Blue else Color.Red,
                        text = if (!startState.value) "START" else "END"
                    ) {
                        if (startState.value) {
                            homeViewModel.onUiEvent(SunlightHomeScreenEvents.OnStopClick)
                            navController.navigate(route = SunlightScreen.StartedStateComposable.route)
                        } else {
                            homeViewModel.onUiEvent(SunlightHomeScreenEvents.OnStartClick)
                        }
                    }
                }
            }
        }
    }
}