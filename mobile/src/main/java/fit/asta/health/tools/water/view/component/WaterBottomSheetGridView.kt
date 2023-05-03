package fit.asta.health.tools.water.view.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.R
import fit.asta.health.tools.sunlight.view.components.BottomSheetButtonLayout
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.DividerLineCenter
import fit.asta.health.tools.view.PracticeGridView
import fit.asta.health.tools.view.components.AddMoreWater
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun WaterBottomSheetGridView(
    scaffoldState: BottomSheetScaffoldState,
    viewModel: WaterViewModel = hiltViewModel()
) {

    val cardList = listOf(
        PracticeGridView(
            cardTitle = "Lifestyle",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "Lightly Active"
        ),
        PracticeGridView(
            cardTitle = "Work",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "Indoor"
        ),
        PracticeGridView(
            cardTitle = "Health",
            cardImg = R.drawable.ic_baseline_cancel_24,
            cardValue = "None"
        )
    )

    val openDialog = remember { mutableStateOf(false) }
    val waterLive by viewModel.modifiedWaterTool.collectAsStateWithLifecycle()
    val selectedBeverageData by viewModel.containerInCharge.collectAsStateWithLifecycle()
    val showSlider by viewModel.showSlider.collectAsStateWithLifecycle()

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false }
        ) {
            waterLive?.let { AllBeveragesPopUp(it) }
        }
    }

    Column(Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.heightIn(16.dp))

        DividerLineCenter()

        Spacer(modifier = Modifier.heightIn(16.dp))

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.heightIn(min = 100.dp, max = 550.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                if (scaffoldState.bottomSheetState.isExpanded) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text(
                                "BEVERAGES",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 22.4.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            TextButton(
                                onClick = {
                                    openDialog.value = !openDialog.value
                                }) {
                                Text(
                                    "see all",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 22.4.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }


                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        BeveragesLayout()
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        Text(
                            "QUANTITY",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 22.4.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                } else {
                    item {
                        Text(
                            "QUANTITY-${selectedBeverageData?.title}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 22.4.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(5.dp))
                }

                item {
                    QuantityLayout()
                }

                if (showSlider) {
                    item {
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 0.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)) {
                            Column (modifier = Modifier.padding(bottom = 10.dp)){
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(
                                        onClick = {
                                            viewModel.closeSlider()
                                        }
                                    ) {
                                        Icon(Icons.Rounded.Close, "close")
                                    }
                                }
                                ForecastSlider(
                                    onValueChange = {
                                        Log.i("Water Bottom Sheet Grid View 189", it.toString())
                                        viewModel.sliderValueChanged(it)
                                    }
                                )
                            }
                        }

                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                /*item {
                    Text("PRACTICE",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 22.4.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(horizontal = 16.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }*/

                /*item {
                    val itemSize: Dp = LocalConfiguration.current.screenWidthDp.dp / 2

                    FlowRow(mainAxisSize = SizeMode.Expand,
                        mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween) {
                        cardList.forEachIndexed { index, _ ->
                            PracticeExpandedCard(cardTitle = cardList[index].cardTitle,
                                cardImg = cardList[index].cardImg,
                                cardValue = cardList[index].cardValue,
                                modifier = Modifier.size(width = itemSize, height = 100.dp))
                        }
                    }
                }*/

                if (scaffoldState.bottomSheetState.isExpanded) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        AddMoreWater()
                    }

                    item {
                        Spacer(modifier = Modifier.height(64.dp))
                    }
                }

            }

            BottomSheetButtonLayout()

        }
    }
}