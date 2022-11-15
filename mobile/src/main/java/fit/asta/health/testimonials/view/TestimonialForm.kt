package fit.asta.health.testimonials.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.testimonials.model.domain.TestimonialType
import fit.asta.health.testimonials.view.components.*
import fit.asta.health.testimonials.viewmodel.create.TestimonialEvent
import fit.asta.health.testimonials.viewmodel.create.TestimonialViewModel
import fit.asta.health.utils.UiString
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TestimonialForm(
    editViewModel: TestimonialViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
) {

    val radioButtonList = listOf(
        ButtonListTypes(title = "Written", type = TestimonialType.TEXT),
        ButtonListTypes(title = "Image", type = TestimonialType.IMAGE),
        ButtonListTypes(title = "Video", type = TestimonialType.VIDEO),
    )

    val selectedOption = radioButtonList.find {
        it.type == TestimonialType.fromInt(editViewModel.data.type)
    } ?: radioButtonList[0]

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = paddingValues.calculateTopPadding(), horizontal = 16.dp)
        .verticalScroll(scrollState), verticalArrangement = Arrangement.Center) {

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialsRadioButton(selectionTypeText = "Testimonial Type",
            radioButtonList = radioButtonList,
            selectedOption = selectedOption,
            content = {
                ValidatedTextField(value = editViewModel.data.testimonial,
                    onValueChange = { editViewModel.onEvent(TestimonialEvent.OnTestimonialChange(it)) },
                    label = "Testimonial",
                    showError = editViewModel.data.testimonialError !is UiString.Empty,
                    errorMessage = editViewModel.data.testimonialError,
                    keyboardOptions = if (editViewModel.data.testimonial.length < 512) {
                        KeyboardOptions(keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Default)
                    } else {
                        KeyboardOptions(keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next)
                    },
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.clearFocus()
                    }),
                    modifier = Modifier.height(120.dp))
            },
            titleTestimonial = {
                ValidatedTextField(value = editViewModel.data.title,
                    onValueChange = { editViewModel.onEvent(TestimonialEvent.OnTitleChange(it)) },
                    label = "Title",
                    showError = editViewModel.data.titleError !is UiString.Empty,
                    errorMessage = editViewModel.data.titleError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }))
            },
            onOptionSelected = {
                editViewModel.onEvent(TestimonialEvent.OnTypeChange(it.type.value))
            })

        Spacer(modifier = Modifier.height(16.dp))

        ValidatedTextField(value = editViewModel.data.org,
            onValueChange = { editViewModel.onEvent(TestimonialEvent.OnOrgChange(it)) },
            label = "Organisation",
            showError = editViewModel.data.orgError !is UiString.Empty,
            errorMessage = editViewModel.data.orgError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }))

        Spacer(modifier = Modifier.height(16.dp))

        ValidatedTextField(value = editViewModel.data.role,
            onValueChange = { editViewModel.onEvent(TestimonialEvent.OnRoleChange(it)) },
            label = "Role",
            showError = editViewModel.data.roleError !is UiString.Empty,
            errorMessage = editViewModel.data.roleError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Exit) }))

        if (selectedOption == radioButtonList[1]) {
            Spacer(modifier = Modifier.height(16.dp))
            TestGetImage()
        } else if (selectedOption == radioButtonList[2]) {
            Spacer(modifier = Modifier.height(16.dp))
            TestGetVideo()
        }

        Button(onClick = { editViewModel.onEvent(TestimonialEvent.OnSubmit) },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue,
                contentColor = Color.White),
            enabled = editViewModel.data.enableSubmit) {
            Text(text = "Submit", fontSize = 16.sp)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun CreateTstScreen(onNavigateTstCreate: () -> Unit) {

    Scaffold(topBar = {
        BottomNavigation(content = {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onNavigateTstCreate) {
                    Icon(Icons.Outlined.NavigateBefore,
                        "back",
                        tint = Color(0xff0088FF),
                        modifier = Modifier.size(24.dp))
                }
                androidx.compose.material3.Text(text = "Create your Testimonial",
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center)
            }
        }, elevation = 10.dp, backgroundColor = Color.White)
    }, content = {
        TestimonialForm(paddingValues = it)
    })

}