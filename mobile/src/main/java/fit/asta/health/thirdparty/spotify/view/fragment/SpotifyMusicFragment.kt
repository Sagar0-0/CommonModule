package fit.asta.health.thirdparty.spotify.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.FragmentSpotifyMusicBinding
import fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed.Track
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_USER_DETAILS
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_USER_TOKEN
import fit.asta.health.thirdparty.spotify.view.activity.SpotifySearchActivity
import fit.asta.health.thirdparty.spotify.view.activity.SpotifyUserLibraryActivity
import fit.asta.health.thirdparty.spotify.view.adapters.SpotifyRecentlyPlayedAdapter
import fit.asta.health.thirdparty.spotify.view.adapters.SpotifyRecommendationAdapter
import fit.asta.health.thirdparty.spotify.view.adapters.SpotifyTopArtistsAdapter
import fit.asta.health.thirdparty.spotify.view.adapters.SpotifyTopTracksAdapter
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModel
import fit.asta.health.utils.NetworkResult

@AndroidEntryPoint
class SpotifyMusicFragment : Fragment() {

    private var _binding: FragmentSpotifyMusicBinding? = null
    private val binding get() = _binding!!

    private val TAG = this::class.simpleName
    private lateinit var spotifyViewModel: SpotifyViewModel

    private lateinit var spotifyTopTracksAdapter: SpotifyTopTracksAdapter
    private lateinit var spotifyTopArtistsAdapter: SpotifyTopArtistsAdapter
    private lateinit var spotifyRecentlyPlayedAdapter: SpotifyRecentlyPlayedAdapter
    private lateinit var spotifyRecommendationAdapter: SpotifyRecommendationAdapter

    //    private lateinit var spotifyUserPlaylistsAdapter: SpotifyUserPlaylistsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSpotifyMusicBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spotifyViewModel = ViewModelProvider(this)[SpotifyViewModel::class.java]

        activity?.intent?.getParcelableExtra<fit.asta.health.thirdparty.spotify.model.net.me.SpotifyMeModel?>(
            SPOTIFY_USER_DETAILS
        )
            ?.let { spotifyMeModel ->

                Log.d(TAG, "onCreate: $spotifyMeModel")

                binding.header.text = "Hey ${spotifyMeModel.displayName}!"

                activity?.intent?.getStringExtra(SPOTIFY_USER_TOKEN)?.let {
                    fetchCurrentUserTopTracks(it)
                    fetchCurrentUserTopArtists(it)
                    fetchCurrentUserRecentlyPlayedTracks(it)

                    binding.search.setOnClickListener { _ ->
                        val intent: Intent = Intent(context, SpotifySearchActivity::class.java)
                        intent.putExtra(SPOTIFY_USER_DETAILS, spotifyMeModel)
                        intent.putExtra(SPOTIFY_USER_TOKEN, it)
                        startActivity(intent)
                    }

                    binding.userLibrary.setOnClickListener { _ ->
                        val intent: Intent = Intent(context, SpotifyUserLibraryActivity::class.java)
                        intent.putExtra(SPOTIFY_USER_DETAILS, spotifyMeModel)
                        intent.putExtra(SPOTIFY_USER_TOKEN, it)
                        startActivity(intent)
                    }
                }


            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchCurrentUserRecentlyPlayedTracks(accessToken: String) {
        spotifyViewModel.getCurrentUserRecentlyPlayed(accessToken)
        spotifyViewModel.currentUserRecentlyPlayed.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.items.let {
                        if (it != null) {
                            binding.currentUserRecentlyPlayedRecyclerView.setHasFixedSize(false)
                            val tracksList = ArrayList<Track>()
                            it.forEach { item ->
                                if (!tracksList.contains(item.track)) {
                                    tracksList.add(item.track)
                                }
                            }

                            fetchRecommendation(
                                accessToken,
                                tracksList[0].artists[0].id,
                                "classical,country",
                                tracksList[0].id,
                                "8"
                            )

                            spotifyRecentlyPlayedAdapter =
                                SpotifyRecentlyPlayedAdapter(tracksList)
                            gridLayoutManager =
                                GridLayoutManager(context, 4, GridLayoutManager.HORIZONTAL, false)
                            binding.currentUserRecentlyPlayedRecyclerView.adapter =
                                spotifyRecentlyPlayedAdapter
                            binding.currentUserRecentlyPlayedRecyclerView.layoutManager =
                                gridLayoutManager
                        }
                    }
                }
                is NetworkResult.Error -> {
//                    binding.topTracksLabel.text = response.message.toString()
                }
                is NetworkResult.Loading -> {
//                    binding.topTracksLabel.text = "Loading..."
                }
                else -> {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchRecommendation(
        accessToken: String,
        seedArtistsId: String,
        seedGenres: String,
        seedTracksId: String,
        limit: String
    ) {
        spotifyViewModel.getRecommendations(
            accessToken,
            seedArtistsId,
            seedGenres,
            seedTracksId,
            limit
        )
        spotifyViewModel.recommendationResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.tracks.let {
                        if (it != null) {
                            Log.d(TAG, "fetchRecommendation: $it")
                            spotifyRecommendationAdapter = SpotifyRecommendationAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            binding.recommendationRecyclerView.adapter =
                                spotifyRecommendationAdapter
                            binding.recommendationRecyclerView.layoutManager =
                                linearLayoutManager
                            binding.recommendationLabel.text = "Recommended"
                        }
                    }
                }
                is NetworkResult.Error -> {
                    binding.recommendationLabel.text = response.message.toString()
                }
                is NetworkResult.Loading -> {
                    binding.recommendationLabel.text = "Loading..."
                }
                else -> {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchCurrentUserTopTracks(accessToken: String) {
        spotifyViewModel.getCurrentUserTopTracks(accessToken)
        spotifyViewModel.currentUserTopTracksResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.itemTopTracks.let {
                        if (it != null) {
                            spotifyTopTracksAdapter = SpotifyTopTracksAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            binding.currentUserTopTracksRecyclerView.adapter =
                                spotifyTopTracksAdapter
                            binding.currentUserTopTracksRecyclerView.layoutManager =
                                linearLayoutManager
                            binding.topTracksLabel.text = "Top Tracks"
                        }
                    }
                }
                is NetworkResult.Error -> {
                    binding.topTracksLabel.text = response.message.toString()
                }
                is NetworkResult.Loading -> {
                    binding.topTracksLabel.text = "Loading..."
                }
                else -> {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchCurrentUserTopArtists(accessToken: String) {
        spotifyViewModel.getCurrentUserTopArtists(accessToken)
        spotifyViewModel.currentUserTopArtistsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.items.let {
                        if (it != null) {
                            spotifyTopArtistsAdapter = SpotifyTopArtistsAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            binding.currentUserTopArtistRecyclerView.adapter =
                                spotifyTopArtistsAdapter
                            binding.currentUserTopArtistRecyclerView.layoutManager =
                                linearLayoutManager
                            binding.topArtistLabel.text = "Top Artists"
                        }
                    }
                }
                is NetworkResult.Error -> {
                    binding.topArtistLabel.text = response.message.toString()
                }
                is NetworkResult.Loading -> {
                    binding.topArtistLabel.text = "Loading..."
                }
                else -> {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}