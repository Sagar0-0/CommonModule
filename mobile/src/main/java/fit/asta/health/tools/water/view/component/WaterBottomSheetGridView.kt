package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import fit.asta.health.R
import fit.asta.health.tools.sunlight.view.components.BottomSheetButtonLayout
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.DividerLineCenter
import fit.asta.health.tools.view.PracticeGridView
import fit.asta.health.tools.view.components.AddMoreWater
import fit.asta.health.tools.view.components.PracticeExpandedCard
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun WaterBottomSheetGridView(viewModel: WaterViewModel = hiltViewModel()) {

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

    Column(Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.heightIn(16.dp))

        DividerLineCenter()

        Spacer(modifier = Modifier.heightIn(16.dp))

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.heightIn(min = 100.dp, max = 550.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        /*TextButton(onClick = {},modifier = Modifier.padding(vertical = 10.dp)) {
                            Text(text = "see all>",fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 22.4.sp,)
                        }*/
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
                           /* Popup {
                                Column {
                                    FlowRow {
                                        viewModel.waterTool.collectAsState().value?.allBeveragesList?.forEach {
                                            BeveragesComponent(it.title,it.code)
                                        }
                                    }
                                    ElevatedButton(onClick = { *//*TODO*//* }) {
                                        Text("save")
                                    }
                                }

                            }*/
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

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    QuantityLayout()
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

            BottomSheetButtonLayout()

        }
    }
}