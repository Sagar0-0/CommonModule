package fit.asta.health.thirdparty.spotify.view.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.thirdparty.MusicHomeActivity
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModel

@AndroidEntryPoint
class SpotifyLoginActivity : ComponentActivity() {

    private val tag = this::class.simpleName
    private var isResume: Boolean = false

    private val spotifyViewModel: SpotifyViewModel by viewModels()
    private var accessToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSpotifyInstalled())
            sendAuthRequest()
        else {
            Toast.makeText(this, "Need to Download Spotify", Toast.LENGTH_SHORT).show()
            isResume = true
            openSpotifyInPlayStore()
        }

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
//                    spotifyViewModel = hiltViewModel()

                    /* TODO :- Getting the Views Here According to the Call State or
                        starting the navigation Graph according to our needs
                     */
                }
            }
        }
    }

    private fun sendAuthRequest() {

        val builder = AuthorizationRequest.Builder(
            SpotifyConstants.SPOTIFY_CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            SpotifyConstants.SPOTIFY_REDIRECT_URI
        )
            .setShowDialog(true)
            .setScopes(arrayOf(SpotifyConstants.SPOTIFY_SCOPES))

        val request = builder.build()

        AuthorizationClient.openLoginActivity(
            this,
            SpotifyConstants.SPOTIFY_AUTH_REQUEST_CODE,
            request
        )
    }

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

    private fun openSpotifyInPlayStore() {
        val packageName = "com.spotify.music"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

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
            handleResponse(response)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val uri: Uri? = intent.data
        if (uri != null) {
            val response = AuthorizationResponse.fromUri(uri)
            handleResponse(response)
        }
    }

    private fun handleResponse(response: AuthorizationResponse) {
        when (response.type) {
            AuthorizationResponse.Type.TOKEN -> {
                accessToken = response.accessToken
                Log.d(tag, "onActivityResult: $accessToken")
                fetchCurrentUserDetails()
            }

            AuthorizationResponse.Type.ERROR -> {
                Log.d(tag, "onActivityResult: ${response.error}")
            }

            else -> {
                Log.d(tag, "onActivityResult: $response")
            }
        }
    }

    private fun fetchCurrentUserDetails() {
        spotifyViewModel.getCurrentUserDetails(accessToken)
        spotifyViewModel.currentUserDetailsResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MusicHomeActivity::class.java)
                    intent.putExtra(SpotifyConstants.SPOTIFY_USER_DETAILS, response.data)
                    intent.putExtra(SpotifyConstants.SPOTIFY_USER_TOKEN, accessToken)
                    SpotifyConstants.SPOTIFY_USER_ACCESS_TOKEN = accessToken
                    startActivity(intent)
                }

                is NetworkResult.Error -> {
                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
