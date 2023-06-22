@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile


import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.MainActivity
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.createprofile.view.DetailsCreateScreen
import fit.asta.health.profile.createprofile.view.DietCreateScreen
import fit.asta.health.profile.createprofile.view.HealthCreateScreen
import fit.asta.health.profile.createprofile.view.LifeStyleCreateScreen
import fit.asta.health.profile.createprofile.view.PhysiqueCreateScreen
import fit.asta.health.profile.createprofile.view.components.Stepper
import fit.asta.health.profile.model.domain.ThreeToggleSelections
import fit.asta.health.profile.model.domain.TwoToggleSelections
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
    val isPhyValid by viewModel.areBasicPhysiqueInputsValid.collectAsStateWithLifecycle()
    val isHealthValid by viewModel.areSelectedHealthOptionsNull.collectAsStateWithLifecycle()
    val isLSValid by viewModel.areLSValid.collectAsStateWithLifecycle()
    val isDietValid by viewModel.dietInputsValid.collectAsStateWithLifecycle()

    // pregnancy
    val selectedIsPregnantOption by viewModel.selectedIsPregnant.collectAsStateWithLifecycle()
    val selectedGenderOption by viewModel.selectedGender.collectAsStateWithLifecycle()

    //Inputs Validity
    val areFemaleInputsNull by viewModel.areFemaleInputNull.collectAsStateWithLifecycle()
    val arePregInputsValid by viewModel.arePregnancyInputValid.collectAsStateWithLifecycle()


    val isDemoPhyValid =
        if (selectedGenderOption == ThreeToggleSelections.Second && areFemaleInputsNull) {
            if (selectedIsPregnantOption == TwoToggleSelections.First) {
                arePregInputsValid
            } else {
                true
            }
        } else {
            isPhyValid
        }

    //Custom Dialog
    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    val numberOfSteps = 5

    var currentStep by rememberSaveable { mutableStateOf(1) }

    val stepDescriptionList = arrayListOf("Details", "Physique", "Heath", "LifeStyle", "Diet")

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

    val phyColor = if (isDemoPhyValid) {
        primaryColor
    } else {
        errorColor
    }

    val healthColor = if (isHealthValid) {
        primaryColor
    } else {
        errorColor
    }

    val lifeStyleColor = if (isLSValid) {
        primaryColor
    } else {
        errorColor
    }

    val dietColor = if (isDietValid) {
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
                        },
                        detailsColor = detailsColor,
                        phyColor = phyColor,
                        healthColor = healthColor,
                        lifeStyleColor = lifeStyleColor,
                        dietColor = dietColor,
                        isDetailValid,
                        isPhyValid = isPhyValid,
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
                    DetailsCreateScreen(eventNext = {
                        currentStep += 1
                    }, onSkipEvent = {
                        currentStep += 1
                    })
                }

                2 -> {
                    PhysiqueCreateScreen(
                        eventNext = {
                            currentStep += 1
                        },
                        eventPrevious = {
                            currentStep -= 1
                        },
                        onSkipEvent = {
                            currentStep += 1
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

