package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ItemData(
    val id: Int,
    val display: String,
    var isSelected: Boolean = false,
    val bgColor: Color? = null,
)


class ItemDataState(val list: MutableList<ItemData>) {

    // were updating the entire list in a single pass using its iterator
    fun onItemSelected(selectedItemData: ItemData) {
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
fun ItemDisplay(
    itemData: ItemData,
    onCheckChanged: (ItemData) -> Unit,
) {

    var demoSelected by remember {
        mutableStateOf(false)
    }

    itemData.bgColor?.let {
        Card(modifier = Modifier
            .fillMaxWidth()
            .clickable {

                if (!itemData.isSelected) {
                    demoSelected = !demoSelected
                }

            }, backgroundColor = it, shape = RoundedCornerShape(8.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(16.dp)) {
                Text(text = itemData.display,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 19.6.sp,
                    color = Color.White)

                if (demoSelected) {
                    Icon(imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.Green,
                        modifier = Modifier.size(20.dp))
                }
            }

        }
    }
}


@Composable
fun ItemList(
    list: MutableList<ItemData>,
    rowTitle: String,
    content: (@Composable () -> Unit)? = null,
) {


    val itemDataState = remember { ItemDataState(list) }


    Column(Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
        Row(Modifier.fillMaxWidth()) {
            Text(text = rowTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 28.sp,
                color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        itemDataState.list.forEachIndexed { index, itemData ->
            ItemDisplay(itemData = itemData, onCheckChanged = itemDataState::onItemSelected)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        content?.let { it() }
    }
}

@Composable
fun SkinColorLayout() {
    val itemDataListDemo = remember {
        mutableStateListOf(ItemData(1, "Fair", bgColor = Color(0xffC8AC99)),
            ItemData(2, "Light", bgColor = Color(0xffB1886C)),
            ItemData(3, "Medium Light", bgColor = Color(0xffA0795F)),
            ItemData(4, "Medium ", bgColor = Color(0xff8C624E)),
            ItemData(5, "Medium Dark", bgColor = Color(0xff634B3F)),
            ItemData(6, "Dark", bgColor = Color(0xff544239)))

    }
    ItemList(list = itemDataListDemo, "Please select your skin color")
}

@Composable
fun AgeRange() {
    val itemListData = remember {
        mutableStateListOf(ItemData(1, "Pre Teen - 30 years", bgColor = Color(0x66959393)),
            ItemData(id = 2, display = "30 - 60 years", bgColor = Color(0x66959393)),
            ItemData(3, "Above 60 years", bgColor = Color(0x66959393)))
    }

    ItemList(list = itemListData, rowTitle = "Please select your age range")
}

@Preview
@Composable
fun SPFLevel() {

    val itemDataListDemo = remember {
        mutableStateListOf(ItemData(1, "SPF 15", bgColor = Color(0x66959393)),
            ItemData(2, "SPF 20", bgColor = Color(0x66959393)),
            ItemData(3, "SPF 30", bgColor = Color(0x66959393)),
            ItemData(4, "SPF 40", bgColor = Color(0x66959393)),
            ItemData(5, "SPF 50", bgColor = Color(0x66959393)),
            ItemData(6, "SPF 70 ", bgColor = Color(0x66959393)),
            ItemData(7, "None", bgColor = Color(0x66959393)))
    }

    ItemList(list = itemDataListDemo,
        rowTitle = "Choose SPF level of your sunscreen",
        content = { SPFLevelContent() })
}

@Composable
fun SPFLevelContent() {

    Column(Modifier.fillMaxWidth()) {
        Card(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color(0x66959593)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Outdoor Timings",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 22.4.sp,
                        color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                TimeSelection()
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Based on the outdoor timing, Clothing and Location inputs we recommend you sunscreen with SPF 40",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 19.6.sp,
                        color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color(0x66959593)) {
            Text(text = "Wearing sunscreen is one of the best — and easiest — ways to protect your skin's appearance and health at any age.Sunscreen may help prevent the sun's rays from causing photographing and skin cancer.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                modifier = Modifier.padding(16.dp))
        }
    }


}

@Composable
fun TimeSelection() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Box(Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = "Start Time",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 19.6.sp,
                    color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(5.dp),
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(2.dp, color = Color(0x99E4E4E4))) {
                    Text(text = "10:00 am",
                        color = Color(0x99000000),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 18.5.dp))
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = "End Time",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 19.6.sp,
                    color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(5.dp),
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(2.dp, color = Color(0x99E4E4E4))) {
                    Text(text = "10:00 am",
                        color = Color(0x99000000),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 18.5.dp))
                }
            }
        }
    }
}
