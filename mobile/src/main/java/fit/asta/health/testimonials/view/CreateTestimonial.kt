package fit.asta.health.testimonials.view

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.R
import fit.asta.health.navigation.home.view.component.MainActivity
import fit.asta.health.testimonials.model.network.TestimonialType
import fit.asta.health.testimonials.view.components.ButtonListTypes
import fit.asta.health.testimonials.view.components.CustomOutlinedTextField
import fit.asta.health.testimonials.view.components.TestimonialRadioType
import fit.asta.health.testimonials.view.components.UploadFiles
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

    val maxChar = 50
    var enableButton by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var title by remember { mutableStateOf("") }
    var testimonial by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var org by remember { mutableStateOf("") }

    var validateTitle by rememberSaveable { mutableStateOf(true) }
    var validateTestimonials by rememberSaveable { mutableStateOf(true) }
    var validateRole by rememberSaveable { mutableStateOf(true) }
    var validateOrg by rememberSaveable { mutableStateOf(true) }

    val validateTitleError = "Please, input a Title"
    val validateOrgError = "Please, input a Organisation"
    val validateRoleError = "Please, input a Role"
    val validateTestimonialsError = if (testimonial.isEmpty()) {
        "Please, write your Testimonial"
    } else {
        "Please, write your Testimonial within ${testimonial.length}/$maxChar"
    }

    fun validateData(
        title: String,
        testimonials: String,
        role: String,
        org: String,
    ): Boolean {

        validateTitle = title.isNotBlank()
        validateTestimonials = testimonials.isNotBlank() && testimonials.length < maxChar
        validateOrg = org.isNotBlank()
        validateRole = role.isNotBlank()

        return validateTitle && validateTestimonials && validateOrg && validateRole
    }

    fun submit(
        title: String,
        testimonials: String,
        role: String,
        org: String,
    ) {
        if (validateData(title, testimonials, role, org)) {
            Log.d(
                MainActivity::class.java.simpleName,
                "Title:$title, testimonials:$testimonials, role:$role, org:$org"
            )
            editViewModel.onEvent(EditTestimonialEvent.OnSubmit)
        } else {
            Toast.makeText(context, "Please, review fields", Toast.LENGTH_LONG).show()
        }
    }

    fun enableButtonDemo(): Boolean {
        enableButton =
            title.isNotBlank() || testimonial.isNotBlank() || role.isNotBlank() || org.isNotBlank()
        return enableButton
    }

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

            CustomOutlinedTextField(
                value = testimonial,
                onValueChange = {
                    if (it.length < maxChar) {
                        testimonial = it
                        editViewModel.onEvent(EditTestimonialEvent.OnTestimonialChange(it))
                    }
                },
                label = "Testimonial",
                showError = !validateTestimonials,
                errorMessage = validateTestimonialsError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                modifier = Modifier.height(120.dp)
            )
        }, titleTestimonial = {
            CustomOutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    editViewModel.onEvent(EditTestimonialEvent.OnTitleChange(it))
                },
                label = "Title",
                showError = !validateTitle,
                errorMessage = validateTitleError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
        }, selectedOption = selectedOption, onOptionSelected = {
            onOptionSelected(it)
            editViewModel.onEvent(EditTestimonialEvent.OnTypeChange(it.type))
        })

        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = org,
            onValueChange = {
                org = it
                editViewModel.onEvent(EditTestimonialEvent.OnOrgChange(it))
            },
            label = "Organisation",
            showError = !validateOrg,
            errorMessage = validateOrgError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = role,
            onValueChange = {
                role = it
                editViewModel.onEvent(EditTestimonialEvent.OnRoleChange(it))
            },
            label = "Role",
            showError = !validateRole,
            errorMessage = validateRoleError,
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
            onClick = { submit(title, testimonial, role, org) },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                contentColor = Color.White
            ),
            enabled = enableButtonDemo()
        ) {
            Text(text = "Submit", fontSize = 16.sp)
        }
    }
}