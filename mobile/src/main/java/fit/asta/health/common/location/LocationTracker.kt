package fit.asta.health.common.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}