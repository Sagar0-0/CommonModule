package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

        itemDataState.list.forEachIndexed { _, itemData ->
            ItemDisplay(itemData = itemData, onCheckChanged = itemDataState::onItemSelected)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        content?.let { it() }
    }
}