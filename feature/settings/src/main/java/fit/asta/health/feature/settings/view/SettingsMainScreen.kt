package fit.asta.health.feature.settings.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.NightShelter
import androidx.compose.material.icons.filled.SettingsPhone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.resources.strings.R
import fit.asta.health.ui.common.AppDialogPopUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsHomeScreen(
    appVersionNumber: String,
    selectedTheme: String,
    deleteAccountState: UiState<Boolean>,
    userLogoutState: UiState<Boolean>,
    onUiEvent: (key: SettingsUiEvent) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    var showDeleteConfirmationDialog by rememberSaveable { mutableStateOf(false) }
    var showSignOutConfirmationDialog by rememberSaveable { mutableStateOf(false) }

    // Scaffold for the Screen
    AppScaffold(
        snackBarHostState = snackBarHostState,
        isScreenLoading = deleteAccountState is UiState.Loading || userLogoutState is UiState.Loading,
        topBar = {
            // Top App Bar
            AppTopBar(
                title = stringResource(id = R.string.title_settings),
                onBack = { onUiEvent(SettingsUiEvent.BACK) }
            )
        }
    ) { padding ->

        AppUiStateHandler(
            uiState = deleteAccountState,
            onLoading = {},
            onErrorRetry = {
                onUiEvent(SettingsUiEvent.DELETE)
            },
            onErrorMessage = {
                onUiEvent(SettingsUiEvent.ResetDeleteState)
            }
        ) {
            onUiEvent(SettingsUiEvent.ResetDeleteState)
            onUiEvent(SettingsUiEvent.NavigateToAuthScreen)
        }
        AppUiStateHandler(
            uiState = userLogoutState,
            onLoading = {},
            onErrorRetry = {
                onUiEvent(SettingsUiEvent.Logout)
            },
            onErrorMessage = {
                onUiEvent(SettingsUiEvent.ResetLogoutState)
            }
        ) {
            onUiEvent(SettingsUiEvent.ResetLogoutState)
            onUiEvent(SettingsUiEvent.NavigateToAuthScreen)
        }

        // This Column contains all the necessary Composable Functions for settings screen
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // support Us Group Card
//            SettingsGroupCard(
//                title = stringResource(id = R.string.user_pref_support_us_cat_title)
//            ) {

//                // Share
//                SettingsCardItem(
//                    icon = Icons.Default.Share,
//                    text = stringResource(R.string.user_pref_share_app_title)
//                ) { onUiEvent(SettingsUiEvent.SHARE) }
//
//                // Orders
//                SettingsCardItem(
//                    icon = Icons.Default.ShoppingCart,
//                    text = R.string.orders.toStringFromResId()
//                ) { onUiEvent(SettingsUiEvent.NavigateToOrders) }

            // Refer and Earn
//                SettingsCardItem(
//                    icon = Icons.Default.MonetizationOn,
//                    text = R.string.refer_and_earn.toStringFromResId()
//                ) { onUiEvent(SettingsUiEvent.REFERRAL) }

//                // Saved Addresses
//                SettingsCardItem(
//                    icon = Icons.Default.LocationOn,
//                    text = R.string.saved_address.toStringFromResId()
//                ) { onUiEvent(SettingsUiEvent.ADDRESS) }

            // Wallet
//                SettingsCardItem(
//                    icon = Icons.Default.AccountBalanceWallet,
//                    text = R.string.wallet.toStringFromResId()
//                ) { onUiEvent(SettingsUiEvent.WALLET) }
//            }

            // This is the Display card section
            SettingsGroupCard(title = R.string.user_pref_display_cat_title.toStringFromResId()) {

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

            // This is the About Card Section
            SettingsGroupCard(title = R.string.user_pref_about_cat_title.toStringFromResId()) {

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
                    text = R.string.user_pref_version_title.toStringFromResId() + " " + appVersionNumber
                ) { onUiEvent(SettingsUiEvent.VERSION) }
            }

            // This is the Account Section
            SettingsGroupCard(
                title = R.string.user_pref_account_cat_title.toStringFromResId()
            ) {

                // Sign out
                SettingsCardItem(
                    icon = Icons.Default.Logout,
                    iconTint = Color.Red,
                    textColor = Color.Red,
                    text = R.string.user_pref_sign_out_title.toStringFromResId()
                ) { showSignOutConfirmationDialog = true }

                // Delete
                SettingsCardItem(
                    icon = Icons.Default.DeleteForever,
                    iconTint = Color.Red,
                    textColor = Color.Red,
                    text = R.string.user_pref_delete_account_title.toStringFromResId()
                ) { showDeleteConfirmationDialog = true }
            }

            //To add vertical space by targeting vertical spacing from parent
            Spacer(modifier = Modifier)
        }

        AnimatedVisibility(showDeleteConfirmationDialog) {
            AppDialogPopUp(
                headingText = R.string.confirm_delete.toStringFromResId(),
                bodyText = "This operation can not be undone.",
                primaryButtonText = R.string.yes.toStringFromResId(),
                secondaryButtonText = R.string.cancel.toStringFromResId(),
                onDismiss = { showDeleteConfirmationDialog = false }
            ) {
                showDeleteConfirmationDialog = false
                onUiEvent(SettingsUiEvent.DELETE)
            }
        }

        AnimatedVisibility(showSignOutConfirmationDialog) {
            AppDialogPopUp(
                headingText = R.string.confirm_logout.toStringFromResId(),
                bodyText = "This operation can not be undone.",
                primaryButtonText = R.string.yes.toStringFromResId(),
                secondaryButtonText = R.string.cancel.toStringFromResId(),
                onDismiss = { showSignOutConfirmationDialog = false }
            ) {
                showSignOutConfirmationDialog = false
                onUiEvent(SettingsUiEvent.Logout)
            }
        }


    }
}