@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.createprofile.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.BuildConfig
import fit.asta.health.common.jetpack.getOneUrl
import fit.asta.health.common.ui.components.*
import fit.asta.health.common.ui.components.functional.AppTextFieldValidate
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppDefServerImg
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.imageSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.UiString
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@ExperimentalMaterial3Api
@Composable
fun DetailsCreateScreenDemo(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventNext: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val nameState by viewModel.name.collectAsStateWithLifecycle()
    val emailState by viewModel.email.collectAsStateWithLifecycle()
    val userImage by viewModel.userImg.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }
    val imgLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            viewModel.onEvent(ProfileEvent.OnUserImgChange(url = uri))
        }

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.medium)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(spacing.medium))
            UserCircleImage(url = getOneUrl(
                localUrl = userImage.localUrl, remoteUrl = userImage.url
            ), onUserProfileSelection = {
                imgLauncher.launch("image/*")
            }, onProfilePicClear = {
                viewModel.onEvent(ProfileEvent.OnProfilePicClear)
            })
            Spacer(modifier = Modifier.height(spacing.medium))
            AppTextFieldValidate(
                value = nameState.value,
                onValueChange = { viewModel.onEvent(ProfileEvent.OnNameChange(name = it)) },
                isError = nameState.error !is UiString.Empty,
                errorMessage = nameState.error,
                label = "Name",
                singleLine = true,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
            Spacer(modifier = Modifier.height(spacing.medium))
            AppTextFieldValidate(
                value = emailState.value,
                onValueChange = { viewModel.onEvent(ProfileEvent.OnEmailChange(email = it)) },
                isError = emailState.error !is UiString.Empty,
                errorMessage = emailState.error,
                label = "E-mail",
                singleLine = true,
                imeAction = ImeAction.Done,
                modifier = Modifier.focusRequester(focusRequester = focusRequester),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )
            Spacer(modifier = Modifier.height(spacing.medium))
            PrivacyAndUserConsent()
            Spacer(modifier = Modifier.height(spacing.medium))
            AppButtons.AppStandardButton(
                onClick = eventNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.large),
                enabled = true,
                shape = CircleShape
            ) {
                AppTexts.LabelLarge(text = "Next", color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }
}

@Composable
fun PrivacyAndUserConsent() {

    val checkedState = remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            AppDefaultIcon(
                imageVector = Icons.Rounded.PrivacyTip,
                contentDescription = "App Privacy",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(spacing.medium))
            Column {
                AppTexts.TitleLarge(text = "Privacy Statement")
                Spacer(modifier = Modifier.height(spacing.extraSmall))
                AppTexts.BodySmall(
                    text = "We value your privacy. We are committed to protecting your " + "privacy and ask for your consent for the use of your personal health information " + "as required during you health care."
                )
            }
        }
        Spacer(modifier = Modifier.height(spacing.medium))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            AppButtons.AppCheckBox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                modifier = Modifier.size(imageSize.standard)
            )
            Spacer(modifier = Modifier.width(spacing.medium))
            AppTexts.BodyMedium(text = "I CONSENT TO THE USE OF MY PERSONAL HEALTH INFORMATION AS REQUIRED DURING YOUR HEALTH CARE.")
        }
    }
}

@Composable
fun UserCircleImage(
    url: String,
    onUserProfileSelection: () -> Unit,
    onProfilePicClear: () -> Unit,
) {

    val imageBaseUrl = BuildConfig.BASE_IMAGE_URL
    val isImgNotAvail = url.isEmpty() || url == imageBaseUrl

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(horizontal = spacing.extraSmall1)
    ) {
        AppDefServerImg(
            model = url,
            contentDescription = "User Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(customSize.extraLarge5)
                .clip(CircleShape)
                .border(
                    border = BorderStroke(
                        width = 4.dp, color = MaterialTheme.colorScheme.primary
                    ), shape = CircleShape
                ),
            placeholder = rememberVectorPainter(image = Icons.Filled.Person)
        )
        if (!isImgNotAvail) {
            DeleteImageButton(onProfilePicClear, modifier = Modifier.align(Alignment.TopEnd))
        }
        EditProfileImageButton(
            isImgNotAvail,
            onUserProfileSelection,
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        )
    }
}

@Composable
private fun DeleteImageButton(onProfilePicClear: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start, modifier = modifier
    ) {
        AppButtons.AppIconButton(onClick = onProfilePicClear) {
            AppDefaultIcon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete Image",
                modifier = Modifier.size(customSize.extraLarge),
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun EditProfileImageButton(
    isImgNotAvail: Boolean,
    onUserProfileSelection: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.End, modifier = modifier
    ) {
        AppButtons.AppIconButton(onClick = onUserProfileSelection) {
            val editIcon = if (isImgNotAvail) Icons.Rounded.AddAPhoto else Icons.Rounded.Edit
            AppDefaultIcon(
                imageVector = editIcon,
                contentDescription = "Edit Profile Image",
                modifier = Modifier.size(customSize.extraLarge),
                tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
            )
        }
    }
}