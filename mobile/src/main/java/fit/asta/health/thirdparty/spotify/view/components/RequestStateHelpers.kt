package fit.asta.health.thirdparty.spotify.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall

/**
 * This checks the state of the call and shows the UI Accordingly
 *
 * @param modifier This is the Modifier which will be followed by the Loading and the Failure Screen
 * @param networkState This is the [SpotifyNetworkCall] state Object which tells us about the states
 * @param onCurrentStateInitialized This function is run when the state is in initialized state
 * @param onCurrentStateSuccess This function is run when the state is in successful state
 */
@Composable
fun <T : SpotifyNetworkCall<*>> MusicStateControl(
    modifier: Modifier = Modifier,
    networkState: T,
    onCurrentStateInitialized: () -> Unit,
    onCurrentStateSuccess: @Composable (T) -> Unit
) {

    // Checking which state is there
    when (networkState) {

        // Nothing is done yet and the fetching will be initiated here
        is SpotifyNetworkCall.Initialized<*> -> onCurrentStateInitialized()

        // The data is being fetched
        is SpotifyNetworkCall.Loading<*> -> LoadingScreen(
            modifier = modifier
        )

        // Data fetched successfully
        is SpotifyNetworkCall.Success<*> -> onCurrentStateSuccess(networkState)

        // Data Fetched UnSuccessfully
        is SpotifyNetworkCall.Failure<*> -> {
            FailureScreen(
                modifier = modifier,
                textToShow = networkState.message.toString()
            ) {
                onCurrentStateInitialized()
            }
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun FailureScreen(
    modifier: Modifier = Modifier,
    textToShow: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 32.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = textToShow,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),

                textAlign = TextAlign.Center
            )
        }
    }
}