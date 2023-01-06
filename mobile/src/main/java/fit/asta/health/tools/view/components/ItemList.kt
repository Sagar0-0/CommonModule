package fit.asta.health.tools.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemList(
    list: MutableList<ItemData>,
    rowTitle: String,
    content: (@Composable () -> Unit)? = null,
    it: PaddingValues? = null,
) {


    val itemDataState = remember { ItemDataState(list) }


    it?.let { it1 ->
        Modifier
            .fillMaxWidth()
            .padding(it1)
            .verticalScroll(rememberScrollState())
    }?.let { it2 ->
        Column(it2) {

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                Text(text = rowTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp,
                    color = MaterialTheme.colorScheme.onBackground)
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
}