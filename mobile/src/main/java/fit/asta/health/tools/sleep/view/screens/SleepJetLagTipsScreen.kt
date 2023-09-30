package fit.asta.health.tools.sleep.view.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.tools.sleep.model.network.jetlag.SleepJetLagTipResponse
import fit.asta.health.tools.sleep.utils.SleepNetworkCall

@Composable
fun SleepJetLagTipsScreen(
    jetLagDetails: SleepNetworkCall<SleepJetLagTipResponse>,
    loadData: () -> Unit
) {

    // Context of the Screen to show Toast
    val context = LocalContext.current

    // Fetching the Data whenever the User is opening this screen
    LaunchedEffect(Unit) {
        loadData()
    }

    // Checking the State of the Screen
    when (jetLagDetails) {

        // Initialized State
        is SleepNetworkCall.Initialized -> {
            loadData()
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)
            ) {

                item {
                    Text(
                        text = "Here are few tips and tricks that may help prevent " +
                                "Jet lag or reduce its effects ",

                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = FontFamily.SansSerif
                    )
                }

                // Showing all the Jet Lag Details in the UI
                jetLagDetails.data?.jetLagTipsData?.jetLagTipDetails?.let { jetDetailsList ->
                    items(jetDetailsList.size) {

                        // Current Item
                        val currentItem = jetDetailsList[it]

                        val icon = when (currentItem.sub) {
                            "Get some sun" -> R.drawable.sunny
                            "Adjust your sleep-wake schedule" -> R.drawable.clock
                            "Focus on getting quality sleep" -> R.drawable.sleeping_in_bed
                            "Avoid new foods" -> R.drawable.food_restrictions
                            "Drink lots of water" -> R.drawable.water_drop
                            "Get solid sleep before your trip" -> R.drawable.solid_sleep
                            "Don’t over-schedule your first days" -> R.drawable.schedule_sleep
                            else -> R.drawable.sleep_factors
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.small)
                        ) {

                            Image(
                                painter = painterResource(id = icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp),
                                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small)) {

                                // Subject / Title
                                Text(
                                    text = currentItem.sub,

                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W600,
                                    fontFamily = FontFamily.SansSerif
                                )

                                // Description
                                Text(
                                    text = currentItem.dsc,

                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W400,
                                    fontFamily = FontFamily.SansSerif
                                )
                            }
                        }
                    }
                }
            }
        }

        // failure State
        is SleepNetworkCall.Failure -> {
            Toast.makeText(
                context,
                jetLagDetails.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}