package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.data.scheduler.db.entity.Weekdays
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppSwitch
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import java.util.Calendar

@Composable
fun OnlyToggleButton(
    imageIcon: ImageVector,
    title: String,
    switchTitle: String = "",
    onNavigateToClickText: (() -> Unit)? = null,
    onCheckClicked: (Boolean) -> Unit = {},
    mCheckedState: Boolean = true,
    testTag: String
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            if (onNavigateToClickText == null) {
                onCheckClicked(!mCheckedState)
            } else {
                onNavigateToClickText.invoke()
            }
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level2, vertical = AppTheme.spacing.level1),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(
                    imageVector = imageIcon,
                    contentDescription = null,
                    tint = AppTheme.colors.primary
                )
                TitleTexts.Level2(
                    text = title,
                    maxLines = 2,
                    color = AppTheme.colors.onSecondaryContainer
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CaptionTexts.Level1(
                    text = switchTitle,
                    maxLines = 1
                )
                AppSwitch(
                    modifier = Modifier.testTag(testTag),
                    checked = mCheckedState,
//                    colors = SwitchDefaults.colors(
//                        checkedThumbColor = AppTheme.colors.primary,
//                        uncheckedThumbColor = AppTheme.colors.primaryContainer,
//                        checkedTrackColor = AppTheme.colors.primaryContainer,
//                        uncheckedTrackColor = AppTheme.colors.background,
//                        checkedBorderColor = AppTheme.colors.primary,
//                        uncheckedBorderColor = AppTheme.colors.primary,
//                    )
                ) {
                    onCheckClicked(it)
                }
            }
        }
    }

}


@Composable
fun DigitalDemo(time: AMPMHoursMin, open: () -> Unit = {}) {
    val hours by animateIntAsState(
        targetValue = time.hours, label = "",
        animationSpec = tween(700, easing = FastOutLinearInEasing)
    )
    val minutes by animateIntAsState(
        targetValue = time.minutes, label = "",
        animationSpec = tween(700, easing = FastOutLinearInEasing)
    )
    AppCard(
        modifier = Modifier
            .testTag("alarmclock"),
        onClick = open
    ) {
        Row(
            modifier = Modifier
                .padding(AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
        ) {
            TitleTexts.Level2(
                text = "${if (time.hours < 10) "0" else ""}${hours}:${if (time.minutes < 10) "0" else ""}${minutes}",
            )
            TitleTexts.Level2(
                text = time.dayTime.name
            )
        }
    }
}

@Composable
fun RepeatAlarm(
    weekdays: Weekdays,
    onDaySelect: (Int) -> Unit
) {
    val context = LocalContext.current
    val text by remember(weekdays) {
        mutableStateOf(
            weekdays.toString(context = context, order = Weekdays.Order.SUN_TO_SAT)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIcon(
                imageVector = Icons.Default.EditCalendar,
                contentDescription = null,
                tint = AppTheme.colors.primary
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level0)
            ) {
                TitleTexts.Level2(
                    text = "Repeat",
                    color = AppTheme.colors.onSecondaryContainer
                )
                TitleTexts.Level3(
                    text = text,
                    color = AppTheme.colors.onSurfaceVariant
                )
            }
        }
        AllDays(onDaySelect = onDaySelect, weekdays = weekdays)
    }
}


@Composable
fun DaysCircleButton(
    day: String,
    isSelected: Boolean = false,
    onDaySelect: () -> Unit = {}
) {

    val colorState: Color =
        if (!isSelected)
            DefaultColorTokens.TSelected
        else
            AppTheme.colors.primary

    val colorState2: Color = if (isSelected) Color.White else Color.Black

    AppFilledButton(
        onClick = { onDaySelect() },
        modifier = Modifier.size(40.dp),
        shape = CircleShape,
        containerColor = colorState,
        contentColor = colorState2,
//        colors = buttonColors(
//            containerColor = colorState,
//            contentColor = colorState2
//        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        BodyTexts.Level1(text = day, color = colorState2, textAlign = TextAlign.Center)
    }
}

@Composable
fun AllDays(
    weekdays: Weekdays,
    onDaySelect: (Int) -> Unit
) {


    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        DaysCircleButton(day = "S", isSelected = weekdays.isBitOn(Calendar.SUNDAY)) {
            onDaySelect(Calendar.SUNDAY)
        }
        DaysCircleButton(day = "M", isSelected = weekdays.isBitOn(Calendar.MONDAY)) {
            onDaySelect(Calendar.MONDAY)
        }
        DaysCircleButton(day = "T", isSelected = weekdays.isBitOn(Calendar.TUESDAY)) {
            onDaySelect(Calendar.TUESDAY)
        }
        DaysCircleButton(
            day = "W", isSelected = weekdays.isBitOn(Calendar.WEDNESDAY)
        ) { onDaySelect(Calendar.WEDNESDAY) }
        DaysCircleButton(day = "T", isSelected = weekdays.isBitOn(Calendar.THURSDAY)) {
            onDaySelect(Calendar.THURSDAY)
        }
        DaysCircleButton(day = "F", isSelected = weekdays.isBitOn(Calendar.FRIDAY)) {
            onDaySelect(Calendar.FRIDAY)
        }
        DaysCircleButton(day = "S", isSelected = weekdays.isBitOn(Calendar.SATURDAY)) {
            onDaySelect(Calendar.SATURDAY)
        }
    }
}
