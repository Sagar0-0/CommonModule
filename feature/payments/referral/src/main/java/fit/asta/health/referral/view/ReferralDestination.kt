package fit.asta.health.referral.view

sealed class ReferralDestination(val route: String) {
    data object Share : ReferralDestination("rs_share")
}