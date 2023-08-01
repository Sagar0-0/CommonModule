package fit.asta.health.scheduler.compose.screen.homescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.ui.components.*
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    list: SnapshotStateList<AlarmEntity>,
    hSEvent: (HomeEvent) -> Unit,
    navAlarmSettingHome: () -> Unit
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    AppScaffold(snackBarHostState = snackBarHostState, content = { paddingValues ->
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            items(list) { data ->
                SwipeAlarm(homeUiState = homeUiState, data = data, onSwipe = {
//                    hSEvent(HomeEvent.DeleteAlarm(data, context))
                    coroutineScope.launch {
                        val snackbarResult = snackBarHostState.showSnackbar(
                            message = "Deleted ${data.info.name}",
                            actionLabel = "Undo",
                            duration = SnackbarDuration.Long
                        )
                        when (snackbarResult) {
                            SnackbarResult.ActionPerformed -> {
//                                hSEvent(HomeEvent.UndoAlarm(data, context))
                            }
                            else -> {}
                        }
                    }
                }, onClick = {
//                    hSEvent(HomeEvent.SetAlarm(data, it, context))
                }, onEdit = {
                    navAlarmSettingHome()
                    hSEvent(HomeEvent.EditAlarm(data))
                }
                )
            }
        }
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navAlarmSettingHome,
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.size(80.dp),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        },
        topBar = {
            AppTopBar(title = "Alarms")
        }
    )
}


@Composable
fun SwipeAlarm(
    onSwipe: () -> Unit = {},
    onClick: (Boolean) -> Unit = {},
    onEdit: () -> Unit = {},
    data: AlarmEntity,
    homeUiState: HomeUiState
) {

    val archive = SwipeAction(
        icon = painterResource(id = R.drawable.ic_baseline_delete_24),
        background = Color.Red,
        onSwipe = { onSwipe() }
    )

    SwipeableActionsBox(
        startActions = listOf(archive),
    ) {
        AlarmCard(
            data = data,
            homeUiState = homeUiState,
            onClick = onClick,
            onEdit = onEdit
        )
    }

}


@Composable
fun AlarmCard(
    onClick: (Boolean) -> Unit = {},
    onEdit: () -> Unit = {},
    data: AlarmEntity,
    homeUiState: HomeUiState
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val color = if (isPressed) MaterialTheme.colorScheme.primary else Color.Transparent

    Button(
        onClick = onEdit,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = ButtonDefaults.buttonElevation(5.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        interactionSource = interactionSource,
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(width = 3.dp, color = color)
    ) {
        SwipeAbleAreaAlarm(
            isSelected =data.status,
            description =data.info.description,
            name =data.info.name,
            tag =data.info.tag,
            url =data.info.url,
            buttonState = homeUiState.buttonState,
            onCheckClicked = onClick
        )
    }
}

@Composable
private fun SwipeAbleAreaAlarm(
    isSelected: Boolean,
    description: String,
    name: String,
    tag: String,
    url: String,
    buttonState:Boolean,
    onCheckClicked: (Boolean) -> Unit
) {
    Box {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://img1.asta.fit${url}")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder_tag),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 120.dp, height = 100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Switch(
                checked = isSelected,
                enabled = buttonState,
                onCheckedChange = {
                    onCheckClicked(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    checkedTrackColor = Color.LightGray,
                    uncheckedTrackColor = MaterialTheme.colorScheme.background,
                    checkedBorderColor = MaterialTheme.colorScheme.primary,
                    uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    }
}