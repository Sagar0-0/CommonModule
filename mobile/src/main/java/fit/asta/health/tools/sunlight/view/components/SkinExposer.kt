package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import kotlin.reflect.KFunction1

data class SkinExposerData(
    val id: Int,
    val percentValue: String,
    val cardImg: Int,
    var isSelected: Boolean = false,
)

@Preview
@Composable
fun SkinExposureLayout() {

    val cardList = remember {
        mutableStateListOf(
            SkinExposerData(1, percentValue = "40%", cardImg = R.drawable.girl_avatar_40),
            SkinExposerData(2, percentValue = "30%", cardImg = R.drawable.boy_avatar_30),
            SkinExposerData(3, percentValue = "45%", cardImg = R.drawable.boy_avatar_30),
            SkinExposerData(4, percentValue = "50%", cardImg = R.drawable.boy_avatar_30),
            SkinExposerData(5, percentValue = "25%", cardImg = R.drawable.boy_avatar_30),
            SkinExposerData(6, percentValue = "40%", cardImg = R.drawable.boy_avatar_30),
            SkinExposerData(7, percentValue = "20%", cardImg = R.drawable.boy_avatar_30),
            SkinExposerData(8, percentValue = "20%", cardImg = R.drawable.boy_avatar_30),
        )
    }


    SkinExposureList(list = cardList,
        rowTitle = "Please select skin exposure percentage based on clothing")

}

@Composable
fun SkinExposureCardComponent(
    itemData: SkinExposerData,
    demoSelected: Boolean,
    cardValue: String,
    cardImg: Int,
) {
    Row(horizontalArrangement = Arrangement.Center) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = cardImg),
                contentDescription = null,
                modifier = Modifier.size(100.dp))
            Text(text = cardValue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 25.2.sp)
        }
        if (demoSelected) {
            Box(contentAlignment = Alignment.TopEnd) {
                androidx.compose.material.Icon(imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.Green,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(top = 8.dp, end = 8.dp))
            }
        }
    }
}

@Composable
fun SkinExposureCard(
    itemData: SkinExposerData,
    onCheckChanged: KFunction1<SkinExposerData, Unit>,
    cardValue: String,
    cardImg: Int,
) {

    var demoSelected by remember {
        mutableStateOf(false)
    }

    Card(modifier = Modifier
        .clickable {
            if (!itemData.isSelected) {
                demoSelected = !demoSelected
            }
        }
        .padding(8.dp), shape = RoundedCornerShape(8.dp), backgroundColor = Color(0x66959393)) {

        SkinExposureCardComponent(cardValue = cardValue,
            cardImg = cardImg,
            itemData = itemData,
            demoSelected = demoSelected)

    }
}

class SkinExposerDataState(val list: MutableList<SkinExposerData>) {

    // were updating the entire healthHisList in a single pass using its iterator
    fun onItemSelected(selectedItemData: SkinExposerData) {
        val iterator = list.listIterator()

        while (iterator.hasNext()) {
            val listItem = iterator.next()

            iterator.set(if (listItem.id == selectedItemData.id) {
                selectedItemData
            } else {
                listItem.copy(isSelected = false)
            })
        }
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
                .padding(horizontal = 16.dp)) {
            androidx.compose.material.Text(text = rowTitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(2),
            modifier = Modifier.padding(10.dp),
            userScrollEnabled = false) {
            items(itemDataState.list.size) {
                SkinExposureCard(cardValue = itemDataState.list[it].percentValue,
                    cardImg = itemDataState.list[it].cardImg,
                    itemData = itemDataState.list[it],
                    onCheckChanged = itemDataState::onItemSelected)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}