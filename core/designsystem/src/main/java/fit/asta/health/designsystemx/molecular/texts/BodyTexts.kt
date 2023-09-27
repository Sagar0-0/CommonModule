package fit.asta.health.designsystemx.molecular.texts

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystemx.AppTheme
import fit.asta.health.designsystemx.atomic.AppTypography


/**
 * [BodyTexts] is a utility class in a Compose-based Android application that provides a set of
 * Composable functions for displaying text with different predefined styles. These Composable
 * functions can be used to display text of mainly type Body Texts.
 *
 * Check [AppTypography] for getting the current typography values
 *
 *
 * @see [DisplayTexts]
 * @see [HeadlineTexts]
 * @see [LabelTexts]
 * @see [TitleTexts]
 */
object BodyTexts {

    /** List of all available methods inside [BodyTexts] -> [Large] , [Medium] and [Small]
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
        color: Color = AppTheme.colorsX.onSurface,
        textAlign: TextAlign? = null
    ) {
        Text(
            text = text,
            style = AppTheme.typographyX.bodyLarge,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
        )
    }

    @Composable
    fun Medium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = AppTheme.colorsX.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = AppTheme.typographyX.bodyMedium,
            modifier = modifier,
            color = color,
            textAlign = textAlign
        )
    }

    @Composable
    fun Small(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = AppTheme.colorsX.onSurface,
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = AppTheme.typographyX.bodySmall,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
        )
    }
}

// Preview Function
@Preview("Body Texts Light")
@Preview(
    name = "Body Texts Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BodyTexts.Small(text = "Small Body Texts")
                BodyTexts.Medium(text = "Medium Body Texts")
                BodyTexts.Large(text = "Large Body Texts")
            }
        }
    }
}