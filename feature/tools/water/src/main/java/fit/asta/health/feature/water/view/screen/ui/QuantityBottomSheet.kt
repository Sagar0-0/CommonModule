@file:OptIn(ExperimentalMaterial3Api::class)

package fit.asta.health.feature.water.view.screen.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Scoreboard
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.water.view.screen.WTEvent
import fit.asta.health.feature.water.viewmodel.WaterToolViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBevBottomSheet(
    viewModel: WaterToolViewModel = hiltViewModel(),
    onBack: () -> Unit,
    event: (WTEvent) -> Unit
) {
    Log.d("rishiRecomposed", "CustomBevSheet called")
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            initialValue = SheetValue.Hidden,
            skipPartiallyExpanded = false
        )
    )

    val uiState = viewModel.uiState.value
    val scrollState = rememberScrollState()
    val sliderValueWater by viewModel.waterQuantity.collectAsState()
    val sliderValueCoconut by viewModel.coconutQuantity.collectAsState()
    val sliderValueFirstPreference by viewModel.firstPrefQuantity.collectAsState()
    val sliderValueSecondPreference by viewModel.secondPrefQuantity.collectAsState()
    val sliderValueRecentAdded by viewModel.recentAddedQuantity.collectAsState()

    val color by viewModel.sliderColor.collectAsState()
    val totalConsumed by viewModel.totalConsumed.collectAsState()
    val context: Context = LocalContext.current
    val scope = rememberCoroutineScope()
    var addedName by rememberSaveable {
        mutableStateOf("")
    }
    var currentBottomSheet: BottomSheetScreen? by remember {
        mutableStateOf(null)
    }
    val closeSheet: () -> Unit = {
        scope.launch {
            bottomSheetState.bottomSheetState.hide()
        }
    }


    val openSheet: (BottomSheetScreen) -> Unit = {
        currentBottomSheet = it
//        Toast.makeText(context, "Sheet will disappear in 5sec", Toast.LENGTH_SHORT).show()
        scope.launch { bottomSheetState.bottomSheetState.expand() }

    }
    val darkBackgroundColor = (Color(0xFF092251))
    val lightBackgroundColor = Color.White

    val backgroundColor = if (isSystemInDarkTheme()) {
        darkBackgroundColor
    } else {
        lightBackgroundColor
    }

    AppBottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = false,
        sheetContainerColor = backgroundColor,
        topBar = {
            AppTopBarWithHelp(
                title = "Water Tool",
                onBack = onBack,
                onHelp = { Toast.makeText(context,"Beverage Tracking Tool",Toast.LENGTH_SHORT).show() }
            )
        },
        sheetContent = {
            currentBottomSheet?.let { currentSheet ->
                SheetLayout(
                    viewModel,
                    color,
                    currentSheet,
                    closeSheet,
                    onSliderValueChanged = {
                        val currValue = it.first
                        val currString = it.second
                        Log.d("ValueChanges", "sliderChange ${currValue} ${currString}")
                        event(WTEvent.UpdateOnSliderChangeQuantity(currString, currValue))
                    },
                    onNameChange = {
                        addedName = it
                    },
                    onGoalChange = {
                        event(WTEvent.GoalChange(it))
                    })
            }
        }
    ) {
        Column(modifier = Modifier.verticalScroll(scrollState)
            .fillMaxSize()) {
            AppHomeScreen(
                totalConsumed = totalConsumed.toInt(),
                waterQuantity = sliderValueWater.toInt(),
                coconutQuantity = sliderValueCoconut.toInt(),
                firstPrefQuantity = sliderValueFirstPreference.toInt(),
                secondPrefQuantity = sliderValueSecondPreference.toInt(),
                recentAddedQuantity = sliderValueRecentAdded.toInt(),
                onClickWater = { openSheet(BottomSheetScreen.Screen1(sliderValueWater, "Water")) },
                onClickCoconut = {
                    openSheet(
                        BottomSheetScreen.Screen1(
                            sliderValueCoconut,
                            "Coconut"
                        )
                    )
                },
                onClickFirstPref = {
                    openSheet(
                        BottomSheetScreen.Screen1(
                            sliderValueFirstPreference,
                            "FirstPref"
                        )
                    )
                },
                onClickSecondPref = {
                    openSheet(
                        BottomSheetScreen.Screen1(
                            sliderValueSecondPreference,
                            "SecondPref"
                        )
                    )
                },
                onClickRecentAdded = {
                    openSheet(
                        BottomSheetScreen.Screen1(
                            sliderValueRecentAdded,
                            "RecentAdd"
                        )
                    )
                },
                onClickCustomize = {
                    openSheet(
                        BottomSheetScreen.Screen2(
                            sliderValueRecentAdded,
                            "RecentAdd"
                        )
                    )
                },
                addedName = addedName,
                onClickGoal = { openSheet(BottomSheetScreen.Screen3()) },
                event = event,
                uiState = uiState
            )
        }
    }
}

@Composable
fun SheetLayout(
    viewModel: WaterToolViewModel = hiltViewModel(),
    color: Color,
    currentScreen: BottomSheetScreen,
    closeSheet: () -> Unit,
    onSliderValueChanged: (Pair<Float, String>) -> Unit,
    onNameChange: (String) -> Unit,
    onGoalChange: (Int) -> Unit
) {
    AppBottomSheetWithCloseDialog(closeSheet) {
        when (currentScreen) {
            is BottomSheetScreen.Screen1 -> Screen1(
                sliderValue = currentScreen.sliderValue,
                onSliderValueChanged = {
                    currentScreen.sliderValue = it
                    Log.d("ValueChanges", "Ye hai : ${currentScreen.sliderValue.toString()}")
                    onSliderValueChanged(Pair(it, currentScreen.bevName))
                },
                color = color)

            is BottomSheetScreen.Screen2 -> Screen2(
                viewModel,
                sliderValue = currentScreen.sliderValue,
                onSliderValueChanged = {
                    Log.d("ValueChanges", "Ye hai : ${currentScreen.sliderValue.toString()}")
                    currentScreen.sliderValue = it
                    onSliderValueChanged(Pair(it, currentScreen.bevName))
                },
                onNameChange = {
                    onNameChange(it)
                },
                color = color)

            is BottomSheetScreen.Screen3 -> Screen3(viewModel,
                onGoalChange = {
                    onGoalChange(it)
                })
            else -> {}
        }
    }
}


@Composable
fun Screen1(sliderValue: Float, onSliderValueChanged: (Float) -> Unit,color: Color) {
    var sliderPosition by remember { mutableStateOf(sliderValue) }
    Column {
//        PlainTooltipBox(
//            tooltip = { Text("Set the Quantity as per your needs" ) },
//            contentColor = Color.Black,
//            containerColor = Color.White
//        ) {
//            IconButton(
//                onClick = { /* Icon button's click event */ },
//                modifier = Modifier.tooltipAnchor()
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Info,
//                    contentDescription = "Localized Description"
//                )
//            }
//        }
        val title = "Set Beverage Quantity"
        val description = "Adjust the quantity for each beverage to track your consumption accurately. Slide the bar to set the desired amount."
        AnimatedContentField(title = title, description = description)
        DaysSlider(
            sliderValue = sliderPosition,
            onSliderValueChanged = { newValue ->
                sliderPosition = newValue
                onSliderValueChanged(newValue)
            },
            color = color,
        ) {
        }
    }

}

@Composable
fun Screen2(
    viewModel: WaterToolViewModel = hiltViewModel(),
    sliderValue: Float,
    color: Color,
    onSliderValueChanged: (Float) -> Unit,
    onNameChange: (String) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(sliderValue) }
    Column {
//        PlainTooltipBox(
//            tooltip = { Text("Add to favorites" ) },
//            contentColor = Color.White,
//        ) {
//            IconButton(
//                onClick = { /* Icon button's click event */ },
//                modifier = Modifier.tooltipAnchor()
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Info,
//                    contentDescription = "Localized Description"
//                )
//            }
//        }
        val title = "Search and Set Beverage Quantity"
        val description =  "Find your favorite beverages quickly by searching. Once found, set the quantity to monitor your intake. Use the search bar to explore."
        AnimatedContentField(title = title, description = description)
        CustomBevCard( onNameChange = {
            onNameChange(it)
        }) {
        }
        DaysSlider(
            sliderValue = sliderPosition,
            onSliderValueChanged = { newValue ->
                sliderPosition = newValue
                onSliderValueChanged(newValue)
            },
            color = color,
        ) {
//        try {
//            scope.launch {
//                delay(5000)
//                bottomSheetState.bottomSheetState.hide()
//            }
//        }catch (e : Exception){
//            Log.e("ErrorMethod",e.message.toString())
//        }
        }
    }

}

@Composable
fun Screen3(
    viewModel: WaterToolViewModel = hiltViewModel(),
    onGoalChange : (Int) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    val goal by viewModel.goal.collectAsState()
    Column {
        val title = "Set Daily Goal"
        val description =  "Define your daily hydration goal to stay on track with your beverage intake. Adjust the goal according to your preferences and health recommendations."
        AnimatedContentField(title = title, description = description)

        HeadingTexts.Level3("Current Goal is : $goal",modifier = Modifier.padding(AppTheme.spacing.level1))


        AppOutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onGoalChange(if(it.isEmpty()) 0 else it.toInt())
            },
            leadingIcon = {
                AppIcon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Set Goal",
                )
            },
            placeholder = {
                BodyTexts.Level3(text = "Edit Your Goal in  ml", color = Color.Black)
            },
            shape = AppTheme.shape.level3,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.DarkGray
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .padding(1.dp)
        )
    }

}

@Composable
fun DaysSlider(
    sliderValue: Float,
    onSliderValueChanged: (Float) -> Unit,
    color : Color,
    OnClick: () -> Unit,
) {
    val darkBackgroundColor = (Color(0xFF092251))
    val lightBackgroundColor = Color.White

    val backgroundColor = if (isSystemInDarkTheme()) {
        darkBackgroundColor
    } else {
        lightBackgroundColor
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = backgroundColor, AppTheme.shape.level2)
            .padding(AppTheme.spacing.level1)
            .fillMaxWidth(),
    ) {
        var sliderPosition by remember { mutableStateOf(sliderValue) }

        Column {
            Row {
                Text(text = "Set Quantity For : ")
                Text(
                    text = "${sliderPosition.toInt()} ml",
                    color = Color(0xFF00458B)
                )
            }
            Row {
                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                        onSliderValueChanged(it) // Update the slider value
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(AppTheme.spacing.level0)
                    ,
                    colors = SliderDefaults.colors(
                        thumbColor = color,
                        activeTrackColor = color,
                        activeTickColor = color,
                        inactiveTickColor = Color(0xFF99DDFF),
                        inactiveTrackColor = Color(0xFF99DDFF),
                    ),
                    steps = 3000,
                    valueRange = 0f..3000f
                )
            }
        }
    }
}

@Composable
fun AnimatedContentField(title: String,description:String) {
    var showDetails by remember{
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Column {
//
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Icon(imageVector = Icons.Default.Info, contentDescription = "Info",
                modifier = Modifier.clickable {
                    Toast.makeText(context,"Click on text",Toast.LENGTH_SHORT).show()
                }
                    .padding(10.dp))
            HeadingTexts.Level3(text = title, modifier = Modifier.clickable {
                showDetails = !showDetails
            })
        }

        AnimatedVisibility(visible = showDetails) {
            BodyTexts.Level2(text = description,modifier = Modifier.padding(10.dp))
        }
    }

}
