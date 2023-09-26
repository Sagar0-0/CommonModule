@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.feature.create

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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.R
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.functional.DialogData
import fit.asta.health.designsystem.components.functional.ShowCustomConfirmationDialog
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.profile.feature.create.view.DetailsCreateScreen
import fit.asta.health.profile.feature.create.view.DietCreateScreen
import fit.asta.health.profile.feature.create.view.HealthCreateScreen
import fit.asta.health.profile.feature.create.view.LifeStyleCreateScreen
import fit.asta.health.profile.feature.create.view.PhysiqueCreateScreen
import fit.asta.health.profile.feature.create.view.components.Stepper
import fit.asta.health.profile.feature.show.vm.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun CreateProfileLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
) {

    var showCustomDialogWithResult by remember { mutableStateOf(false) }
    var currentStep by rememberSaveable { mutableIntStateOf(1) }
    val numberOfSteps = 5

    val steps = listOf(StepData(
        1, Icons.Outlined.AccountCircle, stringResource(id = R.string.details)
    ) { currentStep = 2 },
        StepData(2, Icons.Outlined.Face, stringResource(id = R.string.physique)) {
            currentStep = 3
        },
        StepData(3, Icons.Outlined.Favorite, stringResource(id = R.string.health)) {
            currentStep = 4
        },
        StepData(
            4, Icons.Default.Emergency, stringResource(id = R.string.lifestyle)
        ) { currentStep = 5 },
        StepData(5, Icons.Outlined.Egg, stringResource(id = R.string.diet)) { currentStep = 6 })

    val primaryColor = MaterialTheme.colorScheme.primary

    AppScaffold(topBar = {
        Column(Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.extraSmall))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = AstaThemeX.spacingX.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AppDefaultIcon(imageVector = Icons.Outlined.NavigateBefore,
                    contentDescription = "NavigateBefore",
                    modifier = Modifier.clickable {
                        showCustomDialogWithResult = !showCustomDialogWithResult
                    })
                Spacer(modifier = Modifier.width(AstaThemeX.spacingX.small))
                AppTexts.TitleSmall(text = stringResource(R.string.create_profile))
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
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
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
                    }, navigateBack = onBack)
                }
            }
        }
        if (showCustomDialogWithResult) {
            ShowCustomConfirmationDialog(
                onDismiss = { showCustomDialogWithResult = !showCustomDialogWithResult },
                onNegativeClick = onBack,
                onPositiveClick = { showCustomDialogWithResult = !showCustomDialogWithResult },
                dialogData = DialogData(
                    dialogTitle = stringResource(R.string.discard_profile_creation),
                    dialogDesc = stringResource(R.string.dialogDesc_profile_creation),
                    negTitle = stringResource(R.string.negativeTitle_profile_creation),
                    posTitle = stringResource(R.string.positiveTitle_profile_creation)
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