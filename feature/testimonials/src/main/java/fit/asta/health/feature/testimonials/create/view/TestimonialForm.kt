package fit.asta.health.feature.testimonials.create.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.utils.UiString
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.functional.AppTextFieldValidate
import fit.asta.health.designsystem.components.functional.DialogData
import fit.asta.health.designsystem.components.functional.OnSuccessfulSubmit
import fit.asta.health.designsystem.components.functional.ShowCustomConfirmationDialog
import fit.asta.health.designsystem.components.functional.ValidateTxtLength
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.extras.jetpack.HandleBackPress
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent.OnTypeChange
import fit.asta.health.feature.testimonials.create.vm.TestimonialSubmitState
import fit.asta.health.feature.testimonials.create.vm.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun TestimonialForm(
    paddingValues: PaddingValues,
    onNavigateTstHome: () -> Unit,
    getViewModel: TestimonialViewModel,
) {

    val type by getViewModel.type.collectAsStateWithLifecycle()
    val title by getViewModel.title.collectAsStateWithLifecycle()
    val testimonial by getViewModel.testimonial.collectAsStateWithLifecycle()
    val organization by getViewModel.org.collectAsStateWithLifecycle()
    val role by getViewModel.role.collectAsStateWithLifecycle()
    val areInputsValid by getViewModel.areInputsValid.collectAsStateWithLifecycle()
    val areMediaValid by getViewModel.areMediaValid.collectAsStateWithLifecycle()

    val focusRequester = remember { FocusRequester() }

    val radioButtonList = listOf(
        fit.asta.health.data.testimonials.model.TestimonialType.TEXT,
        fit.asta.health.data.testimonials.model.TestimonialType.IMAGE,
        fit.asta.health.data.testimonials.model.TestimonialType.VIDEO
    )

    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    val selectedOption = radioButtonList.find {
        it == type
    } ?: radioButtonList[0]

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val event = getViewModel.stateSubmit.collectAsState()
    val events = event.value

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = AppTheme.appSpacing.medium,
                end = AppTheme.appSpacing.medium
            )
    ) {
        Column {
            Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))

            TestimonialsRadioButton(selectionTypeText = "Testimonial Type",
                radioButtonList = radioButtonList,
                selectedOption = selectedOption,
                onOptionSelected = {
                    getViewModel.onEvent(OnTypeChange(it))
                })

            Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center
            ) {
                AppTextFieldValidate(
                    value = title.value,
                    label = "Title",
                    isError = title.error !is UiString.Empty,
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    onValueChange = { getViewModel.onEvent(TestimonialEvent.OnTitleChange(it)) },
                    errorMessage = title.error,
                )

                Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))

                if (selectedOption == radioButtonList[0] || selectedOption == radioButtonList[1]) {
                    AppTextFieldValidate(
                        value = testimonial.value,
                        label = "Testimonial",
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.clearFocus()
                        }),
                        onValueChange = {
                            getViewModel.onEvent(
                                TestimonialEvent.OnTestimonialChange(
                                    it
                                )
                            )
                        },
                        errorMessage = testimonial.error,
                        isError = testimonial.error !is UiString.Empty,
                        modifier = Modifier.height(AppTheme.appBoxSize.extraMedium),
                        imeAction = if (testimonial.value.length > ValidateTxtLength.defLength) {
                            ImeAction.Next
                        } else {
                            ImeAction.Default
                        },
                        showLenErrorMsg = true
                    )
                    Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))
                }

                AppTextFieldValidate(
                    value = organization.value,
                    onValueChange = { getViewModel.onEvent(TestimonialEvent.OnOrgChange(it)) },
                    label = "Organisation",
                    isError = organization.error !is UiString.Empty,
                    errorMessage = organization.error,
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    })
                )

                Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))

                AppTextFieldValidate(
                    value = role.value,
                    onValueChange = { getViewModel.onEvent(TestimonialEvent.OnRoleChange(it)) },
                    label = "Role",
                    isError = role.error !is UiString.Empty,
                    errorMessage = role.error,
                    imeAction = ImeAction.Done,
                    modifier = Modifier.focusRequester(focusRequester = focusRequester),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                )

                if (selectedOption == radioButtonList[1]) {
                    Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))
                    ImageLayout(
                        getViewModel = getViewModel,
                    )
                } else if (selectedOption == radioButtonList[2]) {
                    Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))
                    TstGetVideo()
                }

                AppButtons.AppStandardButton(
                    onClick = {
                        showCustomDialogWithResult = !showCustomDialogWithResult
                        getViewModel.onEvent(TestimonialEvent.OnSubmit)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.appSpacing.medium),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    enabled = areInputsValid && areMediaValid
                ) {
                    AppTexts.LabelLarge(text = "Submit")
                }

                if (showCustomDialogWithResult) {
                    when (events) {
                        is TestimonialSubmitState.Error -> {
                            Log.d("validate", "ErrorMessage -> ${events.error}")
                        }

                        is TestimonialSubmitState.Loading -> LoadingAnimation()
                        is TestimonialSubmitState.Success -> {
                            OnSuccessfulSubmit(onDismiss = {
                                showCustomDialogWithResult = !showCustomDialogWithResult
                            }, onNavigateTstHome = onNavigateTstHome, onPositiveClick = {
                                onNavigateTstHome()
                            })
                        }

                        is TestimonialSubmitState.NetworkError -> AppErrorScreen(onTryAgain = {
                            getViewModel.onEvent(
                                TestimonialEvent.OnSubmit
                            )
                        })
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateTstScreen(
    title: String,
    onNavigateTstCreate: () -> Unit,
    onNavigateTstHome: () -> Unit,
    getViewModel: TestimonialViewModel,
) {
    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    AppScaffold(topBar = {
        AppTopBar(title = title, onBack = {
            showCustomDialogWithResult = !showCustomDialogWithResult
        })
    }) {
        TestimonialForm(
            paddingValues = it,
            onNavigateTstHome = onNavigateTstHome,
            getViewModel = getViewModel,
        )
    }
    HandleBackPress {
        showCustomDialogWithResult = !showCustomDialogWithResult
    }
    if (showCustomDialogWithResult) {
        ShowCustomConfirmationDialog(onDismiss = {
            showCustomDialogWithResult = !showCustomDialogWithResult
        }, onNegativeClick = onNavigateTstCreate, onPositiveClick = {
            showCustomDialogWithResult = !showCustomDialogWithResult
        }, dialogData = DialogData(
            dialogTitle = "Discard Testimonial",
            dialogDesc = "Allow Permission to send you notifications when new art styles added.",
            negTitle = "Discard Testimonials",
            posTitle = "Cancel"
        )
        )
    }
}
