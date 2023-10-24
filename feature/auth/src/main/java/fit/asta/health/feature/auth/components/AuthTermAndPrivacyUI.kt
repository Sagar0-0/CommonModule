package fit.asta.health.feature.auth.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.resources.strings.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * This composable function creates the Annotated String in the UI
 *
 * @param modifier This is modifications which may be passed from the Parent Composable
 * @param onTermsClick This function is called when the User clicks on the Terms Text
 * @param onPrivacyClick This function is called when the User clicks on the Privacy Text
 */
@Composable
fun AuthTermAndPrivacyUI(
    modifier: Modifier = Modifier,
    onTermsClick: (String) -> Unit,
    onPrivacyClick: (String) -> Unit
) {

    val context = LocalContext.current

    // Building the Annotated string
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        val str = stringResource(id = R.string.tnc_text)
        val termStartIndex = str.indexOf("Terms")
        val termEndIndex = termStartIndex + 16
        val privacyStartIndex = str.indexOf("Privacy")
        val privacyEndIndex = privacyStartIndex + 14
        append(str)

        // String Before Terms of Service Annotated Link
        NormalText(startIndex = 0, endIndex = termStartIndex - 1)

        // Terms of Service Annotated String
        LinkedString(
            textToAnnotate = "terms",
            annotation = getImgUrl(context.getString(R.string.url_terms_of_use)),
            startIndex = termStartIndex,
            endIndex = termEndIndex
        )

        // String between both the Annotated String
        NormalText(startIndex = termEndIndex + 1, endIndex = privacyStartIndex - 1)

        // Privacy Policy Annotated String
        LinkedString(
            textToAnnotate = "privacy",
            annotation = getImgUrl(context.getString(R.string.url_privacy_policy)),
            startIndex = privacyStartIndex,
            endIndex = privacyEndIndex
        )
    }

    // The Whole Clickable Composable which shows the Text UI
    ClickableText(
        modifier = modifier,
        text = annotatedLinkString
    ) {
        annotatedLinkString
            .getStringAnnotations("terms", it, it)
            .firstOrNull()?.let { stringAnnotation ->
                onTermsClick(
                    URLEncoder.encode(
                        stringAnnotation.item,
                        StandardCharsets.UTF_8.toString()
                    )
                )
            }
        annotatedLinkString
            .getStringAnnotations("privacy", it, it)
            .firstOrNull()?.let { stringAnnotation ->
                onPrivacyClick(
                    URLEncoder.encode(
                        stringAnnotation.item,
                        StandardCharsets.UTF_8.toString()
                    )
                )
            }
    }
}

/**
 * This composable function is used to add Style to a Normal Text in the Terms and Condition UI
 *
 * @param startIndex This is the start Index from where we need to make this text
 * @param endIndex This is the end Index until where we need to make this text
 */
@Composable
private fun AnnotatedString.Builder.NormalText(startIndex: Int, endIndex: Int) = addStyle(
    style = AppTheme.customTypography.body.level2
        .toSpanStyle()
        .copy(color = AppTheme.colors.onSurface),
    start = startIndex,
    end = endIndex
)

/**
 * This composable function is used to add Style to the Annotated Linked String in the Terms and
 * Conditions UI
 *
 * @param textToAnnotate This is the tag used to distinguish annotations
 * @param annotation This is the string annotation that is attached
 * @param startIndex This is start Index of the string
 * @param endIndex This is the end index of the String
 */
@Composable
private fun AnnotatedString.Builder.LinkedString(
    textToAnnotate: String,
    annotation: String,
    startIndex: Int,
    endIndex: Int
) {

    // This function adds the Style and decorations and typography for the Annotated String
    addStyle(
        style = AppTheme.customTypography.body.level2
            .toSpanStyle()
            .copy(
                textDecoration = TextDecoration.Underline,
                color = AppTheme.colors.primary
            ),
        start = startIndex,
        end = endIndex
    )

    // This function sets an Annotations for the Strings
    addStringAnnotation(
        tag = textToAnnotate,
        annotation = annotation,
        start = startIndex,
        end = endIndex
    )
}