package fit.asta.health.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

/**
 * This function is the Card view Template used
 *
 * @param modifier To be passed by the Parent Class
 * @param title This is the title for the card
 * @param body The UI which will be drawn inside this card
 */
@Composable
fun AppTitleElevatedCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    body: @Composable () -> Unit
) {

    // This function draws an elevated Card View
    AppElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = AppTheme.shape.level1
    ) {

        Column(modifier = Modifier.padding(AppTheme.spacing.level1)) {
            if (!title.isNullOrEmpty()) {

                HeadingTexts.Level3(
                    text = title,

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = AppTheme.spacing.level2,
                            start = AppTheme.spacing.level2,
                            end = AppTheme.spacing.level2
                        ),

                    // Text Features
                    textAlign = TextAlign.Start
                )
            }

            // Graph Body Function
            body()
        }
    }
}