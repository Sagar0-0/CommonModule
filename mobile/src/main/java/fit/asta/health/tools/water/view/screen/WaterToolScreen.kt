package fit.asta.health.tools.water.view.screen

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.R
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.DividerLineCenter
import fit.asta.health.tools.walking.view.component.ButtonWithColor
import fit.asta.health.tools.water.model.domain.BeverageDetails
import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.view.component.WaterCircularSlider

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WaterToolScreen(
    Event: (WTEvent) -> Unit,
    beverageList: SnapshotStateList<BeverageDetails>,
    selectedBeverage: String,
    containerIndex: Int,
    containerList: SnapshotStateList<Int>,
    waterTool: WaterTool,
    uiState: WaterUiState
) {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    var visible by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = scaffoldState.bottomSheetState.currentValue) {
        Event(WTEvent.SheetState(sheetState.isCollapsed))
    }
    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(16.dp),
        sheetContent = {
            WaterBottomSheet(
                scaffoldState = scaffoldState,
                Event = Event,
                uiState = uiState,
                beverageList = beverageList,
                selectedBeverage = selectedBeverage,
                containerList = containerList,
                containerIndex = containerIndex
            )
        },
        sheetPeekHeight = 200.dp,
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(elevation = 10.dp, backgroundColor = MaterialTheme.colorScheme.onPrimary) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_exercise_back),
                            contentDescription = null,
                            Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = "Water Tool",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_physique),
                            contentDescription = null,
                            Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color.Green,
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        ) {
            if (uiState.showCustomDialog) {
                CustomAlertDialog(dialogString = uiState.dialogString,
                    onDismiss = {
                        Event(WTEvent.DialogState(false))
                    },
                    onUpdate = {
                        Event(WTEvent.UpdateQuantity)
                        Event(WTEvent.DialogState(false))
                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WaterCircularSlider(
                    modifier = Modifier.size(200.dp),
                    isStarted = uiState.start,
                    appliedAngleDistanceValue = if (uiState.start) uiState.angle else uiState.targetAngle,
                    indicatorValue = waterTool.progressData.consumed.toFloat(),
                    onChangeDistance = {
                        Event(WTEvent.SelectTarget(it))
                    },
                    onChangeAngleDistance = {
                        Event(WTEvent.SelectAngle(it))
                    }
                )

                Row(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
                    ProgressBarItem(
                        modifier = Modifier.weight(0.3f),
                        targetDistance = waterTool.progressData.recommendation.toFloat(),
                        progress = (waterTool.progressData.consumed / waterTool.progressData.recommendation).toFloat(),
                        name = "Recommended"
                    )
                    ProgressBarItem(
                        modifier = Modifier.weight(0.3f),
                        targetDistance = waterTool.progressData.goal.toFloat(),
                        progress = (waterTool.progressData.consumed / waterTool.progressData.goal).toFloat(),
                        name = "Goal"
                    )
                    ProgressBarItem(
                        modifier = Modifier.weight(0.3f),
                        targetDistance = waterTool.progressData.goal.toFloat(),
                        progress = (waterTool.progressData.remaining / waterTool.progressData.goal).toFloat(),
                        name = "Remaining"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WaterBottomSheet(
    scaffoldState: BottomSheetScaffoldState,
    uiState: WaterUiState,
    Event: (WTEvent) -> Unit,
    beverageList: SnapshotStateList<BeverageDetails>,
    selectedBeverage: String,
    containerIndex: Int,
    containerList: SnapshotStateList<Int>
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .heightIn(min = 200.dp, max = 320.dp)
            .background(Color.Magenta)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {

        DividerLineCenter()

        AnimatedVisibility(visible = scaffoldState.bottomSheetState.isExpanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing.medium)
            ) {
                Text(
                    "BEVERAGES",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
                    beverageList.forEach {
                        item {
                            BeveragesItem(
                                icon_code = it.code,
                                title = it.title,
                                index = selectedBeverage,
                                onClick = { Event(WTEvent.SelectBeverage(it.code)) }
                            )
                        }
                    }
                }
                Text(
                    "QUANTITY-${beverageName(selectedBeverage)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
                    containerList.forEachIndexed { index, value ->
                        item {
                            QuantityContainerComponent(
                                value = "$value ml",
                                select = containerIndex == index
                            ) { Event(WTEvent.SelectContainer(index)) }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(visible = scaffoldState.bottomSheetState.isCollapsed) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing.medium)
            ) {
                Text(
                    "QUANTITY-${beverageName(selectedBeverage)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
                    containerList.forEachIndexed { index, value ->
                        item {
                            QuantityContainerComponent(
                                value = "$value ml",
                                select = containerIndex == index
                            ) { Event(WTEvent.SelectContainer(index)) }
                        }
                    }
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
            ButtonWithColor(
                modifier = Modifier.weight(0.5f), color = Color.Green, text = "SCHEDULE"
            ) { Event(WTEvent.Schedule) }
            if (!uiState.start) {
                ButtonWithColor(
                    modifier = Modifier.weight(0.5f),
                    color = Color.Blue,
                    text = "START"
                ) { Event(WTEvent.Start(context)) }
            }
        }
    }
}

@Composable
fun ProgressBarItem(
    modifier: Modifier,
    progress: Float,
    targetDistance: Float,
    name: String
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "%.1f Litres".format(targetDistance),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        val animatedProgress = animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        ).value

        LinearProgressIndicator(
            progress = animatedProgress,
            backgroundColor = Color.LightGray,
            color = Color.Magenta
        )
        Text(
            text = name, fontSize = 12.sp, fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun QuantityContainerComponent(
    value: String = "200 ml",
    select: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(72.dp)
                .background(
                    color = if (select) Color.Green else Color.Gray
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = value,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }

}

@Composable
fun GridItem(
    modifier: Modifier = Modifier,
    name: String,
    type: String,
    @DrawableRes id: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colorScheme.onTertiary,
    ) {

        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(spacing.small)) {
                Icon(
                    painter = painterResource(id), contentDescription = null
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(spacing.small),
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = type,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun CustomAlertDialog(onDismiss: () -> Unit, onUpdate: () -> Unit, dialogString: String) {

    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 8.dp
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {


                Text(
                    text = "Attention:  Quantity Update",
                    modifier = Modifier.padding(8.dp), fontSize = 20.sp
                )

                Text(
                    text = dialogString, modifier = Modifier.padding(8.dp)
                )

                Row(Modifier.padding(top = 10.dp)) {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Cancel")
                    }


                    Button(
                        onClick = { onUpdate() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Update")
                    }
                }
            }
        }
    }
}

@Composable
fun BeveragesItem(title: String, icon_code: String, index: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        Icon(
            painter = painterResource(id = beverageIcons(icon_code)),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (icon_code == index) Color.Green else Color.Gray
        )
        Text(
            text = title,
            fontSize = 12.sp,
            color = if (icon_code == index) Color.Green else Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

fun beverageIcons(code: String): Int {
    return when (code) {
        "W" -> {
            R.drawable.sparkling_water
        }

        "FJ" -> {
            R.drawable.fruit_juice
        }

        "SD" -> {
            R.drawable.butter_milk_bottle
        }

        "M" -> {
            R.drawable.milk_bottle
        }

        "BM" -> {
            R.drawable.butter_milk_bottle
        }

        "C" -> {
            R.drawable.coconut_cocktail
        }

        else -> {
            R.drawable.ic_baseline_favorite_24
        }
    }
}

fun beverageName(code: String): String {
    return when (code) {
        "W" -> "Water"

        "FJ" -> "Fruit Juice"

        "SD" -> "Soft Drinks"

        "M" -> "Milk"

        "BM" -> "Butter Milk"

        "C" -> "Coconut"

        else -> "Water"
    }
}

@Composable
fun TestC() {
    var angle by remember {
        mutableStateOf(100f)
    }
    var progress by remember {
        mutableStateOf(3f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WaterCircularSlider(
            modifier = Modifier.size(200.dp),
            isStarted = false,
            appliedAngleDistanceValue = angle,
            indicatorValue = progress,
            onChangeDistance = {
                progress = it
            },
            onChangeAngleDistance = {
                angle = it
            }
        )
    }
}