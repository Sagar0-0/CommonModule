package fit.asta.health.common.address.data.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import fit.asta.health.common.address.data.di.MyContext
import java.util.Locale
import javax.inject.Inject

class LocationResourceProvider
@Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getFusedLocationProviderClient() =
        LocationServices.getFusedLocationProviderClient(context)

    fun getSettingsClient() = LocationServices.getSettingsClient(context)

    fun getGeocoder() = Geocoder(context, Locale.getDefault())


    fun isPermissionGranted() = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}