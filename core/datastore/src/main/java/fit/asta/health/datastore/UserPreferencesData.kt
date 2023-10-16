package fit.asta.health.datastore

data class UserPreferencesData(
    val screenCode: Int = 0,
    val notificationStatus: Boolean = true,
    val locationPermissionRejectedCount: Int = 0,
    val currentAddress: String = "",
    val theme: String = "",
    val alarmId: Long = 999L,
    val tone: String = "hi",
    val isFcmTokenUploaded: Boolean = false,
    val isReferralChecked: Boolean = false,
    val referralCode: String = "",
    val trackLanguage: String = ""
)

data class UserPreferencesDataAddress(
    val currentAddress: String = "",
    val lat: Double = 0.0,
    val long: Double = 0.0
)
