package fit.asta.health.scheduler.compose.screen.homescreen

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.navigation.AlarmSchedulerScreen
import fit.asta.health.scheduler.viewmodel.SchedulerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(schedulerViewModel: SchedulerViewModel, navController: NavController) {
    val homeUiState = schedulerViewModel.homeUiState.value
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    Scaffold(scaffoldState = scaffoldState, content = {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(it)
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            items(homeUiState.alarmList) { data ->
                SwipeAlarm(data = data, onSwipe = {
                    val deletedTag = data
                    schedulerViewModel.HSEvent(HomeEvent.DeleteAlarm(data,context))
                    coroutineScope.launch {
                        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                            message = "Deleted ${data.info.name}",
                            actionLabel = "Undo",
                            duration = SnackbarDuration.Long
                        )
                        when (snackbarResult) {
                            SnackbarResult.ActionPerformed -> {
                                schedulerViewModel.HSEvent(HomeEvent.InsertAlarm(deletedTag))
                            }
                            else -> {}
                        }
                    }
                }, onClick = {

                })
            }
        }
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(route = AlarmSchedulerScreen.AlarmSettingHome.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.size(80.dp),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        },
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Alarms",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
            }, navigationIcon = {
                androidx.compose.material3.IconButton(onClick = {  }) {
                    Icon(
                        Icons.Outlined.NavigateBefore,
                        "back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            })
        })
}


@Composable
fun AlarmCard(
    onClick: (Boolean) -> Unit = {},
    isSelected: Boolean,
    description: String,
    name: String,
    tag: String,
    url: String

) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val color = if (isPressed) MaterialTheme.colorScheme.primary else Color.Transparent

    androidx.compose.material3.Button(
        onClick = { },
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
        SwipeAbleAreaAlarm(isSelected, description, name, tag, url, onCheckClicked = onClick)
    }
}

@Composable
private fun SwipeAbleAreaAlarm(
    isSelected: Boolean,
    description: String,
    name: String,
    tag: String,
    url: String,
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
                modifier = Modifier.size(width = 120.dp, height = 100.dp)
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
            androidx.compose.material3.Switch(
                checked = isSelected,
                onCheckedChange = {
                    onCheckClicked(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    checkedTrackColor=Color.LightGray,
                    uncheckedTrackColor=MaterialTheme.colorScheme.background,
                    checkedBorderColor=MaterialTheme.colorScheme.primary,
                    uncheckedBorderColor=MaterialTheme.colorScheme.primary,)
            )
        }
    }
}


@Composable
fun SwipeAlarm(onSwipe: () -> Unit = {}, onClick: () -> Unit = {}, data: AlarmEntity) {

    val archive = SwipeAction(
        icon = painterResource(id = R.drawable.ic_baseline_delete_24),
        background = Color.Red,
        onSwipe = { onSwipe() }
    )

    SwipeableActionsBox(
        startActions = listOf(archive),
    ) {
        AlarmCard(
            name = data.info.name,
            description = data.info.description,
            url = data.info.url,
            isSelected = data.status,
            tag = data.info.tag,
            onClick = {}
        )
    }

}