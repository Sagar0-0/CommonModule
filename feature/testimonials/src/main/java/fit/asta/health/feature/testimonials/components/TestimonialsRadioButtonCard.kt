package fit.asta.health.feature.testimonials.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.data.testimonials.remote.model.TestimonialType
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.cards.AppCard
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
            TestimonialsRadioButtonCard(
                cardTitle = "Testimonial Type",
                radioButtonList = listOf(
                    TestimonialType.TEXT,
                    TestimonialType.IMAGE,
                    TestimonialType.VIDEO
                ),
                selectedOption = TestimonialType.IMAGE,
                onOptionSelected = {}
            )
        }
    }
}


/**
 * This function creates the card Layout containing the Radio Options for uploading the testimonials
 *
 * @param modifier Default Modifier passed from the parent function
 * @param cardTitle This is the title of the card.
 * @param radioButtonList This is the options list for the radio buttons
 * @param selectedOption This defines the selected item
 * @param onOptionSelected This function is selected when an option is selected
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TestimonialsRadioButtonCard(
    modifier: Modifier = Modifier,
    cardTitle: String,
    radioButtonList: List<TestimonialType>,
    selectedOption: TestimonialType?,
    onOptionSelected: (TestimonialType) -> Unit,
) {

    // Card Composable Parent
    AppCard {
        Column(
            modifier = modifier.padding(
                top = AppTheme.spacing.level2,
                start = AppTheme.spacing.level2,
                end = AppTheme.spacing.level2
            )
        ) {

            // Title of the Card
            TitleTexts.Level2(text = cardTitle)

            // For showing all the available options
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {

                // Iterating through each option and rending them
                radioButtonList.forEach { item ->

                    // Row containing the Radio Button and the Title
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        // Radio Button
                        AppRadioButton(selected = (item == selectedOption)) {
                            onOptionSelected(item)
                        }

                        // Option Text Title
                        BodyTexts.Level2(text = item.title)
                    }
                }
            }
        }
    }
}