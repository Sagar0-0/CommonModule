package fit.asta.health.feature.water.view.screen.ui

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Interests
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.data.water.local.entity.History
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppSearchBar
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
    Log.d("rishi","With UI state ,TotalConsumed : ${uiState.totalConsumed}, Remainig : ${uiState.remainingToConsume}")
    val remainingToConsume by viewModel.remainingConsumption.collectAsState()
    val goal by viewModel.goal.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            WaterDataCard(uiState,totalConsumed, remainingToConsume, goal)
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
                uiState = uiState,
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
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 5.dp
//        ),
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
                )

            }

        }


    }
}


@Composable
fun WaterDataCard(uiState: WaterToolUiState,totalConsumed: Int, remainingToConsume: Int, goal: Int) {

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
//                .fillMaxHeight(.23f),
//            elevation = CardDefaults.cardElevation(
//                defaultElevation = 5.dp
//            ),
//            colors = CardDefaults.cardColors(
//                containerColor = backgroundColor
//            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(AppTheme.spacing.level1),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        Column {
                            HeadingTexts.Level4(
                                text = "Drinking in %", modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                            )
                            BodyTexts.Level3(
                                text = "${
                                    String.format(
                                        "%.1f",
                                        minOf(
                                            100f,
                                            (uiState.totalConsumed
                                                .toFloat() / if (goal != 0) goal else 1) * 100
                                        )
                                    )
                                } %",
                                color = Color(0xFF008970), modifier = Modifier.padding(bottom = 4.dp)
                            )

                           // Spacer(modifier = Modifier.weight(1f))

                            Row(horizontalArrangement = Arrangement.Center) {
                                HeadingTexts.Level4(text = "Total Consumed ( ml )",
                                    modifier = Modifier.padding(top = 4.dp))
//                            AppRichTooltip(
//                                modifier = Modifier.clipToBounds(),
//                                text = {CaptionTexts.Level2(if (totalConsumed < goal) "Total Quantity Consumed till now" else "You have completed your today's goal")},
//                                action = {AppIcon(
//                                    imageVector = Icons.Filled.Info,
//                                    contentDescription = "Localized Description",
//                                    modifier = Modifier.scale(.8f).tooltipAnchor()
//                                )}
//                            )
                            }

                            BodyTexts.Level3(
                                text = "${uiState.totalConsumed}",
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )

                            Spacer(modifier = Modifier.weight(1f))
                            HeadingTexts.Level4(text = "Yet to Consume ( ml )",
                                modifier = Modifier.padding(top  = 4.dp))
                            BodyTexts.Level3(
                                text = "~${maxOf(0, uiState.remainingToConsume)} ",
                                color = Color.Gray,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(end = 4.dp)) {
                        CircularProgressBar(
                            percentage = (totalConsumed.toFloat() / if (goal != 0) goal else 1),
                            number = if (goal != 0) goal else 1
                        )
                    }
                }
                CaptionTexts.Level3(text = "Recent Consumption : ${uiState.recentConsumedBevName} ${uiState.recentConsumedBevQty} ml", textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp,top = 8.dp))
            }
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

    val controller  = LocalSoftwareKeyboardController.current
    AppSearchBar(
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
                            controller?.hide()
                        }
                    })
            }
        },
        shape = AppTheme.shape.level2,
        modifier = Modifier
            .fillMaxWidth(1f),
//            .shadow(AppTheme.spacing.level0),

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
                BodyTexts.Level3(text = "Not Found, What looking For, Click here to Add this beverage.",
                    color = Color(0xFF00458B),
                    modifier = Modifier
                        .padding(AppTheme.spacing.level1)
                        .clickable {
                            //pass OnClick here instead of this line, and handle inserting
                            // where we can get sliderValue also
                            viewModel.insertRecentAdded(History(0, text, 0))
                        }
                        .background(Color.White, AppTheme.shape.level1))
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
                .padding(AppTheme.spacing.level2)
                .background(color = AppTheme.colors.onSecondary, AppTheme.shape.level3),
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

