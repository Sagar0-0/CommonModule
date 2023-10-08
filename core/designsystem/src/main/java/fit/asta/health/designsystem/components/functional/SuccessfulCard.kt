package fit.asta.health.designsystem.components.functional

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@Composable
fun SuccessfulCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    underReview: Boolean,
) {

    Box(contentAlignment = Alignment.TopCenter) {
        AppCard(
            modifier = modifier
                .padding(top = AppTheme.spacing.level7)
                .heightIn(min = AppTheme.cardHeight.level3)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.spacing.level9)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level3),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HeadingTexts.Level1(
                        text = "Thank You",
                        color = AppTheme.colors.onPrimaryContainer,
                    )
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level3),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CaptionTexts.Level2(
                        text = if (underReview) {
                            "Your feedback is under review. Please wait few seconds."
                        } else {
                            "Your feedback has been submitted"
                        },
                        color = AppTheme.colors.secondary,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level5))
                if (underReview) {
                    AppDotTypingAnimation()
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            }
        }

        Box(
            modifier = Modifier
                .clip(shape = CircleShape)
                .defaultMinSize(
                    minWidth = AppTheme.boxSize.level7,
                    minHeight = AppTheme.boxSize.level7
                )
                .background(color = Color.Green),
            contentAlignment = Alignment.Center
        ) {
            AppIcon(
                imageVector = if (underReview) {
                    Icons.Filled.Reviews
                } else {
                    Icons.Filled.CloudDone
                },
                contentDescription = "Successful Tst Upload",
                tint = AppTheme.colors.onPrimary,
                modifier = Modifier.size(AppTheme.iconSize.level6)
            )
        }
    }
}