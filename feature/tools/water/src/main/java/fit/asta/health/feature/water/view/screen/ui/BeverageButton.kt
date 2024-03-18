package fit.asta.health.feature.water.view.screen.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppExtendedFloatingActionButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.cards.AppElevatedCardWithColor
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.water.view.screen.WTEvent
import fit.asta.health.feature.water.view.screen.WaterToolUiState
import fit.asta.health.feature.water.viewmodel.WaterToolViewModel
import fit.asta.health.resources.drawables.R

@Composable
fun SetOfDefaultChips(
    event: (WTEvent) -> Unit,
    uiState: WaterToolUiState,
    viewModel: WaterToolViewModel = hiltViewModel(),
    onClickWater: () -> Unit,
    onClickCoconut: () -> Unit,
    onClickFirstPref: () -> Unit,
    onClickSecondPref: () -> Unit,
    onClickCustomize: () -> Unit,
    onClickRecentAdded: () -> Unit,
    addedName: String,
) {
    var title by remember {
        viewModel.bevTitle.value
    }
    var quantity by remember {
        viewModel.bevQuantity.value
    }
    val waterQuantity = uiState.sliderValueWater.toInt()
    val coconutQuantity = uiState.sliderValueCoconut.toInt()
    val firstPrefQuantity = uiState.sliderValueFirstPreference.toInt()
    val secondPrefQuantity = uiState.sliderValueSecondPreference.toInt()
    val recentAddedQuantity = uiState.sliderValueRecentAdded.toInt()

    Log.d("rishi","Water : ${uiState.sliderValueWater}" +
            "Coconut : ${uiState.sliderValueCoconut}" +
            "FirstPref : ${uiState.sliderValueFirstPreference}")
    val list by viewModel.beverageList.collectAsStateWithLifecycle()
    val listSize = list.size
    Log.d("rishi", "List size : $listSize \n ${list.toList()}")

    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level1),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
//
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val name = if (listSize == 0) "Water" else list[0].name
                BevChips(
                    name = name,
                    uiState = uiState,
                    containerColor = backGroundContainerColor(Color(0xFF092251), Color(0xFF99DDFF)),
                    contentColor = backGroundContentColor(Color(0xFF092251), Color(0xFF99DDFF)),
                    R.drawable.water,
                    waterQuantity,
                    event = event,
                    onClick = {
                        title = "Water"
                        quantity = waterQuantity
                        event(WTEvent.UpdateBevDetails(title, quantity))
                        event(WTEvent.UpdateBevQuantity)
                       // event(WTEvent.ConsumptionDetails)
                    },
                    onClickBeverage = {
                        onClickWater()
                    })
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val name = if (listSize == 0) "Coconut" else list[1].name
                BevChips(
                    name = name,
                    uiState = uiState,
                    containerColor = backGroundContainerColor(Color(0xFF398300), Color(0xFFB6D83D)),
                    contentColor = backGroundContentColor(Color(0xFF398300), Color(0xFFB6D83D)),
                    R.drawable.coconut,
                    coconutQuantity,
                    event = event,
                    onClick = {
                        title = "Coconut"
                        quantity = coconutQuantity
                        event(WTEvent.UpdateBevDetails(title, quantity))
                        event(WTEvent.UpdateBevQuantity)
//                        event(WTEvent.ConsumptionDetails)
                    },
                    onClickBeverage = {
                        onClickCoconut()
                    })
            }

        }
        AppCard(
            modifier = Modifier
                .fillMaxWidth(),
//            colors = CardDefaults.cardColors(
//                containerColor = Color.Transparent,
//            )
        ) {
            HeadingTexts.Level4(
                text = "Your Most Frequent Used :",
                modifier = Modifier.padding(AppTheme.spacing.level1)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val name = if (listSize == 0) "Not Defined" else list[2].name
                    BevChips(
                        name = name,
                        uiState = uiState,
                        containerColor = backGroundContainerColor(
                            Color(0xFFE85714),
                            Color(0xFFE9B077)
                        ),
                        contentColor = backGroundContentColor(Color(0xFFE85714), Color(0xFFE9B077)),
                        R.drawable.tea,
                        firstPrefQuantity,
                        event = event,
                        onClick = {
                            title = name
                            quantity = firstPrefQuantity
                            event(WTEvent.UpdateBevDetails(title, quantity))
                            event(WTEvent.UpdateBevQuantity)
//                            event(WTEvent.ConsumptionDetails)
                        },
                        onClickBeverage = {
                            onClickFirstPref()
                        })
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val name = if (listSize == 0) "Water" else list[3].name
                    BevChips(
                        name = name,
                        uiState = uiState,
                        containerColor = backGroundContainerColor(
                            Color(0xFFE9980B),
                            Color(0xFFEACE7F)
                        ),
                        contentColor = backGroundContentColor(Color(0xFFE9980B), Color(0xFFEACE7F)),
                        R.drawable.coffee,
                        secondPrefQuantity,
                        event = event,
                        onClick = {
                            title = name
                            quantity = secondPrefQuantity
                            event(WTEvent.UpdateBevDetails(title, quantity))
                            event(WTEvent.UpdateBevQuantity)
//                            event(WTEvent.ConsumptionDetails)
                        },
                        onClickBeverage = {
                            onClickSecondPref()
                        })
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.spacing.level1, start = AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.Start
        ) {
            HeadingTexts.Level4(text = "Add Yours :")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.spacing.level1),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (addedName.isNotEmpty()) {
                    BevChips(
                        addedName,
                        uiState = uiState,
                        containerColor = backGroundContainerColor(
                            Color(0xFF9d9ad9),
                            Color(0xFFcedef0)
                        ),
                        contentColor = backGroundContentColor(Color(0xFF9d9ad9), Color(0xFFcedef0)),
                        R.drawable.unfocusedcontainer,
                        recentAddedQuantity,
                        event = event,
                        onClick = {
                            title = addedName
                            quantity = recentAddedQuantity
                            event(WTEvent.UpdateBevDetails(title, quantity))
                            event(WTEvent.UpdateBevQuantity)
//                            event(WTEvent.ConsumptionDetails)
                        },
                        onClickBeverage = {
                            onClickRecentAdded()
                        })
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AppExtendedFloatingActionButton(
                    text = { BodyTexts.Level3(text = "Customize") },
                    icon = { AppIcon(imageVector = Icons.Default.Add, contentDescription = null) },
                    onClick = { onClickCustomize() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spacing.level1)
                )
            }
        }

    }
}
@Composable
fun BevChips(
    name: String,
    uiState: WaterToolUiState,
    containerColor: Color,
    contentColor: Color,
    resId: Int,
    quantity: Int,
    viewModel: WaterToolViewModel = hiltViewModel(),
    event: (WTEvent) -> Unit,
    onClick: () -> Unit,
    onClickBeverage: () -> Unit
) {
    var showUndoButton by remember {
        mutableStateOf(false)
    }
    AppElevatedCardWithColor(
        modifier = Modifier
            .padding(AppTheme.spacing.level2)
            .fillMaxWidth(1f),
        containerColor = containerColor
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            // modifier = Modifier.fillMaxWidth(1f)
        ) {
            Box(modifier = Modifier
                .clickable {
                    onClick()
                    showUndoButton = !showUndoButton
                }
                .fillMaxWidth(0.6f)
                .padding(8.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Column(verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    HeadingTexts.Level4(text = name)
                    BodyTexts.Level2(
                        text = " $quantity  ml",
                        color = contentColor
                    )
                }
            }
            ClickableBeverage(
                containerColor = containerColor,
                contentColor = contentColor, resId = resId
            ) {
                onClickBeverage()
            }
        }
        UndoCard(contentColor = contentColor, event = event,
            name = name, consumptionExist = uiState.consumedBevExist,
            undoQuantity = uiState.undoBevQty)
    }
}

@Composable
fun ClickableBeverage(
    containerColor: Color,
    contentColor: Color,
    resId: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth(1f)
            .background(Color.White, RoundedCornerShape(topStart = 16.dp, bottomStart = 8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AppIcon(
            painter = painterResource(id = resId), contentDescription = "Tea",
            tint = containerColor,
            modifier = Modifier
                .scale(1.4f)
                .height(AppTheme.spacing.level2)
        )
        Box(
            modifier = Modifier
                .padding(top = 5.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            AppIcon(
                imageVector = Icons.Default.Edit, contentDescription = "Quantity",
                tint = containerColor,
                modifier = Modifier
                    .scale(0.7f)
                    .align(Alignment.BottomEnd)

            )
        }
    }
}

@Composable
fun backGroundContentColor(darkColor: Color, lightColor: Color): Color {

    val backgroundContentColor = if (isSystemInDarkTheme()) {
        lightColor
    } else {
        darkColor
    }
    return backgroundContentColor
}

@Composable
fun backGroundContainerColor(darkColor: Color, lightColor: Color): Color {
    val backgroundContainerColor = if (isSystemInDarkTheme()) {
        darkColor
    } else {
        lightColor
    }
    return backgroundContainerColor
}


