package fit.asta.health.tools.sunlight.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.tools.sunlight.view.components.SunlightBottomSheet

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SunlightHomeScreen() {

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
                Text(text = "Sunlight",
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_physique),
                        contentDescription = null,
                        Modifier.size(24.dp),
                       // tint = Color(0xff0088FF)
                    tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }, elevation = 10.dp, backgroundColor = MaterialTheme.colorScheme.surface)
    }, content = {
        SunlightBottomSheet(paddingValues = it)
    })

}