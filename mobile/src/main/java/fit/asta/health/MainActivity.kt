package fit.asta.health

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.utils.*
import fit.asta.health.firebase.viewmodel.AuthViewModel
import fit.asta.health.network.TokenProvider
import fit.asta.health.profile.CreateUserProfileActivity
import fit.asta.health.profile.UserProfileActivity
import fit.asta.health.profile.viewmodel.ProfileAvailState
import fit.asta.health.profile.viewmodel.ProfileAvailViewModel
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.settings.SettingsActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener,
    FirebaseAuth.IdTokenListener {

    companion object {
        private const val REQUEST_FLEXIBLE_UPDATE: Int = 1369
        private const val REQUEST_IMMEDIATE_UPDATE: Int = 1789
    }

    private val profileAvailViewModel: ProfileAvailViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private var settingsLauncher: ActivityResultLauncher<Intent>? = null
    private lateinit var networkConnectivity: NetworkConnectivity
    private var snackBar: Snackbar? = null
    private var profileImgView: ImageView? = null

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

        if (!authViewModel.isAuthenticated()) {
            signIn()
        } else {
            /*authViewModel.getUserId()?.let {
                createProfile()
                profileAvailViewModel.isUserProfileAvailable(it)
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

        FirebaseAuth.getInstance().addAuthStateListener(this)
        FirebaseAuth.getInstance().addIdTokenListener(this)
    }

    fun loadAppScreen() {

        applicationContext.setAppTheme()
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.appToolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        registerConnectivityReceiver()
        bottomNavBar()
        showUserImage()

    }

    private fun bottomNavBar() {

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_today, R.id.navigation_track
            )
        )

        val navController = findNavController(R.id.navHostFragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<BottomNavigationView>(R.id.navView).setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, _, _ ->

            showViewBars()
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

    private fun registerConnectivityReceiver() {

        snackBar = Snackbar.make(
            findViewById<CoordinatorLayout>(R.id.containerMain),
            getString(R.string.OFFLINE_STATUS),
            Snackbar.LENGTH_INDEFINITE
        ).setAnchorView(findViewById<BottomNavigationView>(R.id.navView))

        networkConnectivity = NetworkConnectivity(this)
        networkConnectivity.observe(this) { status ->

            showNetworkMessage(status)
        }
    }

    private fun showNetworkMessage(isConnected: Boolean) {

        if (!isConnected) {
            snackBar?.show()
        } else {
            snackBar?.dismiss()
        }
    }

    private fun startUserSettingsActivity() {

        val intent = Intent(applicationContext, SettingsActivity::class.java)
        settingsLauncher?.launch(intent)
    }

    private fun signIn() {

        val signInLauncher =
            registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
                this.onSignInResult(res)
            }

        val signInIntent =
            AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false)
                .setAvailableProviders(
                    arrayListOf(
                        AuthUI.IdpConfig.PhoneBuilder().build(),
                        AuthUI.IdpConfig.GoogleBuilder().build()
                    )
                ).setLogo(R.mipmap.ic_launcher_foreground).setTheme(R.style.LoginTheme)
                .setTosAndPrivacyPolicyUrls(
                    getPublicStorageUrl(this, resources.getString(R.string.url_terms_of_use)),
                    getPublicStorageUrl(this, resources.getString(R.string.url_privacy_policy))
                ).build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {

        if (result.resultCode == Activity.RESULT_CANCELED) {

            finishAndRemoveTask() // User pressed back button, exit the application
        }

        if (result.resultCode == Activity.RESULT_OK) {

            if (authViewModel.isAuthenticated()) {
                authViewModel.getUserId()?.let {
                    createProfile()
                    profileAvailViewModel.isUserProfileAvailable(it)
                }
            }

        } else {

            val response = result.idpResponse
            if (response?.error?.message != null) {

                this.showToastMessage(response.error?.message!!)
                Log.d("Error: ", response.error?.toString()!!)
            }
        }

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

    private fun showViewBars() {

        findViewById<AppBarLayout>(R.id.appBarLayout)?.setExpanded(true, true)
        val navView = findViewById<BottomNavigationView>(R.id.navView)
        val paramsBNV = navView?.layoutParams as CoordinatorLayout.LayoutParams
        val behaviorBNV = paramsBNV.behavior as? HideBottomViewOnScrollBehavior
        behaviorBNV?.slideUp(navView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {

        prepareUserProfileMenuItem(menu)
        prepareNotifyMenuItem(menu)

        return super.onPrepareOptionsMenu(menu)
    }

    private fun prepareUserProfileMenuItem(menu: Menu) {

        val profileMenuItem = menu.findItem(R.id.user_profile_menu)
        val rootView = profileMenuItem.actionView
        rootView?.setOnClickListener { onOptionsItemSelected(profileMenuItem) }
        profileImgView = rootView?.findViewById(R.id.app_toolbar_profile_image)

        showUserImage()
    }

    private fun showUserImage() {

        val user = authViewModel.getUser()
        if (profileImgView != null && user?.photoUrl != null) {

            profileImgView?.visibility = View.VISIBLE
            showCircleImageByUrl(user.photoUrl, this.profileImgView)
        }
    }

    private fun prepareNotifyMenuItem(menu: Menu) {

        val itemNotify = menu.findItem(R.id.notifications_menu)
        val bMasterNotify = PrefUtils.getMasterNotification(applicationContext)
        itemNotify.isChecked = bMasterNotify
        itemNotify.icon = ContextCompat.getDrawable(
            applicationContext,
            if (bMasterNotify) R.drawable.ic_notifications_on else R.drawable.ic_notifications_off
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.notifications_menu -> {

                toggleNotifications(item)
                return true
            }
            R.id.settings_menu -> {

                startUserSettingsActivity()
                return true
            }
            R.id.user_profile_menu -> {

                startUserProfileActivity()
                return true
            }
            R.id.share_menu -> {

                shareApp()
                return true
            }
            else -> {

                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun toggleNotifications(item: MenuItem) {

        item.isChecked = !item.isChecked
        item.icon = ContextCompat.getDrawable(
            applicationContext,
            if (item.isChecked) R.drawable.ic_notifications_off else R.drawable.ic_notifications_on
        )

        PrefUtils.setMasterNotification(item.isChecked, applicationContext)
    }

    private fun startUserProfileActivity() {
        UserProfileActivity.launch(this@MainActivity)
    }
}
