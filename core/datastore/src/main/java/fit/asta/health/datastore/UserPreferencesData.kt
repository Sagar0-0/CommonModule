package fit.asta.health.datastore

data class UserPreferencesData(
    val onboardingShown: Boolean = false,
    val notificationStatus: Boolean = false,
    val locationPermissionRejectedCount: Int = 0,
    val currentAddress: String = ""
)
