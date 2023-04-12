@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.createprofile.view.components


import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Egg
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.MainActivity
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.testimonials.view.create.CustomDialogWithResultExample
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileLayout(viewModel: ProfileViewModel = hiltViewModel()) {

    /* TODO Paddings, Font, Elevations (4dp and 6dp), BottomSheets, Colors */

    val context = LocalContext.current

    //ValidInputs
    val isDetailValid by viewModel.areDetailsInputsValid.collectAsStateWithLifecycle()
    val isPhyValid by viewModel.phyInputsValid.collectAsStateWithLifecycle()
    val isHealthValid by viewModel.healthInputsValid.collectAsStateWithLifecycle()
    val isLSValid by viewModel.areLSValid.collectAsStateWithLifecycle()
    val isDietValid by viewModel.dietInputsValid.collectAsStateWithLifecycle()

    //Custom Dialog
    var showCustomDialogWithResult by remember { mutableStateOf(false) }


    val numberOfSteps = 5

    var currentStep by rememberSaveable { mutableStateOf(1) }

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

    val primaryColor = MaterialTheme.colorScheme.primary
    val errorColor = MaterialTheme.colorScheme.error

    val detailsColor = if (isDetailValid) {
        primaryColor
    } else {
        errorColor
    }

    val phyColor = if (isPhyValid && !isSkipPressed) {
        primaryColor
    } else {
        errorColor
    }

    val healthColor = if (isHealthValid && !isSkipPressed) {
        primaryColor
    } else {
        errorColor
    }

    val lifeStyleColor = if (isLSValid && !isSkipPressed) {
        primaryColor
    } else {
        errorColor
    }

    val dietColor = if (isDietValid && !isSkipPressed) {
        primaryColor
    } else {
        errorColor
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
                            showCustomDialogWithResult = !showCustomDialogWithResult
                        },
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "close",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                elevation = cardElevation.small,
                backgroundColor = MaterialTheme.colorScheme.onPrimary
            )

            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                (1..numberOfSteps).forEachIndexed { _, step ->
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
                            currentStep = step
                            Log.d(
                                "currSteps", " LS Boolean -> ${isLSValid && !isSkipPressed}"
                            )
                        },
                        detailsColor = detailsColor,
                        phyColor = phyColor,
                        healthColor = healthColor,
                        lifeStyleColor = lifeStyleColor,
                        dietColor = dietColor,
                        isDetailValid,
                        isPhyValid,
                        isHealthValid,
                        isLSValid,
                        isDietValid,
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
                    HealthCreateScreen(eventNext = {
                        currentStep += 1
                    }, eventPrevious = {
                        currentStep -= 1
                    }, onSkipEvent = {
                        currentStep += 1
                        isSkipPressed = true
                    })
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
                    DietCreateScreen(eventPrevious = {
                        currentStep -= 1
                    })
                }
            }
        }

        if (showCustomDialogWithResult) {
            CustomDialogWithResultExample(
                onDismiss = {
                    showCustomDialogWithResult = !showCustomDialogWithResult
                },
                onNegativeClick = {
                    (context as MainActivity).loadAppScreen()
                },
                onPositiveClick = {
                    showCustomDialogWithResult = !showCustomDialogWithResult
                },
                btnTitle = "Discard Profile Creation",
                btnWarn = "You will miss important FUTURE UPDATES like CUSTOM PLANS based on your PROFILE." + "CLICK Cancel to complete your PROFILE",
                btn1Title = "Discard Profile Creation and Move to Home Screen",
                btn2Title = "Cancel And Continue to Create Profile"
            )
        }


    }, containerColor = MaterialTheme.colorScheme.background)

}

@Composable
fun Stepper(
    modifier: Modifier = Modifier,
    step: Int,
    isCompete: Boolean,
    isCurrent: Boolean,
    isComplete: Boolean,
    isRainbow: Boolean,
    stepDescription: String,
    unSelectedColor: Color,
    selectedColor: Color?,
    icons: ImageVector,
    logic: () -> Unit,
    detailsColor: Color,
    phyColor: Color,
    healthColor: Color,
    lifeStyleColor: Color,
    dietColor: Color,
    isDetailValid: Boolean,
    isPhyValid: Boolean,
    isHealthValid: Boolean,
    isLSValid: Boolean,
    isDietValid: Boolean,
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

                    //after click

                    if (isCompete) {
                        Icon(
                            imageVector = icons,
                            contentDescription = "done",
                            modifier = modifier.padding(4.dp),
                            tint = when (step) {
                                1 -> {
                                    detailsColor
                                }
                                2 -> {
                                    phyColor
                                }
                                3 -> {
                                    healthColor
                                }
                                4 -> {
                                    lifeStyleColor
                                }
                                else -> {
                                    dietColor
                                }
                            }
                        )
                    } else {

                        //before click
//                        IconButton(onClick = logic) {
                        Icon(
                            imageVector = icons,
                            contentDescription = "done",
                            modifier = modifier.padding(4.dp),
                            tint = Color.Black
                        )
//                        }

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
