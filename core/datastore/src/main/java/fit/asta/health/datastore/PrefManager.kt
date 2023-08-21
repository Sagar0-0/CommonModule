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
        UserPreferencesData(
            onboardingShown = it.onboardingShown,
            notificationStatus = it.notificationStatus,
            locationPermissionRejectedCount = it.locationPermissionRejectedCount,
            currentAddress = it.currentAddress
        )
    }

    suspend fun setOnboardingShown() {

        try {
            userPreferences.updateData {
                it.copy {
                    this.onboardingShown = true
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
}