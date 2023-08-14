package fit.asta.health.common.utils

data class UserPreferencesData(
    val onboardingShown: Boolean = false,
    val notificationStatus: Boolean = false,
    val locationPermissionRejectedCount: Int = 0,
    val currentAddress: String = ""
)
