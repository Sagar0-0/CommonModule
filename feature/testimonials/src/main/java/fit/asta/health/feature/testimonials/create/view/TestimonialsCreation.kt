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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.data.testimonials.model.TestimonialType
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.theme.spacing

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
                .padding(start = spacing.medium)
        ) {
            Spacer(modifier = Modifier.height(spacing.medium))
            Row(Modifier.fillMaxWidth()) {
                AppTexts.BodyMedium(text = selectionTypeText)
            }
            FlowRow(Modifier.fillMaxWidth()) {
                radioButtonList.forEach { item ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.padding(
                                top = spacing.small, bottom = spacing.small
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AppButtons.AppRadioButton(
                                    selected = (item == selectedOption),
                                    onClick = {
                                        onOptionSelected(item)
                                    },
                                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                                )
                                AppTexts.BodyLarge(text = item.title)
                            }
                        }
                    }
                }
            }
        }
    })
}