package fit.asta.health.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import fit.asta.health.UserPreferences
import fit.asta.health.copy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefManager
@Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData: Flow<UserPreferencesData> = userPreferences.data.map {
        Log.d("PrefManager", it.toString())
        UserPreferencesData(
            screenCode = it.screenCode,
            notificationStatus = it.notificationStatus,
            locationPermissionRejectedCount = it.locationPermissionRejectedCount,
            currentAddress = it.currentAddress,
            theme = it.theme,
            alarmId = it.alarmId,
            tone = it.tone,
            isFcmTokenUploaded = it.isFcmTokenUploaded,
            isReferralChecked = it.isReferralChecked,
            referralCode = it.referralCode,
            trackLanguage = it.trackLanguage,
            stepsPermissionRejectedCount = it.stepsPermissionRejectedCount,
            sessionState = it.sessionState
        )
    }
    val address: Flow<UserPreferencesDataAddress> = userPreferences.data.map {
        UserPreferencesDataAddress(
            currentAddress = it.currentAddress,
            lat = it.lat,
            long = it.long
        )
    }

    suspend fun setTrackLanguage(trackLanguage: String) {
        try {
            userPreferences.updateData {
                it.copy { this.trackLanguage = trackLanguage }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setAddressValue(address: String, lat: Double, long: Double) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.currentAddress = address
                    this.lat = lat
                    this.long = long
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setReferralChecked() {
        try {
            userPreferences.updateData {
                it.copy {
                    this.isReferralChecked = true
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setReferralCode(value: String) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.referralCode = value
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setIsFcmTokenUploaded(value: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.isFcmTokenUploaded = value
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setSoundTone(tone: String) {

        try {
            userPreferences.updateData {
                it.copy {
                    this.tone = tone
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setAlarmId(alarmId: Long) {

        try {
            userPreferences.updateData {
                it.copy {
                    this.alarmId = alarmId
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setScreenCode(code: Int) {

        try {
            userPreferences.updateData {
                it.copy {
                    this.screenCode = code
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setTheme(newTheme: String) {

        try {
            userPreferences.updateData {
                it.copy {
                    this.theme = newTheme
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setCurrentLocation(location: String) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.currentAddress = location
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setNotificationStatus(value: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.notificationStatus = value
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setLocationPermissionRejectedCount(value: Int) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.locationPermissionRejectedCount = value
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setStepsPermissionRejectedCount(value: Int) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.stepsPermissionRejectedCount = value
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setSessionStatus(value: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.sessionState = value
                }
            }
        } catch (ioException: IOException) {
            Log.e("Pref", "Failed to update user preferences", ioException)
        }
    }
}