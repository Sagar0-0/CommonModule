package fit.asta.health.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import fit.asta.health.R
import fit.asta.health.common.ui.CustomTopBar
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.common.utils.setAppTheme
import fit.asta.health.settings.data.SettingsUiEvent

@Composable
fun SettingsScreenLayout(
    builtVersion: String,
    onClickEvent: (key: SettingsUiEvent) -> Unit
) {

    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            CustomTopBar(stringResource(id = R.string.title_settings)) {
                onClickEvent(SettingsUiEvent.BACK)
            }

//            PreferenceCategory {
//                PreferenceItem(
//                    title = stringResource(id = R.string.user_pref_notification_cat_title),
//                    icon = painterResource(id = R.drawable.ic_notifications)
//                ) { onClickEvent(SettingsUiEvent.NOTIFICATION) }
//            }TODO: Pending implementation

            PreferenceCategory(title = stringResource(id = R.string.user_pref_support_us_cat_title)) {
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_share_app_title),
                    icon = painterResource(id = R.drawable.ic_share)
                ) { onClickEvent(SettingsUiEvent.SHARE) }
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_rate_us_title),
                    icon = painterResource(id = R.drawable.ic_rate_us)
                ) { onClickEvent(SettingsUiEvent.RATE) }
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_feedback_title),
                    icon = painterResource(id = R.drawable.ic_feedback)
                ) { onClickEvent(SettingsUiEvent.FEEDBACK) }
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
                ) { onClickEvent(SettingsUiEvent.SIGNOUT) }
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_delete_account_title),
                    secondary = stringResource(id = R.string.user_pref_delete_account_summary),
                    icon = painterResource(id = R.drawable.ic_delete_forever)
                ) { onClickEvent(SettingsUiEvent.DELETE) }
            }

            PreferenceCategory(title = stringResource(id = R.string.user_pref_about_cat_title)) {
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_bug_report_title),
                    icon = painterResource(id = R.drawable.ic_bug_report)
                ) { onClickEvent(SettingsUiEvent.BUG) }
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_terms_of_use_title),
                    icon = painterResource(id = R.drawable.ic_terms_of_use)
                ) { onClickEvent(SettingsUiEvent.TERMS) }
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_privacy_policy_title),
                    icon = painterResource(id = R.drawable.ic_privacy_policy)
                ) { onClickEvent(SettingsUiEvent.PRIVACY) }
                PreferenceItem(
                    title = stringResource(id = R.string.user_pref_version_title),
                    secondary = builtVersion,
                    icon = painterResource(id = R.drawable.ic_build_ver)
                ) { onClickEvent(SettingsUiEvent.VERSION) }
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
    var selectedIndex by remember { mutableIntStateOf(idx) }

    TextButton(
        onClick = { showDialog = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.extraSmall)
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