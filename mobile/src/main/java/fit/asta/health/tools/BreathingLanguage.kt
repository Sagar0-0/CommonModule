package fit.asta.health.tools

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
fun BreathingLanguage(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(ItemData(1, "English", bgColor = Color(0x66959393)),
            ItemData(id = 2, display = "Hindi", bgColor = Color(0x66959393)),
            ItemData(3, "Kannada", bgColor = Color(0x66959393)),
            ItemData(4, "Telugu", bgColor = Color(0x66959393)),
            ItemData(5, "Bengali", bgColor = Color(0x66959393)),
        )
    }

    ItemList(list = itemListData, rowTitle = "Select your preferred Language", it = it)

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Language() {

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
                Text(text = "Language",
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
        BreathingLanguage(it = it)
    })

}