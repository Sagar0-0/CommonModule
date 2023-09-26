package fit.asta.health.tools.breathing.view.exercise

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBarWithHelp
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.feature.scheduler.ui.components.SnoozeBottomSheet
import fit.asta.health.tools.breathing.model.domain.model.Exercise
import fit.asta.health.tools.breathing.model.domain.model.Ratio
import fit.asta.health.tools.breathing.model.domain.model.toStr
import fit.asta.health.tools.breathing.view.components.CardBreathingRatio
import fit.asta.health.tools.breathing.view.exercise.ExerciseBottomSheetTypes.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    onClick: (List<String>) -> Unit, onBack: () -> Unit
) {
    val goals = listOf(
        Exercise(
            name = "Nadi Shodhana", duration = "2:00", ratio = Ratio(1, 1, 1, 1), isSelected = false
        ),
        Exercise(name = "Ujjayi", duration = "2:00", ratio = Ratio(1, 1, 1, 1), isSelected = false),
        Exercise(
            name = "Shiitali Kumbhaka",
            duration = "2:00",
            ratio = Ratio(1, 1, 1, 1),
            isSelected = false
        ),
        Exercise(
            name = "Brahmari", duration = "2:00", ratio = Ratio(1, 1, 1, 1), isSelected = false
        ),
        Exercise(name = "Bhasari", duration = "2:00", ratio = Ratio(1, 1, 1, 1), isSelected = false)
    )
    var items by remember {
        mutableStateOf(goals)
    }

    var currentBottomSheet: ExerciseBottomSheetTypes? by remember {
        mutableStateOf(null)
    }

    val skipPartiallyExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch { bottomSheetState.hide() }
    }

    val openSheet = {
        scope.launch { bottomSheetState.show() }
    }

    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBarWithHelp(
                title = "Exercise",
                onBack = onBack,
                onHelp = { /*TODO*/ }
            )
        })
    {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Select exercises for your breathing exercise",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            items(count = items.size) { indexNumber ->
                CardBreathingRatio(modifier = Modifier.clickable {
                    items = items.mapIndexed { j, item ->
                        if (indexNumber == j) {
                            item.copy(isSelected = !item.isSelected)
                        } else item
                    }
                    onClick(items.filter { it.isSelected }.map { it.name })
                },
                    color = if (!items[indexNumber].isSelected) {
                        Color(0xFFE9D7F7)
                    } else {
                        Color(0xFF7415BD)
                    },
                    name = items[indexNumber].name,
                    duration = items[indexNumber].duration,
                    ratio = items[indexNumber].ratio.toStr(),
                    onReset = {},
                    onRatio = {
                        currentBottomSheet = RATIO
                        openSheet()
                    },
                    onInfo = {},
                    onDuration = {
                        currentBottomSheet = DURATION
                        openSheet()
                    })
            }
        }
    }
    CustomModelBottomSheet(targetState = bottomSheetState.isVisible,
        sheetState = bottomSheetState,
        content = {
            currentBottomSheet?.let {
                ExerciseBottomSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
            }
        },
        dragHandle = {},
        onClose = { closeSheet() })
}


enum class ExerciseBottomSheetTypes {
    RATIO, DURATION
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExerciseBottomSheetLayout(
    sheetLayout: ExerciseBottomSheetTypes,
    closeSheet: () -> Unit,
//    aSEvent: (AlarmSettingEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    when (sheetLayout) {
        RATIO -> {
            NumberPickerRatio(onCancel = { closeSheet() }, onSave = { closeSheet() })
        }

        DURATION -> {
            SnoozeBottomSheet(onNavigateBack = { closeSheet() }, onValueChange = {

            })
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NumberPickerRatio(onSave: (Ratio) -> Unit = {}, onCancel: () -> Unit = {}) {

    var inhaleValue by remember { mutableIntStateOf(1) }
    var inhaleHoldValue by remember { mutableIntStateOf(1) }
    var exhaleValue by remember { mutableIntStateOf(1) }
    var exhaleHoldValue by remember { mutableIntStateOf(1) }
    var index by remember { mutableIntStateOf(-1) }
    val ratioList = listOf(
        Ratio(4, 4, 7, 0),
        Ratio(4, 4, 4, 0),
        Ratio(4, 4, 7, 0),
        Ratio(4, 6, 7, 0),
        Ratio(4, 7, 8, 0),
        Ratio(5, 5, 5, 0)
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Select Standard Ratio")
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 4,
                verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small),
                horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)
            ) {
                StandardRatio(ratio = ratioList[0],
                    index = 0, code = index, onClick = { index = it })
                StandardRatio(ratio = ratioList[1],
                    index = 1, code = index, onClick = { index = it })
                StandardRatio(ratio = ratioList[2],
                    index = 2, code = index, onClick = { index = it })
                StandardRatio(ratio = ratioList[3],
                    index = 3, code = index, onClick = { index = it })
                StandardRatio(ratio = ratioList[4],
                    index = 4, code = index, onClick = { index = it })
                StandardRatio(ratio = ratioList[5],
                    index = 5, code = index, onClick = { index = it })
            }

            Text(text = "Select Ratio")
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small)) {
                    Text(text = "inhale")
                    NumberPicker(value = inhaleValue,
                        range = 0..10,
                        dividersColor = MaterialTheme.colorScheme.onBackground,
                        onValueChange = {
                            inhaleValue = it
                            index = -1
                        })
                }
                VerticalLine()
                Column(verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small)) {
                    Text(text = "hold")
                    NumberPicker(value = inhaleHoldValue,
                        range = 0..10,
                        dividersColor = MaterialTheme.colorScheme.onBackground,
                        onValueChange = {
                            inhaleHoldValue = it
                            index = -1
                        })
                }
                VerticalLine()
                Column(verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small)) {
                    Text(text = "exhale")
                    NumberPicker(value = exhaleValue,
                        range = 0..10,
                        dividersColor = MaterialTheme.colorScheme.onBackground,
                        onValueChange = {
                            exhaleValue = it
                            index = -1
                        })
                }
                VerticalLine()
                Column(verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small)) {
                    Text(text = "hold")
                    NumberPicker(value = exhaleHoldValue,
                        range = 0..10,
                        dividersColor = MaterialTheme.colorScheme.onBackground,
                        onValueChange = {
                            exhaleHoldValue = it
                            index = -1
                        })
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)) {
                ButtonWithColor(
                    modifier = Modifier.weight(0.5f), color = Color.Red, text = "CANCEL"
                ) { onCancel() }
                ButtonWithColor(
                    modifier = Modifier.weight(0.5f),
                    color = Color.Blue,
                    text = "SAVE"
                ) {
                    if (index != -1) {
                        onSave(ratioList[index])
                    } else {
                        onSave(Ratio(inhaleValue, inhaleHoldValue, exhaleValue, exhaleHoldValue))
                    }
                }
            }
        }
    }
}

@Composable
fun DurationPicker() {
    var min by remember { mutableIntStateOf(1) }
    var sec by remember { mutableIntStateOf(0) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small)
    ) {
        Column {
            Text(text = "Minutes")
            NumberPicker(value = min, range = 0..20, onValueChange = { min = it })
        }
        VerticalLine()
        Column {
            Text(text = "Seconds")
            NumberPicker(value = sec, range = 0..59, onValueChange = { sec = it })
        }
    }
}

@Composable
fun VerticalLine() {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Divider(
            modifier = Modifier
                .height(110.dp)
                .width(3.dp)
        )
    }
}

@Composable
fun StandardRatio(ratio: Ratio, index: Int, code: Int, onClick: (Int) -> Unit) {
    Card(
        border = BorderStroke(
            width = 2.dp, color = if (index != code) {
                MaterialTheme.colorScheme.onBackground
            } else {
                Color(0xFF7415BD)
            }
        ),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.outlinedCardColors(
            contentColor = if (index != code) {
                Color(0xFFE9D7F7)
            } else {
                Color(0xFF7415BD)
            }
        ),
        modifier = Modifier.clickable { onClick(index) }
    ) {
        Text(modifier = Modifier.padding(8.dp), text = ratio.toStr())
    }
}