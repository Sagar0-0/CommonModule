package fit.asta.health.tools.water.view.component

import androidx.compose.runtime.Composable
import fit.asta.health.R
import fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.ui.PracticeGridView

@Composable
fun WaterListLayout() {
    val cardList = listOf(PracticeGridView(cardTitle = "Lifestyle",
        cardImg = R.drawable.ic_baseline_cancel_24,
        cardValue = "Lightly Active"),
        PracticeGridView(cardTitle = "Work",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "Indoor"),
        PracticeGridView(cardTitle = "Health",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "None"))


}