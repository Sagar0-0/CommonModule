package fit.asta.health.feature.sunlight.view.skin_exposure

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R

data class SkinExposerData(
    val id: Int,
    val percentValue: String,
    val cardImg: Int,
    var isSelected: Boolean = false,
)


@Composable
fun SkinExposureLayout() {
    val cardList = remember {
        mutableStateListOf(
            SkinExposerData(1, percentValue = "40%", cardImg = R.drawable.girl_avatar_40),
            SkinExposerData(2, percentValue = "30%", cardImg = R.drawable.boy_avatar_30),
            SkinExposerData(3, percentValue = "45%", cardImg = R.drawable.girl_avatar_45),
            SkinExposerData(4, percentValue = "50%", cardImg = R.drawable.boy_avatar_50),
            SkinExposerData(5, percentValue = "25%", cardImg = R.drawable.girl_avatar_25),
            SkinExposerData(6, percentValue = "40%", cardImg = R.drawable.boy_avatar_40),
            SkinExposerData(7, percentValue = "20%", cardImg = R.drawable.girl_avatar_20),
            SkinExposerData(8, percentValue = "20%", cardImg = R.drawable.boy_avatar_20),
        )
    }
    SkinExposureList(
        list = cardList,
        rowTitle = "Please select skin exposure percentage based on clothing"
    )
}

@Composable
fun SkinExposureCardComponent(
    itemData: SkinExposerData,
    demoSelected: Boolean,
    cardValue: String,
    cardImg: Int,
) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLocalImage(
                painter = painterResource(id = cardImg),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            TitleTexts.Level2(text = cardValue)
        }
        if (demoSelected) {
            Box(contentAlignment = Alignment.TopEnd) {
                AppIcon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.Green,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(top = 8.dp, end = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SkinExposureCard(
    itemData: SkinExposerData,
    onCheckChanged: (SkinExposerData) -> Unit,
    cardValue: String,
    cardImg: Int,
) {

    var demoSelected by remember {
        mutableStateOf(false)
    }

    AppCard(modifier = Modifier
        .clickable {
            if (!itemData.isSelected) {
                demoSelected = !demoSelected
                onCheckChanged(itemData)
            }
        }
        .padding(8.dp)
    ) {
        SkinExposureCardComponent(
            cardValue = cardValue,
            cardImg = cardImg,
            itemData = itemData,
            demoSelected = demoSelected
        )
    }
}

class SkinExposerDataState(val list: MutableList<SkinExposerData>) {

    // were updating the entire list in a single pass using its iterator
    private var lastSelectedId: Int? = null

    // updates the list with the latest selection
    fun onItemSelected(selectedItemData: SkinExposerData) {
        // clear the selection on all items except the last selected item
        list.filterNot { it.id == lastSelectedId }
            .forEach { it.isSelected = false }

        // update the last selected id
        lastSelectedId = selectedItemData.id

        // update the selected state on the selected item
        selectedItemData.isSelected = true
    }
}


@Composable
fun SkinExposureList(
    list: MutableList<SkinExposerData>,
    rowTitle: String,
) {

    val itemDataState = remember { SkinExposerDataState(list) }



    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            TitleTexts.Level2(
                text = rowTitle,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(10.dp),
            userScrollEnabled = false
        ) {
            items(itemDataState.list.size) {
                SkinExposureCard(
                    cardValue = itemDataState.list[it].percentValue,
                    cardImg = itemDataState.list[it].cardImg,
                    itemData = itemDataState.list[it],
                    onCheckChanged = itemDataState::onItemSelected
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}