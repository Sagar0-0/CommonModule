package fit.asta.health.scheduler.compose.naman

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppCard
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppDrawImg
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.aspectRatio
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.navigation.home.view.component.ScheduleIconLayout

@Composable
fun TodayCardType1(
    progressValue: String = "55%",
    cardImgId: Int = R.drawable.weatherimage,
    cardTitle: String = "Water",
    cardDesc: String = "Drink Some Water",
    cardTime: String = "5 AM",
    buttonTitle: String = "Tap to Complete",
) {
    AppCard(
        modifier = Modifier
            .padding(horizontal = spacing.medium)
            .aspectRatio(aspectRatio.wideScreen)
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
    AppDrawImg(
        painter = painterResource(id = cardImgId),
        contentDescription = "Card Image",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun CardCenterButton(buttonTitle: String, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
        AppButtons.AppStandardButton(
            onClick = { /*TODO*/ },
            shape = CircleShape,
            modifier = modifier.size(100.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.6f)
            )
        ) {
            AppTexts.LabelSmall(
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
            .padding(horizontal = spacing.medium, vertical = spacing.small),
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
        AppTexts.TitleLarge(text = cardTitle, color = Color.White)
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
        AppTexts.BodyLarge(text = cardDesc, color = Color.White)
        AppTexts.BodySmall(text = cardTime, color = Color.White)
    }
}

@Composable
fun TodayCardType2(
    progressValue: String = "75%",
    cardImgId: Int = R.drawable.weatherimage,
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
            .padding(horizontal = spacing.medium)
            .aspectRatio(aspectRatio.fullScreen), content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = cardImgId),
                    contentDescription = "Card Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = spacing.medium, vertical = spacing.small),
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
                    AppTexts.BodyLarge(text = cardDesc, color = Color.White)
                    CardBottomLayout(cardTime = cardTime, remainingTime = remainingTime)
                }
            }

            CardCenterButton(buttonTitle = buttonTitle)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing.medium),
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
            AppTexts.BodySmall(text = cardTime, color = Color.White)
        }
        Spacer(modifier = Modifier.height(spacing.extraSmall))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Timelapse,
                contentDescription = "Remaining Time",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(spacing.extraSmall))
            AppTexts.BodySmall(text = remainingTime, color = Color.White)
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
            AppTexts.TitleLarge(text = cardTitle, color = Color.White)
            ScheduleIconLayout(onButtonClick = onButtonClick)
        }
        Spacer(modifier = Modifier.height(spacing.minSmall))
        Row(Modifier.fillMaxWidth(), Arrangement.Start) {
            AppTexts.BodySmall(text = secondaryTitle, color = Color.White)
        }
    }
}

@Composable
private fun DisabledAssistChip(progressValue: String) {
    Box(
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.large)
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        AppTexts.BodySmall(
            text = progressValue,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(spacing.extraSmall)
        )
    }
}

@Preview
@Composable
fun CardDemo() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TodayCardType1()
        Spacer(modifier = Modifier.height(spacing.medium))
        TodayCardType2()
        Spacer(modifier = Modifier.height(spacing.medium))
        AppointmentCard()
    }
}

@Composable
fun AppointmentCard(url: String = "") {

    AppCard(
        modifier = Modifier
            .padding(horizontal = spacing.medium)
            .aspectRatio(aspectRatio.wideScreen)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CardImage(cardImgId = R.drawable.weatherimage)
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = spacing.medium, vertical = spacing.small),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CardTitleAndButton(cardTitle = "Appointment")
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    AppDrawImg(
                        painter = painterResource(id = R.drawable.barsha),
                        contentDescription = "Doctor Pic",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(customSize.extraLarge4)
                            .clip(CircleShape)
                            .border(
                                border = BorderStroke(
                                    width = 1.dp, color = MaterialTheme.colorScheme.primary
                                ), shape = CircleShape
                            ),
                    )
                    Spacer(modifier = Modifier.width(spacing.medium))
                    DoctorLayout()
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppDefaultIcon(
                        imageVector = Icons.Rounded.Schedule,
                        contentDescription = "Scheduled",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(spacing.extraSmall))
                    AppTexts.BodyLarge(text = "30th Feb 7 GM", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun DoctorLayout() {
    Column {
        AppTexts.HeadlineSmall(text = "Dr. Varsha", color = Color.White)
        AppTexts.BodyLarge(text = "", color = Color.White)
    }
}