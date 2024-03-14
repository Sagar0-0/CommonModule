package fit.asta.health.feature.walking.ui.component

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import fit.asta.health.common.health_data.MIN_SUPPORTED_SDK
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.resources.strings.R as StrR

/**
 * Welcome text shown when the app first starts, where the device is not running a sufficient
 * Android version for Health Connect to be used.
 */
@Composable
fun NotSupportedMessage() {
    val tag = stringResource(StrR.string.not_supported_tag)
    val url = stringResource(StrR.string.not_supported_url)
    val handler = LocalUriHandler.current

    val notSupportedText = stringResource(
        id = StrR.string.not_supported_description,
        MIN_SUPPORTED_SDK
    )
    val notSupportedLinkText = stringResource(StrR.string.not_supported_link_text)

    val unavailableText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = AppTheme.colors.onBackground)) {
            append(notSupportedText)
            append("\n\n")
        }
        pushStringAnnotation(tag = tag, annotation = url)
        withStyle(style = SpanStyle(color = AppTheme.colors.primary)) {
            append(notSupportedLinkText)
        }
    }
    ClickableText(
        text = unavailableText,
        style = TextStyle(textAlign = TextAlign.Justify)
    ) { offset ->
        unavailableText.getStringAnnotations(tag = tag, start = offset, end = offset)
            .firstOrNull()?.let {
                handler.openUri(it.item)
            }
    }
}
