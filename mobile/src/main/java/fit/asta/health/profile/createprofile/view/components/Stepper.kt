package fit.asta.health.profile.createprofile.view.components


import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import fit.asta.health.R
import fit.asta.health.profile.view.*

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun StepperView() {

    val numberOfSteps = 5
    var currentStep by rememberSaveable { mutableStateOf(1) }
    val titleList = arrayListOf("Details", "Physique", "Heath", "LifeStyle", "Diet")
    val iconList = listOf(R.drawable.userphoto,
        R.drawable.ic_physique,
        R.drawable.healthandsafety,
        R.drawable.profilelifestyle,
        R.drawable.diet)

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        StepperDemo(modifier = Modifier.fillMaxWidth(),
            numberOfSteps = numberOfSteps,
            currentStep = currentStep,
            stepDescriptionList = titleList,
            unSelectedColor = Color.LightGray,
            selectedColor = Color.Blue,
            isRainbow = false,
            iconsList = iconList)
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            when (currentStep) {
                1 -> {
                    DetailsContent(eventNext = { currentStep += 1 })
                }
                2 -> {
                    PhysiqueContent(eventNext = { currentStep += 1 })
                }
                3 -> {
                    HealthContent(eventNext = { currentStep += 1 })
                }
                4 -> {
                    LifeStyleContent(eventNext = { currentStep += 1 })
                }
                5 -> {
                    DietContent(eventNext = { currentStep += 1 })
                }
            }
        }
    }


}

@Composable
fun ButtonLogic(current: () -> Unit) {

    Button(onClick = current) {
        Text(text = "Next")
    }

}

@Composable
fun StepperDemo(
    modifier: Modifier = Modifier,
    numberOfSteps: Int,
    currentStep: Int,
    stepDescriptionList: List<String> = List(numberOfSteps) { "" },
    unSelectedColor: Color = Color.LightGray,
    selectedColor: Color? = null,
    isRainbow: Boolean = false,
    iconsList: List<Int>,
) {

    val descriptionList = MutableList(numberOfSteps) { "" }

    stepDescriptionList.forEachIndexed { index, element ->
        if (index < numberOfSteps) descriptionList[index] = element
    }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        for (step in 1..numberOfSteps) {
            StepDemo(modifier = Modifier.weight(1F),
                step = step,
                isCompete = step < currentStep,
                isCurrent = step == currentStep,
                isComplete = step == numberOfSteps,
                isRainbow = isRainbow,
                stepDescription = descriptionList[step - 1],
                unSelectedColor = unSelectedColor,
                selectedColor = selectedColor,
                icons = iconsList[step - 1])
        }
    }
}

@Composable
private fun StepDemo(
    modifier: Modifier = Modifier,
    step: Int,
    isCompete: Boolean,
    isCurrent: Boolean,
    isComplete: Boolean,
    isRainbow: Boolean,
    stepDescription: String,
    unSelectedColor: Color,
    selectedColor: Color?,
    icons: Int?,
) {

    val rainBowColor = Brush.linearGradient(listOf(
        Color.Magenta,
        Color.Blue,
        Color.Cyan,
        Color.Green,
        Color.Yellow,
        Color.Red,
    ))

    val transition = updateTransition(isCompete, label = "")

    val innerCircleColor by transition.animateColor(label = "innerCircleColor") {
        if (it) selectedColor ?: MaterialTheme.colors.primary else unSelectedColor
    }
    val txtColor by transition.animateColor(label = "txtColor") {
        if (it || isCurrent) selectedColor ?: MaterialTheme.colors.primary else unSelectedColor
    }

    val color by transition.animateColor(label = "color") {
        if (it || isCurrent) selectedColor ?: MaterialTheme.colors.primary else Color.Gray
    }

    val borderStroke: BorderStroke = if (isRainbow) {
        BorderStroke(2.dp, rainBowColor)
    } else {
        BorderStroke(2.dp, color)
    }

    val textSize by remember { mutableStateOf(12.sp) }

    ConstraintLayout(modifier = modifier) {

        val (circle, txt, line) = createRefs()

        Surface(shape = CircleShape,
            border = borderStroke,
            color = innerCircleColor,
            modifier = Modifier
                .size(30.dp)
                .constrainAs(circle) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }) {

            Box(contentAlignment = Alignment.Center) {
                if (isCompete) Icon(imageVector = Icons.Default.Done,
                    "done",
                    modifier = modifier.padding(4.dp),
                    tint = Color.White)
                else icons?.let { painterResource(id = it) }?.let {
                    Icon(painter = it,
                        contentDescription = "done",
                        modifier = modifier.padding(4.dp))
                }
            }
        }

        Text(
            modifier = Modifier.constrainAs(txt) {
                top.linkTo(circle.bottom, margin = 3.dp)
                start.linkTo(circle.start)
                end.linkTo(circle.end)
                bottom.linkTo(parent.bottom)
            },
            fontSize = textSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = stepDescription,
            color = txtColor,
        )

        if (!isComplete) {
            //Line
            if (isRainbow) {
                Divider(
                    modifier = Modifier
                        .constrainAs(line) {
                            top.linkTo(circle.top)
                            bottom.linkTo(circle.bottom)
                            start.linkTo(circle.end)
                        }
                        .background(rainBowColor),
                    thickness = 1.dp,
                )
            } else {
                Divider(
                    modifier = Modifier.constrainAs(line) {
                        top.linkTo(circle.top)
                        bottom.linkTo(circle.bottom)
                        start.linkTo(circle.end)
                    },
                    color = innerCircleColor,
                    thickness = 1.dp,
                )
            }
        }
    }
}