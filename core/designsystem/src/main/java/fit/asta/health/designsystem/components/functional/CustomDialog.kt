package fit.asta.health.designsystem.components.functional

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.generic.AppDialog
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
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
    AppCard {
        Column(
            modifier = modifier.padding(AppTheme.spacing.level3),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DialogHeader(dialogData = dialogData)
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            DialogDescription(dialogData = dialogData)
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            DialogButtons(
                onNegativeClick = onNegativeClick,
                onPositiveClick = onPositiveClick,
                dialogData = dialogData
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
        }
    }
}

@Composable
private fun DialogHeader(dialogData: DialogData) {
    AppIcon(
        imageVector = Icons.Filled.NotificationImportant,
        contentDescription = "Alert Message",
        tint = AppTheme.colors.error,
        modifier = Modifier.size(AppTheme.iconSize.level6)
    )
    Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
    CaptionTexts.Level1(
        text = dialogData.dialogTitle,
        modifier = Modifier.padding(top = AppTheme.spacing.level1)
    )
}

@Composable
private fun DialogDescription(dialogData: DialogData) {
    BodyTexts.Level2(
        text = dialogData.dialogDesc,
        modifier = Modifier.padding(top = AppTheme.spacing.level1),
        textAlign = TextAlign.Center,
        color = AppTheme.colors.onSurface
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
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
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

    AppFilledButton(
        onClick = onNegativeClick,
        modifier = Modifier
            .height(AppTheme.buttonSize.level7)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.error,
        )
    ) {
        CaptionTexts.Level3(text = dialogData.negTitle, color = AppTheme.colors.onError)
    }
}

@Composable
private fun PositiveButton(
    onPositiveClick: () -> Unit,
    dialogData: DialogData,
) {
    AppFilledButton(
        onClick = onPositiveClick,
        modifier = Modifier
            .height(AppTheme.buttonSize.level7)
            .fillMaxWidth()
    ) {
        CaptionTexts.Level3(text = dialogData.posTitle, color = AppTheme.colors.onPrimary)
    }
}
