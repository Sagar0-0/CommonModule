package fit.asta.health.tools.sunlight.view.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.tools.sunlight.view.components.bottomsheet.HomeScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                Text(text = "Bengaluru, Karnataka",
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
        }, elevation = 10.dp, backgroundColor = Color.White)
    }, content = {
        HomeScreen()
    })

}

@Composable
fun SunlightLayout(it: PaddingValues) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(it)
        .verticalScroll(rememberScrollState())) {

        Spacer(modifier = Modifier.height(70.dp))

        Row(Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {

            Text(text = "Upcoming Slots",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                UpcomingSlotsCard()
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                UpcomingSlotsCard()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Text(text = "Total Duration",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        CardSunBurn()

        Spacer(modifier = Modifier.height(24.dp))

        Row(Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Text(text = "Total Vitamin D ",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        TotalVitaminDCard()

        Spacer(modifier = Modifier.height(24.dp))
    }
}