package fit.asta.health.profile.createprofile.view.components


import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Egg
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import fit.asta.health.profile.view.*
import fit.asta.health.ui.theme.cardElevation
import fit.asta.health.ui.theme.spacing

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileLayout() {

    /* TODO Paddings, Font, Elevations (4dp and 6dp), BottomSheets, Colors */

    val numberOfSteps = 5

    var currentStep by rememberSaveable { mutableStateOf(1) }

    var content by remember { mutableStateOf(1) }

    val stepDescriptionList = arrayListOf("Details", "Physique", "Heath", "LifeStyle", "Diet")

    var isSkipPressed by remember {
        mutableStateOf(false)
    }

    val iconList = listOf(
        Icons.Outlined.AccountCircle,
        Icons.Outlined.Face,
        Icons.Outlined.Favorite,
        Icons.Default.Emergency,
        Icons.Outlined.Egg
    )

    val descriptionList = MutableList(numberOfSteps) { "" }

    stepDescriptionList.forEachIndexed { index, element ->
        if (index < numberOfSteps) descriptionList[index] = element
    }

    val iconColor = if (isSkipPressed) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }

    Scaffold(topBar = {

        Column {
            TopAppBar(
                title = {
                    Text(
                        text = "Create Profile",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            currentStep -= 1
                        },
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                elevation = cardElevation.small,
                backgroundColor = MaterialTheme.colorScheme.onPrimary
            )

            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                for (step in 1..numberOfSteps) {
                    Stepper(
                        modifier = Modifier.weight(1F),
                        step = step,
                        isCompete = step < currentStep,
                        isCurrent = step == currentStep,
                        isComplete = step == numberOfSteps,
                        isRainbow = false,
                        stepDescription = descriptionList[step - 1],
                        unSelectedColor = Color.LightGray,
                        selectedColor = null,
                        icons = iconList[step - 1],
                        logic = {
                            content = step
                            currentStep = step
                        },
                        isPressed = isSkipPressed,
                        iconTint = iconColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }, content = { p ->

        Box(
            modifier = Modifier.padding(p)
        ) {
            when (currentStep) {
                1 -> {
                    DetailsContent(eventNext = {
                        currentStep += 1
                    }, onSkipEvent = {
                        currentStep += 1
                        isSkipPressed = true
                    })
                }
                2 -> {
                    PhysiqueContent(
                        eventNext = {
                            currentStep += 1
                        },
                        eventPrevious = {
                            currentStep -= 1
                        },
                        onSkipEvent = {
                            currentStep += 1
                            isSkipPressed = true
                        },
                    )
                }
                3 -> {
                    HealthCreateScreen(
                        eventNext = {
                            currentStep += 1
                        },
                        eventPrevious = {
                            currentStep -= 1
                        },
                        onSkipEvent = {
                            currentStep += 1
                            isSkipPressed = true
                        },
                    )
                }
                4 -> {
                    LifeStyleCreateScreen(
                        eventNext = {
                            currentStep += 1
                        },
                        eventPrevious = {
                            currentStep -= 1
                        },
                        onSkipEvent = {
                            currentStep += 1
                            isSkipPressed = true
                        },
                    )
                }
                5 -> {
                    DietCreateScreen(eventDone = {
                        currentStep += 1
                    }, eventPrevious = {
                        currentStep -= 1
                    })
                }
            }
        }
    }, containerColor = MaterialTheme.colorScheme.background)

}

@Composable
private fun Stepper(
    modifier: Modifier = Modifier,
    step: Int,
    isCompete: Boolean,
    isCurrent: Boolean,
    isComplete: Boolean,
    isRainbow: Boolean,
    isPressed: Boolean,
    stepDescription: String,
    unSelectedColor: Color,
    selectedColor: Color?,
    icons: ImageVector,
    logic: () -> Unit,
    iconTint: Color,
) {

    val rainBowColor = Brush.linearGradient(
        listOf(
            Color.Magenta,
            Color.Blue,
            Color.Cyan,
            Color.Green,
            Color.Yellow,
            Color.Red,
        )
    )

    val transition = updateTransition(isCompete, label = "")

    val innerCircleColor by transition.animateColor(label = "innerCircleColor") {
        if (it) selectedColor ?: MaterialTheme.colorScheme.primary else unSelectedColor
    }

    val txtColor by transition.animateColor(label = "txtColor") {
        if (it || isCurrent) selectedColor ?: MaterialTheme.colorScheme.primary else unSelectedColor
    }

    val color by transition.animateColor(label = "color") {
        if (it || isCurrent) selectedColor ?: MaterialTheme.colorScheme.primary else Color.Gray
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
            modifier = Modifier
                .size(30.dp)
                .constrainAs(circle) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }) {

            Box(contentAlignment = Alignment.Center) {

                IconButton(onClick = logic) {

                    if (isCompete) {
                        Icon(
                            imageVector = icons,
                            contentDescription = "done",
                            modifier = modifier.padding(4.dp),
                            tint = if (isPressed) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.primary
                            }
                        )
                    } else {
                        IconButton(onClick = logic) {
                            Icon(
                                imageVector = icons,
                                contentDescription = "done",
                                modifier = modifier.padding(4.dp),
                                tint = Color.Black
                            )
                        }
                    }

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