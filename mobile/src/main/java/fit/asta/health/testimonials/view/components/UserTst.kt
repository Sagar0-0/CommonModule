package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.common.ui.theme.Spacing
import fit.asta.health.common.ui.theme.ts

@Composable
fun UserTst(
    spacing: Spacing,
    testimonial: Testimonial,
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