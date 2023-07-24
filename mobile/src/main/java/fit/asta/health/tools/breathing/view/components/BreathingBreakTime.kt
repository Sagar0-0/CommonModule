package fit.asta.health.tools.breathing.view.components

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.tools.view.components.ItemData
import fit.asta.health.tools.view.components.ItemList

@Composable
fun BreathingBreakTime(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(
            ItemData(1, "1 Minute", bgColor = Color.LightGray),
            ItemData(id = 2, display = "2 Minutes", bgColor = Color.LightGray),
            ItemData(3, "3 Minutes", bgColor = Color.LightGray),
            ItemData(4, "4 Minutes", bgColor = Color.LightGray),
            ItemData(5, "5 Minutes", bgColor = Color.LightGray),
            ItemData(6, "6 Minutes", bgColor = Color.LightGray),
            ItemData(7, "7 Minutes", bgColor = Color.LightGray),
            ItemData(8, "8 Minutes", bgColor = Color.LightGray),
        )
    }

    ItemList(list = itemListData,
        rowTitle = "Select the break time for your breathing exercise",
        it = it)

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BreakTime() {

    Scaffold(topBar = {
        NavigationBar(content = {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_exercise_back),
                        contentDescription = null,
                        Modifier.size(24.dp))
                }
                Text(text = "Break Time",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_physique),
                        contentDescription = null,
                        Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary)
                }
            }
        }, tonalElevation = 10.dp)
    }, content = {
        BreathingBreakTime(it = it)
    })

}