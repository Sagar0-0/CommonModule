package fit.asta.health

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
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.auth.FirebaseAuth
import fit.asta.health.auth.ui.AuthViewModel
import fit.asta.health.network.TokenProvider
import fit.asta.health.profile.ui.ProfileActivity
import fit.asta.health.settings.SettingsActivity
import fit.asta.health.utils.*
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener,
    FirebaseAuth.IdTokenListener, KoinComponent {

    companion object {
        private const val REQUEST_FLEXIBLE_UPDATE: Int = 1369
        private const val REQUEST_IMMEDIATE_UPDATE: Int = 1789
    }

    private lateinit var authViewModel: AuthViewModel
    private var settingsLauncher: ActivityResultLauncher<Intent>? = null
    private lateinit var networkConnectivity: NetworkConnectivity
    private var snackBar: Snackbar? = null
    private var profileImgView: ImageView? = null
    private val tokenProvider: TokenProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.apply {

            //To stop taking the screenshot
            //setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

            //System UI color
            /*if (android.os.Build.VERSION.SDK_INT in 21..22) {

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(context, R.color.colorPrimary)
            }

            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.TRANSPARENT*/
        }

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        if (!authViewModel.isAuthenticated())
            signIn()
        else {

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

    private fun loadAppScreen() {

        applicationContext.setAppTheme()
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.appToolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        /*val fab: FloatingActionButton? = findViewById(R.id.fabSignOut)
            fab?.setOnClickListener {

                fab_snack_main.showSnackbar(R.string.app_name)
            }*/

        registerConnectivityReceiver()
        bottomNavBar()
        showUserImage()
    }

    private fun bottomNavBar() {

        /*val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController*/
        val navController = findNavController(R.id.navHostFragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_today,
                R.id.navigation_track
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //nav_view.selectedItemId = R.id.navigation_yoga

        navController.addOnDestinationChangedListener { _, _, _ ->

            showViewBars()
        }

        //nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    /*private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_settings -> {
                    val artistsFragment = SettingsFragment.newInstance()
                    openFragment(artistsFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }

            false
        }*/

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.navHostFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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
            containerMain,
            getString(R.string.OFFLINE_STATUS),
            Snackbar.LENGTH_INDEFINITE
        ).setAnchorView(navView)

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

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setIsSmartLockEnabled(false)
            .setAvailableProviders(
                arrayListOf(
                    AuthUI.IdpConfig.PhoneBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )
            )
            .setLogo(R.mipmap.ic_launcher_foreground)
            .setTheme(R.style.LoginTheme)
            .setTosAndPrivacyPolicyUrls(
                getPublicStorageUrl(this, resources.getString(R.string.url_terms_of_use)),
                getPublicStorageUrl(this, resources.getString(R.string.url_privacy_policy))
            )
            .build()

        signInLauncher.launch(signInIntent)
    }

    /*private fun registerDynamicLinks() {

        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { linkData ->

                var deepLink: Uri? = null
                if (linkData != null) {

                    Log.e("startFireBaseActivity", linkData.link.toString())
                }
            }
            .addOnFailureListener(this) { e ->

                Log.e("startFireBaseActivity", "getDynamicLink:onFailure", e)
            }
    }*/

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {

        if (result.resultCode == Activity.RESULT_CANCELED) {

            finishAndRemoveTask() // User pressed back button, exit the application
        }

        if (result.resultCode == Activity.RESULT_OK) {

            loadAppScreen()

        } else {

            val response = result.idpResponse
            if (response?.error?.message != null) {

                this.showToastMessage(response.error?.message!!)
                Log.d("Error: ", response.error?.toString()!!)
            }
        }
    }

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

    private fun showViewBars() {

        appBarLayout?.setExpanded(true, true)

        val paramsBNV = navView?.layoutParams as CoordinatorLayout.LayoutParams
        val behaviorBNV = paramsBNV.behavior as? HideBottomViewOnScrollBehavior
        behaviorBNV?.slideUp(navView)

        /*val paramsFAB = fab_snack_main?.layoutParams as CoordinatorLayout.LayoutParams
        val behaviorFAB = paramsFAB.behavior as? HideBottomViewOnScrollBehavior
        behaviorFAB?.slideUp(fab_snack_main)*/
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

        //showTextNotification(getString(R.string.title_reminder), "Test", "Just for fun!")
        //showBigTextNotification(getString(R.string.title_reminder), "Test", "Welcome to tutlane, it provides a tutorials related to all technologies in software industry. Here we covered complete tutorials from basic to adavanced topics from all technologies.\n\n - By Tultane", null)
        //showImageNotification(getString(R.string.title_reminder), "Test", "Just for fun!", Uri.parse("https://firebasestorage.googleapis.com/v0/b/yogam-91999.appspot.com/o/media%2Fimages%2Fasanas%2Faerobics.jpg?alt=media"))

        PrefUtils.setMasterNotification(item.isChecked, applicationContext)
    }

    private fun startUserProfileActivity() {

        startActivity(Intent(applicationContext, ProfileActivity::class.java))
    }
}
