package fit.asta.health.navigation.today.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.navigation.home.view.component.NameAndMoodHomeScreenHeader
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun TodayContent() {
    val list = listOf("Fasting", "Sleep", "Morning Walk", "Meditation", "Medicines")
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        NameAndMoodHomeScreenHeader()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
            contentPadding = PaddingValues(0.dp)
        ) {
            list.forEachIndexed { index, title ->
                item {
                    SwipeDemoToday(title=title, onSwipe = {

                    })
                }
            }
        }
    }

}

@Composable
@Preview
fun TodayItem(
    modifier: Modifier = Modifier,
    image: String = "",
    title: String = "Fasting",
    description: String = "Fasting to cleanse your body",
    progress: String = "44%",
    time: String = "9:00am",
    onDone: () -> Unit = {},
    onReschedule: () -> Unit = {}
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 6.dp,
            focusedElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = spacing.small),
                modifier = modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(getImageUrl(url = image))
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder_tag),
                    contentDescription = stringResource(R.string.description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .height(120.dp)
                        .width(80.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(spacing.small),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(space = spacing.small),
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.weight(.5f),
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier
                                .weight(.2f)
                                .clip(RoundedCornerShape(15.dp))
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = .3f)
                                ),
                            text = progress,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    }
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1
                    )
                    OutlinedButton(
                        onClick = onDone,
                        border = BorderStroke(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Text(
                            text = "Done",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = spacing.small),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(.5f),
                    text = time,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier
                        .weight(.5f)
                        .clickable { onReschedule() },
                    text = "Reschedule",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
@Preview
fun SwipeDemoToday(
    onSwipe: () -> Unit = {},
    image: String = "/tags/Breathing+Tag.png",
    title: String = "Fasting",
    description: String = "Fasting to cleanse your body",
    progress: String = "44%",
    time: String = "9:00am",
    onDone: () -> Unit = {},
    onReschedule: () -> Unit = {}
) {

    val archive = SwipeAction(
        icon = painterResource(id = R.drawable.ic_baseline_delete_24),
        background = Color.Red,
        onSwipe = { onSwipe() }
    )

    SwipeableActionsBox(
        startActions = listOf(archive),
        backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.background
    ) {
        TodayCard(
            image = image,
            time = time,
            title = title,
            description = description,
            progress = progress,
            onDone = onDone,
            onReschedule = onReschedule
        )
    }

}

@Composable
fun TodayCard(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
    description: String,
    progress: String,
    time: String,
    onDone: () -> Unit,
    onReschedule: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val color = if (!isPressed) MaterialTheme.colorScheme.primary else Color.Green
    Button(
        onClick = { },
        modifier = modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = ButtonDefaults.buttonElevation(5.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(16.dp),
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = spacing.small),
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(getImageUrl(url = image))
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder_tag),
                    contentDescription = stringResource(R.string.description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .height(120.dp)
                        .width(80.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(spacing.small),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(space = spacing.small),
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.weight(.5f),
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier
                                .weight(.2f)
                                .clip(RoundedCornerShape(15.dp))
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = .3f)
                                ),
                            text = progress,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    }
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    OutlinedButton(
                        onClick = onDone,
                        interactionSource = interactionSource,
                        border = BorderStroke(
                            width = 2.dp,
                            color = color
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Text(
                            text = "Done",
                            style = MaterialTheme.typography.bodyMedium,
                            color = color
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = spacing.small),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(.5f),
                    text = time,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    modifier = Modifier
                        .weight(.5f)
                        .clickable { onReschedule() },
                    text = "Reschedule",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}
