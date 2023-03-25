@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.createprofile.view.components

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.ui.components.PrimaryButton
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.UiString
import fit.asta.health.profile.view.PrivacyStatement
import fit.asta.health.profile.view.UserCircleImage
import fit.asta.health.profile.view.UserConsent
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.testimonials.view.components.ValidatedTextField
import fit.asta.health.testimonials.view.create.getOneUrl
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3Api
@Composable
fun DetailsContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventNext: (() -> Unit)? = null,
    onSkipEvent: (Int) -> Unit,
) {

    val focusManager = LocalFocusManager.current

    val name by viewModel.name.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val userImg by viewModel.userImg.collectAsStateWithLifecycle()

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
                url = getOneUrl(localUrl = userImg.localUrl, remoteUrl = userImg.url)
            ) {
                imgLauncher.launch("image/*")
                Log.d("validate", "User Image URL -> ${userImg.url} and ${userImg.localUrl}")
            }

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
                label = "E-mail"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            PrivacyStatement()

            Spacer(modifier = Modifier.height(spacing.medium))

            UserConsent()

            Spacer(modifier = Modifier.height(spacing.medium))

            PrimaryButton(text = "Next", modifier = Modifier.fillMaxWidth(), event = eventNext)

            Spacer(modifier = Modifier.height(spacing.small))

            SkipPage(onSkipEvent)

            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }


}

@Composable
fun SkipPage(onSkipEvent: (Int) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        ClickableText(
            text = AnnotatedString(
                text = "Skip", spanStyle = SpanStyle(color = Color(0xff4d4d4d))
            ), onClick = onSkipEvent, style = MaterialTheme.typography.bodyLarge
        )
    }
}