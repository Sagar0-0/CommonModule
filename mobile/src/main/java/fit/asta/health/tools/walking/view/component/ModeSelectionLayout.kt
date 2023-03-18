package fit.asta.health.tools.walking.view.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.R
import fit.asta.health.tools.sunlight.view.components.SkinExposerData
import fit.asta.health.tools.sunlight.view.components.SkinExposureList

@Composable
@Preview
fun ModeSelectionLayout() {

    val cardList = remember {
        mutableStateListOf(SkinExposerData(1,
            percentValue = "Outdoor",
            cardImg = R.drawable.boy_avatar_30),
            SkinExposerData(id = 2, percentValue = "Indoor", cardImg = R.drawable.boy_avatar_30))
    }

    SkinExposureList(list = cardList, rowTitle = "Select mode of exercise")

}