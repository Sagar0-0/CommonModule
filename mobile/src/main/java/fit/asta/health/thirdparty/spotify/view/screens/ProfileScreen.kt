package fit.asta.health.thirdparty.spotify.view.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX

@Composable
fun ProfileScreen(
    navController: NavController,
    spotifyViewModelX: SpotifyViewModelX
) {
    Text(text = "Profile Screen")
}