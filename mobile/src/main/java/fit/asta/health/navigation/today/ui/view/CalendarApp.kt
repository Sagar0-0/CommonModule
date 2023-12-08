package fit.asta.health.navigation.today.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.BodyTexts


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
fun WeekTabBar(
    modifier: Modifier = Modifier,
    data: CalendarUiModel,
    onDateClickListener: (CalendarUiModel.Date, Int) -> Unit,
    strokeWidth: Float = 10f,
    selectedColor: Color = AppTheme.colors.primary,
) {

    // Card Layout Which is elevated
    // TODO :- Removing the Elevated card and using AppElevatedCard is changing shape
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = AppTheme.elevation.level4),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.Transparent),
        shape = RectangleShape
    ) {

        // Contains all the Text Options
        Row(
            modifier = modifier
                .background(AppTheme.colors.background)
                .fillMaxWidth()
        ) {
//            data.visibleDates.forEachIndexed { index, date ->
//                ContentItem(
//                    date = date,
//                    containerColor = if (date.isSelected) {
//                        if (date.isToday) Color.Green
//                        else AppTheme.colors.primary
//                    } else {
//                        if (date.isToday) Color.Green.copy(alpha = .5f)
//                        else if (index == 0) Color.LightGray
//                        else AppTheme.colors.primaryContainer
//                    },
//                    onClickListener = onDateClickListener
//                )
//            }

            data.visibleDates.forEachIndexed { index, date ->

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .size(AppTheme.boxSize.level6)
                        .clickable {
                            onDateClickListener(date, index)
                        }
                        .drawBehind {
                            if (date.isSelected)
                                drawLine(
                                    color = selectedColor,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = strokeWidth
                                )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
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
    }
}

@Composable
fun ContentItem(
    date: CalendarUiModel.Date,
    containerColor: Color,
    onClickListener: (CalendarUiModel.Date) -> Unit,
) {
    AppCard(
        modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
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