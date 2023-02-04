package fit.asta.health.testimonials.view.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.R
import fit.asta.health.testimonials.view.components.SuccessfulCard
import fit.asta.health.testimonials.view.theme.buttonSize
import fit.asta.health.testimonials.view.theme.cardElevation
import fit.asta.health.testimonials.view.theme.imageHeight
import fit.asta.health.tools.sunlight.view.components.BottomSheetButton
import fit.asta.health.ui.spacing
import kotlinx.coroutines.delay

@Composable
fun CustomDialogWithResultExample(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
    btnTitle: String,
    btnWarn: String,
    btn1Title: String,
    btn2Title: String,
) {
    Dialog(onDismissRequest = onDismiss) {
        DialogContent(
            onNegativeClick = onNegativeClick,
            onPositiveClick = onPositiveClick,
            btnTitle = btnTitle,
            btnWarn = btnWarn,
            btn1Title = btn1Title,
            btn2Title = btn2Title
        )
    }
}


@Composable
fun OnSuccessfulSubmit(
    onDismiss: () -> Unit,
    onNavigateTstHome: () -> Unit,
    onPositiveClick: () -> Unit,
) {

    var underReview by remember {
        mutableStateOf(true)
    }


    LaunchedEffect(key1 = Unit, block = {
        delay(2000)
        underReview = false
        delay(3000)
        onNavigateTstHome()
    })


    Dialog(
        onDismissRequest = onDismiss, properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        SuccessfulCard(onClick = onPositiveClick, underReview = underReview)
    }

}

@Composable
fun DialogContent(
    modifier: Modifier = Modifier,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
    btnTitle: String,
    btnWarn: String,
    btn1Title: String,
    btn2Title: String,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation.small)
    ) {
        Column(modifier.background(Color.White)) {

            Spacer(modifier = Modifier.height(spacing.large))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_notifications),
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .height(imageHeight.medium)
                        .fillMaxWidth(),

                    )
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = btnTitle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = spacing.extraSmall)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.extraLarge2),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = btnWarn,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = spacing.extraSmall)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.small),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    BottomSheetButton(
                        title = btn1Title,
                        colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.height(buttonSize.extraLarge),
                        onClick = onNegativeClick
                    )
                }

                Spacer(modifier = Modifier.width(spacing.small))

                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    BottomSheetButton(
                        title = btn2Title,
                        modifier.height(buttonSize.extraLarge),
                        onClick = onPositiveClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing.medium))

        }
    }
}



