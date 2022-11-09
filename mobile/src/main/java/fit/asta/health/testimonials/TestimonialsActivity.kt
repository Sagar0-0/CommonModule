package fit.asta.health.testimonials

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.databinding.ActivityProfileNewBinding
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.MainActivity
import fit.asta.health.navigation.home.view.component.NoInternetLayout
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.view.AllTestimonialsLayout
import fit.asta.health.testimonials.view.components.CustomOutlinedTextField
import fit.asta.health.testimonials.view.components.TestimonialType
import fit.asta.health.testimonials.view.components.UploadFiles
import fit.asta.health.testimonials.viewmodel.TestimonialListState
import fit.asta.health.testimonials.viewmodel.TestimonialListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TestimonialsActivity : AppCompatActivity() {

    private lateinit var navController: NavHostController
    private lateinit var binding: ActivityProfileNewBinding
    private val viewModel: TestimonialListViewModel by viewModels()
    //private val editViewModel: EditTestimonialViewModel by viewModels()

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, TestimonialsActivity::class.java)
            intent.apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileNewBinding.inflate(layoutInflater)
        binding.profileComposeView.setContent {

            val testimonialState = viewModel.state.collectAsState().value
            navController = rememberNavController()
            TestimonialsContent(state = viewModel.state.collectAsState().value)

        }
        setContentView(binding.root)
    }

}

@Composable
fun TestimonialsScreen(
    navController: NavHostController,
    testimonial: List<NetTestimonial>,
) {


    NavHost(navController, startDestination = TstScreen.TstHome.route) {

        composable(route = TstScreen.TstHome.route) {

            AllTestimonialsLayout(onNavigateUp = {
                navController.navigate(route = TstScreen.TstCreate.route)
            }, testimonial = testimonial, onNavigateBack = {
                navController.popBackStack()
            })

        }
        composable(route = TstScreen.TstCreate.route) {

//                val editViewModelDemo: EditTestimonialViewModel = hiltViewModel()

            ShowForm(onNavigateTstCreate = {

//                    editViewModelDemo.onEvent(TestimonialEvent.OnSaveClick)
                //navController.navigate(route = TstScreen.TstHome.route)
                navController.popBackStack()
            })
        }
    }


}

//@Preview
//@Composable
//fun ScreenPreview() {
//    TestimonialsScreen(navController = rememberNavController(),)
//}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TestimonialsContent(state: TestimonialListState) {

    when (state) {
        is TestimonialListState.Error -> NoInternetLayout()
        is TestimonialListState.Loading -> LoadingAnimation()
        is TestimonialListState.Success -> TestimonialsScreen(
            navController = rememberNavController(),
            testimonial = state.testimonial
        )
    }
}

@Composable
fun ShowForm(onNavigateTstCreate: () -> Unit) {

    val maxChar = 50

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var title by remember { mutableStateOf("") }
    var testimonials by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var org by remember { mutableStateOf("") }

    var validateTitle by rememberSaveable { mutableStateOf(true) }
    var validateTestimonials by rememberSaveable { mutableStateOf(true) }
    var validateUserName by rememberSaveable { mutableStateOf(true) }
    var validateRole by rememberSaveable { mutableStateOf(true) }
    var validateOrg by rememberSaveable { mutableStateOf(true) }

    val validateTitleError = "Please, input a Title"
    val validateTestimonialsError = "Please, write your Testimonial within Range"
    val validateUserNameError = "Please, input a valid Name"
    val validateRoleError = "Please, input a Role"
    val validateOrgError = "Please, input a Organisation"

    fun validateData(
        title: String,
        testimonials: String,
        userName: String,
        role: String,
        org: String,
    ): Boolean {

        validateTitle = title.isNotBlank()
        validateTestimonials = testimonials.isNotBlank() && testimonials.length < 50
        validateUserName = userName.isNotBlank() && userName.any { !it.isDigit() }
        validateOrg = org.isNotBlank()
        validateRole = role.isNotBlank()

        return validateTitle && validateTestimonials && validateUserName && validateOrg && validateRole
    }

    fun submit(
        title: String,
        testimonials: String,
        userName: String,
        role: String,
        org: String,
    ) {
        if (validateData(title, testimonials, userName, role, org)) {
            Log.d(
                MainActivity::class.java.simpleName,
                "Title:$title, testimonials:$testimonials, userName:$userName, role:$role, org:$org"
            )
        } else {
            Toast.makeText(context, "Please, review fields", Toast.LENGTH_LONG).show()
        }
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

        TestimonialType(contentTestType = {

            CustomOutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = "Title",
                showError = !validateTitle,
                errorMessage = validateTitleError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = testimonials,
                onValueChange = { testimonials = it },
                label = "Testimonials",
                showError = !validateTestimonials,
                errorMessage = validateTestimonialsError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
        })

        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = org,
            onValueChange = { org = it },
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
            onValueChange = { role = it },
            label = "Role",
            showError = !validateRole,
            errorMessage = validateRoleError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        Spacer(modifier = Modifier.height(16.dp))

        UploadFiles()

        Button(
            onClick = { submit(title, testimonials, userName, role, org) },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Submit", fontSize = 16.sp)
        }
    }
}