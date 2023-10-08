package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.functional.ScheduleIconLayout
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR

@Composable
fun TodayCardType1(
    progressValue: String = "55%",
    cardImgId: Int = DrawR.drawable.weatherimage,
    cardTitle: String = "Water",
    cardDesc: String = "Drink Some Water",
    cardTime: String = "5 AM",
    buttonTitle: String = "Tap to Complete",
) {
    AppCard(
        modifier = Modifier
            .padding(horizontal = AppTheme.spacing.level3)
            .aspectRatio(AppTheme.aspectRatio.wideScreen)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CardImage(cardImgId)
            CardContent(
                cardTitle = cardTitle,
                cardDesc = cardDesc,
                cardTime = cardTime,
                buttonTitle = buttonTitle,
                progressValue = progressValue
            )
            CardCenterButton(buttonTitle = buttonTitle)
        }
    }
}

@Composable
private fun CardImage(cardImgId: Int) {
    AppLocalImage(
        painter = painterResource(id = cardImgId),
        contentDescription = stringResource(StringR.string.card_image),
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun CardCenterButton(buttonTitle: String, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
        AppFilledButton(
            onClick = { /*TODO*/ },
            shape = CircleShape,
            modifier = modifier.size(100.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.6f)
            )
        ) {
            CaptionTexts.Level3(
                text = buttonTitle, textAlign = TextAlign.Center, color = Color.White
            )
        }
    }
}

@Composable
private fun CardContent(
    cardTitle: String,
    cardDesc: String,
    cardTime: String,
    buttonTitle: String,
    progressValue: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppTheme.spacing.level3, vertical = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CardTitleAndButton(cardTitle)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            DisabledAssistChip(progressValue)
        }
        CardDescriptionAndTime(cardDesc, cardTime)
    }
}

@Composable
private fun CardTitleAndButton(cardTitle: String) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        TitleTexts.Level1(text = cardTitle, color = Color.White)
        ScheduleIconLayout(onButtonClick = { /*TODO*/ })
    }
}

@Composable
private fun CardDescriptionAndTime(cardDesc: String, cardTime: String) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyTexts.Level1(text = cardDesc, color = Color.White)
        BodyTexts.Level3(text = cardTime, color = Color.White)
    }
}

@Composable
fun TodayCardType2(
    progressValue: String = "75%",
    cardImgId: Int = DrawR.drawable.weatherimage,
    cardTitle: String = "Fasting",
    cardDesc: String = "Drink Some Water",
    cardTime: String = "7 AM",
    buttonTitle: String = "Tap to Complete",
    remainingTime: String = "6 Hours",
    secondaryTitle: String = "Intermittent Fasting",
    onButtonClick: () -> Unit = { /* TODO */ },
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level3)
            .aspectRatio(AppTheme.aspectRatio.fullScreen), content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = cardImgId),
                    contentDescription = stringResource(id = StringR.string.card_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = AppTheme.spacing.level3,
                        vertical = AppTheme.spacing.level2
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CardTopLayout(
                    cardTitle = cardTitle,
                    secondaryTitle = secondaryTitle,
                    onButtonClick = onButtonClick
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BodyTexts.Level1(text = cardDesc, color = Color.White)
                    CardBottomLayout(cardTime = cardTime, remainingTime = remainingTime)
                }
            }

            CardCenterButton(buttonTitle = buttonTitle)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppTheme.spacing.level3),
                contentAlignment = Alignment.CenterEnd
            ) {
                DisabledAssistChip(progressValue = progressValue)
            }
        }
    })
}

@Composable
private fun CardBottomLayout(cardTime: String, remainingTime: String) {
    Column {
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            BodyTexts.Level3(text = cardTime, color = Color.White)
        }
        Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIcon(
                imageVector = Icons.Rounded.Timelapse,
                contentDescription = stringResource(StringR.string.remaining_time),
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(AppTheme.spacing.level1))
            BodyTexts.Level3(text = remainingTime, color = Color.White)
        }
    }
}

@Composable
private fun CardTopLayout(cardTitle: String, secondaryTitle: String, onButtonClick: () -> Unit) {
    Column {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleTexts.Level1(text = cardTitle, color = Color.White)
            ScheduleIconLayout(onButtonClick = onButtonClick)
        }
        Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
        Row(Modifier.fillMaxWidth(), Arrangement.Start) {
            BodyTexts.Level3(text = secondaryTitle, color = Color.White)
        }
    }
}

@Composable
private fun DisabledAssistChip(progressValue: String) {
    Box(
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.large)
            .background(color = AppTheme.colors.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        BodyTexts.Level3(
            text = progressValue,
            color = AppTheme.colors.onPrimaryContainer,
            modifier = Modifier.padding(AppTheme.spacing.level1)
        )
    }
}
