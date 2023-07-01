package fit.asta.health.feedback.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.feedback.view.components.OnlyTextFieldCard
import fit.asta.health.feedback.view.components.RatingCard
import fit.asta.health.feedback.view.components.VerticalRadioBttnCard
import fit.asta.health.feedback.view.components.WelcomeCard
import fit.asta.health.testimonials.view.create.UploadFiles

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionFeedback() {

    Scaffold(content = {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(it)
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
        ) {

            Spacer(modifier = Modifier.height(spacing.medium))

            WelcomeCard()

            Spacer(modifier = Modifier.height(spacing.medium))

            RatingCard(
                cardTitle = "How was you online session experience?",
                textFieldTitle = "Do you like to tell us to improve?"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            RatingCard(
                cardTitle = "How did you feel after the session? ",
                textFieldTitle = "Do you like to tell us to improve?"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            VerticalRadioBttnCard()

            Spacer(modifier = Modifier.height(spacing.medium))

            OnlyTextFieldCard()

            Spacer(modifier = Modifier.height(spacing.medium))

            UploadFiles(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium)
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            Text(
                text = "By submitting review you give us consent to publish your review in our app. ",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium)
            )

            Spacer(modifier = Modifier.height(spacing.small))

            SubmitButton(text = "Update")

            Spacer(modifier = Modifier.height(spacing.small))
        }
    }, topBar = {
        TopAppBar(title = {
            Text(
                text = "Feedback",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
        }, navigationIcon = {
            Icon(Icons.Outlined.NavigateBefore, "back", tint = MaterialTheme.colorScheme.primary)
        })
    })
}


@Composable
fun SubmitButton(
    text: String,
    onClick: (() -> Unit)? = null,
) {
    onClick?.let {
        Button(
            onClick = it,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.medium),
            shape = RoundedCornerShape(spacing.extraSmall),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
            )
        }
    }
}