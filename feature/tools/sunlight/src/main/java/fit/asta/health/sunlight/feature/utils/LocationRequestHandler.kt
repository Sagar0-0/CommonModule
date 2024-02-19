package fit.asta.health.sunlight.feature.utils

/*
class LocationRequestHandler(
    private val context: Context,
    private val onLocationResult: (ApiResult<Address>) -> Unit
) {
    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            try {
                locationResult.lastLocation?.let { location ->

                    val geocoder = Geocoder(context, Locale.getDefault())
                    val list: List<Address> =
                        try {
                            geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            ) as List<Address>
                        } catch (e: Exception) {
                            emptyList()
                        }
                    if (list.isNotEmpty()) {
                        onLocationResult(ApiResult.Success(list[0]))
                        removeLocationUpdates()
                    }
                }
            } catch (e: Exception) {
                onLocationResult(ApiResult.Error(e.message.toString()))
            }
        }
    }

    init {
        requestSingleLocationUpdate()
    }

    private fun requestSingleLocationUpdate() {
        if (checkLocationPermission()) {
            Log.d("initLocal", "requestSingleLocationUpdate: ")
            val locationRequest = createLocationRequest()
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun removeLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun checkLocationPermission(): Boolean {
        return checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PermissionChecker.PERMISSION_GRANTED &&
                checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ) == PermissionChecker.PERMISSION_GRANTED

    }

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 100
        }
    }
}
*/
