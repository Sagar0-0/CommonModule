package fit.asta.health.designsystem.molecular

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.HeadingTexts


/**[AppErrorMsgCard] composable function is a UI component that displays an error message along with
 *  an associated image in a card format.
 * @param message A required String parameter that represents the error message to be displayed.
 * @param imageVector A required ImageVector parameter that represents the vector image to be displayed along with the error message.
 * */

@Composable
fun AppErrorMsgCard(message: String, imageVector: ImageVector) {
    AppCard(
        modifier = Modifier
            .padding(AppTheme.spacing.level2)
            .fillMaxWidth()
            .wrapContentHeight(),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level1),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AppIcon(
                    imageVector = imageVector,
                    contentDescription = "ErrorMessage Occurred while fetching Tst List",
                    tint = AppTheme.colors.surface,
                    modifier = Modifier.size(AppTheme.imageSize.level6)
                )
                HeadingTexts.Level4(text = message, color = AppTheme.colors.onError)
            }
        },
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.error)
    )
}