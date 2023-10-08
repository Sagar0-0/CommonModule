@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.feature.profile.create.view

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.utils.UiString
import fit.asta.health.common.utils.getOneUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.molecular.AppTextFieldValidate
import fit.asta.health.designsystem.molecular.button.AppCheckBoxButton
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.create.vm.ProfileEvent
import fit.asta.health.feature.profile.show.vm.ProfileViewModel
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@ExperimentalMaterial3Api
@Composable
fun DetailsCreateScreen(
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
                .padding(horizontal = AppTheme.spacing.level3)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            UserCircleImage(url = getOneUrl(
                localUrl = userImage.localUrl, remoteUrl = userImage.url
            ), onUserProfileSelection = {
                imgLauncher.launch("image/*")
            }, onProfilePicClear = {
                viewModel.onEvent(ProfileEvent.OnProfilePicClear)
            })
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            AppTextFieldValidate(
                value = nameState.value,
                onValueChange = { viewModel.onEvent(ProfileEvent.OnNameChange(name = it)) },
                isError = nameState.error !is UiString.Empty,
                errorMessage = nameState.error,
                label = stringResource(R.string.name_profile_creation),
                singleLine = true,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            AppTextFieldValidate(
                value = emailState.value,
                onValueChange = { viewModel.onEvent(ProfileEvent.OnEmailChange(email = it)) },
                isError = emailState.error !is UiString.Empty,
                errorMessage = emailState.error,
                label = stringResource(R.string.email_profile_creation),
                singleLine = true,
                imeAction = ImeAction.Done,
                modifier = Modifier.focusRequester(focusRequester = focusRequester),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            PrivacyAndUserConsent()
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            AppFilledButton(
                textToShow = stringResource(R.string.next_button),
                onClick = eventNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level5),
                shape = CircleShape
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
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
            AppIcon(
                imageVector = Icons.Rounded.PrivacyTip,
                contentDescription = "App Privacy",
                tint = AppTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(AppTheme.spacing.level3))
            Column {
                TitleTexts.Level2(text = stringResource(R.string.privacy_statement_title))
                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                BodyTexts.Level2(text = stringResource(R.string.privacy_statement))
            }
        }
        Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            AppCheckBoxButton(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                modifier = Modifier.size(AppTheme.imageSize.level3)
            )
            Spacer(modifier = Modifier.width(AppTheme.spacing.level3))
            BodyTexts.Level2(text = stringResource(R.string.user_consent))
        }
    }
}

@Composable
fun UserCircleImage(
    url: String,
    onUserProfileSelection: () -> Unit,
    onProfilePicClear: () -> Unit,
) {

    val isImgNotAvail = url.isEmpty()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(horizontal = AppTheme.spacing.level2)
    ) {
        AppNetworkImage(
            model = url,
            contentDescription = "User Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(AppTheme.customSize.level12)
                .clip(CircleShape)
                .border(
                    border = BorderStroke(
                        width = 4.dp, color = AppTheme.colors.primary
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
fun DeleteImageButton(onProfilePicClear: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start, modifier = modifier
    ) {
        AppIconButton(
            imageVector = Icons.Filled.Delete,
            iconTint = AppTheme.colors.error,
            onClick = onProfilePicClear,
        )
    }
}

@Composable
fun EditProfileImageButton(
    isImgNotAvail: Boolean,
    onUserProfileSelection: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val editIcon = if (isImgNotAvail) Icons.Rounded.AddAPhoto else Icons.Rounded.Edit
    Row(
        horizontalArrangement = Arrangement.End, modifier = modifier
    ) {
        AppIconButton(
            onClick = onUserProfileSelection,
            imageVector = editIcon,
            iconTint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        )
    }
}