package fit.asta.health.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import fit.asta.health.R
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.common.utils.setAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenLayout(
    snackbarMsg: String?,
    builtVersion: String,
    onClick: (key: SettingsUiEvent) -> Unit
) {

    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_settings),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onClick(SettingsUiEvent.BACK) }) {
                        Icon(
                            Icons.Outlined.NavigateBefore,
                            "back",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ), modifier = Modifier.shadow(elevation = cardElevation.medium)
            )

            PreferenceCategory {
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_notification_cat_title),
                    icon = painterResource(id = R.drawable.ic_notifications)
                ) { onClick(SettingsUiEvent.NOTIFICATION) }
            }

            PreferenceCategory(title = stringResource(id = R.string.user_pref_support_us_cat_title)) {
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_share_app_title),
                    icon = painterResource(id = R.drawable.ic_share)
                ) { onClick(SettingsUiEvent.SHARE) }
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_rate_us_title),
                    icon = painterResource(id = R.drawable.ic_rate_us)
                ) { onClick(SettingsUiEvent.RATE) }
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_feedback_title),
                    icon = painterResource(id = R.drawable.ic_feedback)
                ) { onClick(SettingsUiEvent.FEEDBACK) }
            }

            PreferenceCategory(title = stringResource(id = R.string.user_pref_display_cat_title)) {
                ListPreference(
                    title = stringResource(id = R.string.user_pref_theme_title),
                    icon = painterResource(id = R.drawable.ic_theme_settings),
                    entries = stringArrayResource(id = R.array.user_pref_theme_entries),
                    values = stringArrayResource(id = R.array.user_pref_theme_values)
                ) {
                    setAppTheme(it, context)
                }
            }

            PreferenceCategory(title = stringResource(id = R.string.user_pref_account_cat_title)) {
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_sign_out_title),
                    icon = painterResource(id = R.drawable.ic_exit_sign_out)
                ) { onClick(SettingsUiEvent.SIGNOUT) }
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_delete_account_title),
                    secondary = stringResource(id = R.string.user_pref_delete_account_summary),
                    icon = painterResource(id = R.drawable.ic_delete_forever)
                ) { onClick(SettingsUiEvent.DELETE) }
            }

            PreferenceCategory(title = stringResource(id = R.string.user_pref_about_cat_title)) {
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_bug_report_title),
                    icon = painterResource(id = R.drawable.ic_bug_report)
                ) { onClick(SettingsUiEvent.BUG) }
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_terms_of_use_title),
                    icon = painterResource(id = R.drawable.ic_terms_of_use)
                ) { onClick(SettingsUiEvent.TERMS) }
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_privacy_policy_title),
                    icon = painterResource(id = R.drawable.ic_privacy_policy)
                ) { onClick(SettingsUiEvent.PRIVACY) }
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_version_title),
                    secondary = builtVersion,
                    icon = painterResource(id = R.drawable.ic_build_ver)
                ) { onClick(SettingsUiEvent.VERSION) }
            }
        }

        if (snackbarMsg != null) {
            LaunchedEffect(Unit) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = snackbarMsg,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}

@Composable
fun PreferenceCategory(
    title: String? = null,
    content: @Composable () -> Unit
) {
    Column {
        if (title != null) Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = spacing.medium, top = spacing.small)
        )
        Column(
            modifier = Modifier.padding(
                vertical = spacing.small,
                horizontal = spacing.small
            )
        ) {
            content()
        }
    }
}

@Composable
fun PreferenceItem(
    title: String,
    secondary: String? = null,
    icon: Painter,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.extraSmall)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.padding(end = spacing.medium))
        Column {
            Text(text = title)
            if (secondary != null) Text(text = secondary)
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ListPreference(
    title: String,
    icon: Painter,
    entries: Array<String>,
    values: Array<String>,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current
    val theme = PrefUtils.getTheme(context)
    val idx = values.indexOf(theme)
    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(idx) }

    Column(
        modifier = Modifier
            .clickable(onClick = { showDialog = true })
            .padding(spacing.extraSmall)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = title,
                modifier = Modifier.padding(end = spacing.medium)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = entries[selectedIndex],
                style = MaterialTheme.typography.titleSmall
            )

            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = title)
            },
            buttons = {},
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    entries.forEachIndexed { index, entry ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedIndex == index,
                                onClick = {
                                    showDialog = false
                                    selectedIndex = index
                                    onValueChange(values[selectedIndex])
                                }
                            )
                            Text(text = entry)
                        }
                    }
                }
            }
        )
    }
}