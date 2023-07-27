package fit.asta.health.tools.breathing.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
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
import fit.asta.health.common.ui.components.AppScaffold
import fit.asta.health.tools.view.components.ItemData
import fit.asta.health.tools.view.components.ItemList

@Composable
fun BreathingMusic(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(
            ItemData(1, "The Breath of Joy", bgColor = Color.LightGray),
            ItemData(id = 2, display = "Source of your Prana", bgColor = Color.LightGray),
            ItemData(3, "Vital life force", bgColor = Color.LightGray),
            ItemData(4, "Release Stress", bgColor = Color.LightGray),
            ItemData(5, "Quieting the Mind", bgColor = Color.LightGray),
        )
    }

    ItemList(list = itemListData, rowTitle = "Select music for your breathing exercise", it = it)
}

@Preview
@Composable
fun Music() {

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
                Text(text = "Language",
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
        BreathingMusic(it = it)
    })
}