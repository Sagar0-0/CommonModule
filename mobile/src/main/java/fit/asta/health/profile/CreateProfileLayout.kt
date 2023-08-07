@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Egg
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.common.ui.components.*
import fit.asta.health.common.ui.components.functional.DialogData
import fit.asta.health.common.ui.components.functional.ShowCustomConfirmationDialog
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.createprofile.view.DetailsCreateScreen
import fit.asta.health.profile.createprofile.view.DietCreateScreen
import fit.asta.health.profile.createprofile.view.HealthCreateScreen
import fit.asta.health.profile.createprofile.view.LifeStyleCreateScreen
import fit.asta.health.profile.createprofile.view.PhysiqueCreateScreen
import fit.asta.health.profile.createprofile.view.components.Stepper
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun CreateProfileLayoutDemo(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
) {

    var showCustomDialogWithResult by remember { mutableStateOf(false) }
    var currentStep by rememberSaveable { mutableIntStateOf(1) }
    val numberOfSteps = 5

    val steps = listOf(StepData(1, Icons.Outlined.AccountCircle, "Details") { currentStep = 2 },
        StepData(2, Icons.Outlined.Face, "Physique") { currentStep = 3 },
        StepData(3, Icons.Outlined.Favorite, "Health") { currentStep = 4 },
        StepData(4, Icons.Default.Emergency, "LifeStyle") { currentStep = 5 },
        StepData(5, Icons.Outlined.Egg, "Diet") { currentStep = 6 })

    val primaryColor = MaterialTheme.colorScheme.primary

    AppScaffold(topBar = {
        Column(Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(spacing.extraSmall))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = spacing.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AppDefaultIcon(imageVector = Icons.Outlined.NavigateBefore,
                    contentDescription = "NavigateBefore",
                    modifier = Modifier.clickable {
                        showCustomDialogWithResult = !showCustomDialogWithResult
                    })
                Spacer(modifier = Modifier.width(spacing.small))
                AppTexts.TitleSmall(text = "Create Profile")
            }
            Row(Modifier.fillMaxWidth()) {
                steps.forEach { step ->
                    Step(modifier = Modifier.weight(1f),
                        step = step.step,
                        isCompete = step.step < currentStep,
                        isCurrent = step.step == currentStep,
                        isComplete = step.step == numberOfSteps,
                        stepDescription = step.description,
                        selectedColor = primaryColor,
                        icons = step.icon,
                        onStepClick = { currentStep = step.step })
                }
            }
            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }, content = { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (currentStep) {
                1 -> {
                    DetailsCreateScreen(eventNext = {
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
                    )
                }

                3 -> {
                    HealthCreateScreen(eventNext = {
                        currentStep += 1
                    }, eventPrevious = {
                        currentStep -= 1
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
            ShowCustomConfirmationDialog(
                onDismiss = { showCustomDialogWithResult = !showCustomDialogWithResult },
                onNegativeClick = onBack,
                onPositiveClick = { showCustomDialogWithResult = !showCustomDialogWithResult },
                dialogData = DialogData(
                    dialogTitle = "Discard Profile Creation",
                    dialogDesc = "You will miss important FUTURE UPDATES like CUSTOM PLANS based on your PROFILE. CLICK Cancel to complete your PROFILE",
                    negTitle = "Discard Profile Creation and Move to Home Screen",
                    posTitle = "Cancel And Continue to Create Profile"
                ),
            )
        }
    })
}

@Composable
fun Step(
    step: Int,
    modifier: Modifier = Modifier,
    isCompete: Boolean,
    isCurrent: Boolean,
    isComplete: Boolean,
    stepDescription: String,
    selectedColor: Color,
    icons: ImageVector,
    onStepClick: () -> Unit,
) {
    Stepper(
        modifier = modifier,
        step = step,
        isCompete = isCompete,
        isCurrent = isCurrent,
        isComplete = isComplete,
        isRainbow = false,
        stepDescription = stepDescription,
        unSelectedColor = Color.LightGray,
        selectedColor = if (isCurrent) selectedColor else null,
        icons = icons,
        logic = { onStepClick() },
        detailsColor = selectedColor,
        phyColor = selectedColor,
        healthColor = selectedColor,
        lifeStyleColor = selectedColor,
        dietColor = selectedColor,
        isDetailValid = true,
        isPhyValid = true,
        isHealthValid = true,
        isLSValid = true,
        isDietValid = true,
    )
}

data class StepData(
    val step: Int,
    val icon: ImageVector,
    val description: String,
    val onStepClick: () -> Unit,
)

@Preview
@Composable
private fun CustomProfileTopBar(onClick: () -> Unit = {}) {
    Row(
        Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        AppButtons.AppIconButton(onClick = onClick) {
            AppDefaultIcon(imageVector = Icons.Rounded.Close, contentDescription = "Close")
        }
        Spacer(modifier = Modifier.width(spacing.small))
        AppTexts.TitleMedium(text = "Create Profile")
    }
}