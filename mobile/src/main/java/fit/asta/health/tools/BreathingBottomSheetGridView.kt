package fit.asta.health.tools

/*
class BreathingBottomSheetGridView {
}*/

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import fit.asta.health.R
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.BottomSheetButtonLayout
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.DividerLineCenter
import fit.asta.health.tools.view.PracticeGridView
import fit.asta.health.tools.view.components.AddMoreWater
import fit.asta.health.tools.view.components.PracticeExpandedCard

@Composable
fun BreathingBottomSheetGridView(cardList: List<PracticeGridView>) {


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
                Text("PRACTICE",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.4.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp))
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                val itemSize: Dp = LocalConfiguration.current.screenWidthDp.dp / 2

                FlowRow(mainAxisSize = SizeMode.Expand,
                    mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween) {
                    cardList.forEachIndexed { index, _ ->
                        PracticeExpandedCard(cardTitle = cardList[index].cardTitle,
                            cardImg = cardList[index].cardImg,
                            cardValue = cardList[index].cardValue,
                            modifier = Modifier.size(width = itemSize, height = 100.dp))
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
                Spacer(modifier = Modifier.height(54.dp))
            }

        }
        BottomSheetButtonLayout()
    }

}

@Preview
@Composable
fun BreathingPracticeGridView() {

    val cardList = listOf(
        PracticeGridView(cardTitle = "Exercise",
            cardValue = "Ujjayi+3",
            cardImg = R.drawable.ic_baseline_favorite_24),
        PracticeGridView(cardTitle = "Pace",
            cardValue = "Slow",
            cardImg = R.drawable.ic_baseline_favorite_24),
        PracticeGridView(cardTitle = "Level",
            cardValue = "Beginner 2",
            cardImg = R.drawable.ic_baseline_favorite_24),
        PracticeGridView(cardTitle = "Music",
            cardValue = "Vital life of force",
            cardImg = R.drawable.ic_baseline_favorite_24),
        PracticeGridView(cardTitle = "Language",
            cardValue = "English",
            cardImg = R.drawable.ic_baseline_favorite_24),
        PracticeGridView(cardTitle = "Break",
            cardValue = "2 Minutes",
            cardImg = R.drawable.ic_baseline_favorite_24),
        PracticeGridView(cardTitle = "Goal",
            cardValue = "Destress",
            cardImg = R.drawable.ic_baseline_favorite_24),
    )

    BreathingBottomSheetGridView(cardList = cardList)

}