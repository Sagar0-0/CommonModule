package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.common.utils.convert12hrTo24hr
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.scheduler.util.Constants.Companion.getTimeDifference
import kotlin.math.abs
import fit.asta.health.resources.strings.R as StringR

@Composable
fun TimePickerBottomSheet(
    title: String,
    time: AMPMHoursMin,
    onCancel: () -> Unit,
    onSave: (AMPMHoursMin) -> Unit
) {

    TimePickerClock(
        dividersColor = AppTheme.colors.primary,
        value = time,
        title = title,
        onCancel = onCancel,
        onSave = onSave
    )
}

@Composable
fun TimePickerClock(
    onCancel: () -> Unit, onSave: (AMPMHoursMin) -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    value: AMPMHoursMin,
    leadingZero: Boolean = true,
    hoursRange: Iterable<Int> = (1..12),
    minutesRange: Iterable<Int> = (0..59),
    hoursDivider: (@Composable () -> Unit)? = null,
    minutesDivider: (@Composable () -> Unit)? = null,
    dividersColor: Color = Color.Green,
    textStyle: TextStyle = AppTheme.customTypography.title.level3.copy(color = AppTheme.colors.onPrimaryContainer),
) {
    val selectionTimePassed = stringResource(StringR.string.selected_time_is_passed)
    var ampmHoursMin by remember(value) {
        mutableStateOf(value)
    }

    var time by remember {
        mutableStateOf("")
    }

    var visibility by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = ampmHoursMin) {
        val timeDifference = getTimeDifference(ampmHoursMin.convert12hrTo24hr())
        time = if (timeDifference < 0) {
            selectionTimePassed
        } else {
            // Convert the time difference to hours and minutes
            val hour = timeDifference / 60
            val min = timeDifference % 60
            // Format the result as "HH:MM"
            val formattedDifference = String.format("%02d:%02d", hour, min)
            "Ring In $formattedDifference"
        }
    }
    AppCard {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleTexts.Level1(text = title)
            AnimatedVisibility(visible = visibility) {
                TitleTexts.Level1(text = time)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TitleTexts.Level3(
                    modifier = Modifier.weight(.4f),
                    textAlign = TextAlign.Center,
                    text = "Hours",
                )
                TitleTexts.Level3(
                    modifier = Modifier.weight(.4f),
                    textAlign = TextAlign.Center,
                    text = "Minutes",
                )
                Spacer(modifier = Modifier.weight(.2f))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NumberPicker(modifier = Modifier.weight(1f), value = ampmHoursMin.hours, label = {
                    "${if (leadingZero && (abs(it) < 10)) "0" else ""}$it"
                }, onValueChange = {
                    visibility = true
                    ampmHoursMin = ampmHoursMin.copy(hours = it)
                }, dividersColor = dividersColor, textStyle = textStyle, range = hoursRange
                )

                hoursDivider?.invoke()

                NumberPicker(modifier = Modifier.weight(1f), label = {
                    "${if (leadingZero && (abs(it) < 10)) "0" else ""}$it"
                }, value = ampmHoursMin.minutes, onValueChange = {
                    visibility = true
                    ampmHoursMin = ampmHoursMin.copy(minutes = it)
                }, dividersColor = dividersColor, textStyle = textStyle, range = minutesRange
                )

                minutesDivider?.invoke()

                NumberPicker(modifier = Modifier, value = when (ampmHoursMin.dayTime) {
                    AMPMHoursMin.DayTime.AM -> 0
                    else -> 1
                }, label = {
                    when (it) {
                        0 -> "AM"
                        else -> "PM"
                    }
                }, onValueChange = {
                    visibility = true
                    ampmHoursMin = ampmHoursMin.copy(
                        dayTime = when (it) {
                            0 -> AMPMHoursMin.DayTime.AM
                            else -> AMPMHoursMin.DayTime.PM
                        }
                    )
                }, dividersColor = dividersColor, textStyle = textStyle, range = (0..1)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)) {
                ButtonWithColor(
                    modifier = Modifier.weight(0.5f),
                    color = Color.Red,
                    text = stringResource(id = StringR.string.cancel)
                ) { onCancel() }
                ButtonWithColor(
                    modifier = Modifier.weight(0.5f),
                    color = Color.Blue,
                    text = stringResource(StringR.string.save)
                ) { onSave(ampmHoursMin) }
            }
        }
    }
}