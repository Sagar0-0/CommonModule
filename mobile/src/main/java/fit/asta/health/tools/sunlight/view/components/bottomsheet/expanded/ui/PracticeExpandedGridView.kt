package fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.R


@Composable
fun PracticeExpandedGridView() {

    val cardList = listOf(PracticeGridView(cardTitle = "Sunscreen",
        cardImg = R.drawable.ic_baseline_cancel_24,
        cardValue = "40 SPF"),
        PracticeGridView(cardTitle = "Sunscreen",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "40 SPF"),
        PracticeGridView(cardTitle = "Sunscreen",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "40 SPF"),
        PracticeGridView(cardTitle = "Sunscreen",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "40 SPF"),
        PracticeGridView(cardTitle = "Sunscreen",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "40 SPF"))


    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier.padding(10.dp),
        userScrollEnabled = false) {
        items(cardList.size) {
            PracticeExpandedCard(cardTitle = cardList[it].cardTitle,
                cardImg = cardList[it].cardImg,
                cardValue = cardList[it].cardValue)
        }
    }
}


