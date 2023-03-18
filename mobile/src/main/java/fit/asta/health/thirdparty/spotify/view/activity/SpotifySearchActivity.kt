package fit.asta.health.thirdparty.spotify.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.SpotifySearchActivityBinding
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_USER_DETAILS
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_USER_TOKEN
import fit.asta.health.thirdparty.spotify.view.adapters.*
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModel
import fit.asta.health.utils.NetworkResult

@AndroidEntryPoint
class SpotifySearchActivity : AppCompatActivity() {

    private lateinit var binding: SpotifySearchActivityBinding
    private val tag = this::class.simpleName

    private lateinit var spotifyViewModel: SpotifyViewModel

    private lateinit var spotifyTopTracksAdapter: SpotifyTopTracksAdapter
    private lateinit var spotifyTopArtistsAdapter: SpotifyTopArtistsAdapter
    private lateinit var spotifyUserPlaylistsAdapter: SpotifyUserPlaylistsAdapter
    private lateinit var spotifyUserAlbumsAdapter: SpotifyAlbumsAdapter
    private lateinit var spotifyCategoriesAdapter: SpotifyCategoriesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager

    private var query: String = "Anuv Jain"
    private var type: String = ""
    private var market: String = ""
    private var includeExternal: String = "audio"

    private var listOfType: ArrayList<CheckBox> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SpotifySearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spotifyViewModel = ViewModelProvider(this)[SpotifyViewModel::class.java]

        listOfType.add(binding.albumCheckBox)
        listOfType.add(binding.artistCheckBox)
        listOfType.add(binding.playlistCheckBox)
        listOfType.add(binding.trackCheckBox)
        listOfType.add(binding.showCheckBox)
        listOfType.add(binding.episodeCheckBox)

        intent?.getParcelableExtra<fit.asta.health.thirdparty.spotify.model.net.me.SpotifyMeModel?>(
            SPOTIFY_USER_DETAILS
        )
            ?.let { spotifyMeModel ->

                market = spotifyMeModel.country

                intent?.getStringExtra(SPOTIFY_USER_TOKEN)?.let {

                    fetchCategories(it, market)

                    binding.searchQueryInputEdittext.setOnEditorActionListener(
                        TextView.OnEditorActionListener { _, actionId, _ ->
                            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                                performSearchAction(it, false)
                                return@OnEditorActionListener true
                            }
                            false
                        })

                    binding.searchQueryInputEdittext.addTextChangedListener(object : TextWatcher {

                        override fun afterTextChanged(s: Editable) {}

                        override fun beforeTextChanged(
                            s: CharSequence, start: Int,
                            count: Int, after: Int
                        ) {

                        }

                        override fun onTextChanged(
                            s: CharSequence, start: Int,
                            before: Int, count: Int
                        ) {
                            if (count > 0) {
                                binding.clearSearchQuery.visibility = View.VISIBLE
                            } else {
                                binding.clearSearchQuery.visibility = View.GONE
                            }
                        }
                    })

                    binding.clearSearchQuery.setOnClickListener {
                        binding.searchQueryInputEdittext.setText("")
                    }

                    binding.cancelTypes.setOnClickListener {
                        binding.typesContainer.visibility = View.GONE
                    }

                    binding.saveTypes.setOnClickListener { _ ->
                        binding.typesContainer.visibility = View.GONE
                        performSearchAction(it, false)
                    }

                    binding.searchFiltersOpener.setOnClickListener {
                        if (binding.typesContainer.visibility == View.GONE) {
                            binding.typesContainer.visibility = View.VISIBLE
                        } else {
                            binding.typesContainer.visibility = View.GONE
                        }
                    }
                }

            }
    }

    private fun performSearchAction(accessToken: String, async: Boolean) {
        if (binding.searchQueryInputEdittext.text.toString().isNotEmpty()) {
            type = ""
            listOfType.forEach { checkBox ->
                if (checkBox.isChecked) {
                    if (type.isEmpty()) {
                        type += checkBox.text
                    } else {
                        type = type + "," + checkBox.text
                    }
                }
            }

            if (type.isNotEmpty()) {
                query = binding.searchQueryInputEdittext.text.toString()
                fetchSearchQueryResult(accessToken)
            } else {
                Toast.makeText(
                    this,
                    "Select minimum one type to perform search!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            if (!async) {
                Toast.makeText(
                    this,
                    "Enter search query!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun fetchSearchQueryResult(accessToken: String) {
        spotifyViewModel.searchQuery(accessToken, query, type.lowercase(), includeExternal, market)
        spotifyViewModel.searchResponse.observe(this) { response ->
            Log.d(tag, "fetchSearchQueryResult: ${response.data?.albums}")
            when (response) {
                is NetworkResult.Success -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

                    binding.categoriesRecyclerView.visibility = View.GONE
                    response.data?.tracks?.items.let {
                        if (it != null) {
                            spotifyTopTracksAdapter = SpotifyTopTracksAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                            binding.resultTracksRecyclerView.adapter = spotifyTopTracksAdapter
                            binding.resultTracksRecyclerView.layoutManager = linearLayoutManager
                            binding.resultTracksLabel.text = "Tracks"
                            updateVisibilityOfView(
                                binding.tracksContainer,
                                binding.searchResultLabel
                            )
                        } else {
                            binding.tracksContainer.visibility = View.GONE
                        }
                    }

                    response.data?.artists?.items.let {
                        if (it != null) {
                            spotifyTopArtistsAdapter = SpotifyTopArtistsAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                            binding.resultArtistRecyclerView.adapter = spotifyTopArtistsAdapter
                            binding.resultArtistRecyclerView.layoutManager = linearLayoutManager
                            binding.resultArtistLabel.text = "Artists"
                            updateVisibilityOfView(
                                binding.artistsContainer,
                                binding.searchResultLabel
                            )
                        } else {
                            binding.artistsContainer.visibility = View.GONE
                        }
                    }

                    response.data?.albums?.albumItems.let {
                        if (it != null) {
                            spotifyUserAlbumsAdapter = SpotifyAlbumsAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                            binding.resultAlbumsRecyclerView.adapter = spotifyUserAlbumsAdapter
                            binding.resultAlbumsRecyclerView.layoutManager = linearLayoutManager
                            binding.resultAlbumsLabel.text = "Albums"
                            updateVisibilityOfView(
                                binding.albumsContainer,
                                binding.searchResultLabel
                            )
                        } else {
                            binding.albumsContainer.visibility = View.GONE
                        }
                    }

                    response.data?.playlists?.items.let {
                        if (it != null) {
                            spotifyUserPlaylistsAdapter = SpotifyUserPlaylistsAdapter(it)
                            linearLayoutManager =
                                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                            binding.resultPlaylistRecyclerView.adapter = spotifyUserPlaylistsAdapter
                            binding.resultPlaylistRecyclerView.layoutManager = linearLayoutManager
                            binding.resultPlaylistLabel.text = "Playlists"
                            updateVisibilityOfView(
                                binding.playlistsContainer,
                                binding.searchResultLabel
                            )
                        } else {
                            binding.playlistsContainer.visibility = View.GONE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    binding.tracksContainer.visibility = View.GONE
                    binding.artistsContainer.visibility = View.GONE
                    binding.playlistsContainer.visibility = View.GONE
                    binding.searchResultLabel.visibility = View.VISIBLE

                    binding.resultTracksLabel.text = response.message.toString()
                    binding.searchResultLabel.text = response.message.toString()
                }
                is NetworkResult.Loading -> {
                    Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                    binding.resultTracksLabel.text = "Loading..."
                    binding.searchResultLabel.text = "Loading..."
                }
                else -> {
                    Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateVisibilityOfView(view1: View, view2: View) {
        view1.visibility = View.VISIBLE
        view2.visibility = View.GONE
    }

    private fun fetchCategories(accessToken: String, country: String) {
        spotifyViewModel.getCategories(accessToken, country)
        spotifyViewModel.categoriesResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.categories?.items.let {
                        spotifyCategoriesAdapter = SpotifyCategoriesAdapter(it!!)
                        gridLayoutManager =
                            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
                        binding.categoriesRecyclerView.adapter = spotifyCategoriesAdapter
                        binding.categoriesRecyclerView.layoutManager = gridLayoutManager
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
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