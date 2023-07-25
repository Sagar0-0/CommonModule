@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile


import androidx.compose.foundation.layout.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.createprofile.view.DetailsCreateScreen
import fit.asta.health.profile.createprofile.view.DietCreateScreen
import fit.asta.health.profile.createprofile.view.HealthCreateScreen
import fit.asta.health.profile.createprofile.view.LifeStyleCreateScreen
import fit.asta.health.profile.createprofile.view.PhysiqueCreateScreen
import fit.asta.health.profile.createprofile.view.components.Stepper
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.testimonials.view.create.CustomDialogWithResultExample
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun CreateProfileLayout(viewModel: ProfileViewModel = hiltViewModel(), onBack: () -> Unit) {

    /* TODO Paddings, Font, Elevations (4dp and 6dp), BottomSheets, Colors */

//    val context = LocalContext.current

    //ValidInputs

    // pregnancy


    //Inputs Validity


    //Custom Dialog
    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    val numberOfSteps = 5

    var currentStep by rememberSaveable { mutableIntStateOf(1) }

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
    MaterialTheme.colorScheme.error


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
                colors =  TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.onPrimary)
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
                        detailsColor = primaryColor,
                        phyColor = primaryColor,
                        healthColor = primaryColor,
                        lifeStyleColor = primaryColor,
                        dietColor = primaryColor,
                        true,
                        isPhyValid = true,
                        isHealthValid = true,
                        isLSValid = true,
                        isDietValid = true,
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
                onNegativeClick = onBack,
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

