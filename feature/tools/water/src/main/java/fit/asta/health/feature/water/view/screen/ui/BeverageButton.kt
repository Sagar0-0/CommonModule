package fit.asta.health.feature.water.view.screen.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.button.AppExtendedFloatingActionButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.cards.AppElevatedCardWithColor
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.water.view.screen.WTEvent
import fit.asta.health.feature.water.viewmodel.WaterToolViewModel
import fit.asta.health.resources.drawables.R

@Composable
fun SetOfDefaultChips(
    event: (WTEvent) -> Unit,
    viewModel: WaterToolViewModel = hiltViewModel(),
    waterQuantity: Int,
    coconutQuantity: Int,
    firstPrefQuantity: Int,
    secondPrefQuantity: Int,
    recentAddedQuantity: Int,
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
                BevChips(
                    if (listSize == 0) "Water" else list[0].name,
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
                        event(WTEvent.colorChange(Color(0xFF00458B)))
                    },
                    onClickBeverage = {
                        onClickWater()
                    })
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                BevChips(
                    if (listSize == 0) "Water" else list[1].name,
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
                        event(WTEvent.colorChange(Color(0xFF398300)))
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
                    BevChips(
                        if (listSize == 0) "Water" else list[2].name,
                        containerColor = backGroundContainerColor(
                            Color(0xFFE85714),
                            Color(0xFFE9B077)
                        ),
                        contentColor = backGroundContentColor(Color(0xFFE85714), Color(0xFFE9B077)),
                        R.drawable.tea,
                        firstPrefQuantity,
                        event = event,
                        onClick = {
                            title = "Tea"
                            quantity = firstPrefQuantity
                            event(WTEvent.UpdateBevDetails(title, quantity))
                            event(WTEvent.UpdateBevQuantity)
                            event(WTEvent.colorChange(Color(0xFFE85714)))
                        },
                        onClickBeverage = {
                            onClickFirstPref()
                        })
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BevChips(
                        if (listSize == 0) "Water" else list[3].name,
                        containerColor = backGroundContainerColor(
                            Color(0xFFE9980B),
                            Color(0xFFEACE7F)
                        ),
                        contentColor = backGroundContentColor(Color(0xFFE9980B), Color(0xFFEACE7F)),
                        R.drawable.coffee,
                        secondPrefQuantity,
                        event = event,
                        onClick = {
                            title = "Coffee"
                            quantity = secondPrefQuantity
                            event(WTEvent.UpdateBevDetails(title, quantity))
                            event(WTEvent.UpdateBevQuantity)
                            event(WTEvent.colorChange(Color(0xFFE9980B)))
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
                            event(WTEvent.colorChange(Color(0xFF9d9ad9)))
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
        AnimatedVisibility(visible = showUndoButton) {
            ButtonWithColor(color = contentColor, text = "Undo",
                modifier = Modifier.fillMaxWidth()) {
                viewModel.showUndoDialog.value = true
                event(WTEvent.UndoConsumption(name))
            }
        }
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
//@Composable
//fun BeverageInfoCard(
//    name: String,
//    containerColor: Color,
//    contentColor: Color,
//    resId: Int,
//    quantity: Int,
//    onClick: () -> Unit,
//    onClickBeverage: () -> Unit
//) {
//    var showUndoButton by remember {
//        mutableStateOf(false)
//    }
//    AppElevatedCardWithColor(
//        contentColor = contentColor,
//        containerColor = containerColor,
//        modifier = Modifier
//            .scale(1.1f)
//            .padding(8.dp)
//    ) {
//        Column(
//            modifier = Modifier.clickable {
//                onClick()
//            },
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                AppIcon(
//                    painter = painterResource(id = resId),
//                    tint = contentColor,
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .clip(CircleShape)
//                )
//                BodyTexts.Level2(
//                    text = " $quantity  ml",
//                    color = contentColor,
//                    modifier = Modifier.padding(
//                        8.dp,end = 16.dp
//                    )
//                )
//            }
//            HeadingTexts.Level4(
//                text = name,
//                modifier = Modifier.padding()
//            )
//        }
//        AppIcon(
//            imageVector = Icons.Default.Edit, contentDescription = "Quantity",
//            tint = containerColor,
//            modifier = Modifier
//                .scale(0.8f)
//                .background(color = Color.White,shape = RoundedCornerShape(topEnd = 8.dp))
//                .clickable {
//                    showUndoButton = !showUndoButton
//                }
//        )
//        AnimatedVisibility(visible = showUndoButton) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Box(
//                    contentAlignment = Alignment.Center,
//                    modifier = Modifier.clickable {
//
//                    }
//                ) {
//                    Row(
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically,
//                    ) {
//                        AppIcon(
//                            imageVector = Icons.AutoMirrored.Filled.Undo,
//                            modifier = Modifier
//                                .padding()
//                                .scale(0.5f),
//                        )
//                        CaptionTexts.Level4(
//                            text = "Undo", textAlign = TextAlign.Center,
//                            modifier = Modifier.padding(start = 6.dp,end = 6.dp)
//                        )
//                    }
//                }
//                Box(
//                    contentAlignment = Alignment.Center,
//                    modifier = Modifier.clickable {
//                        onClickBeverage()
//                    }
//                ) {
//                    Row(
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically,
//                    ) {
//                        AppIcon(
//                            imageVector = Icons.Default.Add,
//                            modifier = Modifier
//                                .padding()
//                                .scale(0.5f)
//                        )
//                        CaptionTexts.Level4(
//                            text = "Add", textAlign = TextAlign.Center,
//                            modifier = Modifier.padding(start = 4.dp,end = 4.dp)
//                        )
//                    }
//                }
//            }
//
//        }
//}
//    }
