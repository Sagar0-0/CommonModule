package com.example.referral.view

sealed class ReferralDestination(val route: String) {
    data object Share : ReferralDestination("rs_share")
}