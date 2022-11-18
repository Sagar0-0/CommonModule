package fit.asta.health.player.audio

import android.content.res.Resources
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.databinding.FragmentMusicBinding
import fit.asta.health.player.audio.adapters.PlayerAdapter
import fit.asta.health.player.audio.adapters.PlayerClickListener
import fit.asta.health.player.audio.data.entity.Song
import fit.asta.health.player.audio.exoplayer.isPlaying
import fit.asta.health.player.audio.exoplayer.toSong
import fit.asta.health.player.audio.other.Status
import fit.asta.health.player.audio.viewmodels.SongViewModel

@AndroidEntryPoint
class MusicFragment : Fragment(), PlayerClickListener {

    private var _binding: FragmentMusicBinding? = null
    private val binding get() = _binding!!

    lateinit var songViewModel: SongViewModel

    // song selected in any tool's home screen
    private var songSelected: Song? = null

    private var playbackState: PlaybackStateCompat? = null

    private var curPlayingSong: Song? = null

    private var shouldUpdateSeekbar = true

    private lateinit var adapter: PlayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // To be received from another fragment
        // songSelected = song

        songViewModel = ViewModelProvider(requireActivity())[SongViewModel::class.java]
        subscribeToObservers()

        adapter = PlayerAdapter(this)
        binding.rvMusicList.adapter = adapter

        BottomSheetBehavior.from(binding.bottomSheet).maxHeight =
            (Resources.getSystem().displayMetrics.heightPixels) / 2

        curPlayingSong?.let {
            songViewModel.playOrToggleSong(it)
        }

        binding.ibPlayPauseButton.setOnClickListener {
            curPlayingSong?.let {
                songViewModel.playOrToggleSong(it, true)
            }
        }

        binding.ibPrevious.setOnClickListener {
            songViewModel.skipToPreviousSong()
        }

        binding.ibNext.setOnClickListener {
            songViewModel.skipToNextSong()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                shouldUpdateSeekbar = false
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                binding.seekBar.let {
                    songViewModel.seekTo(it.progress.toLong())
                    shouldUpdateSeekbar = true
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        songViewModel.pauseSong()
    }

    private fun updateTitleAndSongImage(song: Song) {
        binding.tvHeaderTitle.text = song.title
        binding.tvHeaderSubtitle.text = song.subtitle
    }

    private fun subscribeToObservers() {
        songViewModel.mediaItems.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let { songs ->
                        adapter.updateList(songs)
                        if (songSelected == null && songs.isNotEmpty()) {
                            songSelected = songs[0]
                        }
                        songSelected?.let {
                            songViewModel.playSong(it)
                        }
                    }
//                    songViewModel.repeat()
                }
                Status.ERROR -> Unit
                Status.LOADING -> Unit
            }
        }

        songViewModel.playbackState.observe(viewLifecycleOwner) {
            playbackState = it
            binding.ibPlayPauseButton.setImageResource(
                if (playbackState?.isPlaying == true) R.drawable.ic_baseline_pause_circle_outline_24
                else R.drawable.ic_baseline_play_circle_outline_24
            )
            binding.seekBar.progress = it?.position?.toInt() ?: 0
        }

        songViewModel.curPlayingSong.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            curPlayingSong = it.toSong()
            updateTitleAndSongImage(curPlayingSong!!)
        }

        songViewModel.isConnected.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.ERROR -> Snackbar.make(
                        requireView(),
                        result.message ?: "An unknown error occurred",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    else -> Unit
                }
            }
        }

        songViewModel.networkError.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.ERROR -> Snackbar.make(
                        requireView(),
                        result.message ?: "An unknown error occurred",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    else -> Unit
                }
            }
        }

        songViewModel.curPlayerPosition.observe(viewLifecycleOwner) {
            if (shouldUpdateSeekbar) {
                binding.seekBar.progress = it.toInt()
            }
        }

        songViewModel.curSongDuration.observe(viewLifecycleOwner) {
            binding.seekBar.max = it.toInt()
        }
    }

    override fun onItemClicked(song: Song) {
        songViewModel.playOrToggleSong(song)
    }
}