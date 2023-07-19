package fit.asta.health

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.CATEGORY_DEFAULT
import android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.location.maps.MapsActivity
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.common.utils.*
import fit.asta.health.network.TokenProvider
import fit.asta.health.profile.UserProfileActivity
import fit.asta.health.settings.SettingsActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : ComponentActivity(), FirebaseAuth.AuthStateListener,
    FirebaseAuth.IdTokenListener {

    companion object {
        private const val REQUEST_FLEXIBLE_UPDATE: Int = 1369
        private const val REQUEST_IMMEDIATE_UPDATE: Int = 1789
        fun launch(context: Context) {
            Intent(context, MainActivity::class.java)
                .apply {
                    context.startActivity(this)
                }
            (context as Activity).finishAffinity()
        }
    }

    private val authViewModel: AuthViewModel by viewModels()
    private var settingsLauncher: ActivityResultLauncher<Intent>? = null
    private lateinit var networkConnectivity: NetworkConnectivity
    private var profileImgView: ImageView? = null
    private val profileImgUri = mutableStateOf<Uri?>(null)
    private val notificationEnabled = mutableStateOf(true)
    private val isConnected = mutableStateOf(true)
    private val addressText = mutableStateOf("")

    @Inject
    lateinit var tokenProvider: TokenProvider
    lateinit var permissionResultLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerLocationPermissionLauncher()
        loadAppScreen()

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

    private fun registerLocationPermissionLauncher() {
        val permissionResultListener = object : PermissionResultListener {
            override fun onGranted(perm: String) {
                if (perm == Manifest.permission.ACCESS_COARSE_LOCATION) {
                    Toast.makeText(
                        this@MainActivity,
                        "Fine Location is recommended for better functionality.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                PrefUtils.setLocationPermissionRejectedCount(this@MainActivity, 1)
                MapsActivity.launch(this@MainActivity)
            }

            override fun onDenied(perm: String) {
                if (perm == Manifest.permission.ACCESS_COARSE_LOCATION) {
                    Toast.makeText(
                        this@MainActivity,
                        "Precise Location is recommended for better functionality.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.location_access_required),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                PrefUtils.setLocationPermissionRejectedCount(
                    this@MainActivity,
                    PrefUtils.getLocationPermissionRejectedCount(this@MainActivity) + 1
                )
            }
        }

        permissionResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->
            kotlin.run lit@{
                perms.keys.forEach {
                    if (it == Manifest.permission.ACCESS_FINE_LOCATION || it == Manifest.permission.ACCESS_COARSE_LOCATION) {
                        if (perms[it] == true) {
                            permissionResultListener.onGranted(it)
                        } else {
                            permissionResultListener.onDenied(it)
                        }
                        return@lit
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
            == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            PrefUtils.setLocationPermissionRejectedCount(this@MainActivity, 1)
            MapsActivity.launch(this@MainActivity)
        } else {
            if (PrefUtils.getLocationPermissionRejectedCount(this) >= 2) {
                Toast.makeText(this, "Please allow Location permission access.", Toast.LENGTH_SHORT)
                    .show()
                with(Intent(ACTION_APPLICATION_DETAILS_SETTINGS)) {
                    data = Uri.fromParts("package", applicationContext.packageName, null)
                    addCategory(CATEGORY_DEFAULT)
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                    addFlags(FLAG_ACTIVITY_NO_HISTORY)
                    addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                    this@MainActivity.startActivity(this)
                }
            } else {
                permissionResultLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    interface PermissionResultListener {
        fun onGranted(perm: String)
        fun onDenied(perm: String)
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
        addressText.value = PrefUtils.getCurrentAddress(this)
        checkUpdate()
    }

    override fun onDestroy() {
        super.onDestroy()

        FirebaseAuth.getInstance().removeAuthStateListener(this)
        FirebaseAuth.getInstance().removeIdTokenListener(this)
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