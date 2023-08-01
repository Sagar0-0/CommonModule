package fit.asta.health.common.ui.components.generic

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

/**[AppTexts] is a utility class in a Compose-based Android application that provides a set of
 * Composable functions for displaying text with different predefined styles. These Composable
 * functions can be used to display text in different sizes and styles consistently across the
 * application.
 * */

object AppTexts {

    /** List of all available methods inside [AppTexts]:
     *[DisplayLarge]
     *[DisplayMedium]
     *[DisplaySmall]
     *[HeadlineLarge]
     *[HeadlineMedium]
     *[HeadlineSmall]
     *[TitleLarge]
     *[TitleMedium]
     *[TitleSmall]
     *[BodyLarge]
     *[BodyMedium]
     *[BodySmall]
     *[LabelLarge]
     *[LabelMedium]
     *[LabelSmall]
     * @param text the text to be displayed
     * @param modifier the [Modifier] to be applied to this layout node
     * @param color [Color] to apply to the text.
     * @param textAlign the alignment of the text within the lines of the paragraph.
     */

    @Composable
    fun DisplayLarge(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayLarge,
            modifier = modifier,
            color = color,
            textAlign = textAlign
        )
    }

    @Composable
    fun DisplayMedium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun DisplaySmall(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displaySmall,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun HeadlineLarge(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun HeadlineMedium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier,
            color = color
        )
    }


    @Composable
    fun HeadlineSmall(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier,
            color = color,
            textAlign = textAlign
        )
    }

    @Composable
    fun TitleLarge(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun TitleMedium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier,
            color = color
        )
    }


    @Composable
    fun TitleSmall(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun BodyLarge(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun BodyMedium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier,
            color = color,
            textAlign = textAlign
        )
    }

    @Composable
    fun BodySmall(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun LabelLarge(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun LabelMedium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun LabelSmall(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            modifier = modifier,
            color = color
        )
    }

}