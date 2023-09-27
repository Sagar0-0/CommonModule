package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.designsystemx.AppTheme
import fit.asta.health.resources.drawables.R


@Composable
fun WelcomeCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.appSpacing.medium),
        shape = RoundedCornerShape(AppTheme.appSpacing.small),
        elevation = CardDefaults.cardElevation(AppTheme.appSpacing.extraSmall),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(AppTheme.appSpacing.medium)
        ) {
            Image(
                painter = painterResource(id = R.drawable.feedback1),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.45f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(AppTheme.appSpacing.medium))

            Column {
                Text(
                    text = "Your Feedback will help us to serve you better",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Left
                )

                Spacer(modifier = Modifier.height(AppTheme.appSpacing.small))

                Text(
                    text = "Your feedback is important to us. We read every feedback we get and take it seriously.",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}