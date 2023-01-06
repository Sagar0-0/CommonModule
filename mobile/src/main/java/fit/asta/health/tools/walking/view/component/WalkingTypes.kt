package fit.asta.health.tools.walking.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
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
fun WalkingTypeLayoutWalkingType(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(ItemData(1, "Walking", bgColor = Color.LightGray),
            ItemData(id = 2, display = "Stairs, Walk", bgColor = Color.LightGray),
            ItemData(3, "Treadmill", bgColor = Color.LightGray),
            ItemData(4, "Dog Walk", bgColor = Color.LightGray),
            ItemData(5, "Power Walk", bgColor = Color.LightGray),
            ItemData(5, "Running", bgColor = Color.LightGray),
            ItemData(5, "Trekking", bgColor = Color.LightGray))
    }

    ItemList(list = itemListData, rowTitle = "Select the walking type", it = it)

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun WalkingType() {

    Scaffold(topBar = {
        BottomNavigation(content = {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_exercise_back),
                        contentDescription = null,
                        Modifier.size(24.dp))
                }
                Text(text = "Walking Types",
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
        }, elevation = 10.dp)
    }, content = {
        WalkingTypeLayoutWalkingType(it = it)
    })

}