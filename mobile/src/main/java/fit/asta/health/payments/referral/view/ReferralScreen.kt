package fit.asta.health.payments.referral.view

sealed class ReferralScreen(val route: String) {
    object Share : ReferralScreen("rs_share")
}
