package fit.asta.health.thirdparty.spotify.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.SpotifyFragmentFavMusicBinding
import fit.asta.health.thirdparty.spotify.view.adapters.FavAlbumsAdapter
import fit.asta.health.thirdparty.spotify.view.adapters.FavTracksAdapter
import fit.asta.health.thirdparty.spotify.viewmodel.FavoriteViewModel

@AndroidEntryPoint
class FavMusicFragment : Fragment() {

    private var _binding: SpotifyFragmentFavMusicBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = SpotifyFragmentFavMusicBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        favoriteViewModel.allTracks.observe(viewLifecycleOwner) {
            val favTracksAdapter = FavTracksAdapter(it)
            binding.favoriteTracksRecyclerView.adapter = favTracksAdapter
            binding.favoriteTracksRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        favoriteViewModel.allAlbums.observe(viewLifecycleOwner) {
            val favAlbumAdapter = FavAlbumsAdapter(it)
            binding.favoriteAlbumsRecyclerView.adapter = favAlbumAdapter
            binding.favoriteAlbumsRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}