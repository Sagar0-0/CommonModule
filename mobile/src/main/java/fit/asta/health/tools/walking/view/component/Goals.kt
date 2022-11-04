package fit.asta.health.tools.walking.view.component

import android.annotation.SuppressLint
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
import fit.asta.health.tools.sunlight.view.components.ItemData
import fit.asta.health.tools.sunlight.view.components.ItemList


@Composable
fun GoalLayout(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(ItemData(1, "Loosing Weight", bgColor = Color(0x66959393)),
            ItemData(id = 2, display = "Boost Mind", bgColor = Color(0x66959393)),
            ItemData(3, "Tone Body", bgColor = Color(0x66959393)),
            ItemData(4, "Improve muscles", bgColor = Color(0x66959393)),
            ItemData(5, "Reduce Stress and Anxiety", bgColor = Color(0x66959393)))
    }

    ItemList(list = itemListData, rowTitle = "Select you walking goals", it = it)

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Goal() {

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
                Text(text = "Goals",
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_physique),
                        contentDescription = null,
                        Modifier.size(24.dp),
                        tint = Color(0xff0088FF))
                }
            }
        }, elevation = 10.dp)
    }, content = {
        GoalLayout(it = it)
    })

}