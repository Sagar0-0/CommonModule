package fit.asta.health.common.ui.components.functional

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
import fit.asta.health.common.ui.components.generic.AppDefBtn
import fit.asta.health.common.ui.components.generic.AppDefCard
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppDialog
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.buttonSize
import fit.asta.health.common.ui.theme.iconSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.testimonials.view.components.SuccessfulCard
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
    AppDefCard(content = {
        Column(
            modifier = modifier
                .background(Color.White)
                .padding(spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DialogHeader(dialogData = dialogData)
            Spacer(modifier = Modifier.height(spacing.medium))
            DialogDescription(dialogData = dialogData)
            Spacer(modifier = Modifier.height(spacing.medium))
            DialogButtons(
                onNegativeClick = onNegativeClick,
                onPositiveClick = onPositiveClick,
                dialogData = dialogData
            )
            Spacer(modifier = Modifier.height(spacing.medium))
        }
    })
}


@Composable
private fun DialogHeader(dialogData: DialogData) {
    AppDefaultIcon(
        imageVector = Icons.Filled.NotificationImportant,
        contentDescription = "Alert Message",
        tint = MaterialTheme.colorScheme.error,
        modifier = Modifier.size(iconSize.medium)
    )
    Spacer(modifier = Modifier.height(spacing.medium))
    AppTexts.LabelLarge(
        text = dialogData.dialogTitle,
        modifier = Modifier.padding(top = spacing.extraSmall),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
private fun DialogDescription(dialogData: DialogData) {
    AppTexts.BodyMedium(
        text = dialogData.dialogDesc,
        modifier = Modifier.padding(top = spacing.extraSmall),
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
        horizontalArrangement = Arrangement.spacedBy(spacing.small)
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
    AppDefBtn(
        onClick = onNegativeClick,
        modifier = Modifier
            .height(buttonSize.extraLarge)
            .fillMaxWidth(),
        color = ButtonDefaults.buttonColors(
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
    AppDefBtn(
        onClick = onPositiveClick, modifier = Modifier
            .height(buttonSize.extraLarge)
            .fillMaxWidth()
    ) {
        AppTexts.LabelMedium(
            text = dialogData.posTitle,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
