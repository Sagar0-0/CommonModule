package fit.asta.health.feature.water.view.screen.ui

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.button.AppExtendedFloatingActionButton
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.cards.AppElevatedCardWithColor
import fit.asta.health.designsystem.molecular.dialog.AppAlertDialog
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.water.view.screen.WTEvent
import fit.asta.health.feature.water.view.screen.WaterToolUiState
import fit.asta.health.feature.water.viewmodel.WaterToolViewModel
import fit.asta.health.resources.drawables.R
import kotlinx.coroutines.delay

@Composable
fun SetOfDefaultChips(
    event: (WTEvent) -> Unit,
    uiState: WaterToolUiState,
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
fun UndoCard(
    contentColor: Color,
    viewModel: WaterToolViewModel = hiltViewModel(),
    event: (WTEvent) -> Unit,
    name: String,
    consumptionExist: Boolean,
    undoQuantity : Int,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    ButtonWithColor(color = contentColor, text = "Undo",
        modifier = Modifier.fillMaxWidth()) {
        event(WTEvent.UndoConsumption(name))
        showDialog = !showDialog
    }
   // }
    AnimatedVisibility(visible = showDialog) {
        AppAlertDialog(onDismissRequest = { /*TODO*/ },
            text = {
                BodyTexts.Level2(text = "Are You Sure, You want to undo this consumption,\n" +
                        "${name} : ${undoQuantity} ml")
            },
            title = {
                HeadingTexts.Level2(text = "Undo Consumption")
            },
            confirmButton = {
                if(undoQuantity==0){
                    MToast(message = "You have not consumed ${name}")
                }
                ButtonWithColor(
                    color = contentColor, text = if(undoQuantity==0) "No Consumption" else "Undo",
                    modifier = Modifier.fillMaxWidth()
                ) {
                    viewModel.showUndoDialog.value = true
                    event(WTEvent.DeleteRecentConsumption(name))
                    showDialog = !showDialog
                }
            },
            dismissButton = {
                AppFilledButton(textToShow = "Dismiss",
                    modifier = Modifier.fillMaxWidth()) {
                    showDialog = !showDialog
                }
            })
    }

}
@Composable
fun MToast(message : String) {
    Toast.makeText(LocalContext.current,message,Toast.LENGTH_SHORT).show()
}

