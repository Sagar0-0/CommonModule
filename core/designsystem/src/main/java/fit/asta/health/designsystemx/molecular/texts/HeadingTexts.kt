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
import fit.asta.health.designsystemx.atomic.AppCustomTypography


/**
 * [HeadingTexts] is a utility class in a Compose-based Android application that provides a set of
 * Composable functions for displaying text with different predefined styles. These Composable
 * functions can be used to display text of mainly type Body Texts.
 *
 * Check [AppCustomTypography] for getting the current typography values
 *
 */
object HeadingTexts {


    /** List of all available methods inside [HeadingTexts] -> [Level1] , [Level2] , [Level3] and
     * [Level4]
     *
     * @param text the text to be displayed
     * @param modifier the [Modifier] to be applied to this layout node
     * @param color [Color] to apply to the text.
     * @param textAlign the alignment of the text within the lines of the paragraph.
     */
    @Composable
    fun Level1(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = AppTheme.colorsX.onSurface,
        textAlign: TextAlign? = null
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.heading.level1,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
        )
    }

    @Composable
    fun Level2(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = AppTheme.colorsX.onSurface,
        textAlign: TextAlign? = null
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.heading.level2,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
        )
    }

    @Composable
    fun Level3(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = AppTheme.colorsX.onSurface,
        textAlign: TextAlign? = null
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.heading.level3,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
        )
    }

    @Composable
    fun Level4(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = AppTheme.colorsX.onSurface,
        textAlign: TextAlign? = null
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.heading.level3,
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
                HeadingTexts.Level1(text = "Level 1 Texts")
                HeadingTexts.Level2(text = "Level 2 Texts")
                HeadingTexts.Level3(text = "Level 3 Texts")
                HeadingTexts.Level4(text = "Level 4 Texts")
            }
        }
    }
}