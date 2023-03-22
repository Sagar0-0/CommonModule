package fit.asta.health.thirdparty.spotify.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.SpotifyTracksDetailsActivityBinding
import fit.asta.health.thirdparty.spotify.model.db.entity.TrackEntity
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_TRACK_ID
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_USER_TOKEN
import fit.asta.health.thirdparty.spotify.viewmodel.FavoriteViewModel
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModel
import fit.asta.health.common.utils.NetworkResult

@AndroidEntryPoint
class SpotifyTracksDetailsActivity : AppCompatActivity() {

    private lateinit var binding: SpotifyTracksDetailsActivityBinding
    private val tag = this::class.simpleName

    private lateinit var spotifyViewModel: SpotifyViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    private var isPresentInFavoriteTrackDatabase: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SpotifyTracksDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.item = TrackEntity(
            trackAlbum = null,
            trackArtists = null,
            trackAvailableMarkets = null,
            trackDiscNumber = null,
            trackDurationMs = null,
            trackExplicit = null,
            trackId = "null",
            trackExternalIds = null,
            trackExternalUrls = null,
            trackHref = null,
            trackIsLocal = null,
            trackName = null,
            trackPopularity = null,
            trackPreviewUrl = null,
            trackTrackNumber = null,
            trackType = null,
            trackUri = null
        )


        spotifyViewModel = ViewModelProvider(this)[SpotifyViewModel::class.java]
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        intent?.getStringExtra(SPOTIFY_USER_TOKEN)?.let { TOKEN ->
            intent?.getStringExtra(SPOTIFY_TRACK_ID)?.let { TRACK_ID ->
                fetchTrackDetails(TOKEN, TRACK_ID)
            }
        }
    }

    private fun fetchTrackDetails(token: String, trackId: String) {
        spotifyViewModel.getTrackDetails(token, trackId)
        spotifyViewModel.trackDetailsResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    response.data?.let { it ->
                        Log.d(tag, "fetchTrackDetails: $it")

                        val track = TrackEntity(
                            trackAlbum = it.album,
                            trackArtists = it.artists,
                            trackAvailableMarkets = it.availableMarkets,
                            trackDiscNumber = it.discNumber,
                            trackDurationMs = it.durationMs,
                            trackExplicit = it.explicit,
                            trackId = it.id,
                            trackExternalIds = it.externalIds,
                            trackExternalUrls = it.externalUrls,
                            trackHref = it.href,
                            trackIsLocal = it.isLocal,
                            trackName = it.name,
                            trackPopularity = it.popularity,
                            trackPreviewUrl = it.previewUrl,
                            trackTrackNumber = it.trackNumber,
                            trackType = it.type,
                            trackUri = it.uri
                        )

                        binding.item = track

                        favoriteViewModel.allTracks.observe(this) { list ->
                            Log.d(tag, "fetchTrackDetails: $list")
                            list.contains(track).let { bool ->
                                when (bool) {
                                    true -> {
                                        binding.addToFavoriteButton.text = "Added To Favorite"
                                    }
                                    false -> {
                                        binding.addToFavoriteButton.text = "Add To Favorite"
                                    }
                                }
                                isPresentInFavoriteTrackDatabase = bool
                            }

                            binding.addToFavoriteButton.setOnClickListener { _ ->
                                if (!isPresentInFavoriteTrackDatabase) {
                                    favoriteViewModel.insertTrack(track)
                                }
                            }
                        }

                        binding.openInSpotifyButton.setOnClickListener {
                            val defaultBrowser =
                                Intent(Intent.ACTION_VIEW, Uri.parse(track.trackUri))
                            ContextCompat.startActivity(this, defaultBrowser, null)
                        }
                    }
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
                else -> {
                    Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}