package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.data.scheduler.db.entity.Weekdays
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.button.AppToggleButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import java.util.Calendar

@Composable
fun OnlyToggleButton(
    imageIcon: ImageVector,
    title: String,
    switchTitle: String,
    onNavigateToClickText: (() -> Unit)?,
    onCheckClicked: (Boolean) -> Unit = {},
    mCheckedState: Boolean = false,
    btnEnabled: Boolean = false
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.Center) {
                    AppIcon(
                        imageVector = imageIcon,
                        contentDescription = null,
                        tint = AppTheme.colors.primary
                    )
                }
                TitleTexts.Level2(
                    text = title, maxLines = 2,
                    color = AppTheme.colors.onSecondaryContainer
                )
            }
        }
        Box {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectableText(
                    btnEnabled = btnEnabled,
                    arrowTitle = switchTitle,
                    onClick = { onNavigateToClickText?.invoke() })
                AppToggleButton(
                    checked = mCheckedState,
                    onCheckedChange = {
                        onCheckClicked(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AppTheme.colors.primary,
                        uncheckedThumbColor = AppTheme.colors.primaryContainer,
                        checkedTrackColor = AppTheme.colors.primaryContainer,
                        uncheckedTrackColor = AppTheme.colors.background,
                        checkedBorderColor = AppTheme.colors.primary,
                        uncheckedBorderColor = AppTheme.colors.primary,
                    )
                )
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
    AppCard(modifier = Modifier.clickable { open() }, content = {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleTexts.Level2(
                text = "${if (time.hours < 10) "0" else ""}${hours}:${if (time.minutes < 10) "0" else ""}${minutes}",
            )
            Spacer(modifier = Modifier.width(8.dp))
            TitleTexts.Level2(
                text = time.dayTime.name
            )
        }
    })
}

@Composable
fun RepeatAlarm(
    onDaySelect: (Int) -> Unit, weekdays: Weekdays,
) {
    val context = LocalContext.current
    val text by remember(weekdays) {
        mutableStateOf(
            weekdays.toString(context = context, order = Weekdays.Order.SUN_TO_SAT)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        AppIcon(
                            imageVector = Icons.Default.EditCalendar,
                            contentDescription = null,
                            tint = AppTheme.colors.primary
                        )
                    }
                    Column {
                        TitleTexts.Level2(
                            text = "Repeat",
                            color = AppTheme.colors.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        TitleTexts.Level3(
                            text = text,
                            color = AppTheme.colors.onSurfaceVariant
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AllDays(onDaySelect = onDaySelect, weekdays = weekdays)
        Spacer(modifier = Modifier.height(16.dp))
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

    AppTextButton(
        onClick = { onDaySelect() },
        shape = CircleShape,
        modifier = Modifier.size(40.dp),
        colors = buttonColors(
            containerColor = colorState,
            contentColor = colorState2
        ),
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
