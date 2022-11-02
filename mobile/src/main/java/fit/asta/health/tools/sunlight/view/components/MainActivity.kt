package fit.asta.health.tools.sunlight.view.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


        }
    }
}

@Preview
@Composable
fun Demo2() {

    var items by remember {
        mutableStateOf((1..20).map {
            ListItem(title = "Item $it", isSelected = false)
        })
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items.size) { i ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    items = items.mapIndexed { j, item ->
                        if (i == j) {
                            item.copy(isSelected = !item.isSelected)
                        } else item
                    }

                }
                .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = items[i].title)
                if (items[i].isSelected) {
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
fun ItemView(index: Int, selected: Boolean, onClick: (Int) -> Unit) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClick.invoke(index)
        }
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Item $index", modifier = Modifier.padding(12.dp), color = Color.White)
        if (selected) {
            Icon(imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.Green,
                modifier = Modifier.size(20.dp))
        }
    }

}


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
                    if (selectedIndex == index) {
                        isSelected = !isSelected
                    }
                }
                .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Item $index", modifier = Modifier.padding(12.dp), color = Color.White)
                if (isSelected) {
                    Icon(imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.Green,
                        modifier = Modifier.size(20.dp))
                }
            }

        }
    }
}