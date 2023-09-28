package fit.asta.health.feature.testimonials.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.atomic.AppSpacing
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@Composable
fun UserTst(
    spacing: AppSpacing,
    testimonial: fit.asta.health.data.testimonials.model.Testimonial,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.medium)
    ) {
        Column {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                HeadingTexts.Level3(text = "❝")
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium)
            ) {
                BodyTexts.Level2(text = testimonial.testimonial)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                HeadingTexts.Level3(text = "❞")
            }
            UserCard(
                user = testimonial.user.name,
                userOrg = testimonial.user.org,
                userRole = testimonial.user.role,
                url = testimonial.user.url
            )
        }
    }
}