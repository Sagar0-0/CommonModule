package fit.asta.health.feature.settings.view

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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppDialog
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.resources.strings.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenLayout(
    builtVersion: String,
    theme: String,
    onClickEvent: (key: SettingsUiEvent) -> Unit
) {
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
                        .padding(AppTheme.spacing.level3),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppTexts.HeadlineMedium(
                        text = R.string.confirm_delete.toStringFromResId()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.spacing.level3),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppButtons.AppTextButton(
                            onClick = {
                                showDeleteConfirmationDialog = false
                            }
                        ) {
                            AppTexts.TitleMedium(text = R.string.cancel.toStringFromResId())
                        }
                        AppButtons.AppTextButton(
                            onClick = {
                                showDeleteConfirmationDialog = false
                                onClickEvent(SettingsUiEvent.DELETE)
                            }
                        ) {
                            AppTexts.TitleMedium(text = R.string.yes.toStringFromResId())
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
                    title = R.string.subscribe.toStringFromResId(),
                    imageVector = Icons.Default.Subscriptions
                ) { onClickEvent(SettingsUiEvent.NavigateToSubscription) }
                PreferenceItem(
                    title = R.string.orders.toStringFromResId(),
                    imageVector = Icons.Default.ShoppingCart
                ) { onClickEvent(SettingsUiEvent.NavigateToOrders) }
                PreferenceItem(
                    title = R.string.refer_and_earn.toStringFromResId(),
                    imageVector = Icons.Default.MonetizationOn
                ) { onClickEvent(SettingsUiEvent.REFERRAL) }
                PreferenceItem(
                    title = R.string.saved_address.toStringFromResId(),
                    imageVector = Icons.Default.LocationOn
                ) { onClickEvent(SettingsUiEvent.ADDRESS) }

                PreferenceItem(
                    title = R.string.wallet.toStringFromResId(),
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
                    theme = theme,
                    entries = stringArrayResource(id = R.array.user_pref_theme_entries),
                    values = stringArrayResource(id = R.array.user_pref_theme_values)
                ) {
                    onClickEvent(SettingsUiEvent.SetTheme(it))
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
        if (titleId != null) AppTexts.TitleMedium(
            text = stringResource(id = titleId),
            modifier = Modifier.padding(
                start = AppTheme.spacing.level3,
                top = AppTheme.spacing.level2
            )
        )
        Column(
            modifier = Modifier.padding(
                vertical = AppTheme.spacing.level2,
                horizontal = AppTheme.spacing.level2
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
    AppButtons.AppTextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level1)
    ) {
        AppDefaultIcon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.padding(end = AppTheme.spacing.level3)
        )
        Column {
            AppTexts.TitleMedium(text = title ?: stringResource(id = titleId))
            if (secondary != null) AppTexts.TitleMedium(text = secondary)
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ListPreference(
    titleId: Int,
    imageVector: ImageVector,
    theme: String,
    entries: Array<String>,
    values: Array<String>,
    onValueChange: (String) -> Unit
) {
    val title = stringResource(id = titleId)
    val idx = values.indexOf(theme.ifEmpty { "system" })
    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(idx) }

    AppButtons.AppTextButton(
        onClick = { showDialog = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level1)
    ) {
        AppDefaultIcon(
            imageVector = imageVector,
            contentDescription = title,
            modifier = Modifier.padding(end = AppTheme.spacing.level3)
        )

        AppTexts.TitleMedium(
            text = title
        )

        Spacer(modifier = Modifier.weight(1f))

        AppTexts.TitleMedium(
            text = entries[selectedIndex],
        )

        AppDefaultIcon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null
        )
    }

    if (showDialog) {
        AppDialog(
            onDismissRequest = { showDialog = false }
        ) {
            AppSurface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    entries.forEachIndexed { index, entry ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AppButtons.AppRadioButton(
                                selected = selectedIndex == index,
                                onClick = {
                                    showDialog = false
                                    selectedIndex = index
                                    onValueChange(values[selectedIndex])
                                }
                            )
                            AppTexts.TitleMedium(text = entry)
                        }
                    }
                }
            }
        }
    }
}