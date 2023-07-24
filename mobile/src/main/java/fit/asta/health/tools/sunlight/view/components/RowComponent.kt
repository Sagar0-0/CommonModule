package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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