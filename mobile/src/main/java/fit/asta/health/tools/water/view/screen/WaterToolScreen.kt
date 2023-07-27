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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.R
import fit.asta.health.common.ui.components.AppTopBar
import fit.asta.health.common.ui.components.ButtonWithColor
import fit.asta.health.common.ui.components.CircularSliderFloat
import fit.asta.health.common.ui.components.ProgressBarFloat
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.tools.sunlight.view.components.DividerLineCenter
import fit.asta.health.tools.water.model.domain.BeverageDetails
import fit.asta.health.tools.water.model.network.TodayActivityData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterToolScreen(
    Event: (WTEvent) -> Unit,
    beverageList: SnapshotStateList<BeverageDetails>,
    todayActivityData: SnapshotStateList<TodayActivityData>,
    selectedBeverage: String,
    containerIndex: Int,
    containerList: SnapshotStateList<Int>,
    uiState: WaterUiState,
    onBack: () -> Unit
) {
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    var visible by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = scaffoldState.bottomSheetState.currentValue) {
        Event(WTEvent.SheetState(sheetState.hasPartiallyExpandedState))
    }
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.tertiary,
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
            AppTopBar(
                text = "Water Tool",
                onBackPressed = onBack,
                actionItems = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_physique),
                            contentDescription = null,
                            Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) {
        val scrollState = rememberScrollState()
        var isScrollEnabled by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState, enabled = isScrollEnabled)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
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

                    CircularSliderFloat(
                        modifier = Modifier.size(200.dp),
                        isStarted = uiState.start,
                        appliedAngleDistanceValue = if (uiState.start) uiState.angle else uiState.targetAngle,
                        indicatorValue = uiState.water.consume.toFloat(),
                        onChangeDistance = {
                            Event(WTEvent.SelectTarget(it))
                        },
                        onChangeAngleDistance = {
                            Event(WTEvent.SelectAngle(it))
                        }
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
                        ProgressBarFloat(
                            modifier = Modifier.weight(0.3f),
                            targetDistance = uiState.water.recommend.toFloat(),
                            progress = (uiState.water.consume / uiState.water.recommend).toFloat(),
                            name = "Recommended"
                        )
                        ProgressBarFloat(
                            modifier = Modifier.weight(0.3f),
                            targetDistance = uiState.water.target.toFloat(),
                            progress = if (uiState.water.target == 0.0) 0f else (uiState.water.consume / uiState.water.target).toFloat(),
                            name = "Goal"
                        )
                        ProgressBarFloat(
                            modifier = Modifier.weight(0.3f),
                            targetDistance = uiState.water.target.toFloat(),
                            progress = if (uiState.water.target == 0.0) 0f else (uiState.water.remaining / uiState.water.target).toFloat(),
                            name = "Remaining"
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(spacing.small)
                    ) {
                        Text(
                            text = "Recommendation ",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Start
                        )
                        RecommendItem(
                            title = beverageName("M"),
                            value = (uiState.milk.recommend * 1000).toFloat(),
                            progress = (uiState.milk.consume * 100.0 / uiState.milk.recommend).toInt()
                        )
                        RecommendItem(
                            title = beverageName("C"),
                            value = (uiState.coconut.recommend * 1000).toFloat(),
                            progress = (uiState.coconut.consume * 100.0 / uiState.coconut.recommend).toInt()
                        )
                        RecommendItem(
                            title = beverageName("BM"),
                            value = (uiState.butterMilk.recommend * 1000).toFloat(),
                            progress = (uiState.butterMilk.consume * 100.0 / uiState.butterMilk.recommend).toInt()
                        )
                        RecommendItem(
                            title = beverageName("FJ"),
                            value = (uiState.fruitJuice.recommend * 1000).toFloat(),
                            progress = (uiState.fruitJuice.consume * 100.0 / uiState.fruitJuice.recommend).toInt()
                        )
                    }
                }
            }
            DailyActivity(todayActivityData)
            Spacer(modifier = Modifier.height(240.dp))
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {

        DividerLineCenter()

        AnimatedVisibility(visible = scaffoldState.bottomSheetState.hasExpandedState) {
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

        AnimatedVisibility(visible = scaffoldState.bottomSheetState.hasPartiallyExpandedState) {
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
fun DailyActivity(todayActivityData: SnapshotStateList<TodayActivityData>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            Text(
                text = "Today Activity",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Beverages",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Consume",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Time",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
            }
            val daily = listOf("W", "SD", "FG", "M", "BM", "C")
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                items(todayActivityData) {
                    ActivityItem(
                        title = it.bev,
                        consumeValue = (it.qty * 1000).toFloat(),
                        icon_code = beverageNameToIcon(it.bev),
                        time = it.time
                    )
                }
            }
        }
    }
}

@Composable
fun ActivityItem(title: String, consumeValue: Float, icon_code: Int, time: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Icon(
            painter = painterResource(id = icon_code),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .weight(.15f),
            tint = Color.Green
        )
        Text(
            modifier = Modifier.weight(.35f),
            text = title,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier.weight(.3f),
            text = "$consumeValue ml",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier.weight(.2f),
            text = time,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
@Preview
fun RecommendBeverage() {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
//        color = Color.Green,
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
    ) {
        val daily = listOf("W", "SD", "FG", "M", "BM", "C")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            item {
                Text(
                    text = "Recommend ",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start
                )
            }
            items(daily) {
                RecommendItem(title = "Water", value = 700f, progress = 30)
            }
        }
    }
}

@Composable
fun RecommendItem(modifier: Modifier = Modifier, title: String, value: Float, progress: Int) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(.3f),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start
            )
            Text(
                text = "$value ml",
                modifier = Modifier.weight(.2f),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
            Text(
                text = "$progress %",
                modifier = Modifier.weight(.5f),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End
            )
        }
        CustomProgressBar(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(6.dp))
                .height(6.dp),
            backgroundColor = Color.LightGray,
            percent = progress
        )
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
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onTertiary),
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
            elevation = CardDefaults.cardElevation(8.dp)
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

fun beverageNameToIcon(name: String): Int {
    return when (name) {
        "Water" -> {
            R.drawable.sparkling_water
        }

        "Fruit Juice" -> {
            R.drawable.fruit_juice
        }

        "Soft Drinks" -> {
            R.drawable.butter_milk_bottle
        }

        "Milk" -> {
            R.drawable.milk_bottle
        }

        "Butter milk" -> {
            R.drawable.butter_milk_bottle
        }

        "Coconut" -> {
            R.drawable.coconut_cocktail
        }

        else -> {
            R.drawable.ic_baseline_favorite_24
        }
    }
}

@Composable
@Preview
fun Test() {
    CustomProgressBar(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(6.dp))
            .height(14.dp),
        backgroundColor = Color.LightGray,
        percent = 80
    )
}


@Composable
fun CustomProgressBar(
    modifier: Modifier, backgroundColor: Color,
    foregroundColor: Brush = Brush.horizontalGradient(
        0f to Color.Yellow,
        0.3f to Color.Green,
        0.6f to Color.Red,
        0.9f to Color.Magenta,
        1f to Color(0x00EF7B7B)
    ),
    percent: Int
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp - 32
    val animatedProgress = animateFloatAsState(
        targetValue = (screenWidth * percent / 100).toFloat(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Box(
        modifier = modifier
            .background(backgroundColor)
            .width(screenWidth.dp)
    ) {
        Box(
            modifier = modifier
                .background(foregroundColor)
                .width(animatedProgress.dp)
        )
    }
}