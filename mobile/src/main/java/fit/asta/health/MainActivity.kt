package fit.asta.health

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.utils.*
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.main.MainNavHost
import fit.asta.health.main.MainViewModel
import fit.asta.health.network.TokenProvider
import fit.asta.health.network.utils.NetworkConnectivity
import fit.asta.health.player.audio.MusicService
import fit.asta.health.tools.walking.nav.navigateToStepsCounterProgress
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : ComponentActivity(),
    FirebaseAuth.IdTokenListener {

    companion object {
        private const val REQUEST_FLEXIBLE_UPDATE: Int = 1369
        private const val REQUEST_IMMEDIATE_UPDATE: Int = 1789
        private const val TAG = "MainActivity"
    }

    private lateinit var networkConnectivity: NetworkConnectivity
    private val isConnected = mutableStateOf(true)
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var navController: NavHostController

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {

            // TODO: Inform user that that your app will not show notifications.
        }
    }

    @Inject
    lateinit var tokenProvider: TokenProvider
    private lateinit var referrerClient: InstallReferrerClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This app draws behind the system bars, so we want to handle fitting system windows
        //https://developer.android.com/develop/ui/views/layout/edge-to-edge
        //WindowCompat.setDecorFitsSystemWindows(window, true)

        installSplashScreen()
        lifecycleScope.launch {
            mainViewModel.isReferralChecked.collect {
                if (it is UiState.Success && !it.data) {
                    initReferralTracking()
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = ContextCompat.getSystemService(this, AlarmManager::class.java)
            if (alarmManager?.canScheduleExactAlarms() == false) {
                Intent().also { intent ->
                    intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                    this.startActivity(intent)
                }
            }
        }

        registerConnectivityReceiver()
        startMainNavHost()
        FirebaseAuth.getInstance().addIdTokenListener(this)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.let {
            if (it.getStringExtra(Constants.NOTIFICATION_TAG) == "walking") {
                if (::navController.isInitialized) {
                    navController.navigateToStepsCounterProgress()
                }
            }
        }
    }

    private fun initReferralTracking() {
        referrerClient = InstallReferrerClient.newBuilder(this).build()
        referrerClient.startConnection(object : InstallReferrerStateListener {

            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                Log.d(TAG, "onInstallReferrerSetupFinished - $responseCode")
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        // Connection established.
                        obtainReferrerDetails()
                    }

                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                        // Connection couldn't be established.
                    }

                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                        // API not available on the current Play Store app.
                    }
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    private fun obtainReferrerDetails() {
        val response: ReferrerDetails = referrerClient.installReferrer
        Log.d(TAG, "response - $response")
        val referrerUrl: String = response.installReferrer
        Log.d(TAG, "referrerUrl - $referrerUrl")

        if (referrerUrl.isNotEmpty()) {
            val referrerParts = referrerUrl.split("&")
            Log.d(TAG, "referrerParts - $referrerParts")

            val utmSource = referrerParts.find {
                it.contains("utm_source")
            }?.split("=")?.get(1)
            Log.d(TAG, "utmSource - $utmSource")

            if (utmSource != null && utmSource == "refer") {
                val utmContent = referrerParts.find {
                    it.contains("utm_content")
                }?.split("=")?.get(1)
                Log.d(TAG, "utmContent - $utmContent")

                if (utmContent != null) {
                    mainViewModel.setReferCode(utmContent)
                }
            }
        }

        mainViewModel.setReferralChecked()
        referrerClient.endConnection()
    }

    private fun askNotificationPermission() {//TODO: ASK NOTIFICATION PERMISSION ON app startup
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }


    private fun registerConnectivityReceiver() {
        networkConnectivity = NetworkConnectivity(this)
        networkConnectivity.observe(this) { status ->
            isConnected.value = status
        }
    }


    private fun startMainNavHost() {
        setContent {
            val theme by mainViewModel.theme.collectAsStateWithLifecycle()
            if (theme is UiState.Success) {
                val isDarkMode = when ((theme as UiState.Success<String>).data) {
                    "dark" -> {
                        true
                    }

                    "light" -> {
                        false
                    }

                    else -> {
                        isSystemInDarkTheme()
                    }
                }
                navController = rememberNavController()

                AppTheme(darkTheme = isDarkMode) {
                    MainNavHost(isConnected.value, mainViewModel, navController)
                    handleIntent(intent)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkUpdate()
    }

    @UnstableApi
    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().removeIdTokenListener(this)
        val stopIntent = Intent(this, MusicService::class.java)
        this.stopService(stopIntent)
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

    override fun onIdTokenChanged(auth: FirebaseAuth) {

        auth.currentUser?.getIdToken(false)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.token?.let { tokenProvider.load(it) }
            } else {
                Log.d("AuthToken", "Exception: = ${task.exception}")
            }
        }
    }

}