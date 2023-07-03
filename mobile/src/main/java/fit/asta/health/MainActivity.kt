package fit.asta.health

//import fit.asta.health.profile.UserProfileActivity
//import fit.asta.health.profile.viewmodel.ProfileAvailViewModel
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.auth.AuthNavHost
import fit.asta.health.common.location.maps.MapsActivity
import fit.asta.health.common.location.maps.MapsViewModel
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.common.utils.*
import fit.asta.health.firebase.viewmodel.AuthViewModel
import fit.asta.health.network.TokenProvider
import fit.asta.health.profile.CreateUserProfileActivity
import fit.asta.health.profile.viewmodel.ProfileAvailState
import fit.asta.health.profile.viewmodel.ProfileAvailViewModel
import fit.asta.health.settings.SettingsActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener,
    FirebaseAuth.IdTokenListener {

    companion object {
        private const val REQUEST_FLEXIBLE_UPDATE: Int = 1369
        private const val REQUEST_IMMEDIATE_UPDATE: Int = 1789
    }

    private val profileAvailViewModel: ProfileAvailViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val mapsViewModel: MapsViewModel by viewModels()
    private var settingsLauncher: ActivityResultLauncher<Intent>? = null
    private var locationRequestLauncher: ActivityResultLauncher<IntentSenderRequest>? = null
    private lateinit var networkConnectivity: NetworkConnectivity
    private var profileImgView: ImageView? = null
    private val profileImgUri = mutableStateOf<Uri?>(null)
    private val notificationEnabled = mutableStateOf(true)
    private val isConnected = mutableStateOf(true)
    private val addressText = mutableStateOf("...")

    @Inject
    lateinit var tokenProvider: TokenProvider
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            //viewModel.loadHomeData()
        }

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mapsViewModel.getCurrentLatLng(this@MainActivity)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                mapsViewModel.currentLatLng.collect {
                    mapsViewModel.getCurrentAddress(this@MainActivity)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                mapsViewModel.currentAddress.collect {
                    if (it != null) {
                        addressText.value = it.sub
                    }
                }
            }
        }

        if (!authViewModel.isAuthenticated()) {
            loadAuthScreen()
        } else {
            /*authViewModel.getUserId()?.let {
                createProfile()
                profileViewModel.isUserProfileAvailable(it)
            }*/
            loadAppScreen()
        }

        onBackPressedDispatcher.addCallback(this) {

            if (!authViewModel.isAuthenticated()) {
                finishAndRemoveTask()
            }
        }

        settingsLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->

                if (res.resultCode == Activity.RESULT_OK) {
                    invalidateOptionsMenu()
                }
            }

        locationRequestLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
                if (activityResult.resultCode == RESULT_OK)
                    mapsViewModel.getCurrentLatLng(this)
                else {
                    Toast.makeText(this, "User location access required!!", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }

        FirebaseAuth.getInstance().addAuthStateListener(this)
        FirebaseAuth.getInstance().addIdTokenListener(this)
    }

    private fun loadAuthScreen() {
        setContent {
            AppTheme {
                val context = LocalContext.current
                val navController = rememberNavController()
                AuthNavHost(navController) {
                    if (authViewModel.isAuthenticated()) {
                        authViewModel.getUserId()?.let {
                            createProfile()
                            profileAvailViewModel.isUserProfileAvailable(it)
                        }
                        Toast.makeText(
                            context,
                            "Sign in Successful",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setProfileImage() {
        val user = authViewModel.getUser()
        if (user?.photoUrl != null) {
            profileImgUri.value = user.photoUrl
        }
    }


    private fun createLocationRequest() {

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
            Log.d("Location", "createLocationRequest: Success")
            mapsViewModel.getCurrentLatLng(this)
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()
                    locationRequestLauncher!!.launch(intentSenderRequest)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun onOptionsItemClicked(key: MainTopBarActions) {
        when (key) {
            MainTopBarActions.LOCATION -> {
                startActivity(Intent(this, MapsActivity::class.java))
            }

            MainTopBarActions.NOTIFICATION -> {
                PrefUtils.setMasterNotification(!notificationEnabled.value, applicationContext)
                notificationEnabled.value = PrefUtils.getMasterNotification(applicationContext)
            }

            MainTopBarActions.SETTINGS -> {
                startUserSettingsActivity()
            }

            MainTopBarActions.PROFILE -> {
                startUserProfileActivity()
            }

            MainTopBarActions.SHARE -> {
                shareApp()
            }
        }
    }

    fun loadAppScreen() {
        notificationEnabled.value = PrefUtils.getMasterNotification(applicationContext)
        networkConnectivity = NetworkConnectivity(this)
        networkConnectivity.observe(this) { status ->
            isConnected.value = status
        }
        setProfileImage()
        createLocationRequest()
        setContent {
            Log.d("URI", "loadAppScreen: ${profileImgUri.value}")
            AppTheme {
                MainActivityLayout(
                    addressText.value,
                    profileImgUri.value,
                    notificationEnabled.value,
                    isConnected.value
                ) {
                    onOptionsItemClicked(
                        it
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        checkUpdate()
    }

    override fun onDestroy() {
        super.onDestroy()

        FirebaseAuth.getInstance().removeAuthStateListener(this)
        FirebaseAuth.getInstance().removeIdTokenListener(this)
    }

    private fun startUserSettingsActivity() {

        val intent = Intent(applicationContext, SettingsActivity::class.java)
        settingsLauncher?.launch(intent)
    }

    private fun createProfile() {

        lifecycleScope.launchWhenStarted {
            profileAvailViewModel.state.collectLatest {
                when (it) {
                    ProfileAvailState.Loading -> {
                        //Do nothing
                    }
                    is ProfileAvailState.Error -> {
                        //Error Handling
                    }
                    is ProfileAvailState.Success -> {
                        if (it.isAvailable) {
                            loadAppScreen()
                        } else {
                            CreateUserProfileActivity.launch(this@MainActivity)
                        }
                    }
                    ProfileAvailState.NoInternet -> {

                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMMEDIATE_UPDATE) {

            if (requestCode != RESULT_OK) {

                checkUpdate()
            }
        }
    }

    private fun checkUpdate() {

        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->

            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {

                appUpdateManager.completeUpdate()
            }

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {

                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        this,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE)
                            .setAllowAssetPackDeletion(true)
                            .build(),
                        REQUEST_IMMEDIATE_UPDATE
                    )
                } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        this,
                        AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE)
                            .setAllowAssetPackDeletion(true)
                            .build(),
                        REQUEST_FLEXIBLE_UPDATE
                    )
                }
            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {

                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    this,
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE)
                        .setAllowAssetPackDeletion(true)
                        .build(),
                    REQUEST_IMMEDIATE_UPDATE
                )
            }
        }
    }

    override fun onAuthStateChanged(fireBaseAuth: FirebaseAuth) {

        showUserImage()
    }

    override fun onIdTokenChanged(auth: FirebaseAuth) {

        auth.currentUser?.getIdToken(false)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.token?.let { tokenProvider.load(it) }
            } else {
                Log.d("AuthToken", "Exception: = ${task.exception}")
            }
        }
    }

    private fun showUserImage() {

        val user = authViewModel.getUser()
        if (profileImgView != null && user?.photoUrl != null) {

            profileImgView?.visibility = View.VISIBLE
            showCircleImageByUrl(user.photoUrl, this.profileImgView)
        }
    }

    private fun startUserProfileActivity() {
        //UserProfileActivity.launch(this@MainActivity)
    }

}


