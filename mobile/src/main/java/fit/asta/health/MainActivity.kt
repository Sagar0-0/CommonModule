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
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
import fit.asta.health.network.TokenProvider
import fit.asta.health.network.utils.NetworkConnectivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : ComponentActivity(),
    FirebaseAuth.IdTokenListener {

    companion object {
        private const val REQUEST_FLEXIBLE_UPDATE: Int = 1369
        private const val REQUEST_IMMEDIATE_UPDATE: Int = 1789
    }

    private lateinit var networkConnectivity: NetworkConnectivity
    private val isConnected = mutableStateOf(true)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
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

    private fun askNotificationPermission() {
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
            AppTheme {
                MainNavHost(isConnected.value)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkUpdate()
    }

    override fun onDestroy() {
        super.onDestroy()
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