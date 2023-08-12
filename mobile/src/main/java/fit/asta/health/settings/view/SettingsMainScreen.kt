package fit.asta.health.settings.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.NightShelter
import androidx.compose.material.icons.filled.SettingsPhone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.R
import fit.asta.health.common.ui.components.*
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppCard
import fit.asta.health.common.ui.components.generic.AppDialog
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.setAppTheme
import fit.asta.health.settings.data.SettingsUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenLayout(
    builtVersion: String,
    onClickEvent: (key: SettingsUiEvent) -> Unit
) {

    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    var showDeleteConfirmationDialog by rememberSaveable {
        mutableStateOf(false)
    }

    AnimatedVisibility(showDeleteConfirmationDialog) {
        AppDialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = { showDeleteConfirmationDialog = false }
        ) {
            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppTexts.HeadlineMedium(
                        text = "Confirm delete?"
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.medium),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppButtons.AppTextButton(
                            onClick = {
                                showDeleteConfirmationDialog = false
                            }
                        ) {
                            Text(text = "Cancel")
                        }
                        AppButtons.AppTextButton(
                            onClick = {
                                showDeleteConfirmationDialog = false
                                onClickEvent(SettingsUiEvent.DELETE)
                            }
                        ) {
                            Text(text = "Yes")
                        }
                    }
                }
            }
        }
    }

    AppScaffold(snackBarHostState = snackBarHostState) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            AppTopBar(
                title = stringResource(id = R.string.title_settings),
                onBack = { onClickEvent(SettingsUiEvent.BACK) }
            )

            PreferenceCategory(titleId = R.string.user_pref_support_us_cat_title) {
                PreferenceItem(
                    titleId = R.string.user_pref_share_app_title,
                    imageVector = Icons.Default.Share
                ) { onClickEvent(SettingsUiEvent.SHARE) }
                PreferenceItem(
                    title = "Subscribe",
                    imageVector = Icons.Default.Subscriptions
                ) { onClickEvent(SettingsUiEvent.SUBSCRIBE) }
                PreferenceItem(
                    title = "Refer and earn",
                    imageVector = Icons.Default.MonetizationOn
                ) { onClickEvent(SettingsUiEvent.REFERRAL) }
                PreferenceItem(
                    title = "Saved Address",
                    imageVector = Icons.Default.LocationOn
                ) { onClickEvent(SettingsUiEvent.ADDRESS) }

                PreferenceItem(
                    title = "Wallet",
                    imageVector = Icons.Default.AccountBalanceWallet
                ) { onClickEvent(SettingsUiEvent.WALLET) }
                PreferenceItem(
                    titleId = R.string.user_pref_rate_us_title,
                    imageVector = Icons.Default.StarRate
                ) { onClickEvent(SettingsUiEvent.RATE) }
                PreferenceItem(
                    titleId = R.string.user_pref_feedback_title,
                    imageVector = Icons.Default.Feedback
                ) { onClickEvent(SettingsUiEvent.FEEDBACK) }
            }

            PreferenceCategory(titleId = R.string.user_pref_display_cat_title) {
                ListPreference(
                    titleId = R.string.user_pref_theme_title,
                    imageVector = Icons.Default.ColorLens,
                    entries = stringArrayResource(id = R.array.user_pref_theme_entries),
                    values = stringArrayResource(id = R.array.user_pref_theme_values)
                ) {
                    setAppTheme(it, context)
                }
            }

            PreferenceCategory(titleId = R.string.user_pref_account_cat_title) {
                PreferenceItem(
                    titleId = R.string.user_pref_sign_out_title,
                    imageVector = Icons.Default.Logout
                ) { onClickEvent(SettingsUiEvent.SIGNOUT) }
                PreferenceItem(
                    titleId = R.string.user_pref_delete_account_title,
                    secondary = stringResource(id = R.string.user_pref_delete_account_summary),
                    imageVector = Icons.Default.DeleteForever
                ) { showDeleteConfirmationDialog = true }
            }

            PreferenceCategory(titleId = R.string.user_pref_about_cat_title) {
                PreferenceItem(
                    titleId = R.string.user_pref_bug_report_title,
                    imageVector = Icons.Default.BugReport
                ) { onClickEvent(SettingsUiEvent.BUG) }
                PreferenceItem(
                    titleId = R.string.user_pref_terms_of_use_title,
                    imageVector = Icons.Default.FileCopy
                ) { onClickEvent(SettingsUiEvent.TERMS) }
                PreferenceItem(
                    titleId = R.string.user_pref_privacy_policy_title,
                    imageVector = Icons.Default.NightShelter
                ) { onClickEvent(SettingsUiEvent.PRIVACY) }
                PreferenceItem(
                    titleId = R.string.user_pref_version_title,
                    secondary = builtVersion,
                    imageVector = Icons.Default.SettingsPhone
                ) { onClickEvent(SettingsUiEvent.VERSION) }
            }
        }
    }
}

@Composable
fun PreferenceCategory(
    titleId: Int? = null,
    content: @Composable () -> Unit
) {
    Column {
        if (titleId != null) Text(
            text = stringResource(id = titleId),
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
    titleId: Int = 0,
    title: String? = null,
    secondary: String? = null,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.extraSmall)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.padding(end = spacing.medium)
        )
        Column {
            Text(text = title ?: stringResource(id = titleId))
            if (secondary != null) Text(text = secondary)
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPreference(
    titleId: Int,
    imageVector: ImageVector,
    entries: Array<String>,
    values: Array<String>,
    onValueChange: (String) -> Unit
) {
    val title = stringResource(id = titleId)
    val context = LocalContext.current
    val theme = PrefManager.getTheme(context)
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
            imageVector = imageVector,
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
            onDismissRequest = { showDialog = false }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
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
        }
    }
}