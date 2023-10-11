package fit.asta.health.tools.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts

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
        AppCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable {
                    if (!itemData.isSelected) {
                        demoSelected = !demoSelected
                    }
                }
        ) {
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
        BodyTexts.Level2(
            text = itemData.display,
            color = AppTheme.colors.onPrimary
        )

        if (demoSelected) {
            AppIcon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.Green,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

