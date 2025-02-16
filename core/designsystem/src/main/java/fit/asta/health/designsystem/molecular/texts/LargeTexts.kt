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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.AppCustomTypography


/**
 * [LargeTexts] is a utility class in a Compose-based Android application that provides a set of
 * Composable functions for displaying text with different predefined styles. These Composable
 * functions can be used to display text of mainly type Body Texts.
 *
 * Check [AppCustomTypography] for getting the current typography values
 *
 * @see [BodyTexts]
 * @see [CaptionTexts]
 * @see [HeadingTexts]
 * @see [LargeTexts]
 * @see [TitleTexts]
 */
object LargeTexts {


    /** List of all available methods inside [LargeTexts] -> [Level1] , [Level2] and [Level3]
     *
     * @param modifier the [Modifier] to be applied to this layout node
     * @param text the text to be displayed
     * @param color [Color] to apply to the text.
     * @param textAlign the alignment of the text within the lines of the paragraph.
     * @param maxLines This denotes the maximum lines this text composable can have
     * @param overflow This is the way the text will be shown when it overflows
     */
    @Composable
    fun Level1(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = AppTheme.colors.onSurface,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.large.level1,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Composable
    fun Level2(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = AppTheme.colors.onSurface,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.large.level2,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Composable
    fun Level3(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = AppTheme.colors.onSurface,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.large.level3,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }
}

// Preview Function
@Preview("Large Texts Light")
@Preview(
    name = "Large Texts Dark",
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
                LargeTexts.Level1(text = "Level 1 Texts")
                LargeTexts.Level2(text = "Level 2 Texts")
                LargeTexts.Level3(text = "Level 3 Texts")
            }
        }
    }
}