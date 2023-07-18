package fit.asta.health.tools.walking.view.component

import androidx.compose.runtime.Composable
import fit.asta.health.R
import fit.asta.health.tools.view.PracticeGridView

@Composable
fun WalkingBottomSheetGridView() {

    val cardList = listOf(
        PracticeGridView(cardTitle = "Music",
            cardValue = "Spotify",
            cardImg = R.drawable.ic_baseline_favorite_24),
        PracticeGridView(cardTitle = "Mode",
            cardValue = "Indoor",
            cardImg = R.drawable.ic_baseline_favorite_24),
        PracticeGridView(cardTitle = "Goals",
            cardValue = "Loose Weight",
            cardImg = R.drawable.ic_baseline_favorite_24),
        PracticeGridView(cardTitle = "Type",
            cardValue = "Stairs, Walk",
            cardImg = R.drawable.ic_baseline_favorite_24),
        PracticeGridView(cardTitle = "Distance",
            cardValue = "99.00",
            cardImg = R.drawable.ic_baseline_favorite_24),
    )

    //SunlightBottomSheetGridView(cardList = cardList)
}