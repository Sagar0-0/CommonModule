package fit.asta.health.feature.testimonials.create.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.data.testimonials.model.TestimonialType
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TestimonialsRadioButton(
    selectionTypeText: String,
    radioButtonList: List<TestimonialType>,
    selectedOption: TestimonialType?,
    onOptionSelected: (TestimonialType) -> Unit,
) {
    AppCard(modifier = Modifier.fillMaxWidth(), content = {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = AppTheme.spacing.level2)
        ) {
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            Row(Modifier.fillMaxWidth()) {
                BodyTexts.Level2(text = selectionTypeText)
            }
            FlowRow(Modifier.fillMaxWidth()) {
                radioButtonList.forEach { item ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.padding(
                                top = AppTheme.spacing.level1, bottom = AppTheme.spacing.level1
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AppRadioButton(selected = (item == selectedOption),
                                    onClick = { onOptionSelected(item) })
                                TitleTexts.Level2(text = item.title)
                            }
                        }
                    }
                }
            }
        }
    })
}