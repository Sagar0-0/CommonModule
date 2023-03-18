package fit.asta.health.thirdparty.spotify.view.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.SpotifyLoginActivityBinding
import fit.asta.health.thirdparty.MusicHomeActivity
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SHARED_PREF_SPOTIFY
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SHARED_PREF_SPOTIFY_TOKEN
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_AUTH_REQUEST_CODE
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_CLIENT_ID
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_REDIRECT_URI
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_SCOPES
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_USER_ACCESS_TOKEN
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_USER_DETAILS
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_USER_TOKEN
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModel
import fit.asta.health.utils.NetworkResult

@AndroidEntryPoint
class SpotifyLoginActivity : AppCompatActivity() {

    private lateinit var binding: SpotifyLoginActivityBinding
    private val tag = this::class.simpleName

    private lateinit var builder: AuthorizationRequest.Builder
    private lateinit var request: AuthorizationRequest

    private lateinit var spotifyViewModel: SpotifyViewModel
    private var accessToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SpotifyLoginActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        spotifyViewModel = ViewModelProvider(this)[SpotifyViewModel::class.java]

        authenticateWithSpotify()

    }

    private fun authenticateWithSpotify() {
        builder =
            AuthorizationRequest.Builder(
                SPOTIFY_CLIENT_ID,
                AuthorizationResponse.Type.TOKEN,
                SPOTIFY_REDIRECT_URI
            )

        builder.setShowDialog(true)
        builder.setScopes(arrayOf(SPOTIFY_SCOPES))

        request = builder.build()

        binding.loginWithApp.setOnClickListener {
            AuthorizationClient.openLoginActivity(this, SPOTIFY_AUTH_REQUEST_CODE, request)
        }
        binding.loginWithBrowser.setOnClickListener {
            AuthorizationClient.openLoginInBrowser(this, request)
        }
    }

    private fun saveToken(token: String) {
        val sharedPreference = getSharedPreferences(SHARED_PREF_SPOTIFY, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString(SHARED_PREF_SPOTIFY_TOKEN, token)
        editor.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == SPOTIFY_AUTH_REQUEST_CODE) {
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
                Log.d(tag, "onActivityResult: $response")
                accessToken = response.accessToken
                fetchCurrentUserDetails()
            }
            AuthorizationResponse.Type.CODE -> {
                Log.d(tag, "onActivityResult: $response")
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
                    val intent: Intent = Intent(this, MusicHomeActivity::class.java)
                    intent.putExtra(SPOTIFY_USER_DETAILS, response.data)
                    intent.putExtra(SPOTIFY_USER_TOKEN, accessToken)
                    SPOTIFY_USER_ACCESS_TOKEN = accessToken
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
