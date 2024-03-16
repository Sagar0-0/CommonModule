package fit.asta.health.feature.scheduler.ui.screen.spotify

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.feature.scheduler.ui.viewmodel.SpotifyViewModel
import fit.asta.health.feature.scheduler.util.SpotifyUtil

const val TAG = "SPOTIFY"

@Composable
fun SpotifyLauncherScreen(spotifyViewModel: SpotifyViewModel, navigateToHome: () -> Unit) {
    var isResume by remember {
        mutableStateOf(false)
    }
    val activity = LocalContext.current as? ComponentActivity
    val context = LocalContext.current
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val resultCode = result.resultCode
        val intent = result.data
        val response = AuthorizationClient.getResponse(resultCode, intent)
        spotifyViewModel.handleSpotifyAuthResponse(response)
    }
    val sendAuthRequest: (() -> Unit) = {
        try {
            activityResultLauncher.launch(
                AuthorizationClient.createLoginActivityIntent(
                    activity,
                    SpotifyUtil.getSpotifyRequest()
                )
            )
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }

    }
    val openSpotifyInPlayStore: (() -> Unit) = {
        val packageName = "com.spotify.music"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity?.startActivity(intent)
    }

    val getSpotifyRemote: (() -> Unit) = {
        SpotifyAppRemote.connect(
            activity,
            SpotifyUtil.getConnectionParams(),
            object : Connector.ConnectionListener {
                // When Connection is established and we get the remote
                override fun onConnected(appRemote: SpotifyAppRemote) {
                    spotifyViewModel.setSpotifyAppRemote(appRemote)
                    navigateToHome.invoke()
                }

                // when connection is not established and we don't get the remote
                override fun onFailure(throwable: Throwable) {
                    spotifyViewModel.unableToGetSpotifyRemote()
                }
            })
    }
    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        if (isResume) {
            if (SpotifyUtil.isSpotifyInstalled(context))
                sendAuthRequest()
            else
                Toast.makeText(activity, "Spotify Not Installed", Toast.LENGTH_SHORT).show()
            isResume = false
        }
    }


    // Checking which state is there
    when (val loginState =
        spotifyViewModel.currentUserData.collectAsStateWithLifecycle().value) {

        // Nothing is done yet and the fetching will be initiated here
        is UiState.Idle -> {
            AppDotTypingAnimation(modifier = Modifier.fillMaxSize())
            // checking if spotify is installed or not
            LaunchedEffect(Unit) {
                if (SpotifyUtil.isSpotifyInstalled(context)) {
                    // Starting the Auth Flow from here
                    sendAuthRequest()
                } else {
                    // downloading Spotify App in the user's App so it can be used
                    Toast.makeText(activity, "Need to Download Spotify", Toast.LENGTH_SHORT)
                        .show()
                    isResume = true
                    openSpotifyInPlayStore()
                }
            }
        }

        // The data is being fetched
        is UiState.Loading -> {
            AppDotTypingAnimation(modifier = Modifier.fillMaxSize())
        }

        // Data fetched successfully
        is UiState.Success<*> -> {
            AppDotTypingAnimation(modifier = Modifier.fillMaxSize())
            // Getting the Spotify App Remote
            LaunchedEffect(Unit) {
                getSpotifyRemote()
            }
        }

        // Data Fetched UnSuccessfully
        is UiState.ErrorMessage -> {
            AppInternetErrorDialog(
                text = loginState.resId.toStringFromResId()
            ) {

                // checking if spotify is installed or not
                if (SpotifyUtil.isSpotifyInstalled(context)) {
                    // Starting the Auth Flow from here
                    sendAuthRequest()
                } else {
                    Toast.makeText(
                        activity,
                        "Need to Download Spotify",
                        Toast.LENGTH_SHORT
                    ).show()
                    isResume = true
                    openSpotifyInPlayStore()
                }
            }
        }

        else -> {
            AppDotTypingAnimation(modifier = Modifier.fillMaxSize())
        }
    }
}