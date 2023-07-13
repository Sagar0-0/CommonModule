package fit.asta.health.tools.sleep.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fit.asta.health.R
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.tools.sleep.viewmodel.SleepToolViewModel

@Composable
fun SleepDisturbanceScreen(
    navController: NavController,
    sleepToolViewModel: SleepToolViewModel
) {

    val itemList = listOf(
        Pair("Add Other", R.drawable.sleep_factors),
        Pair("Dream", R.drawable.dreamcatcher),
        Pair("Kids", R.drawable.kids),
        Pair("Love", R.drawable.favorite),
        Pair("Water", R.drawable.water_glass),
        Pair("Toilet", R.drawable.toilet)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Text(
                text = "Tap to add/remove the reasons for sleep disturbances",

                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                fontFamily = FontFamily.SansSerif
            )
        }

        items(itemList.size) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(spacing.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Current Item
                    val currentItem = itemList[it]

                    Icon(
                        painter = painterResource(id = currentItem.second),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )

                    Text(text = currentItem.first)
                }
            }
        }
    }
}