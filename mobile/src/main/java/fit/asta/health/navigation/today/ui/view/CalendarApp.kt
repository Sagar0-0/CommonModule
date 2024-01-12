package fit.asta.health.navigation.today.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.tab.AppTab
import fit.asta.health.designsystem.molecular.tab.AppTabRow
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import java.time.LocalDate


@Composable
fun WeekScreen(
    modifier: Modifier = Modifier,
    data: CalendarUiModel,
    onDateClickListener: (CalendarUiModel.Date) -> Unit,
) {
    Row(modifier = modifier) {
        data.visibleDates.forEachIndexed { index, date ->
            ContentItem(
                date = date,
                containerColor = if (date.isSelected) {
                    if (date.isToday) Color.Green
                    else AppTheme.colors.primary
                } else {
                    if (date.isToday) Color.Green.copy(alpha = .5f)
                    else if (index == 0) Color.LightGray
                    else AppTheme.colors.primaryContainer
                },
                onClickListener = onDateClickListener
            )
        }
    }
}

@Composable
@Preview("Light WeekTabBar")
@Preview(
    name = "Dark WeekTabBar",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
fun WeekTabBarPreview() {
    AppTheme {
        WeekTabBar(
            data = CalendarUiModel(
                selectedDate = CalendarUiModel.Date(
                    date = LocalDate.EPOCH,
                    isSelected = false,
                    isToday = false
                ),
                visibleDates = listOf(
                    CalendarUiModel.Date(
                        date = LocalDate.MIN,
                        isSelected = false,
                        isToday = true
                    ),
                    CalendarUiModel.Date(
                        date = LocalDate.MIN,
                        isSelected = false,
                        isToday = false
                    ),
                    CalendarUiModel.Date(
                        date = LocalDate.MIN,
                        isSelected = false,
                        isToday = false
                    ),
                    CalendarUiModel.Date(
                        date = LocalDate.MIN,
                        isSelected = false,
                        isToday = false
                    ),
                    CalendarUiModel.Date(
                        date = LocalDate.MIN,
                        isSelected = false,
                        isToday = false
                    ),
                    CalendarUiModel.Date(
                        date = LocalDate.MIN,
                        isSelected = false,
                        isToday = false
                    ),
                    CalendarUiModel.Date(
                        date = LocalDate.MIN,
                        isSelected = false,
                        isToday = false
                    ),
                )
            )
        ) { _, _ ->

        }
    }
}

@Composable
fun WeekTabBar(
    data: CalendarUiModel,
    onDateClickListener: (CalendarUiModel.Date, Int) -> Unit,
) {
    AppTabRow(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(50)),
        selectedTabIndex = data.visibleDates.indexOfFirst {
            it.isSelected
        }
    ) {
        data.visibleDates.forEachIndexed { index, date ->
            AppTab(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .padding(horizontal = 16.dp),
                selected = date.isSelected,
                onClick = {
                    onDateClickListener(date, index)
                }
            ) {
                BodyTexts.Level2(
                    text = date.day, // day "Mon", "Tue"
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = if (date.isSelected) {
                        if (date.isToday) Color.Green
                        else AppTheme.colors.primary
                    } else {
                        if (date.isToday) Color.Green
                        else if (index == 0) Color.LightGray
                        else Color.Blue
                    }
                )
                BodyTexts.Level2(
                    text = date.date.dayOfMonth.toString(), // date "15", "16"
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}

@Composable
fun ContentItem(
    date: CalendarUiModel.Date,
    containerColor: Color,
    onClickListener: (CalendarUiModel.Date) -> Unit,
) {
    AppCard(
        onClick = { onClickListener(date) }
    ) {
        Column(
            modifier = Modifier
                .width(40.dp)
                .height(48.dp)
                .padding(4.dp)
        ) {
            BodyTexts.Level2(
                text = date.day, // day "Mon", "Tue"
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            BodyTexts.Level2(
                text = date.date.dayOfMonth.toString(), // date "15", "16"
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}