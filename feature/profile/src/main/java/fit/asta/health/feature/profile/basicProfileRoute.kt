package fit.asta.health.feature.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.designsystem.component.AstaValidatedTextField
import fit.asta.health.designsystem.component.AstaValidatedTextFieldType
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.feature.auth.util.GoogleSignIn
import fit.asta.health.feature.auth.util.PhoneSignIn
import fit.asta.health.feature.profile.vm.BasicProfileViewModel
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR

const val BASIC_PROFILE_GRAPH_ROUTE = "graph_basic_profile"

fun NavController.navigateToBasicProfile(navOptions: NavOptions? = null) {
    if (navOptions == null) {
        this.navigate(BASIC_PROFILE_GRAPH_ROUTE) {
            popUpToTop(this@navigateToBasicProfile)
        }
    } else {
        this.navigate(BASIC_PROFILE_GRAPH_ROUTE, navOptions)
    }
}

fun NavGraphBuilder.basicProfileRoute() {
    composable(BASIC_PROFILE_GRAPH_ROUTE) {
        val context = LocalContext.current
        val basicProfileViewModel: BasicProfileViewModel = hiltViewModel()
        var user by remember {
            mutableStateOf(basicProfileViewModel.getUser())
        }
        Log.e("TAG", "basicProfileRoute: $user")

        val checkReferralCodeState by basicProfileViewModel.checkReferralCodeState.collectAsStateWithLifecycle()
        val createBasicProfileState by basicProfileViewModel.createBasicProfileState.collectAsStateWithLifecycle()
        val referralCode by basicProfileViewModel.referralCode.collectAsStateWithLifecycle()
        val linkAccountState by basicProfileViewModel.linkAccountState.collectAsStateWithLifecycle()

        when (linkAccountState) {
            is UiState.Loading -> {
                LoadingAnimation()
            }

            is UiState.Success -> {
                LaunchedEffect(Unit) {
                    user = basicProfileViewModel.getUser()
                }
            }

            else -> {}
        }

        Column(Modifier.verticalScroll(rememberScrollState())) {
            var name by rememberSaveable {
                mutableStateOf(user.name ?: "")
            }
            val email by rememberSaveable {
                mutableStateOf(user.email ?: "")
            }
            val phone by rememberSaveable {
                mutableStateOf(user.phoneNumber ?: "")
            }
            var gender by rememberSaveable {
                mutableStateOf("1")
            }
            var ref by rememberSaveable {
                mutableStateOf(referralCode)
            }

            Image(
                modifier = Modifier.clip(CircleShape),
                painter = rememberAsyncImagePainter(
                    model = user.photoUrl,
                    placeholder = painterResource(id = DrawR.drawable.ic_person)
                ), contentDescription = "Profile"
            )

            AstaValidatedTextField(
                label = StringR.string.name,
                value = name,
                onValueChange = { name = it })
            Crossfade(targetState = user.email, label = "") { mail ->
                if (mail.isNullOrEmpty()) {
                    GoogleSignIn(
                        StringR.string.link_with_google_account,
                        basicProfileViewModel::linkWithCredentials
                    )
                } else {
                    Text(email)
                }
            }

            Crossfade(targetState = user.phoneNumber, label = "") { ph ->
                if (ph.isNullOrEmpty()) {
                    PhoneSignIn(basicProfileViewModel::linkWithCredentials)
                } else {
                    Text(phone)
                }
            }

            Crossfade(targetState = checkReferralCodeState, label = "") { state ->
                when (state) {
                    is UiState.Success -> {
                        LaunchedEffect(Unit) {
                            Toast.makeText(
                                context,
                                "YAY! You just got referred!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        Image(
                            modifier = Modifier.clip(CircleShape),
                            painter = rememberAsyncImagePainter(
                                model = state.data.data!!.pic,
                                placeholder = painterResource(id = DrawR.drawable.ic_person)
                            ), contentDescription = "Profile"
                        )
                    }

                    is UiState.Error -> {
                        LaunchedEffect(Unit) {
                            Toast.makeText(
                                context,
                                "Invalid Refer code",
                                Toast.LENGTH_SHORT
                            ).show()
                            basicProfileViewModel.resetCheckCodeState()
                        }
                    }

                    else -> {
                        AstaValidatedTextField(
                            type = AstaValidatedTextFieldType.Default(10, 10),
                            label = StringR.string.refer_code,
                            value = ref,
                            onValueChange = { str ->
                                ref = str
                            }
                        )
                        Button(
                            enabled = ref.length == 8,
                            onClick = { basicProfileViewModel.checkReferralCode(ref) }
                        ) {
                            Text(text = "Check")
                        }
                    }
                }
            }

            Button(
                enabled = checkReferralCodeState is UiState.Success || checkReferralCodeState is UiState.Idle,
                onClick = {
                    basicProfileViewModel
                        .createBasicProfile(
                            BasicProfileDTO(
                                uid = user.uid,
                                mailUrl = user.photoUrl,
                                name = name,
                                gen = gender.toInt(),
                                mail = email,
                                ph = phone,
                                refCode = ref
                            )
                        )
                }
            ) {
                when (createBasicProfileState) {
                    is UiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is UiState.Success -> {
                        LaunchedEffect(Unit) {
                            basicProfileViewModel.navigateToHome()
                        }
                    }

                    is UiState.Error -> {
                        LaunchedEffect(Unit) {
                            Toast.makeText(
                                context,
                                "Can not create profile, try again!",
                                Toast.LENGTH_SHORT
                            ).show()
                            basicProfileViewModel.resetCreateProfileState()
                        }
                    }

                    is UiState.Idle -> {
                        Text(text = "Create")
                    }
                }
            }

        }
    }
}