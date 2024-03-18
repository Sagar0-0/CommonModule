package fit.asta.health.feature.water.view.screen.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.token.AppSheetValue
import fit.asta.health.designsystem.molecular.AppLinearSlider
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.background.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.background.AppScreen
import fit.asta.health.designsystem.molecular.background.AppSheetState
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.background.appRememberBottomSheetScaffoldState
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.water.view.screen.WTEvent
import fit.asta.health.feature.water.view.screen.WaterToolUiState
import kotlinx.coroutines.launch


@Composable
fun CustomBevBottomSheet(
    onBack: () -> Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    event: (WTEvent) -> Unit,
    onClickSchedule : () -> Unit,
    uiState : WaterToolUiState
) {
    Log.d("rishiRecomposed", "CustomBevSheet called" +
            "UiState : ${uiState}")
    val bottomSheetState = appRememberBottomSheetScaffoldState(bottomSheetState = AppSheetState(
        initialValue = AppSheetValue.Hidden,
        skipPartialExpanded = false,
    ))

    val scrollState = rememberScrollState()
    val sliderValueWater = uiState.sliderValueWater
    val sliderValueCoconut = uiState.sliderValueCoconut
    val sliderValueFirstPreference = uiState.sliderValueFirstPreference
    val sliderValueSecondPreference = uiState.sliderValueSecondPreference
    val sliderValueRecentAdded = uiState.sliderValueRecentAdded

    val goal = uiState.goal
    val context: Context = LocalContext.current
    val scope = rememberCoroutineScope()
    var addedName by rememberSaveable {
        mutableStateOf("")
    }
    var currentBottomSheet: BottomSheetScreen? by remember {
        mutableStateOf(null)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                event(WTEvent.OnDisposeAddData)
            }
        }
        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
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

    AppBottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetPeekHeight = 0.dp,
        //sheetSwipeEnabled = true,
        //sheetContainerColor = backgroundColor,
        topBar = {
            AppTopBarWithHelp(
                title = "Water Tool",
                onBack = onBack,
                onHelp = {
                    Toast.makeText(context, "Beverage Tracking Tool", Toast.LENGTH_SHORT).show()
                }
            )
        },
        sheetContent = {
            currentBottomSheet?.let { currentSheet ->
                SheetLayout(
                    goal = goal,
                    currentScreen = currentSheet,
                    closeSheet = closeSheet,
                    onSliderValueChanged = {
                        val currValue = it.first
                        val currString = it.second
                        Log.d("ValueChanges", "sliderChange $currValue ${currString}")
                        event(WTEvent.UpdateOnSliderChangeQuantity(currString, currValue))
                    },
                    onNameChange = {
                        addedName = it
                    },
                    onGoalChange = {
                        event(WTEvent.GoalChange(it))
                    })
            }
        },
    ) {
        AppScreen {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
            ) {
                AppHomeScreen(
                    onClickWater = {
                        openSheet(
                            BottomSheetScreen.Screen1(
                                sliderValueWater,
                                "Water"
                            )
                        )
                    },
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
                ButtonWithColor(
                    color = Color.Blue, text = "Schedule",
                    modifier = Modifier.padding(AppTheme.spacing.level1)
                ) {
                    onClickSchedule()
                }
            }
        }
    }
}

@Composable
fun SheetLayout(
    goal : Int,
    currentScreen: BottomSheetScreen,
    closeSheet: () -> Unit,
    onSliderValueChanged: (Pair<Float, String>) -> Unit,
    onNameChange: (String) -> Unit,
    onGoalChange: (Int) -> Unit
) {
    val controller = LocalSoftwareKeyboardController.current
    AppBottomSheetWithCloseDialog(onClosePressed = {
        closeSheet()
        controller?.hide()
    }) {
        when (currentScreen) {
            is BottomSheetScreen.Screen1 -> Screen1(
                sliderValue = currentScreen.sliderValue,
                onSliderValueChanged = {
                    currentScreen.sliderValue = it
                    Log.d("ValueChanges", "Ye hai : ${currentScreen.sliderValue}")
                    onSliderValueChanged(Pair(it, currentScreen.bevName))
                },
            )

            is BottomSheetScreen.Screen2 -> Screen2(
                sliderValue = currentScreen.sliderValue,
                onSliderValueChanged = {
                    currentScreen.sliderValue = it
                    onSliderValueChanged(Pair(it, currentScreen.bevName))
                },
                onNameChange = {
                    onNameChange(it)
                },
            )

            is BottomSheetScreen.Screen3 -> Screen3(
                goal = goal,
                onGoalChange = {
                    onGoalChange(it)
                })

            else -> {}
        }
    }
}



@Composable
fun DaysSlider(
    sliderValue: Float,
    onSliderValueChanged: (Float) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(AppTheme.spacing.level1)
            .fillMaxWidth(),
    ) {
        var sliderPosition by remember { mutableStateOf(0f) }

        Column {
            Row {
                CaptionTexts.Level2(text = "Set Quantity : ")
                CaptionTexts.Level2(
                    text = "${sliderPosition.toInt()} ml",
                    color = AppTheme.colors.primary
                )
            }
            Row {
                AppLinearSlider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                    },
                    onValueChangeFinished = {
                        onSliderValueChanged(sliderPosition) // Update the slider value
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(AppTheme.spacing.level0),
                    steps = 3000,
                    minimumValue = 0f,
                    maximumValue = 3000f,
                   // valueRange = 0f..3000f
                )
            }
        }
    }
}

@Composable
fun AnimatedContentField(title: String, description: String) {
    var showDetails by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AppIcon(imageVector = Icons.Default.Info, contentDescription = "Info",
                modifier = Modifier
                    .clickable {
                        Toast
                            .makeText(context, "Click on text", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .padding(10.dp))
            HeadingTexts.Level3(text = title, modifier = Modifier.clickable {
                showDetails = !showDetails
            })
        }

        AnimatedVisibility(visible = showDetails) {
            BodyTexts.Level2(text = description, modifier = Modifier.padding(10.dp))
        }
    }

}
