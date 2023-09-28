package fit.asta.health.designsystem.molecular.texts

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
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.AppTypography


/**
 * [TitleTexts] is a utility class in a Compose-based Android application that provides a set of
 * Composable functions for displaying text with different predefined styles. These Composable
 * functions can be used to display text of mainly type Title Texts.
 *
 * Check [AppTypography] for getting the current typography values
 *
 * @see [BodyTexts]
 * @see [CaptionTexts]
 * @see [HeadingTexts]
 * @see [LargeTexts]
 * @see [TitleTexts]
 */
object TitleTexts {


    /** List of all available methods inside [TitleTexts] -> [Level1] , [Level2] , [Level3] and
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
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.title.level1,
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
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.title.level2,
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
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.title.level3,
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
        textAlign: TextAlign? = null,
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.title.level4,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
        )
    }
}

// Preview Function
@Preview("Title Texts Light")
@Preview(
    name = "Title Texts Dark",
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
                TitleTexts.Level1(text = "Title Level 1")
                TitleTexts.Level2(text = "Title Level 2")
                TitleTexts.Level3(text = "Title Level 3")
                TitleTexts.Level4(text = "Title Level 4")
            }
        }
    }
}