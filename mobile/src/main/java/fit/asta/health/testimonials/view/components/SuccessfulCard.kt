package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.common.ui.components.generic.AppDefCard
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.boxSize
import fit.asta.health.common.ui.theme.cardHeight
import fit.asta.health.common.ui.theme.iconSize
import fit.asta.health.common.ui.theme.spacing


@Composable
fun SuccessfulCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    underReview: Boolean,
) {

    Box(contentAlignment = Alignment.TopCenter) {

        AppDefCard(
            modifier = modifier
                .padding(top = spacing.extraLarge)
                .heightIn(min = cardHeight.large)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.extraLarge3)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.medium),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppTexts.HeadlineMedium(
                        text = "Thank You",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
                Spacer(modifier = Modifier.height(spacing.small))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.medium),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppTexts.BodyMedium(
                        text = if (underReview) {
                            "Your feedback is under review. Please wait few seconds."
                        } else {
                            "Your feedback has been submitted"
                        }, color = MaterialTheme.colorScheme.secondary, textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(spacing.large))
                if (underReview) {
                    LoadingAnimation()
                }
                Spacer(modifier = Modifier.height(spacing.medium))
            }
        }

        Box(
            modifier = Modifier
                .clip(shape = CircleShape)
                .defaultMinSize(minWidth = boxSize.medium, minHeight = boxSize.medium)
                .background(color = Color.Green), contentAlignment = Alignment.Center
        ) {
            AppDefaultIcon(
                imageVector = if (underReview) {
                    Icons.Filled.Reviews
                } else {
                    Icons.Filled.CloudDone
                },
                contentDescription = "Successful Tst Upload",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(iconSize.medium)
            )
        }
    }


}