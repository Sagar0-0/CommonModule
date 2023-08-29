package fit.asta.health.datastore

data class UserPreferencesData(
    val screenCode: Int = 0,
    val notificationStatus: Boolean = false,
    val locationPermissionRejectedCount: Int = 0,
    val currentAddress: String = "",
    val theme: String = "",
    val alarmId: Long = 999L,
    val tone: String = "hi",
    val isFcmTokenUploaded: Boolean = false
)
