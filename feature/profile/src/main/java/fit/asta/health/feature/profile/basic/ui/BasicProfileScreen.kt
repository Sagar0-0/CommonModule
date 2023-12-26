package fit.asta.health.feature.profile.basic.ui

import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.rounded.CameraEnhance
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
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
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.auth.util.GoogleSignIn
import fit.asta.health.feature.profile.utils.REFERRAL_LENGTH
import fit.asta.health.resources.drawables.R

@Preview("Light Button")
@Preview(
    name = "Dark Button", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true
)
@Composable
fun BasicProfilePreview() {
    AppTheme {
        BasicProfileScreenUi(
            checkReferralCodeState = UiState.Success(CheckReferralDTO()),
            createBasicProfileState = UiState.Success(PutResponse()),
            autoFetchedReferralCode = "",
            onEvent = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicProfileScreenUi(
    user: User = User(),
    checkReferralCodeState: UiState<CheckReferralDTO>,
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
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

            EmailUi(user.email) { cred ->
                onEvent(BasicProfileEvent.Link(cred))
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            PhoneUi(
                phoneNumber = phone,
                navigateToPhoneAuth = {
                    onEvent(BasicProfileEvent.NavigateToPhoneAuth)
                }
            )

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
                    onEvent(BasicProfileEvent.ResetReferralCodeState)
                }
            ) {
                referralCode = it
                isReferralChanged = true
            }

            CreateButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = AppTheme.spacing.level2,
                        end = AppTheme.spacing.level2,
                        bottom = AppTheme.spacing.level2
                    ),
                enabled = (checkReferralCodeState is UiState.Success || checkReferralCodeState is UiState.Idle) && name.isNotEmpty(),
                text = "Create your Profile",
            ) {
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
    }

}

data class GenderData(
    val name: String,
    val icon: ImageVector
)

@Composable
fun ColumnScope.GenderUi(gender: Int, onValueChange: (Int) -> Unit) {
    val genders = linkedMapOf(
        GenderData("Male", Icons.Default.Male) to GenderCode.Male.gender,
        GenderData("Female", Icons.Default.Female) to GenderCode.Female.gender,
        GenderData("Others", Icons.Default.QuestionMark) to GenderCode.Other.gender
    )

    TitleTexts.Level1(text = "Gender")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        genders.entries.forEach { entry ->
            AppCard(
                colors = CardDefaults.cardColors(
                    containerColor = if (gender == entry.value) {
                        AppTheme.colors.primary
                    } else {
                        AppTheme.colors.primary.copy(alpha = AppTheme.alphaValues.level3)
                    }
                ),
                onClick = {
                    onValueChange(entry.value)
                }
            ) {
                Row(
                    modifier = Modifier
                        .padding(AppTheme.spacing.level1),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppIcon(imageVector = entry.key.icon)
                    TitleTexts.Level2(
                        text = entry.key.name
                    )
                }

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

    AppOutlinedTextField(
        modifier = Modifier
            .padding(horizontal = AppTheme.spacing.level2)
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
        appTextFieldType = AppTextFieldValidator(AppTextFieldType.Custom(maxSize = 30))
    )
}

@Composable
fun EmailUi(gmail: String?, onClick: (AuthCredential) -> Unit) {
    if (gmail.isNullOrEmpty()) {
        GoogleSignIn(
            modifier = Modifier
                .padding(horizontal = AppTheme.spacing.level2)
                .fillMaxWidth()
                .height(AppTheme.buttonSize.level6),
            textId = fit.asta.health.resources.strings.R.string.link_with_google_account
        ) { cred ->
            onClick(cred)
        }
    } else {
        AppCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level2)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
                ) {
                    AppIcon(imageVector = Icons.Rounded.Email)
                    TitleTexts.Level3(
                        modifier = Modifier.weight(1f),
                        text = gmail,
                        color = AppTheme.colors.onSurface
                    )
                }

                AppIcon(
                    imageVector = Icons.Default.Verified
                )
            }
        }

    }
}

@Composable
fun PhoneUi(
    phoneNumber: String?,
    navigateToPhoneAuth: () -> Unit
) {
    if (phoneNumber.isNullOrEmpty()) {
        // Sign in with Phone Button
        AppFilledButton(
            textToShow = "Link with Phone Number",
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.buttonSize.level6),
            leadingIcon = Icons.Default.Phone
        ) {
            navigateToPhoneAuth()
        }
    } else {
        AppCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level2)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
                ) {
                    AppIcon(imageVector = Icons.Rounded.Phone)
                    TitleTexts.Level3(
                        modifier = Modifier.weight(1f),
                        text = phoneNumber,
                        color = AppTheme.colors.onSurface
                    )
                }

                AppIcon(imageVector = Icons.Default.Verified)
            }
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
    Crossfade(
        targetState = checkReferralState,
        label = ""
    ) { checkReferralCodeState ->
        when (checkReferralCodeState) {
            is UiState.Idle -> {
                val focusRequester = remember { FocusRequester() }

                AppOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level2)
                        .focusRequester(focusRequester),
                    value = refCode,
                    onValueChange = onValueChange,
                    trailingIcon = Icons.Default.ArrowForwardIos,
                    onTrailingIconClicked = {
                        onApplyReferralCode()
                    },
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level2),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
                ) {
                    AppNetworkImage(
                        modifier = Modifier.clip(CircleShape),
                        model = checkReferralCodeState.data.pic,
                    )
                    TitleTexts.Level3(text = checkReferralCodeState.data.name)
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