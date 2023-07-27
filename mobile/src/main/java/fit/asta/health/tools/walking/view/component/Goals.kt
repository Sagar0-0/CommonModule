package fit.asta.health.tools.walking.view.component

import android.annotation.SuppressLint
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
import fit.asta.health.common.ui.components.*
import fit.asta.health.tools.view.components.ItemData
import fit.asta.health.tools.view.components.ItemList


@Composable
fun GoalLayout(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(ItemData(1, "Loosing Weight", bgColor = Color.LightGray),
            ItemData(id = 2, display = "Boost Mind", bgColor = Color.LightGray),
            ItemData(3, "Tone Body", bgColor = Color.LightGray),
            ItemData(4, "Improve muscles", bgColor = Color.LightGray),
            ItemData(5, "Reduce Stress and Anxiety", bgColor = Color.LightGray))
    }

    ItemList(list = itemListData, rowTitle = "Select you walking goals", it = it)

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Goal() {

    AppScaffold(topBar = {
        NavigationBar(content = {
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
        GoalLayout(it = it)
    })

}