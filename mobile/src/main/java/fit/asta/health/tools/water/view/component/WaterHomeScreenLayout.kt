package fit.asta.health.tools.water.view.component

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.tools.view.components.CardSunBurn
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.roundToInt

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun WaterHomeScreen(paddingValues: PaddingValues, viewModel: WaterViewModel = hiltViewModel()) {

    val waterTool by viewModel.modifiedWaterTool.collectAsState()

    fun valueChanged(value: Float) {
        val x = (6.0 * value) / 1.0
        //wT=(x.toInt()+1).toString()
//        viewModel.changedTarget(x.toInt() + 1)
        Log.i("Liters", (x.toInt() + 1).toString())
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(paddingValues)
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        CardSunBurn(cardTitle = "Total",
            cardValue = "${waterTool?.progressData?.consumed?.toString()} Liters",
            recommendedTitle = "Recommended",
            remainingValue = "${waterTool?.progressData?.remaining?.toString()!!} Liters",
            goalTitle = "Goal",
            goalValue = "${waterTool?.progressData?.goal?.toString()!!} Liters",
            remainingTitle = "Remaining",
            recommendedValue = "${
                waterTool?.progressData?.recommendation?.roundToInt().toString()
            } Liters",
            valueChanged = {
                valueChanged(it)
            })

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = fit.asta.health.R.drawable.information_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Tie it into a routine. Drink a glass of water every time you brush your teeth, eat a meal or use the bathroom.",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Thin,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

