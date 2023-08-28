package fit.asta.health.feature.profile

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
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
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.feature.profile.vm.BasicProfileViewModel
import fit.asta.health.resources.drawables.R

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

fun NavGraphBuilder.basicProfileRoute(navigateToHome: () -> Unit) {

    composable(BASIC_PROFILE_GRAPH_ROUTE) {

        val context = LocalContext.current
        val basicProfileViewModel: BasicProfileViewModel = hiltViewModel()
        val user = basicProfileViewModel.getUser()

        LaunchedEffect(Unit) {
            basicProfileViewModel.isProfileAvailable()
        }

        val isProfileAvailable by basicProfileViewModel.isProfileAvailable.collectAsStateWithLifecycle()
        when (isProfileAvailable) {
            is UiState.Loading -> {
                LoadingAnimation()
            }

            is UiState.Success -> {
                LaunchedEffect(Unit) {
                    if ((isProfileAvailable as UiState.Success<Boolean>).data) {
                        basicProfileViewModel.setProfilePresent()
                        navigateToHome()
                    }
                }
            }

            else -> {}
        }

        val checkReferralCodeState by basicProfileViewModel.checkReferralCodeState.collectAsStateWithLifecycle()
        val createBasicProfileState by basicProfileViewModel.createBasicProfileState.collectAsStateWithLifecycle()

        Column {
            var name by rememberSaveable {
                mutableStateOf(user.name ?: "")
            }
            var phone by rememberSaveable {
                mutableStateOf(user.phoneNumber ?: "")
            }
            var gender by rememberSaveable {
                mutableStateOf("1")
            }
            var ref by rememberSaveable {
                mutableStateOf("")
            }
            Image(
                modifier = Modifier.clip(CircleShape),
                painter = rememberAsyncImagePainter(
                    model = user.photoUrl,
                    placeholder = painterResource(id = R.drawable.ic_person)
                ), contentDescription = "Profile"
            )
            TextField(label = { Text(text = "UID") }, value = user.uid, onValueChange = {})
            TextField(label = { Text(text = "Name") }, value = name, onValueChange = { name = it })
            TextField(label = { Text(text = "Gender") },
                value = gender,
                onValueChange = { gender = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                )
            )

            if (user.email == null) {
                //Show Google sign in
            } else {
                TextField(
                    label = { Text(text = "E-mail") },
                    value = user.email ?: "",
                    onValueChange = {})
            }

            if (user.phoneNumber.isNullOrEmpty()) {
                TextField(label = { Text(text = "Phone") }, value = phone, onValueChange = { str ->
                    if (str.length <= 8) phone = str
                })
                Button(onClick = { }) {
                    Text(text = "Verify")
                }
            }

            TextField(label = { Text(text = "Refer code") }, value = ref, onValueChange = { str ->
                if (str.length <= 8) ref = str
            })

            Crossfade(targetState = checkReferralCodeState, label = "") { state ->
                if (state is UiState.Success) {
                    Image(
                        modifier = Modifier.clip(CircleShape),
                        painter = rememberAsyncImagePainter(
                            model = state.data.data!!.pic,
                            placeholder = painterResource(id = R.drawable.ic_person)
                        ), contentDescription = "Profile"
                    )
                } else {
                    Button(
                        enabled = ref.length == 8,
                        onClick = { basicProfileViewModel.checkReferralCode(ref) }
                    ) {
                        when (checkReferralCodeState) {
                            is UiState.Loading -> {
                                CircularProgressIndicator()
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

                            is UiState.Idle -> {
                                Text(text = "Create")
                            }

                            else -> {}
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
                                mail = user.email ?: "",
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
                            navigateToHome()
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