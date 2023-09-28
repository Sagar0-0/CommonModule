package fit.asta.health.feature.testimonials.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.extras.theme.ts
import fit.asta.health.designsystem.atomic.AppSpacing

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
                Text(
                    text = "❝", style = ts.quoteBold, color = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium)
            ) {
                Text(
                    text = testimonial.testimonial,
                    style = ts.quote,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    text = "❞", style = ts.quoteBold, color = MaterialTheme.colorScheme.primary
                )
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