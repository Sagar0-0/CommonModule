package fit.asta.health.testimonials.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.R
import fit.asta.health.testimonials.model.domain.TestimonialType
import fit.asta.health.testimonials.view.components.ButtonListTypes
import fit.asta.health.testimonials.view.components.TestimonialRadioType
import fit.asta.health.testimonials.view.components.UploadFiles
import fit.asta.health.testimonials.view.components.ValidatedTextField
import fit.asta.health.testimonials.viewmodel.edit.EditTestimonialEvent
import fit.asta.health.testimonials.viewmodel.edit.EditTestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun TestimonialForm(
    onNavigateTstCreate: () -> Unit,
    editViewModel: EditTestimonialViewModel = hiltViewModel()
) {

    val radioButtonList = listOf(
        ButtonListTypes(title = "Written", type = TestimonialType.TEXT),
        ButtonListTypes(title = "Image", type = TestimonialType.IMAGE),
        ButtonListTypes(title = "Video", type = TestimonialType.VIDEO),
    )

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioButtonList[0]) }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val validateTitle by rememberSaveable { mutableStateOf(true) }
    val validateTestimonials by rememberSaveable { mutableStateOf(true) }
    val validateRole by rememberSaveable { mutableStateOf(true) }
    val validateOrg by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center
    ) {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            IconButton(onClick = onNavigateTstCreate) {
                Icon(
                    painter = painterResource(id = R.drawable.removeicon),
                    contentDescription = null,
                    Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialRadioType(contentTestType = {

            ValidatedTextField(
                value = editViewModel.state.testimonial,
                onValueChange = { editViewModel.onEvent(EditTestimonialEvent.OnTestimonialChange(it)) },
                label = "Testimonial",
                showError = !validateTestimonials,
                errorMessage = editViewModel.state.testimonialError.asString(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                modifier = Modifier.height(120.dp)
            )
        }, titleTestimonial = {
            ValidatedTextField(
                value = editViewModel.state.title,
                onValueChange = { editViewModel.onEvent(EditTestimonialEvent.OnTitleChange(it)) },
                label = "Title",
                showError = !validateTitle,
                errorMessage = editViewModel.state.titleError.asString(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
        }, selectedOption = selectedOption, onOptionSelected = {
            onOptionSelected(it)
            editViewModel.onEvent(EditTestimonialEvent.OnTypeChange(it.type.value))
        })

        Spacer(modifier = Modifier.height(16.dp))

        ValidatedTextField(
            value = editViewModel.state.organization,
            onValueChange = {
                editViewModel.onEvent(EditTestimonialEvent.OnOrgChange(it))
            },
            label = "Organisation",
            showError = !validateOrg,
            errorMessage = editViewModel.state.organizationError.asString(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        Spacer(modifier = Modifier.height(16.dp))

        ValidatedTextField(
            value = editViewModel.state.role,
            onValueChange = { editViewModel.onEvent(EditTestimonialEvent.OnRoleChange(it)) },
            label = "Role",
            showError = !validateRole,
            errorMessage = editViewModel.state.roleError.asString(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        if (selectedOption == radioButtonList[1] || selectedOption == radioButtonList[2]) {

            Spacer(modifier = Modifier.height(16.dp))
            UploadFiles()
        }

        Button(
            onClick = { editViewModel.onEvent(EditTestimonialEvent.OnSubmit) },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                contentColor = Color.White
            ),
            enabled = editViewModel.state.enableSubmit
        ) {
            Text(text = "Submit", fontSize = 16.sp)
        }
    }
}