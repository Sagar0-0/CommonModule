@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.createprofile.view

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.R
import fit.asta.health.common.ui.components.PrimaryButton
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.imageSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.UiString
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.testimonials.view.components.ValidatedTextField
import fit.asta.health.testimonials.view.create.getOneUrl
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3Api
@Composable
fun DetailsCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventNext: (() -> Unit)? = null,
    onSkipEvent: (Int) -> Unit,
) {

    val focusManager = LocalFocusManager.current

    val name by viewModel.name.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val userImg by viewModel.userImg.collectAsStateWithLifecycle()
    val areInputsValid by viewModel.areDetailsInputsValid.collectAsStateWithLifecycle()

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

            UserCircleImage(
                url = getOneUrl(localUrl = userImg.localUrl, remoteUrl = userImg.url),
                onClick = {
                    imgLauncher.launch("image/*")
                    Log.d(
                        "validate",
                        "User Image URL -> URL -> ${userImg.url} and localURL -> ${userImg.localUrl}"
                    )
                })

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Name*",
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(spacing.extraSmall))

            ValidatedTextField(
                value = name.value,
                onValueChange = { viewModel.onEvent(ProfileEvent.OnNameChange(name = it)) },
                showError = name.error !is UiString.Empty,
                errorMessage = name.error,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                label = "Name",
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "E-mail*",
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(spacing.extraSmall))

            ValidatedTextField(
                value = email.value,
                onValueChange = { viewModel.onEvent(ProfileEvent.OnEmailChange(email = it)) },
                showError = email.error !is UiString.Empty,
                errorMessage = email.error,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                label = "E-mail",
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            PrivacyStatement()

            Spacer(modifier = Modifier.height(spacing.medium))

            UserConsent()

            Spacer(modifier = Modifier.height(spacing.medium))

            PrimaryButton(
                text = "Next",
                modifier = Modifier.fillMaxWidth(),
                event = eventNext,
                enableButton = areInputsValid
            )

            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }


}


@Composable
fun PrivacyStatement() {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.privacy),
                contentDescription = null,
                modifier = Modifier.size(imageSize.extraMedium)
            )
        }
        Spacer(modifier = Modifier.width(spacing.medium))
        Column {
            Text(
                text = "Privacy Statement",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xDE000000)
            )
            Spacer(modifier = Modifier.height(spacing.extraSmall))
            Text(
                text = "We value your privacy. We are committed to protecting your privacy and ask for your consent for the use of your personal health information as required during you health care.",
                color = Color(0xDE000000),
                style = MaterialTheme.typography.bodySmall,
                softWrap = true
            )
        }
    }
}

@Composable
fun UserConsent() {

    val checkedState = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                modifier = Modifier.size(imageSize.extraMedium)
            )
        }
        Spacer(modifier = Modifier.width(spacing.medium))
        Column {
            Text(
                text = "I CONSENT TO THE USE OF MY PERSONAL HEALTH INFORMATION AS REQUIRED DURING YOUR HEALTH CARE.",
                color = Color(0xFF375369),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun UserCircleImage(url: String, onClick: () -> Unit) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(start = spacing.extraSmall1, end = spacing.extraSmall1)
    ) {

        Image(
            painter = if (url.isEmpty()) {
                painterResource(id = R.drawable.userphoto)
            } else {
                rememberAsyncImagePainter(model = url)
            },
            contentDescription = null,
            modifier = Modifier
                .size(customSize.extraLarge5)
                .clip(shape = CircleShape)

        )

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        ) {
            Box {
                IconButton(onClick = onClick) {

                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        modifier = Modifier
                            .size(customSize.largeSmall)
                            .clip(shape = CircleShape)
                    )

                }

            }
        }

    }
}
