package fit.asta.health.feature.spotify

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.spotify.navigation.TopTabNavigation
import fit.asta.health.feature.spotify.utils.SpotifyConstants
import fit.asta.health.feature.spotify.viewmodel.SpotifyViewModelX
import fit.asta.health.resources.strings.R

@AndroidEntryPoint
class SpotifyLoginActivity : ComponentActivity() {

    /**
     * This variables is to provide a lock on the onResume code in this
     * activity so that it works when we want it to
     */
    private var isResume: Boolean = false

    /**
     * This is the [SpotifyViewModelX] viewModel which contains all the business logic of this
     * activity
     */
    private val spotifyViewModelX: SpotifyViewModelX by viewModels()

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, SpotifyLoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {

                    val needToDownloadSpotify =
                        stringResource(id = R.string.need_to_download_spotify)

                    // Handling the States of all the Authorization flow of spotify
                    when (val loginState =
                        spotifyViewModelX.currentUserData.collectAsState().value) {

                        is UiState.Idle -> {

                            // checking if spotify is installed or not
                            if (isSpotifyInstalled()) {
                                // Starting the Auth Flow from here
                                sendAuthRequest()
                            } else {

                                // downloading Spotify App in the user's App so it can be used
                                Toast.makeText(this, needToDownloadSpotify, Toast.LENGTH_SHORT)
                                    .show()
                                isResume = true
                                openSpotifyInPlayStore()
                            }
                        }

                        is UiState.Loading -> {
                            LoadingAnimation(modifier = Modifier.fillMaxSize())
                        }

                        is UiState.Success -> {

                            // Getting the Spotify App Remote
                            getSpotifyRemote()

                            val musicNavController = rememberNavController()
                            TopTabNavigation(
                                spotifyViewModelX = spotifyViewModelX,
                                navController = musicNavController
                            )
                        }

                        is UiState.ErrorMessage -> {
                            AppErrorScreen(desc = loginState.resId.toStringFromResId()) {

                                // checking if spotify is installed or not
                                if (isSpotifyInstalled()) {
                                    // Starting the Auth Flow from here
                                    sendAuthRequest()
                                } else {

                                    // downloading Spotify App in the user's App so it can be used
                                    Toast.makeText(this, needToDownloadSpotify, Toast.LENGTH_SHORT)
                                        .show()
                                    isResume = true
                                    openSpotifyInPlayStore()
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    /**
     * This function is getting the spotify App remote and Sharing it to the ViewModel so that it can
     * be used everywhere
     */
    private fun getSpotifyRemote() {

        // Setting a Connection Params which can be used to connect to the spotify and get the Remote
        val connectionParams = ConnectionParams.Builder(SpotifyConstants.SPOTIFY_CLIENT_ID)
            .setRedirectUri(SpotifyConstants.SPOTIFY_REDIRECT_URI)
            .showAuthView(true)
            .build()

        // Connecting to Spotify
        SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {

            // When Connection is established and we get the remote
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyViewModelX.setSpotifyAppRemote(appRemote)
            }

            // when connection is not established and we don't get the remote
            override fun onFailure(throwable: Throwable) {
                spotifyViewModelX.unableToGetSpotifyRemote()
            }
        })
    }


    /**
     * This function creates the Authorization Request which authorizes our app so that we can read
     * data according to these spotify scopes [SpotifyConstants.SPOTIFY_SCOPES]
     */
    private fun sendAuthRequest() {

        val request = AuthorizationRequest.Builder(
            SpotifyConstants.SPOTIFY_CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            SpotifyConstants.SPOTIFY_REDIRECT_URI
        )
            .setShowDialog(true)
            .setScopes(arrayOf(SpotifyConstants.SPOTIFY_SCOPES))
            .build()

        // Starting the authorization window or intent
        AuthorizationClient.openLoginActivity(
            this,
            SpotifyConstants.SPOTIFY_AUTH_REQUEST_CODE,
            request
        )
    }

    /**
     * This function checks if the spotify is already installed in the current device
     */
    private fun isSpotifyInstalled(): Boolean {
        val packageName = "com.spotify.music"
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    /**
     * This function redirects the user to the google play store and prompts him to download the
     * spotify app
     */
    private fun openSpotifyInPlayStore() {
        val packageName = "com.spotify.music"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    /**
     * Overridden this so we can check if the user has installed spotify after we redirect him to
     * play store
     */
    override fun onResume() {
        super.onResume()

        // Checking if the Spotify App is installed by the User or not
        if (isResume) {
            if (isSpotifyInstalled())
                sendAuthRequest()
            else
                Toast.makeText(this, "Spotify Not Installed", Toast.LENGTH_SHORT).show()
            isResume = false
        }
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == SpotifyConstants.SPOTIFY_AUTH_REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, intent)
            spotifyViewModelX.handleSpotifyAuthResponse(response)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val uri: Uri? = intent.data
        if (uri != null) {
            val response = AuthorizationResponse.fromUri(uri)
            spotifyViewModelX.handleSpotifyAuthResponse(response)
        }
    }

    /**
     * This function pauses the Spotify Remote
     */
    override fun onStop() {
        super.onStop()
        spotifyViewModelX.onSpotifyRemoteStop()
    }


    /**
     * This function removes the Spotify Remote and frees the Space and all
     */
    override fun onDestroy() {
        super.onDestroy()
        spotifyViewModelX.disconnectSpotifyRemote()
    }
}