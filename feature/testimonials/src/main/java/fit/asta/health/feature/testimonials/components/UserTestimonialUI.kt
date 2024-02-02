package fit.asta.health.feature.testimonials.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts


// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        AppSurface {
            UserTestimonialUI(userTestimonial = "Testimonial Is given")
        }
    }
}


/**
 * This function creates the UI of the Testimonial of the users where it is enclosed within "" commas.
 *
 * @param modifier This is for the modifications to be passed from the Parent Function
 * @param userTestimonial This is for the testimonial which is submitted by the user.
 */
@Composable
fun UserTestimonialUI(
    userTestimonial: String,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {

        // First Starting Quote
        TitleTexts.Level2(
            modifier = Modifier.align(Alignment.Top),
            text = "❝",
            color = AppTheme.colors.primary
        )

        // User's Testimonials
        BodyTexts.Level3(
            text = userTestimonial,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Last Ending Quote
        TitleTexts.Level2(
            text = "❞",
            modifier = Modifier.align(Alignment.Bottom),
            color = AppTheme.colors.primary
        )
    }
}