package fit.asta.health.scheduler.compose


//@RequiresApi(Build.VERSION_CODES.N)
//@Preview
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AlarmSettingLayout(
//    onNavigateToTag: (() -> Unit)?={},
//    onNavigateToLabel: (() -> Unit)?={},
//    onNavigateToDesc: (() -> Unit)?={},
//    onNavigateToIntervalSettings: (() -> Unit)?={},
//    onNavigateToReminderMode: (() -> Unit)?={},
//    onNavigateToVibration: (() -> Unit)?={},
//    onNavigateToSound: (() -> Unit)?={},
//) {
//    Scaffold(topBar = {
//        NavigationBar(content = {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                IconButton(onClick = { /*TODO*/ }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_round_close_24),
//                        contentDescription = null,
//                        Modifier.size(24.dp)
//                    )
//                }
//                Text(
//                    text = "Alarm Setting",
//                    fontSize = 20.sp,
//                    color = MaterialTheme.colorScheme.onTertiaryContainer,
//                    textAlign = TextAlign.Center
//                )
//                IconButton(onClick = { /*TODO*/ }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_baseline_check_24),
//                        contentDescription = null,
//                        Modifier.size(24.dp),
//                        tint = MaterialTheme.colorScheme.primary
//                    )
//                }
//            }
//        }, tonalElevation = 10.dp, containerColor = Color.White)
//    }, content = {
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(it)
//                .verticalScroll(rememberScrollState())
//        ) {
//            DigitalDemo(onTimeChange = {})
//            RepeatAlarm(alarmSettingUiState = alarmSettingUiState)
//            OnlyToggleButton(
//                icon = R.drawable.ic_ic24_alert,
//                title = "Status",
//                switchTitle = "",
//                onNavigateToClickText = null
//            )
//            onNavigateToTag?.let { it1 ->
//                AlarmIconButton(
//                    image = R.drawable.ic_ic24_alarm_snooze,
//                    title = "Tag",
//                    arrowTitle = "Water",
//                    arrowImage = R.drawable.ic_ic24_right_arrow, onNavigateToScreen = it1
//                )
//            }
//            onNavigateToLabel?.let { it1 ->
//                AlarmIconButton(
//                    image = R.drawable.ic_ic24_label,
//                    title = "Label",
//                    arrowTitle = "Power Nap",
//                    arrowImage = R.drawable.ic_ic24_right_arrow, onNavigateToScreen = it1
//                )
//            }
//            onNavigateToDesc?.let { it1 ->
//                AlarmIconButton(
//                    image = R.drawable.ic_ic24_description,
//                    title = "Description",
//                    arrowTitle = "Relax to energise",
//                    arrowImage = R.drawable.ic_ic24_right_arrow, onNavigateToScreen = it1
//                )
//            }
//            onNavigateToIntervalSettings?.let { it1 ->
//                AlarmIconButton(
//                    image = R.drawable.ic_ic24_time,
//                    title = "Intervals Settings",
//                    arrowTitle = "Power Nap",
//                    arrowImage = R.drawable.ic_ic24_right_arrow,
//                    onNavigateToScreen = it1
//                )
//            }
//            onNavigateToReminderMode?.let { it1 ->
//                AlarmIconButton(
//                    image = R.drawable.ic_ic24_notification,
//                    title = "Reminder Mode",
//                    arrowTitle = "Notification",
//                    arrowImage = R.drawable.ic_ic24_right_arrow,
//                    onNavigateToScreen = it1
//                )
//            }
//            OnlyToggleButton(
//                icon = R.drawable.ic_ic24_vibrate,
//                title = "Vibration ",
//                switchTitle = "Pattern 1", onNavigateToClickText = onNavigateToVibration
//            )
//            OnlyToggleButton(
//                icon = R.drawable.ic_ic24_voice,
//                title = "Sound",
//                switchTitle = "Spring", onNavigateToClickText = onNavigateToSound
//            )
//            OnlyToggleButton(
//                icon = R.drawable.ic_ic24_warning,
//                title = "Important",
//                switchTitle = "", onNavigateToClickText = null
//            )
//            Text(
//                text = "This will make sure you attempt with the help of flashlight, sound changes, vibration etc.",
//                color = MaterialTheme.colorScheme.onSurfaceVariant,
//                fontSize = 16.sp,
//                modifier = Modifier.padding(horizontal = 16.dp)
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//    })
//}
