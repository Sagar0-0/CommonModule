package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun LazyColumnWithSelection() {

    var isSelected by remember {
        mutableStateOf(false)
    }

    var selectedIndex by remember { mutableStateOf(0) }

    val onItemClick = { index: Int -> selectedIndex = index }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(100) { index ->

            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick.invoke(index)
                }
                .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Item $index", modifier = Modifier.padding(12.dp), color = Color.White)
                if (selectedIndex == index) {
                    Icon(imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.Green,
                        modifier = Modifier.size(20.dp))
                }
            }

        }
    }

}