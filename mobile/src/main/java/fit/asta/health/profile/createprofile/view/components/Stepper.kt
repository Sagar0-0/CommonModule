package fit.asta.health.profile.createprofile.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.maryamrzdh.stepper.Stepper


@Preview
@Composable
fun StepperView() {

    val numberOfSteps = 5
    val currentStep by rememberSaveable { mutableStateOf(6) }
    val titleList = arrayListOf("Details", "Physique", "Heath", "LifeStyle", "Diet")

    Stepper(modifier = Modifier.fillMaxWidth(),
        numberOfSteps = numberOfSteps,
        currentStep = currentStep,
        stepDescriptionList = titleList,
        unSelectedColor = Color.LightGray,
        selectedColor = Color.Blue,
        isRainbow = false)

}