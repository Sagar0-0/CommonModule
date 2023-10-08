package fit.asta.health.feature.profile.basic.ui

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.auth.model.domain.User
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.molecular.textfield.AstaValidatedTextField
import fit.asta.health.designsystem.molecular.textfield.AstaValidatedTextFieldType
import fit.asta.health.feature.auth.util.GoogleSignIn
import fit.asta.health.feature.auth.util.PhoneSignIn
import fit.asta.health.feature.profile.utils.REFERRAL_LENGTH
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicProfileScreen(
    user: User = User(),
    checkReferralCodeState: UiState<CheckReferralDTO>,
    linkAccountState: UiState<User>,
    createBasicProfileState: UiState<Boolean>,
    autoFetchedReferralCode: String,
    onEvent: (BasicProfileEvent) -> Unit,
) {
    Log.d("TAG", "BasicProfileScreen: $user")
    val context = LocalContext.current
    var profileImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                profileImageUri = it
            }
        }

    AppScaffold(
        topBar = {
            AppTopBar(
                backIcon = null,
                title = "Create a Profile"
            )
        }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .verticalScroll(rememberScrollState())
        ) {
            var name by rememberSaveable {
                mutableStateOf(user.name ?: "")
            }
            val email by rememberSaveable {
                mutableStateOf("")
            }
            val phone by rememberSaveable {
                mutableStateOf("")
            }
            val gender by rememberSaveable {
                mutableStateOf("1")
            }
            var ref by rememberSaveable {
                mutableStateOf(autoFetchedReferralCode)
            }
            var refChanged by rememberSaveable {
                mutableStateOf(false)
            }

            LaunchedEffect(ref) {
                if (ref.length == REFERRAL_LENGTH && refChanged) {
                    refChanged = false
                    onEvent(BasicProfileEvent.CheckReferralCode(ref))
                }
            }

            if (profileImageUri == null && user.photoUrl == null) {
                Surface(
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .size(AppTheme.boxSize.level7)
                ) {
                    Image(
                        painter = painterResource(id = DrawR.drawable.ic_person),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop
                    )
                }
            } else {
                Surface(
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .size(AppTheme.boxSize.level7)
                ) {
                    if (profileImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(profileImageUri),
                            contentDescription = "Profile",
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(user.photoUrl),
                            contentDescription = "Profile",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }



            AstaValidatedTextField(
                label = StringR.string.name,
                value = name.ifEmpty { user.name ?: "" },
                onValueChange = { s -> name = s }
            )
            Crossfade(
                targetState = user.email,
                label = ""
            ) { mail ->
                if (mail.isNullOrEmpty()) {
                    GoogleSignIn(
                        StringR.string.link_with_google_account
                    ) { cred ->
                        onEvent(BasicProfileEvent.Link(cred))
                    }
                } else {
                    Row {
                        Text(user.email ?: email)
                        Icon(imageVector = Icons.Default.Verified, contentDescription = "")
                    }
                }
            }

            Crossfade(targetState = user.phoneNumber, label = "") { ph ->
                if (ph.isNullOrEmpty()) {
                    PhoneSignIn(
                        failed = linkAccountState is UiState.ErrorMessage,
                        resetFailedState = {
                            onEvent(BasicProfileEvent.ResetLinkAccountState)
                        }) { cred ->
                        onEvent(BasicProfileEvent.Link(cred))
                    }
                } else {
                    Row {
                        Text("Phone: " + (user.phoneNumber ?: phone))
                        Icon(imageVector = Icons.Default.Verified, contentDescription = "")
                    }
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
                        Row(Modifier.fillMaxWidth()) {
                            Image(
                                modifier = Modifier.clip(CircleShape),
                                painter = rememberAsyncImagePainter(
                                    model = state.data.data!!.pic,
                                    placeholder = painterResource(id = DrawR.drawable.ic_person)
                                ), contentDescription = "Profile"
                            )
                            Text(text = state.data.data!!.name)
                        }
                    }

                    is UiState.ErrorMessage -> {
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
                        Row {
                            AstaValidatedTextField(
                                type = AstaValidatedTextFieldType.Default(
                                    REFERRAL_LENGTH,
                                    REFERRAL_LENGTH
                                ),
                                label = StringR.string.refer_code,
                                value = ref,
                                onValueChange = { str ->
                                    ref = str
                                    refChanged = true
                                }
                            )

                            Button(
                                enabled = ref.length == REFERRAL_LENGTH,
                                onClick = {
                                    refChanged = false
                                    onEvent(BasicProfileEvent.CheckReferralCode(ref))
                                }
                            ) {
                                Text(text = "Check")
                            }
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

                    is UiState.ErrorMessage -> {
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

                    else -> {}
                }
            }

        }
    }
}
