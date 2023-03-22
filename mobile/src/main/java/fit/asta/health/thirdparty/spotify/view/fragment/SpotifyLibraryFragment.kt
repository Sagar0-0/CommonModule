package fit.asta.health.thirdparty.spotify.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.SpotifyLibraryFragmentBinding
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants
import fit.asta.health.thirdparty.spotify.view.adapters.*
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModel
import fit.asta.health.common.utils.NetworkResult

@AndroidEntryPoint
class SpotifyLibraryFragment : Fragment() {

    private var _binding: SpotifyLibraryFragmentBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.simpleName

    private lateinit var spotifyViewModel: SpotifyViewModel
    private lateinit var spotifyUserPlaylistsAdapter: SpotifyUserPlaylistsAdapter
    private lateinit var spotifyUserTracksAdapter: SpotifyUserTracksAdapter
    private lateinit var spotifyUserAlbumsAdapter: SpotifyUserAlbumsAdapter
    private lateinit var spotifyUserShowsAdapter: SpotifyUserShowsAdapter
    private lateinit var spotifyUserEpisodesAdapter: SpotifyUserEpisodesAdapter
    private lateinit var spotifyUserArtistsAdapter: SpotifyUserArtistsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SpotifyLibraryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spotifyViewModel = ViewModelProvider(this)[SpotifyViewModel::class.java]

        activity?.intent?.getParcelableExtra<fit.asta.health.thirdparty.spotify.model.net.me.SpotifyMeModel?>(
            SpotifyConstants.SPOTIFY_USER_DETAILS
        )
            ?.let { spotifyMeModel ->

                Log.d(TAG, "onCreate: $spotifyMeModel")

                activity?.intent?.getStringExtra(SpotifyConstants.SPOTIFY_USER_TOKEN)?.let {

                    binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                        Log.d(TAG, "onViewCreated: $group $checkedIds")
                        if (checkedIds.size > 0) {
                            when (checkedIds[0]) {
                                binding.chipTracks.id -> {
                                    fetchCurrentUserTracks(it)
                                }

                                binding.chipPlaylists.id -> {
                                    fetchCurrentUserPlaylists(it)
                                }

                                binding.chipArtists.id -> {
                                    fetchCurrentUserArtists(it)
                                }

                                binding.chipAlbums.id -> {
                                    fetchCurrentUserAlbums(it)
                                }

                                binding.chipShows.id -> {
                                    fetchCurrentUserShows(it)
                                }

                                binding.chipEpisode.id -> {
                                    fetchCurrentUserEpisodes(it)
                                }
                            }
                        }
                    }

                    binding.chipTracks.isChecked = true
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchCurrentUserTracks(accessToken: String) {
        spotifyViewModel.getCurrentUserTracks(accessToken)
        spotifyViewModel.currentUserTracksResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.items.let {
                        if (it != null) {
                            spotifyUserTracksAdapter = SpotifyUserTracksAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            binding.resultRecyclerView.adapter = spotifyUserTracksAdapter
                            binding.resultRecyclerView.layoutManager = linearLayoutManager
                        }
                    }
                }
                is NetworkResult.Error -> {
//                    binding.userPlaylistLabel.text = response.message.toString()
                }
                is NetworkResult.Loading -> {
//                    binding.userPlaylistLabel.text = "Loading..."
                }
                else -> {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchCurrentUserPlaylists(accessToken: String) {
        spotifyViewModel.getCurrentUserPlaylists(accessToken)
        spotifyViewModel.currentUserPlaylistsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.userPlaylistItems.let {
                        if (it != null) {
                            spotifyUserPlaylistsAdapter = SpotifyUserPlaylistsAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            binding.resultRecyclerView.adapter = spotifyUserPlaylistsAdapter
                            binding.resultRecyclerView.layoutManager = linearLayoutManager
                        }
                    }
                }
                is NetworkResult.Error -> {
//                    binding.userPlaylistLabel.text = response.message.toString()
                }
                is NetworkResult.Loading -> {
//                    binding.userPlaylistLabel.text = "Loading..."
                }
                else -> {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchCurrentUserArtists(accessToken: String) {
        spotifyViewModel.getCurrentUserFollowingArtists(accessToken)
        spotifyViewModel.currentUserFollowingArtistsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.artists?.items.let {
                        if (it != null) {
                            spotifyUserArtistsAdapter = SpotifyUserArtistsAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            binding.resultRecyclerView.adapter = spotifyUserArtistsAdapter
                            binding.resultRecyclerView.layoutManager = linearLayoutManager
                        }
                    }
                }
                is NetworkResult.Error -> {
//                    binding.userPlaylistLabel.text = response.message.toString()
                }
                is NetworkResult.Loading -> {
//                    binding.userPlaylistLabel.text = "Loading..."
                }
                else -> {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchCurrentUserAlbums(accessToken: String) {
        spotifyViewModel.getCurrentUserAlbums(accessToken)
        spotifyViewModel.currentUserAlbumsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.items.let {
                        if (it != null) {
                            spotifyUserAlbumsAdapter = SpotifyUserAlbumsAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            binding.resultRecyclerView.adapter = spotifyUserAlbumsAdapter
                            binding.resultRecyclerView.layoutManager = linearLayoutManager
                        }
                    }
                }
                is NetworkResult.Error -> {
//                    binding.userPlaylistLabel.text = response.message.toString()
                }
                is NetworkResult.Loading -> {
//                    binding.userPlaylistLabel.text = "Loading..."
                }
                else -> {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchCurrentUserShows(accessToken: String) {
        spotifyViewModel.getCurrentUserShows(accessToken)
        spotifyViewModel.currentUserShowsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.items.let {
                        if (it != null) {
                            spotifyUserShowsAdapter = SpotifyUserShowsAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            binding.resultRecyclerView.adapter = spotifyUserShowsAdapter
                            binding.resultRecyclerView.layoutManager = linearLayoutManager
                        }
                    }
                }
                is NetworkResult.Error -> {
//                    binding.userPlaylistLabel.text = response.message.toString()
                }
                is NetworkResult.Loading -> {
//                    binding.userPlaylistLabel.text = "Loading..."
                }
                else -> {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchCurrentUserEpisodes(accessToken: String) {
        spotifyViewModel.getCurrentUserEpisodes(accessToken)
        spotifyViewModel.currentUserEpisodesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.items.let {
                        if (it != null) {
                            spotifyUserEpisodesAdapter = SpotifyUserEpisodesAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            binding.resultRecyclerView.adapter = spotifyUserEpisodesAdapter
                            binding.resultRecyclerView.layoutManager = linearLayoutManager
                        }
                    }
                }
                is NetworkResult.Error -> {
//                    binding.userPlaylistLabel.text = response.message.toString()
                }
                is NetworkResult.Loading -> {
//                    binding.userPlaylistLabel.text = "Loading..."
                }
                else -> {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}