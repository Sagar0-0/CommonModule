package fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
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


@Composable
fun PracticeExpandedGridViewLayout(cardList: List<PracticeGridView>) {

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(10.dp)
            .height(500.dp),
        userScrollEnabled = false) {
        items(cardList.size) {
            PracticeExpandedCard(cardTitle = cardList[it].cardTitle,
                cardImg = cardList[it].cardImg,
                cardValue = cardList[it].cardValue)
        }

    }

}

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
            androidx.compose.material3.Text("BEVERAGES",
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
            androidx.compose.material3.Text("QUANTITY",
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
            androidx.compose.material3.Text("PRACTICE",
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
            Spacer(modifier = Modifier.height(16.dp))
        }


        item {
            BottomSheetButtonLayout()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}