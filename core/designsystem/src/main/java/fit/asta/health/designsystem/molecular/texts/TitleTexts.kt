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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
     * @param modifier the [Modifier] to be applied to this layout node
     * @param text the text to be displayed
     * @param color [Color] to apply to the text.
     * @param textAlign the alignment of the text within the lines of the paragraph.
     * @param maxLines This denotes the maximum lines this text composable can have
     * @param overflow This is the way the text will be shown when it overflows
     */
    @Composable
    fun Level1(
        modifier: Modifier = Modifier,
        text: String,
        color: Color = AppTheme.colors.onSurface,
        style: TextStyle? = null,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            style = style ?: AppTheme.customTypography.title.level1,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Composable
    fun Level2(
        modifier: Modifier = Modifier,
        text: String,
        style: TextStyle? = null,
        color: Color = AppTheme.colors.onSurface,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            modifier = modifier,
            text = text,
            style = style ?: AppTheme.customTypography.title.level2,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Composable
    fun Level3(
        modifier: Modifier = Modifier,
        text: String,
        color: Color = AppTheme.colors.onSurface,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.title.level3,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Composable
    fun Level4(
        modifier: Modifier = Modifier,
        text: String,
        color: Color = AppTheme.colors.onSurface,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            text = text,
            style = AppTheme.customTypography.title.level4,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
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