package fit.asta.health.tools.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    // were updating the entire healthHisList in a single pass using its iterator
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
        Card(
            modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                if (!itemData.isSelected) {
                    demoSelected = !demoSelected
                }
            },
            colors = CardDefaults.cardColors(it),
            shape = RoundedCornerShape(8.dp)) {
            CardComponents(itemData, demoSelected)
        }
    }
}

@Composable
private fun CardComponents(
    itemData: ItemData,
    demoSelected: Boolean,
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(16.dp)) {
        Text(text = itemData.display,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 19.6.sp,
            color = MaterialTheme.colorScheme.onPrimary)

        if (demoSelected) {
            Icon(imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.Green,
                modifier = Modifier.size(20.dp))
        }
    }
}

