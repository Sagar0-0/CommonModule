package fit.asta.health.profile.bottomsheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ListItem(val id: Int, val title: String, val description: String)

@HiltViewModel
class MyViewModel @Inject constructor() : ViewModel() {

    val itemList = mutableStateListOf(
        ListItem(id = 0, title = "Demo 0", description = "null"),
        ListItem(id = 1, title = "Demo 1", description = "null"),
        ListItem(id = 2, title = "Demo 2", description = "null"),
        ListItem(id = 3, title = "Demo 3", description = "null"),
        ListItem(id = 4, title = "Demo 4", description = "null"),
    )

    val selectedIds = mutableStateListOf<Int>()

    val selectedItems = mutableStateListOf<ListItem>()

    fun onItemClicked(itemId: Int) {
        if (selectedIds.contains(itemId)) {
            selectedIds.remove(itemId)
            selectedItems.removeAll { it.id == itemId }
        } else {
            selectedIds.add(itemId)
            itemList.firstOrNull { it.id == itemId }?.let { selectedItems.add(it) }
        }
    }

}

@Preview
@Composable
fun MyScreen(viewModel: MyViewModel = hiltViewModel()) {

    LazyColumn {

        viewModel.itemList.forEach {
            item {
                ListItem(item = it,
                    isSelected = viewModel.selectedIds.contains(it.id),
                    onClick = { viewModel.onItemClicked(it.id) })
            }
        }

        item {
            Text(text = "Selected Items:")
        }

        viewModel.selectedItems.forEach {
            item {
                Text(text = "${it.title} - ${it.description}")

            }
        }

    }
}

@Composable
fun ListItem(item: ListItem, isSelected: Boolean, onClick: () -> Unit) {

    val backgroundColor = if (isSelected) Color.Gray else Color.Transparent

    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(color = backgroundColor)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = item.title)
        Text(text = item.description)
    }
}
