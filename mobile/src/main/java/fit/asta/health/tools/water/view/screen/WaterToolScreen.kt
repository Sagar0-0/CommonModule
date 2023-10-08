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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.R
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.CircularSliderFloat
import fit.asta.health.designsystem.molecular.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.ProgressBarFloat
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.tools.water.model.domain.BeverageDetails
import fit.asta.health.tools.water.model.network.TodayActivityData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterToolScreen(
    event: (WTEvent) -> Unit,
    beverageList: SnapshotStateList<BeverageDetails>,
    todayActivityData: SnapshotStateList<TodayActivityData>,
    selectedBeverage: String,
    containerIndex: Int,
    containerList: SnapshotStateList<Int>,
    uiState: WaterUiState,
    onBack: () -> Unit
) {

    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    LaunchedEffect(key1 = scaffoldState.bottomSheetState.currentValue) {
        event(WTEvent.SheetState(sheetState.hasPartiallyExpandedState))
    }

    AppBottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 240.dp,
        topBar = {
            AppTopBarWithHelp(
                title = "Water Tool",
                onBack = onBack,
                onHelp = { /*TODO*/ }
            )
        },
        sheetContent = {
            WaterBottomSheet(
                scaffoldState = scaffoldState,
                Event = event,
                uiState = uiState,
                beverageList = beverageList,
                selectedBeverage = selectedBeverage,
                containerList = containerList,
                containerIndex = containerIndex
            )
        },
    ) {
        val scrollState = rememberScrollState()
        val isScrollEnabled by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState, enabled = isScrollEnabled)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
        ) {
            AppSurface(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.showCustomDialog) {
                    CustomAlertDialog(
                        dialogString = uiState.dialogString,
                        onDismiss = {
                            event(WTEvent.DialogState(false))
                        },
                        onUpdate = {
                            event(WTEvent.UpdateQuantity)
                            event(WTEvent.DialogState(false))
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    CircularSliderFloat(
                        modifier = Modifier.size(200.dp),
                        isStarted = uiState.start,
                        appliedAngleDistanceValue = if (uiState.start) uiState.angle else uiState.targetAngle,
                        indicatorValue = uiState.water.consume.toFloat(),
                        onChangeDistance = {
                            event(WTEvent.SelectTarget(it))
                        },
                        onChangeAngleDistance = {
                            event(WTEvent.SelectAngle(it))
                        }
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)) {
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
                        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
                    ) {
                        TitleTexts.Level2(
                            text = "Recommendation ",
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
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        AnimatedVisibility(visible = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
            ) {
                TitleTexts.Level2(
                    text = "BEVERAGES",
                    color = AppTheme.colors.onBackground
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)) {
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
                TitleTexts.Level2(
                    text = "QUANTITY-${beverageName(selectedBeverage)}",
                    color = AppTheme.colors.onBackground
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)) {
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

        AnimatedVisibility(visible = scaffoldState.bottomSheetState.currentValue != SheetValue.Expanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
            ) {
                TitleTexts.Level2(
                    text = "QUANTITY-${beverageName(selectedBeverage)}",
                    color = AppTheme.colors.onBackground
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)) {
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

        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)) {
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
                CaptionTexts.Level2(
                    text = value,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }

}

@Composable
fun DailyActivity(todayActivityData: SnapshotStateList<TodayActivityData>) {
    AppSurface(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            TitleTexts.Level2(
                text = "Today Activity",
                textAlign = TextAlign.Start
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BodyTexts.Level3(
                    text = "Beverages",
                    textAlign = TextAlign.Start
                )
                BodyTexts.Level3(
                    text = "Consume",
                    textAlign = TextAlign.Start
                )
                BodyTexts.Level3(
                    text = "Time",
                    textAlign = TextAlign.Start
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
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
        AppIcon(
            painter = painterResource(id = icon_code),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .weight(.15f),
            tint = Color.Green
        )
        TitleTexts.Level3(
            modifier = Modifier.weight(.35f),
            text = title,
        )
        TitleTexts.Level3(
            modifier = Modifier.weight(.3f),
            text = "$consumeValue ml",
        )
        TitleTexts.Level3(
            modifier = Modifier.weight(.2f),
            text = time,
        )
    }
}

@Composable
@Preview
fun RecommendBeverage() {
    AppSurface(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        val daily = listOf("W", "SD", "FG", "M", "BM", "C")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
        ) {
            item {
                TitleTexts.Level2(
                    text = "Recommend ",
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
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            TitleTexts.Level3(
                text = title,
                modifier = Modifier.weight(.3f),
                textAlign = TextAlign.Start
            )
            BodyTexts.Level2(
                text = "$value ml",
                modifier = Modifier.weight(.2f),
                textAlign = TextAlign.Start
            )
            BodyTexts.Level3(
                text = "$progress %",
                modifier = Modifier.weight(.5f),
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
    AppCard(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(AppTheme.colors.onTertiary),
    ) {

        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)) {
                AppIcon(
                    painter = painterResource(id), contentDescription = null
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            ) {
                TitleTexts.Level3(text = name)
                BodyTexts.Level3(text = type)
            }
        }
    }
}

@Composable
fun CustomAlertDialog(onDismiss: () -> Unit, onUpdate: () -> Unit, dialogString: String) {

    AppDialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        AppCard(
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


                HeadingTexts.Level2(
                    text = "Attention:  Quantity Update",
                    modifier = Modifier.padding(8.dp)
                )

                TitleTexts.Level2(
                    text = dialogString, modifier = Modifier.padding(8.dp)
                )

                Row(Modifier.padding(top = 10.dp)) {
                    AppOutlinedButton(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F),
                        textToShow = "Cancel"
                    )
                    AppFilledButton(
                        onClick = { onUpdate() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F),
                        textToShow = "Update"
                    )
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
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
    ) {
        AppIcon(
            painter = painterResource(id = beverageIcons(icon_code)),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (icon_code == index) Color.Green else Color.Gray
        )
        CaptionTexts.Level3(
            text = title,
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
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec, label = ""
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