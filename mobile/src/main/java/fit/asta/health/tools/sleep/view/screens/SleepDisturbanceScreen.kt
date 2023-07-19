package fit.asta.health.tools.sleep.view.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.tools.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.tools.sleep.utils.SleepNetworkCall
import fit.asta.health.tools.sleep.view.components.SleepCardItems
import fit.asta.health.tools.sleep.viewmodel.SleepToolViewModel

@Composable
fun SleepDisturbanceScreen(
    navController: NavController,
    sleepDisturbanceState: SleepNetworkCall<SleepDisturbanceResponse>,
    loadDataFunction: () -> Unit
) {

    val context = LocalContext.current

    // Fetching the Data from the Server
    LaunchedEffect(Unit) {
        loadDataFunction()
    }

    // Handling the State of the Api Call
    when (sleepDisturbanceState) {

        // Initialized State
        is SleepNetworkCall.Initialized -> {
            loadDataFunction()
        }

        // loading State
        is SleepNetworkCall.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Success State
        is SleepNetworkCall.Success -> {

            // List of Items or we can say disturbances data
            val itemList = sleepDisturbanceState.data?.sleepData

            // Checking if the itemList is null or not
            if (itemList != null) {
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

                    itemList.propertyData?.let { list ->
                        items(list.size) {
                            SleepCardItems(textToShow = list[it].name)
                        }
                    }

                    itemList.customPropertyData?.let { list ->
                        items(list.size) {
                            SleepCardItems(textToShow = list[it].name)
                        }
                    }
                }
            }
        }

        // failure State
        is SleepNetworkCall.Failure -> {
            Toast.makeText(
                context,
                sleepDisturbanceState.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}