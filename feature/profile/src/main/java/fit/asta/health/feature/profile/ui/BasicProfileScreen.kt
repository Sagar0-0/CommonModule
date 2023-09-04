package fit.asta.health.feature.profile.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.auth.model.domain.User
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.designsystem.component.AstaValidatedTextField
import fit.asta.health.designsystem.component.AstaValidatedTextFieldType
import fit.asta.health.feature.auth.util.GoogleSignIn
import fit.asta.health.feature.auth.util.PhoneSignIn
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR

@Composable
fun BasicProfileScreen(
    user: User,
    checkReferralCodeState: UiState<CheckReferralDTO>,
    createBasicProfileState: UiState<Boolean>,
    autoFetchedReferralCode: String,
    onEvent: (BasicProfileEvent) -> Unit
) {
    val context = LocalContext.current
    var profileImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            profileImageUri = uri
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
        val gender by rememberSaveable {
            mutableStateOf("1")
        }
        var ref by rememberSaveable {
            mutableStateOf(autoFetchedReferralCode)
        }

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    imagePickerLauncher.launch("image/*")
                }
        ) {
            if (profileImageUri == null && user.photoUrl == null) {
                Image(
                    painter = painterResource(id = DrawR.drawable.placeholder_tag),
                    contentDescription = "Profile"
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = if (profileImageUri != null) {
                            profileImageUri
                        } else {
                            user.photoUrl
                        },
                        placeholder = painterResource(id = DrawR.drawable.ic_person)
                    ),
                    contentDescription = "Profile"
                )
            }
        }


        AstaValidatedTextField(
            label = StringR.string.name,
            value = name,
            onValueChange = { name = it })
        Crossfade(targetState = user.email, label = "") { mail ->
            if (mail.isNullOrEmpty()) {
                GoogleSignIn(
                    StringR.string.link_with_google_account
                ) {
                    onEvent(BasicProfileEvent.Link(it))
                }
            } else {
                Text(email)
            }
        }

        Crossfade(targetState = user.phoneNumber, label = "") { ph ->
            if (ph.isNullOrEmpty()) {
                PhoneSignIn {
                    onEvent(BasicProfileEvent.Link(it))
                }
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
                        onEvent(BasicProfileEvent.ResetCodeState)
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
                        onClick = { onEvent(BasicProfileEvent.CheckReferralCode(ref)) }
                    ) {
                        Text(text = "Check")
                    }
                }
            }
        }

        Button(
            enabled = checkReferralCodeState is UiState.Success || checkReferralCodeState is UiState.Idle,
            onClick = {
                onEvent(
                    BasicProfileEvent.CreateBasicProfile(
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
                )
            }
        ) {
            when (createBasicProfileState) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UiState.Success -> {
                    LaunchedEffect(Unit) {
                        onEvent(BasicProfileEvent.NavigateToHome)
                    }
                }

                is UiState.Error -> {
                    LaunchedEffect(Unit) {
                        Toast.makeText(
                            context,
                            "Can not create profile, try again!",
                            Toast.LENGTH_SHORT
                        ).show()
                        onEvent(BasicProfileEvent.ResetCreateProfileState)
                    }
                }

                is UiState.Idle -> {
                    Text(text = "Create")
                }
            }
        }

    }
}
