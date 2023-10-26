package fit.asta.health.feature.settings.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.NightShelter
import androidx.compose.material.icons.filled.SettingsPhone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.resources.strings.R
import fit.asta.health.ui.common.AppDialogPopUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenLayout(
    appVersionNumber: String,
    selectedTheme: String,
    onUiEvent: (key: SettingsUiEvent) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    var showDeleteConfirmationDialog by rememberSaveable { mutableStateOf(false) }

    // This is the App Dialog which pops up
    AnimatedVisibility(showDeleteConfirmationDialog) {
        AppDialogPopUp(
            headingText = R.string.confirm_delete.toStringFromResId(),
            bodyText = "Are you sure you want to delete ??",
            primaryButtonText = R.string.yes.toStringFromResId(),
            secondaryButtonText = R.string.cancel.toStringFromResId(),
            onDismiss = { showDeleteConfirmationDialog = false }
        ) {
            showDeleteConfirmationDialog = false
            onUiEvent(SettingsUiEvent.DELETE)
        }
    }

    // Scaffold for the Screen
    AppScaffold(
        snackBarHostState = snackBarHostState,
        topBar = {
            // Top App Bar
            AppTopBar(
                title = stringResource(id = R.string.title_settings),
                onBack = { onUiEvent(SettingsUiEvent.BACK) }
            )
        }
    ) { padding ->

        // This Column contains all the necessary Composable Functions
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
        ) {

            // support Us Card Layout Function
            SettingsCardLayout(title = stringResource(id = R.string.user_pref_support_us_cat_title)) {

                // Share
                SettingsCardItem(
                    icon = Icons.Default.Share,
                    text = stringResource(R.string.user_pref_share_app_title)
                ) { onUiEvent(SettingsUiEvent.SHARE) }

                // Subscribe
                SettingsCardItem(
                    icon = Icons.Default.Subscriptions,
                    text = R.string.subscribe.toStringFromResId()
                ) { onUiEvent(SettingsUiEvent.NavigateToSubscription) }

                // Orders
                SettingsCardItem(
                    icon = Icons.Default.ShoppingCart,
                    text = R.string.orders.toStringFromResId()
                ) { onUiEvent(SettingsUiEvent.NavigateToOrders) }

                // Refer and Earn
                SettingsCardItem(
                    icon = Icons.Default.MonetizationOn,
                    text = R.string.refer_and_earn.toStringFromResId()
                ) { onUiEvent(SettingsUiEvent.REFERRAL) }

                // Saved Addresses
                SettingsCardItem(
                    icon = Icons.Default.LocationOn,
                    text = R.string.saved_address.toStringFromResId()
                ) { onUiEvent(SettingsUiEvent.ADDRESS) }

                // Wallet
                SettingsCardItem(
                    icon = Icons.Default.AccountBalanceWallet,
                    text = R.string.wallet.toStringFromResId()
                ) { onUiEvent(SettingsUiEvent.WALLET) }
            }

            // This is the Display card section
            SettingsCardLayout(title = R.string.user_pref_display_cat_title.toStringFromResId()) {

                // Theme
                SettingsPreferenceDialog(
                    titleId = R.string.user_pref_theme_title,
                    imageVector = Icons.Default.ColorLens,
                    theme = selectedTheme,
                    entries = stringArrayResource(id = R.array.user_pref_theme_entries),
                    values = stringArrayResource(id = R.array.user_pref_theme_values)
                ) {
                    onUiEvent(SettingsUiEvent.SetTheme(it))
                }
            }

            // This is the Account Section
            SettingsCardLayout(title = R.string.user_pref_account_cat_title.toStringFromResId()) {

                // Sign out
                SettingsCardItem(
                    icon = Icons.Default.Logout,
                    text = R.string.user_pref_sign_out_title.toStringFromResId()
                ) { onUiEvent(SettingsUiEvent.SIGNOUT) }

                // Delete
                SettingsCardItem(
                    icon = Icons.Default.DeleteForever,
                    text = R.string.user_pref_delete_account_title.toStringFromResId()
                ) { showDeleteConfirmationDialog = true }
            }

            // This is the About Card Section
            SettingsCardLayout(title = R.string.user_pref_about_cat_title.toStringFromResId()) {

                // Bug Report
                SettingsCardItem(
                    icon = Icons.Default.BugReport,
                    text = R.string.user_pref_bug_report_title.toStringFromResId()
                ) { onUiEvent(SettingsUiEvent.BUG) }

                // Terms of Use
                SettingsCardItem(
                    icon = Icons.Default.FileCopy,
                    text = R.string.user_pref_terms_of_use_title.toStringFromResId()
                ) { onUiEvent(SettingsUiEvent.TERMS) }

                // Privacy Policy
                SettingsCardItem(
                    icon = Icons.Default.NightShelter,
                    text = R.string.user_pref_privacy_policy_title.toStringFromResId()
                ) { onUiEvent(SettingsUiEvent.PRIVACY) }

                // Version
                SettingsCardItem(
                    icon = Icons.Default.SettingsPhone,
                    text = R.string.user_pref_version_title.toStringFromResId() + appVersionNumber
                ) { onUiEvent(SettingsUiEvent.VERSION) }
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
        }
    }
}