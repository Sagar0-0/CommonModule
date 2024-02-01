package fit.asta.health.feature.profile.basic.ui

import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.SafetyDivider
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.rounded.CameraEnhance
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.remote.model.GenderCode
import fit.asta.health.data.profile.remote.model.PrimeTypes
import fit.asta.health.data.profile.remote.model.UserProfileImageTypes
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppMultiChoiceSegmentedButtonRow
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
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
            checkReferralCodeState = UiState.Idle,
//                .Success(
//                    CheckReferralDTO(
//                        mail = "mail@gmail.com",
//                        name = "Sagar",
//                        pic = "pic",
//                        prime = true
//                    )
//                ),
            createBasicProfileState = UiState.Idle,
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
    var screenLoading by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(createBasicProfileState, checkReferralCodeState) {
        screenLoading =
            checkReferralCodeState is UiState.Loading || createBasicProfileState is UiState.Loading
    }

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

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2)
                    .verticalScroll(rememberScrollState()),
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
                                refCode = if (checkReferralCodeState is UiState.Success) referralCode else ""
                            )
                        )
                    )
                }
            }

            if (screenLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppTheme.colors.surface.copy(alpha = AppTheme.alphaValues.level2)),
                    contentAlignment = Alignment.Center
                ) {
                    AppDotTypingAnimation()
                }
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

    AppMultiChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth(),
        items = genders.map { entry ->
            {
                Row(
                    modifier = Modifier
                        .padding(AppTheme.spacing.level0),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level0),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppIcon(imageVector = entry.key.icon)
                    TitleTexts.Level3(
                        text = entry.key.name,
                        maxLines = 1
                    )
                }
            }
        },
        checked = gender,
        onCheckedChange = onValueChange
    )

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

    AppOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = name,
        singleLine = true,
        leadingIcon = Icons.Default.Person,
        onValueChange = onValueChange,
        label = "Name",
        appTextFieldType = AppTextFieldValidator(AppTextFieldType.Custom(maxSize = 30))
    )
}

@Composable
fun EmailUi(gmail: String?, onClick: (AuthCredential) -> Unit) {
    if (gmail.isNullOrEmpty()) {
        GoogleSignIn(
            modifier = Modifier
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
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
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
                        color = AppTheme.colors.onSurface,
                        maxLines = 1
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
        // Linking with Phone Button
        // Sign in with Phone Button
        AppOutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.buttonSize.level6),
            onClick = {
                navigateToPhoneAuth()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = null,
                modifier = Modifier.padding(end = AppTheme.spacing.level1),
            )
            // Button Text
            CaptionTexts.Level1(
                text = "Link with Phone Number",
                color = AppTheme.colors.onSurface
            )
        }
    } else {
        AppCard(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
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
                        color = AppTheme.colors.onSurface,
                        maxLines = 1
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
                AppOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = refCode,
                    onValueChange = onValueChange,
                    leadingIcon = Icons.Default.SafetyDivider,
                    trailingIcon = Icons.Default.PersonSearch,
                    onTrailingIconClicked = {
                        onApplyReferralCode()
                    },
                    label = "Referral Code",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                    ),
                    appTextFieldType = AppTextFieldValidator(
                        AppTextFieldType.Custom(
                            REFERRAL_LENGTH,
                            REFERRAL_LENGTH
                        )
                    )
                )
            }

            is UiState.Success -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        "YAY! You just got referred!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                AppCard(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(AppTheme.spacing.level2),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
                    ) {
                        AppNetworkImage(
                            modifier = Modifier
                                .size(AppTheme.imageSize.level4)
                                .clip(CircleShape),
                            model = if (checkReferralCodeState.data.imageType == UserProfileImageTypes.GOOGLE.imageType) checkReferralCodeState.data.pic else getImgUrl(
                                checkReferralCodeState.data.pic
                            ),
                        )
                        TitleTexts.Level3(text = checkReferralCodeState.data.name)
                        AppIcon(
                            imageVector =
                            if (checkReferralCodeState.data.prime == PrimeTypes.PRIME.code) {
                                Icons.Default.WorkspacePremium
                            } else {
                                Icons.Default.VerifiedUser
                            },
                            contentDescription = "Premium User"
                        )
                    }
                }
            }

            is UiState.ErrorMessage -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        checkReferralCodeState.resId.toStringFromResId(context),
                        Toast.LENGTH_SHORT
                    ).show()
                    resetCodeState()
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

            else -> {}
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