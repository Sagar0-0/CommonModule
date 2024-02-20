@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package fit.asta.health.feature.water.view.screen.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Interests
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.data.water.check.model.History
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.water.view.screen.WTEvent
import fit.asta.health.feature.water.view.screen.WaterToolUiState
import fit.asta.health.feature.water.viewmodel.WaterToolViewModel
import fit.asta.health.resources.drawables.R


@Composable
fun AppHomeScreen(
    viewModel: WaterToolViewModel = hiltViewModel(),
    totalConsumed: Int,
    waterQuantity: Int,
    coconutQuantity: Int,
    firstPrefQuantity: Int,
    secondPrefQuantity: Int,
    recentAddedQuantity: Int,
    event: (WTEvent) -> Unit,
    onClickWater: () -> Unit,
    onClickCoconut: () -> Unit,
    onClickFirstPref: () -> Unit,
    onClickSecondPref: () -> Unit,
    onClickCustomize: () -> Unit,
    onClickRecentAdded: () -> Unit,
    addedName: String,
    uiState: WaterToolUiState,
    onClickGoal: () -> Unit
) {
    Log.d("rishiRecomposed", "AppScreenCalled")
    val remainingToConsume by viewModel.remainingConsumption.collectAsState()
    val goal by viewModel.goal.collectAsState()
    val darkBackgroundColor = (Color(0xFF040429))
    val lightBackgroundColor = (Color(0xFFF2F8FC))

    val backgroundColor = if (isSystemInDarkTheme()) {
        darkBackgroundColor
    } else {
        lightBackgroundColor
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            WaterDataCard(totalConsumed, remainingToConsume, goal)
            GoalCard {
                onClickGoal()
            }
            HintsOnScreen()
            Spacer(modifier = Modifier.weight(.3f))
            SetOfDefaultChips(
                event = event,
                addedName = addedName,
                waterQuantity = waterQuantity,
                coconutQuantity = coconutQuantity,
                firstPrefQuantity = firstPrefQuantity,
                secondPrefQuantity = secondPrefQuantity,
                recentAddedQuantity = recentAddedQuantity,
                onClickWater = onClickWater,
                onClickCoconut = onClickCoconut,
                onClickFirstPref = onClickFirstPref,
                onClickSecondPref = onClickSecondPref,
                onClickCustomize = onClickCustomize,
                onClickRecentAdded = onClickRecentAdded,
                viewModel = viewModel
            )
//            Spacer(modifier = Modifier.weight(1f))
        }


    }
}

@Composable
fun GoalCard(onClickGoal: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickGoal()
            },
        horizontalArrangement = Arrangement.Center
    ) {
        BodyTexts.Level3(
            text = "Edit Your Goal",
        )
        AppIcon(
            imageVector = Icons.Default.EditNote, contentDescription = "Edit Goal",
            modifier = Modifier.padding(start = AppTheme.spacing.level1)
        )
    }

}

@Composable
fun HintsOnScreen() {
    AppElevatedCard(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(AppTheme.spacing.level2),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE2E9E8),
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level1)
        ) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                AppIcon(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = "Icon",
                    modifier = Modifier.scale(1.3f),
                    tint = Color.Unspecified
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                horizontalAlignment = Alignment.Start,

                ) {
                BodyTexts.Level3(
                    text = "Tie it into a routine. Drink a glass of water every time you brush your teeth, eat a meal or use the bathroom.",
                    color = Color.Black,
//                    lineHeight = 1.4.em,
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold
//                    )
                )

            }

        }


    }
}

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

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        )
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
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            )
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

                ExtendedFloatingActionButton(
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
fun WaterDataCard(totalConsumed: Int, remainingToConsume: Int, goal: Int) {

    val darkBackgroundColor = (Color(0xFF092251))
    val lightBackgroundColor = (Color(0xFFF2F8FC))

    val backgroundColor = if (isSystemInDarkTheme()) {
        darkBackgroundColor
    } else {
        lightBackgroundColor
    }

    Column {
        Box(
            modifier = Modifier.padding(
                start = AppTheme.spacing.level2,
                top = AppTheme.spacing.level1
            )
        ) {
            TitleTexts.Level3(
                text = "Make Yourself Hydrated!",
            )
        }
        AppElevatedCard(
            modifier = Modifier
                .padding(AppTheme.spacing.level1)
                .fillMaxWidth()
                .fillMaxHeight(.23f),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(AppTheme.spacing.level1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Column {
                        HeadingTexts.Level4(
                            text = "Drinking in %",
                        )
                        BodyTexts.Level3(
                            text = "${
                                String.format(
                                    "%.1f",
                                    minOf(
                                        100f,
                                        (totalConsumed.toFloat() / if (goal != 0) goal else 1) * 100
                                    )
                                )
                            } %",
                            color = Color(0xFF008970),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Row(horizontalArrangement = Arrangement.Center) {
                            HeadingTexts.Level4(text = "Total Consumed ( ml )")
                            PlainTooltipBox(
                                tooltip = { Text(if (totalConsumed < goal) "Total Quantity Consumed till now" else "You have completed your today's goal") },
                                contentColor = Color.Black,
                                containerColor = Color(0xff6b9bd1),
                                modifier = Modifier.clipToBounds()
                            ) {
                                IconButton(
                                    onClick = { /* Icon button's click event */ },
                                    modifier = Modifier.tooltipAnchor()
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Info,
                                        contentDescription = "Localized Description",
                                        modifier = Modifier.scale(.8f)
                                    )
                                }
                            }

                        }
                        BodyTexts.Level3(
                            text = "$totalConsumed",
                            color = Color.Gray,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        HeadingTexts.Level4(text = "Yet to Consume ( ml )")
                        BodyTexts.Level3(
                            text = "~${maxOf(0, remainingToConsume)} ",
                            color = Color.Gray,
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Box {
                    CircularProgressBar(
                        percentage = (totalConsumed.toFloat() / if (goal != 0) goal else 1),
                        number = if (goal != 0) goal else 1
                    )
                }
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
    onClick: () -> Unit,
    onClickBeverage: () -> Unit
) {
    AppElevatedCard(
        modifier = Modifier
            .padding(AppTheme.spacing.level1)
            .wrapContentHeight(unbounded = true),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        )
    ) {
        Row(
            modifier = Modifier
                .padding(AppTheme.spacing.level1)
        ) {
            Box(modifier = Modifier
                .clickable {
                    onClick()
                }
                .width(90.dp)) {
                Column {
                    HeadingTexts.Level4(text = name)
                    Spacer(modifier = Modifier.weight(1f))
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
            .width(AppTheme.spacing.level6),
        horizontalAlignment = Alignment.End,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 3.dp)
                .clip(CircleShape)
        ) {
            AppIcon(
                painter = painterResource(id = resId), contentDescription = "Tea",
                tint = contentColor,
                modifier = Modifier
                    .scale(1f)
                    .align(Alignment.Center)
                    .height(AppTheme.spacing.level2)
            )
        }
        // Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(top = 3.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape)
                .background(Color.White)
        ) {
            AppIcon(
                imageVector = Icons.Default.Edit, contentDescription = "Quantity",
                tint = contentColor,
                modifier = Modifier
                    .scale(0.6f)
                    .align(Alignment.BottomEnd)

            )
        }
    }
}


@Composable
fun BevSearchBar(
    viewModel: WaterToolViewModel = hiltViewModel(),
    onNameChange: (String) -> Unit,
) {
    val darkBackgroundColor = (Color(0xFF040429))
    val lightBackgroundColor = Color.White

    val backgroundColor = if (isSystemInDarkTheme()) {
        darkBackgroundColor
    } else {
        lightBackgroundColor
    }
    var text by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    val recentItem by viewModel.recentHistory.collectAsStateWithLifecycle()
    val bevItem by remember {
        viewModel.bevList
    }
    val filteredHistory by viewModel.filteredHistory.collectAsState()

    SearchBar(
        query = text,
        onQueryChange = {
            text = it
            viewModel.searchList(it)
            viewModel.searchHistory(it)
        },
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
            text = ""
        },
        placeholder = {
            BodyTexts.Level3(text = "Search Beverage")
        },
        leadingIcon = {
            AppIcon(imageVector = Icons.Default.Search, contentDescription = "Search")
        },
        trailingIcon = {
            if (active) {
                AppIcon(imageVector = Icons.Default.Clear, contentDescription = "Clear",
                    modifier = Modifier.clickable {
                        if (text.isNotEmpty()) {
                            text = ""
                        } else {
                            active = false
                        }
                    })
            }
        },
        shape = AppTheme.shape.level2,
        modifier = Modifier
            .fillMaxWidth(1f)
            .shadow(AppTheme.spacing.level0),
        colors = SearchBarDefaults.colors(
            containerColor = backgroundColor,
            dividerColor = Color(0xFF00458B)
        )
    ) {

        val bevListSize = bevItem.size
        val filteredListSize = filteredHistory.size
        if (text.isEmpty()) {
            recentItem.forEach {
                Row(modifier = Modifier
                    .padding(all = AppTheme.spacing.level1)
                    .clickable {
                        onNameChange(it.bevName)
                        active = false
                    }
                    .fillMaxWidth()) {
                    AppIcon(
                        modifier = Modifier.padding(end = AppTheme.spacing.level1),
                        imageVector = Icons.Default.History,
                        contentDescription = null
                    )
                    BodyTexts.Level3(text = it.bevName)
                }
            }

        } else {
            if (bevListSize == 0 && filteredListSize == 0) {
                BodyTexts.Level3(text = "Not Found, What looking For, Click here to Add this beverage. $bevListSize",
                    color = Color(0xFF00458B),
                    modifier = Modifier
                        .padding(AppTheme.spacing.level1)
                        .clickable {
                            //pass OnClick here instead of this line, and handle inserting
                            // where we can get sliderValue also
                            viewModel.insertRecentAdded(History(0, text, 0))
                        }
                        .background(Color.White, AppTheme.shape.level3))
            }
            bevItem.forEach {
                Row(modifier = Modifier
                    .padding(all = AppTheme.spacing.level1)
                    .clickable {
                        onNameChange(it.name)
                        text = it.name
                        active = false
                    }
                    .fillMaxWidth()) {
                    AppIcon(
                        modifier = Modifier.padding(end = AppTheme.spacing.level1),
                        imageVector = Icons.Default.Interests,
                        contentDescription = null
                    )
                    BodyTexts.Level3(text = it.name)
                }
            }
            filteredHistory.forEach {
                Row(modifier = Modifier
                    .padding(all = AppTheme.spacing.level1)
                    .clickable {
                        onNameChange(it.bevName)
                        text = it.bevName
                        active = false
                    }
                    .fillMaxWidth()) {
                    AppIcon(
                        modifier = Modifier.padding(end = AppTheme.spacing.level1),
                        imageVector = Icons.Default.History,
                        contentDescription = null
                    )
                    BodyTexts.Level3(text = it.bevName)
                }
            }
        }
    }
}

@Composable
fun CustomBevCard(
    onNameChange: (String) -> Unit,
    onClick: () -> Unit
) {
    val darkBackgroundColor = (Color(0xFF092251))
    val lightBackgroundColor = Color.White

    val backgroundColor = if (isSystemInDarkTheme()) {
        darkBackgroundColor
    } else {
        lightBackgroundColor
    }
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .background(color = backgroundColor, AppTheme.shape.level3),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    BodyTexts.Level2(
                        "Enter Your Custom Beverage ",
                        modifier = Modifier.padding(),
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BevSearchBar {
                        onNameChange(it)
                    }
                }
            }
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

@Composable
fun ErrorUi(
    viewModel: WaterToolViewModel = hiltViewModel(),
    event: (WTEvent) -> Unit
) {
    val isLoading by remember {
        viewModel._isLoading
    }
    AppSurface {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_error_not_found),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.scale(.8f)
                )
                Text(text = "An Unknown Error Occurred", textAlign = TextAlign.Center)
                AppFilledButton(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(AppTheme.spacing.level2),
                    onClick = { event(WTEvent.RetrySection) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (isLoading) {
                            Log.d("rishi", "CircularProgressCalled")
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier
                                    .scale(0.6f)
                                    .clipToBounds()
                            )
                        }
                        CaptionTexts.Level2(
                            text = "Tap to Retry",
                            textAlign = TextAlign.Center
                        )
                    }

                }

            }
        }
    }

}