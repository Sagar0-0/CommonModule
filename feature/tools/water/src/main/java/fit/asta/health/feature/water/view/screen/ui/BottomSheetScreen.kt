package fit.asta.health.feature.water.view.screen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.water.viewmodel.WaterToolViewModel

sealed class BottomSheetScreen() {
    class Screen1(var sliderValue: Float, val bevName: String) : BottomSheetScreen()
    class Screen2(var sliderValue: Float, val bevName: String) : BottomSheetScreen()
    class Screen3() : BottomSheetScreen()
}

@Composable
fun AppBottomSheetWithCloseDialog(
    onClosePressed: () -> Unit,
    modifier: Modifier = Modifier,
    closeButtonColor: Color = Color.Gray,
    content: @Composable() () -> Unit
) {
    Box(modifier.fillMaxWidth()) {
        content()
        AppIconButton(
            onClick = onClosePressed,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(AppTheme.spacing.level2)
                .size(AppTheme.spacing.level3)

        ) {
            AppIcon(imageVector = Icons.Filled.Close, tint = closeButtonColor, contentDescription = null)
        }

    }
}

// Adding Quantity BottomSheet
@Composable
fun Screen1(sliderValue: Float, onSliderValueChanged: (Float) -> Unit, color: Color) {
    var sliderPosition by remember { mutableStateOf(sliderValue) }
    Column {
        val title = "Set Beverage Quantity"
        val description =
            "Adjust the quantity for each beverage to track your consumption accurately. Slide the bar to set the desired amount."
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

// Searching BottomSheet
@Composable
fun Screen2(
    sliderValue: Float,
    color: Color,
    onSliderValueChanged: (Float) -> Unit,
    onNameChange: (String) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(sliderValue) }
    Column {
        val title = "Search and Set Beverage\n Quantity"
        val description =
            "Find your favorite beverages quickly by searching. Once found, set the quantity to monitor your intake. Use the search bar to explore."
        AnimatedContentField(title = title, description = description)
        CustomBevCard(onNameChange = {
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
            // fnc invoked when we change the slider value
        }
    }

}

// Daily Goal BottomSheet Screen
@Composable
fun Screen3(
    viewModel: WaterToolViewModel = hiltViewModel(),
    onGoalChange: (Int) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    val goal by viewModel.goal.collectAsState()
    Column {
        val title = "Set Daily Goal"
        val description =
            "Define your daily hydration goal to stay on track with your beverage intake. Adjust the goal according to your preferences and health recommendations."
        AnimatedContentField(title = title, description = description)

        HeadingTexts.Level3(
            "Current Goal is : $goal",
            modifier = Modifier.padding(AppTheme.spacing.level1)
        )


        AppOutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onGoalChange(if (it.isEmpty()) 0 else it.toInt())
            },
            leadingIcon = {
                AppIcon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Set Goal",
                )
            },
            placeholder = {
                BodyTexts.Level3(text = "Edit Your Goal in  ml")
            },
            shape = AppTheme.shape.level3,
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