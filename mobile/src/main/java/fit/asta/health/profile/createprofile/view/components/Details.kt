package fit.asta.health.profile.createprofile.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import fit.asta.health.profile.view.NextButton
import fit.asta.health.profile.view.PrivacyStatement
import fit.asta.health.profile.view.UserCircleImage
import fit.asta.health.profile.view.UserConsent
import fit.asta.health.testimonials.view.components.ValidatedTextField
import fit.asta.health.ui.spacing
import fit.asta.health.utils.UiString

@ExperimentalMaterial3Api
@Composable
fun DetailsContent(
    eventNext: (() -> Unit)? = null,
    onSkipEvent: (Int) -> Unit,
) {

    Card(shape = MaterialTheme.shapes.medium) {

        var text by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(spacing.medium))

            UserCircleImage()

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Name*",
                    color = Color(0xff132839),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(spacing.extraSmall))

            ValidatedTextField(
                value = text,
                onValueChange = { text = it },
                errorMessage = UiString.Empty,
                modifier = Modifier.padding(horizontal = spacing.medium),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                label = "Name"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "E-mail*",
                    color = Color(0xff132839),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(spacing.extraSmall))

            ValidatedTextField(
                value = text,
                onValueChange = { text = it },
                errorMessage = UiString.Empty,
                modifier = Modifier.padding(horizontal = spacing.medium),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                label = "E-mail"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            PrivacyStatement()

            Spacer(modifier = Modifier.height(spacing.medium))

            UserConsent()

            Spacer(modifier = Modifier.height(spacing.medium))

            NextButton(text = "Next", modifier = Modifier.fillMaxWidth(), event = eventNext)

            Spacer(modifier = Modifier.height(spacing.medium))

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