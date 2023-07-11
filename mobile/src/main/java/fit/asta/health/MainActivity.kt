package fit.asta.health

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
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
import fit.asta.health.profile.UserProfileActivity
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
    private lateinit var networkConnectivity: NetworkConnectivity
    private var profileImgView: ImageView? = null
    private val profileImgUri = mutableStateOf<Uri?>(null)
    private val notificationEnabled = mutableStateOf(true)
    private val isConnected = mutableStateOf(true)
    private val addressText = mutableStateOf("...")

    @Inject
    lateinit var tokenProvider: TokenProvider
    lateinit var permissionResultLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerLocationPermissionLauncher()

        if (!authViewModel.isAuthenticated()) {
            loadAuthScreen()
        } else {
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

        FirebaseAuth.getInstance().addAuthStateListener(this)
        FirebaseAuth.getInstance().addIdTokenListener(this)
    }

    private fun observeCurrentLocation() {
        mapsViewModel.updateCurrentLocationData(this@MainActivity)
        lifecycleScope.launch {
            mapsViewModel.currentAddress.collect {
                if (it != null) {
                    addressText.value = it.sub
                }
            }
        }
    }

    private fun registerLocationPermissionLauncher() {
        val permissionResultListener = object : PermissionResultListener {
            override fun onGranted() {
                MapsActivity.launch(this@MainActivity)
            }

            override fun onDenied() {
                Toast.makeText(
                    this@MainActivity,
                    "Need location permission to access this feature.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        permissionResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->
            perms.keys.forEach {
                if (it == Manifest.permission.ACCESS_FINE_LOCATION) {
                    if (perms[it] == true) {
                        permissionResultListener.onGranted()
                    } else {
                        permissionResultListener.onDenied()
                    }
                }
            }
        }
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
                            context, "Sign in Successful", Toast.LENGTH_SHORT
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

    private fun onOptionsItemClicked(key: MainTopBarActions) {
        when (key) {
            MainTopBarActions.LOCATION -> {
                checkPermissionAndLaunchMapsActivity()
            }

            MainTopBarActions.NOTIFICATION -> {
                PrefUtils.setMasterNotification(!notificationEnabled.value, applicationContext)
                notificationEnabled.value = PrefUtils.getMasterNotification(applicationContext)
            }

            MainTopBarActions.SETTINGS -> {
                SettingsActivity.launch(this)
            }

            MainTopBarActions.PROFILE -> {
                startUserProfileActivity()
            }

            MainTopBarActions.SHARE -> {
                shareApp()
            }
        }
    }

    private fun checkPermissionAndLaunchMapsActivity() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_DENIED
        ) {
            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        } else {
            MapsActivity.launch(this@MainActivity)
        }
    }

    interface PermissionResultListener {
        fun onGranted()
        fun onDenied()
    }

    fun loadAppScreen() {
        notificationEnabled.value = PrefUtils.getMasterNotification(applicationContext)
        networkConnectivity = NetworkConnectivity(this)
        networkConnectivity.observe(this) { status ->
            isConnected.value = status
        }
        setProfileImage()
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

        observeCurrentLocation()
        checkUpdate()
    }

    override fun onDestroy() {
        super.onDestroy()

        FirebaseAuth.getInstance().removeAuthStateListener(this)
        FirebaseAuth.getInstance().removeIdTokenListener(this)
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
                            .setAllowAssetPackDeletion(true).build(),
                        REQUEST_IMMEDIATE_UPDATE
                    )
                } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        this,
                        AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE)
                            .setAllowAssetPackDeletion(true).build(),
                        REQUEST_FLEXIBLE_UPDATE
                    )
                }
            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {

                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    this,
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE)
                        .setAllowAssetPackDeletion(true).build(),
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
        UserProfileActivity.launch(this@MainActivity)
    }

}