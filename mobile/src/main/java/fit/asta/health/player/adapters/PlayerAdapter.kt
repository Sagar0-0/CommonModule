package fit.asta.health.player.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.databinding.PlaylistItemBinding
import fit.asta.health.player.data.entity.Song

class PlayerAdapter(
    private val listener: PlayerClickListener
) : BaseAdapter<Song>() {

    class PlayerViewHolder(
        private val binding: PlaylistItemBinding
    ) : BaseViewHolder<Song>(binding.root) {
        override fun bindData(content: Song) {
            binding.txtExerciseTitle.text = content.title
            binding.txtExerciseSubTitle.text = content.subtitle
            binding.txtExerciseDuration.text = content.duration
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Song> {
        val binding =
            PlaylistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Song>, position: Int) {
        val song = items[position]
        holder.bindData(song)
        holder.itemView.setOnClickListener {
            listener.onItemClicked(song)
        }
    }
}

interface PlayerClickListener {
    fun onItemClicked(song: Song)
}