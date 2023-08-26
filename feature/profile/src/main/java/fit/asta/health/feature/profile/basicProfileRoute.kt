package fit.asta.health.feature.profile

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.model.domain.toUser
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.feature.profile.vm.BasicProfileViewModel
import fit.asta.health.resources.drawables.R

internal const val BASIC_PROFILE_GRAPH_ROUTE = "graph_basic_profile"

fun NavController.navigateToBasicProfile(user: User, navOptions: NavOptions? = null) {
    val json = user.toString()
    if (navOptions != null) {
        this.navigate("$BASIC_PROFILE_GRAPH_ROUTE/$json", navOptions)
    } else {
        this.navigate("$BASIC_PROFILE_GRAPH_ROUTE/$json") {
            popUpToTop(this@navigateToBasicProfile)
        }
    }
}

fun NavGraphBuilder.basicProfileRoute(navigateToHome: () -> Unit) {

    composable("$BASIC_PROFILE_GRAPH_ROUTE/{user}") {
        val user = it.arguments?.getString("user")!!.toUser()

        val context = LocalContext.current
        val basicProfileViewModel: BasicProfileViewModel = hiltViewModel()
        val checkReferralCodeState by basicProfileViewModel.checkReferralCodeState.collectAsStateWithLifecycle()
        val createBasicProfileState by basicProfileViewModel.createBasicProfileState.collectAsStateWithLifecycle()

        Column {
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
            TextField(value = user.uid, onValueChange = {})
            TextField(value = user.email ?: "", onValueChange = {})
            TextField(value = user.name ?: "", onValueChange = {})
            TextField(value = user.phoneNumber ?: "", onValueChange = {})
            TextField(value = ref, onValueChange = { str ->
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
                                url = user.photoUrl.toString(),
                                name = user.name!!,
                                gen = "",
                                mail = user.email,
                                ph = user.phoneNumber,
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
                            basicProfileViewModel.resetCreateprofileState()
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