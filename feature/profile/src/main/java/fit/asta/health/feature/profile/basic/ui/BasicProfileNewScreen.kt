package fit.asta.health.feature.profile.basic.ui

import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.rounded.CameraEnhance
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.firebase.auth.AuthCredential
import fit.asta.health.auth.model.domain.User
import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.remote.model.GenderCode
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.textfield.AppTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.auth.util.GoogleSignIn
import fit.asta.health.feature.auth.util.PhoneSignIn
import fit.asta.health.feature.profile.utils.REFERRAL_LENGTH
import fit.asta.health.resources.drawables.R
import fit.asta.otpfield.OTPInput
import fit.asta.otpfield.configuration.OTPCellConfiguration
import fit.asta.otpfield.configuration.OTPConfigurations

@Preview("Light Button")
@Preview(
    name = "Dark Button", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true
)
@Composable
fun BasicProfilePreview() {
    AppTheme {
        BasicProfileNewScreen(
            checkReferralCodeState = UiState.Success(CheckReferralDTO()),
            linkAccountState = UiState.Success(User()),
            createBasicProfileState = UiState.Success(PutResponse()),
            autoFetchedReferralCode = "",
            onEvent = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicProfileNewScreen(
    user: User = User(),
    checkReferralCodeState: UiState<CheckReferralDTO>,
    linkAccountState: UiState<User>,
    createBasicProfileState: UiState<PutResponse>,
    autoFetchedReferralCode: String,
    onEvent: (BasicProfileEvent) -> Unit,
) {
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf(user.name ?: "") }
    var referralCode by rememberSaveable { mutableStateOf(autoFetchedReferralCode) }
    var isReferralChanged by rememberSaveable { mutableStateOf(false) }
    var genderCode by rememberSaveable { mutableIntStateOf(GenderCode.Other.gender) }
    val phone by rememberSaveable { mutableStateOf(user.phoneNumber ?: "") }
    val email by rememberSaveable { mutableStateOf(user.email ?: "") }

    LaunchedEffect(referralCode) {
        if (referralCode.length == REFERRAL_LENGTH && isReferralChanged) {
            isReferralChanged = false
            onEvent(BasicProfileEvent.CheckReferralCode(referralCode))
        }
    }

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
            AppTopBar(title = "Create Basic Profile", backIcon = null)
        }
    ) { paddingValues ->
        when (createBasicProfileState) {
            is UiState.Loading -> {
                AppDotTypingAnimation()
            }

            is UiState.Success -> {
                LaunchedEffect(Unit) {
                    onEvent(BasicProfileEvent.NavigateToHome)
                }
            }

            is UiState.NoInternet -> {
                AppInternetErrorDialog {
                    onEvent(BasicProfileEvent.ResetCreateProfileState)
                    onEvent(
                        BasicProfileEvent.CreateBasicProfile(
                            BasicProfileDTO(
                                uid = user.uid,
                                gmailPic = user.photoUrl,
                                name = name,
                                gen = genderCode,
                                mail = email,
                                ph = phone,
                                refCode = referralCode
                            )
                        )
                    )
                }
            }

            is UiState.ErrorRetry -> {
                AppErrorScreen(
                    text = createBasicProfileState.resId.toStringFromResId()
                ) {
                    onEvent(BasicProfileEvent.ResetCreateProfileState)
                }
            }

            is UiState.ErrorMessage -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        createBasicProfileState.resId.toStringFromResId(context),
                        Toast.LENGTH_SHORT
                    ).show()
                    onEvent(BasicProfileEvent.ResetCreateProfileState)
                }
            }

            else -> {}
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
        ) {
            ProfileImageUi(
                profileImageUri = profileImageUri,
                googlePicUrl = user.photoUrl,
                onClick = { imagePickerLauncher.launch("image/*") }
            )

            UsernameUi(name) { newName ->
                name = newName
            }

            GenderUi(genderCode) { newGender ->
                genderCode = newGender
            }

            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            EmailUi(user.email) { cred ->
                onEvent(BasicProfileEvent.Link(cred))
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            PhoneUi(
                phoneNumber = phone,
                isFailed = linkAccountState is UiState.ErrorMessage,
                onResetLinkState = {
                    onEvent(BasicProfileEvent.ResetLinkAccountState)
                },
                onLinkAccount = { cred ->
                    onEvent(BasicProfileEvent.Link(cred))
                }
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            ReferralUi(
                refCode = referralCode,
                checkReferralState = checkReferralCodeState,
                onApplyReferralCode = {
                    if (referralCode.length == REFERRAL_LENGTH && isReferralChanged) {
                        isReferralChanged = false
                        onEvent(BasicProfileEvent.CheckReferralCode(referralCode))
                    }
                },
                resetCodeState = {
                    onEvent(BasicProfileEvent.ResetCodeState)
                }
            ) {
                referralCode = it
                isReferralChanged = true
            }

            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            CreateButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2),
                enabled = (checkReferralCodeState is UiState.Success || checkReferralCodeState is UiState.Idle) && name.isNotEmpty(),
                text = "Create your Profile",
            ) {
                onEvent(
                    BasicProfileEvent.CreateBasicProfile(
                        BasicProfileDTO(
                            uid = user.uid,
                            gmailPic = user.photoUrl,
                            name = name,
                            gen = genderCode.toInt(),
                            mail = email,
                            ph = phone,
                            refCode = referralCode
                        )
                    )
                )
            }

            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
        }
    }

}

@Composable
fun GenderUi(gender: Int, onValueChange: (Int) -> Unit) {
    val genders = linkedMapOf(
        "Male" to GenderCode.Male.gender,
        "Female" to GenderCode.Female.gender,
        "Prefer not to say" to GenderCode.Other.gender
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        genders.entries.forEach { entry ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AppRadioButton(selected = gender == entry.value) {
                    onValueChange(entry.value)
                }
                Spacer(modifier = Modifier.padding(AppTheme.spacing.level0))
                TitleTexts.Level2(text = entry.key)
            }
        }

    }
}

@Composable
fun ProfileImageUi(
    modifier: Modifier = Modifier,
    profileImageUri: Uri? = null,
    googlePicUrl: String? = null,
    onClick: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AppTheme.spacing.level2)
    ) {
        val (image, button) = createRefs()

        if (profileImageUri == null && googlePicUrl == null) {
            AppLocalImage(
                modifier = Modifier
                    .size(AppTheme.boxSize.level8)
                    .clip(CircleShape)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "Profile"
            )
        } else if (profileImageUri != null) {
            AppNetworkImage(
                modifier = Modifier
                    .size(AppTheme.boxSize.level8)
                    .clip(CircleShape)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                model = profileImageUri,
                contentDescription = "Profile"
            )
        } else {
            AppNetworkImage(
                modifier = Modifier
                    .size(AppTheme.boxSize.level8)
                    .clip(CircleShape)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                model = googlePicUrl,
                contentDescription = "Profile"
            )
        }

        AppIconButton(
            imageVector = Icons.Rounded.CameraEnhance,
            modifier = Modifier
                .size(AppTheme.buttonSize.level6)
                .constrainAs(button) {
                    bottom.linkTo(image.bottom)
                    end.linkTo(image.end)
                },
            onClick = onClick
        )
    }
}

@Composable
fun UsernameUi(name: String, onValueChange: (String) -> Unit) {
    val focusRequester = remember { FocusRequester() }

    AppTextField(
        modifier = Modifier
            .padding(AppTheme.spacing.level2)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = name,
        singleLine = true,
        leadingIcon = Icons.Default.Person,
        onValueChange = onValueChange,
        label = "Name",
        keyboardActions = KeyboardActions(
            onDone = {
                focusRequester.freeFocus()
            }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        appTextFieldType = AppTextFieldValidator(AppTextFieldType.Custom(maxSize = 20))
    )
}

@Composable
fun EmailUi(gmail: String?, onClick: (AuthCredential) -> Unit) {
    if (gmail.isNullOrEmpty()) {
        GoogleSignIn(
            modifier = Modifier
                .padding(AppTheme.spacing.level2)
                .fillMaxWidth()
                .height(AppTheme.buttonSize.level6),
            textId = fit.asta.health.resources.strings.R.string.link_with_google_account
        ) { cred ->
            onClick(cred)
        }
    } else {
        Row(
            modifier = Modifier
                .padding(AppTheme.spacing.level2)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIcon(imageVector = Icons.Rounded.Email)
            Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
            TitleTexts.Level4(
                modifier = Modifier.weight(1f),
                text = gmail,
                color = AppTheme.colors.onSurface
            )
            AppIcon(imageVector = Icons.Default.Verified)
        }
    }
}

@Composable
fun PhoneUi(
    phoneNumber: String?,
    isFailed: Boolean,
    onResetLinkState: () -> Unit,
    onLinkAccount: (AuthCredential) -> Unit
) {
    if (phoneNumber.isNullOrEmpty()) {
        PhoneSignIn(
            failed = isFailed,
            resetFailedState = {
                onResetLinkState()

            }) { cred ->
            onLinkAccount(cred)
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIcon(imageVector = Icons.Rounded.Phone)
            Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
            TitleTexts.Level4(
                modifier = Modifier.weight(1f),
                text = phoneNumber, color = AppTheme.colors.onSurface
            )
            AppIcon(imageVector = Icons.Default.Verified)
        }
    }
}

@Composable
fun ReferralUi(
    refCode: String,
    checkReferralState: UiState<CheckReferralDTO>,
    onApplyReferralCode: () -> Unit,
    resetCodeState: () -> Unit,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current
    Crossfade(targetState = checkReferralState, label = "") { checkReferralCodeState ->
        when (checkReferralCodeState) {
            is UiState.Idle -> {
                val focusRequester = remember { FocusRequester() }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level2)
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = refCode,
                        onValueChange = onValueChange,
                        placeholder = {
                            CaptionTexts.Level4(text = "Enter your Referral Code")
                        },
                        keyboardActions = KeyboardActions(onDone = {
                            focusRequester.freeFocus()
                        }),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                        )
                    )
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                    VerifyButton(text = "Apply Referral Code") {
                        onApplyReferralCode()
                    }
                }
            }

            is UiState.Loading -> {
                AppDotTypingAnimation()
            }

            is UiState.Success -> {

                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        "YAY! You just got referred!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Row(Modifier.fillMaxWidth()) {
                    AppNetworkImage(
                        modifier = Modifier.clip(CircleShape),
                        model = checkReferralCodeState.data.pic,
                    )
                    TitleTexts.Level2(text = checkReferralCodeState.data.name)
                }
            }

            is UiState.ErrorMessage -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        checkReferralCodeState.resId.toStringFromResId(context),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            is UiState.NoInternet -> {
                AppInternetErrorDialog {
                    resetCodeState()
                }
            }

            is UiState.ErrorRetry -> {
                AppInternetErrorDialog {
                    resetCodeState()
                }
            }
        }
    }
}

@Composable
fun CreateButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    text: String = "",
    onClick: () -> Unit = {},
) {
    AppFilledButton(enabled = enabled, textToShow = text, modifier = modifier, onClick = onClick)
}

@Composable
fun VerifyButton(text: String, onClick: () -> Unit = {}) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        AppOutlinedButton(
            textToShow = text,
            onClick = onClick,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = AppTheme.colors.primary),
            border = BorderStroke(color = AppTheme.colors.primary, width = 2.dp)
        )
    }
}

@Preview
@Composable
fun OTPBlock() {

    var otpValue: String by remember { mutableStateOf("") }

    val defaultConfig = OTPCellConfiguration.withDefaults()

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        OTPInput(
            value = otpValue,
            onValueChange = { newValue, isValid ->
                otpValue = newValue
                if (isValid) {
                    // Validate the value here...
                }
            },
            /* when the value is 1111, all cells will use errorCellConfig */
            isValueInvalid = otpValue == "111111",
            configurations = OTPConfigurations.withDefaults(
                cellsCount = 6,
                emptyCellConfig = defaultConfig,
                filledCellConfig = defaultConfig,
                activeCellConfig = defaultConfig.copy(
                    borderColor = AppTheme.colors.onSurface, borderWidth = AppTheme.elevation.level1
                ),
                errorCellConfig = defaultConfig.copy(
                    borderColor = AppTheme.colors.error, borderWidth = AppTheme.elevation.level1
                ),
                placeHolder = "*",
                cellModifier = Modifier
                    .padding(horizontal = AppTheme.spacing.level0)
                    .size(AppTheme.customSize.level6),
            ),
        )
    }
}