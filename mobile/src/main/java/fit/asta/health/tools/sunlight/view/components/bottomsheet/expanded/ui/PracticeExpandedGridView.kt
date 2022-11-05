package fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.ui

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
import fit.asta.health.tools.water.view.component.BeveragesLayout
import fit.asta.health.tools.water.view.component.QuantityLayout


@Preview
@Composable
fun PracticeExpandedGridView() {

    val cardList = listOf(PracticeGridView(cardTitle = "Lifestyle",
        cardImg = R.drawable.ic_baseline_cancel_24,
        cardValue = "40 SPF"),
        PracticeGridView(cardTitle = "Work",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "40 SPF"),
        PracticeGridView(cardTitle = "Health",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "40 SPF"),
        PracticeGridView(cardTitle = "Sunscreen",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "40 SPF"),
        PracticeGridView(cardTitle = "Sunscreen",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "40 SPF"))

//    PracticeExpandedGridViewLayout(cardList = cardList)

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
                Text("BEVERAGES",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.4.sp,
                    color = Color.White)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                BeveragesLayout()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text("QUANTITY",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.4.sp,
                    color = Color.White)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                QuantityLayout()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text("PRACTICE",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.4.sp,
                    color = Color.White)
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
                Spacer(modifier = Modifier.height(32.dp))
            }

//            item {
//                BottomSheetButtonLayout()
//            }
//
//            item {
//                Spacer(modifier = Modifier.height(16.dp))
//            }

        }
        BottomSheetButtonLayout()
    }
}