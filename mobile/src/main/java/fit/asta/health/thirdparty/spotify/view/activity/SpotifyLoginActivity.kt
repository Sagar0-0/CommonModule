package fit.asta.health.thirdparty.spotify.view.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.thirdparty.spotify.SpotifyNavGraph
import fit.asta.health.thirdparty.spotify.model.net.me.SpotifyMeModel
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyAuthViewModelX

@AndroidEntryPoint
class SpotifyLoginActivity : ComponentActivity() {

    /**
     * This variables is to provide a lock on the onResume code in this
     * activity so that it works when we want it to
     */
    private var isResume: Boolean = false

    /**
     * This is the [SpotifyAuthViewModelX] viewModel which contains all the business logic of this
     * activity
     */
    private val spotifyAuthViewModelX: SpotifyAuthViewModelX by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {

                    // Handling the States of all the Authorization flow of spotify
                    when (spotifyAuthViewModelX.currentUserData) {

                        // Initial State when the Auth Flow hasn't Started yet
                        is SpotifyNetworkCall.Initialized -> {

                            // checking if spotify is installed or not
                            if (isSpotifyInstalled()) {
                                // Starting the Auth Flow from here
                                sendAuthRequest()
                            } else {

                                // downloading Spotify App in the user's App so it can be used
                                Toast.makeText(this, "Need to Download Spotify", Toast.LENGTH_SHORT)
                                    .show()
                                isResume = true
                                openSpotifyInPlayStore()
                            }
                        }

                        // Loading State when the Auth Flow has started and is fetching all the data
                        is SpotifyNetworkCall.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        // This is when the Auth Flow is completed and has executed Successfully
                        is SpotifyNetworkCall.Success<SpotifyMeModel> -> {

//                            val intent = Intent(this, MusicHomeActivity::class.java)
//
//                            // Sending User Details to the next Activity
//                            intent.putExtra(
//                                SpotifyConstants.SPOTIFY_USER_DETAILS,
//                                spotifyAuthViewModelX.currentUserData.data
//                            )
//
//                            // Sending the User Token to the next Activity
//                            intent.putExtra(
//                                SpotifyConstants.SPOTIFY_USER_TOKEN,
//                                spotifyAuthViewModelX.accessToken
//                            )
//
//                            SpotifyConstants.SPOTIFY_USER_ACCESS_TOKEN =
//                                spotifyAuthViewModelX.accessToken
//
//                            // Starting the Activity
//                            startActivity(intent)

                            val navController = rememberNavController()
                            SpotifyNavGraph(
                                navController = navController,
                                spotifyAuthViewModelX = spotifyAuthViewModelX
                            )
                        }

                        // This is when the Auth Flow is completed and has executed UnSuccessfully
                        is SpotifyNetworkCall.Failure<SpotifyMeModel> -> {

                            // This shows Error Message to the User
                            Toast.makeText(
                                this,
                                spotifyAuthViewModelX.currentUserData.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
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
    @Suppress("DEPRECATION")
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

//    private fun saveToken(token: String) {
//        val sharedPreference = getSharedPreferences(SHARED_PREF_SPOTIFY, Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        editor.putString(SHARED_PREF_SPOTIFY_TOKEN, token)
//        editor.commit()
//    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == SpotifyConstants.SPOTIFY_AUTH_REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, intent)
            spotifyAuthViewModelX.handleSpotifyAuthResponse(response)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val uri: Uri? = intent.data
        if (uri != null) {
            val response = AuthorizationResponse.fromUri(uri)
            spotifyAuthViewModelX.handleSpotifyAuthResponse(response)
        }
    }
}
