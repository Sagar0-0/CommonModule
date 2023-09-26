package fit.asta.health.designsystem.components.functional

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppDialog
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystemx.AstaThemeX
import kotlinx.coroutines.delay

data class DialogData(
    val dialogTitle: String,
    val dialogDesc: String,
    val negTitle: String,
    val posTitle: String,
)


@Composable
fun ShowCustomConfirmationDialog(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
    dialogData: DialogData,
) {
    AppDialog(onDismissRequest = onDismiss, content = {
        DialogContent(
            onNegativeClick = onNegativeClick,
            onPositiveClick = onPositiveClick,
            dialogData = dialogData
        )
    })
}

@Composable
fun OnSuccessfulSubmit(
    onDismiss: () -> Unit = {},
    onNavigateTstHome: () -> Unit = {},
    onPositiveClick: () -> Unit = {},
) {
    var underReview by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = Unit, block = {
        delay(10000)
        underReview = false
        delay(10000)
        onNavigateTstHome()
    })
    AppDialog(onDismissRequest = onDismiss, content = {
        SuccessfulCard(
            underReview = underReview, onClick = onPositiveClick
        )
    })
}


@Composable
fun DialogContent(
    modifier: Modifier = Modifier,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
    dialogData: DialogData,
) {
    AppCard(content = {
        Column(
            modifier = modifier
                .background(Color.White)
                .padding(AstaThemeX.spacingX.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DialogHeader(dialogData = dialogData)
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            DialogDescription(dialogData = dialogData)
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            DialogButtons(
                onNegativeClick = onNegativeClick,
                onPositiveClick = onPositiveClick,
                dialogData = dialogData
            )
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
        }
    })
}


@Composable
private fun DialogHeader(dialogData: DialogData) {
    AppDefaultIcon(
        imageVector = Icons.Filled.NotificationImportant,
        contentDescription = "Alert Message",
        tint = MaterialTheme.colorScheme.error,
        modifier = Modifier.size(AstaThemeX.iconSizeX.medium)
    )
    Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
    AppTexts.LabelLarge(
        text = dialogData.dialogTitle,
        modifier = Modifier.padding(top = AstaThemeX.spacingX.extraSmall),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
private fun DialogDescription(dialogData: DialogData) {
    AppTexts.BodyMedium(
        text = dialogData.dialogDesc,
        modifier = Modifier.padding(top = AstaThemeX.spacingX.extraSmall),
        textAlign = TextAlign.Center
    )
}


@Composable
private fun DialogButtons(
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
    dialogData: DialogData,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small)
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            NegativeButton(onNegativeClick = onNegativeClick, dialogData = dialogData)
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            PositiveButton(onPositiveClick = onPositiveClick, dialogData = dialogData)
        }
    }
}


@Composable
private fun NegativeButton(
    onNegativeClick: () -> Unit,
    dialogData: DialogData,
) {

    AppButtons.AppStandardButton(
        onClick = onNegativeClick,
        modifier = Modifier
            .height(AstaThemeX.buttonSizeX.extraLarge)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
        )
    ) {
        AppTexts.LabelMedium(
            text = dialogData.negTitle,
            color = MaterialTheme.colorScheme.onError,
        )
    }
}

@Composable
private fun PositiveButton(
    onPositiveClick: () -> Unit,
    dialogData: DialogData,
) {
    AppButtons.AppStandardButton(
        onClick = onPositiveClick, modifier = Modifier
            .height(AstaThemeX.buttonSizeX.extraLarge)
            .fillMaxWidth()
    ) {
        AppTexts.LabelMedium(
            text = dialogData.posTitle,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
