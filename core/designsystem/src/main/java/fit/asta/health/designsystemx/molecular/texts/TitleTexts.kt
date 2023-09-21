package fit.asta.health.designsystemx.molecular.texts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.designsystemx.atomic.AstaTypographyX


/**
 * [TitleTexts] is a utility class in a Compose-based Android application that provides a set of
 * Composable functions for displaying text with different predefined styles. These Composable
 * functions can be used to display text of mainly type Title Texts.
 *
 * Check [AstaTypographyX] for getting the current typography values
 *
 * @see [DisplayTexts]
 * @see [HeadlineTexts]
 * @see [LabelTexts]
 * @see [BodyTexts]
 */
object TitleTexts {


    /** List of all available methods inside [TitleTexts] -> [Large] , [Medium] and [Small]
     *
     * @param text the text to be displayed
     * @param modifier the [Modifier] to be applied to this layout node
     * @param color [Color] to apply to the text.
     * @param textAlign the alignment of the text within the lines of the paragraph.
     */
    @Composable
    fun Large(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = AstaThemeX.colorsX.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = AstaThemeX.typographyX.bodyLarge,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
        )
    }

    @Composable
    fun Medium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = AstaThemeX.colorsX.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = AstaThemeX.typographyX.bodyLarge,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
        )
    }

    @Composable
    fun Small(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = AstaThemeX.colorsX.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = AstaThemeX.typographyX.bodyLarge,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
        )
    }
}