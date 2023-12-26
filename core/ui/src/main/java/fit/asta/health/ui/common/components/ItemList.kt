package fit.asta.health.ui.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.TitleTexts

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

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                TitleTexts.Level2(
                    text = rowTitle,
                    color = AppTheme.colors.onBackground
                )
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