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
import fit.asta.health.databinding.SpotifyAlbumDetailsActivityBinding
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.model.net.common.ExternalUrls
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants
import fit.asta.health.thirdparty.spotify.viewmodel.FavoriteViewModel
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModel
import fit.asta.health.common.utils.NetworkResult

@AndroidEntryPoint
class SpotifyAlbumDetailsActivity : AppCompatActivity() {

    private lateinit var binding: SpotifyAlbumDetailsActivityBinding
    private val tag = this::class.simpleName

    private lateinit var spotifyViewModel: SpotifyViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    private var isPresentInFavoriteDatabase: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SpotifyAlbumDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.item = Album(
            albumType = "null",
            artists = emptyList(),
            availableMarkets = emptyList(),
            externalUrls = ExternalUrls(""),
            href = "album.href",
            id = "album.id",
            images = emptyList(),
            name = "album.name",
            releaseDate = "album.releaseDate",
            releaseDatePrecision = "album.releaseDatePrecision",
            totalTracks = 0,
            type = "album.type",
            uri = "album.uri"
        )

        spotifyViewModel = ViewModelProvider(this)[SpotifyViewModel::class.java]
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        intent?.getStringExtra(SpotifyConstants.SPOTIFY_USER_TOKEN)?.let { TOKEN ->
            intent?.getStringExtra(SpotifyConstants.SPOTIFY_ALBUM_ID)?.let { ALBUM_ID ->
                fetchAlbumDetails(TOKEN, ALBUM_ID)
            }
        }
    }

    private fun fetchAlbumDetails(token: String, albumId: String) {
        spotifyViewModel.getAlbumDetails(token, albumId)
        spotifyViewModel.albumDetailsResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    response.data?.let { album ->
                        Log.d(tag, "fetchAlbumDetails: $album")

                        val album = Album(
                            albumType = album.albumType,
                            artists = album.artists,
                            availableMarkets = album.availableMarkets,
                            externalUrls = album.externalUrls,
                            href = album.href,
                            id = album.id,
                            images = album.images,
                            name = album.name,
                            releaseDate = album.releaseDate,
                            releaseDatePrecision = album.releaseDatePrecision,
                            totalTracks = album.totalTracks,
                            type = album.type,
                            uri = album.uri
                        )

                        binding.item = album
                        favoriteViewModel.allAlbums.observe(this) { list ->
                            Log.d(tag, "fetchTrackDetails: $list")
                            list.contains(binding.item as Album).let { bool ->
                                when (bool) {
                                    true -> {
                                        binding.addToFavoriteButton.text = "Added To Favorite"
                                    }
                                    false -> {
                                        binding.addToFavoriteButton.text = "Add To Favorite"
                                    }
                                }
                                isPresentInFavoriteDatabase = bool
                            }

                            binding.openInSpotifyButton.setOnClickListener {
                                val defaultBrowser =
                                    Intent(Intent.ACTION_VIEW, Uri.parse(album.uri))
                                ContextCompat.startActivity(this, defaultBrowser, null)
                            }

                            binding.addToFavoriteButton.setOnClickListener { _ ->
                                if (!isPresentInFavoriteDatabase) {
                                    favoriteViewModel.insertAlbum(binding.item as Album)
                                }
                            }
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