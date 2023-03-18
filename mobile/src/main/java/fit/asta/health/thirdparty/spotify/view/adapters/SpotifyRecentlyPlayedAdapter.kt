package fit.asta.health.thirdparty.spotify.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.databinding.SpotifyItemRecentlyPlayedBinding
import fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed.Track
import fit.asta.health.thirdparty.spotify.utils.CommonDiffUtils

class SpotifyRecentlyPlayedAdapter(
    private var mList: List<Track>
) : RecyclerView.Adapter<SpotifyRecentlyPlayedAdapter.InnerViewHolder>() {

    class InnerViewHolder(private val binding: SpotifyItemRecentlyPlayedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recentlyPlayedItem: Track) {
            binding.recentlyPlayedTrack = recentlyPlayedItem
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): InnerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SpotifyItemRecentlyPlayedBinding.inflate(layoutInflater, parent, false)
                return InnerViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        return InnerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(updatedList: List<Track>) {
        val commonDiffUtils = CommonDiffUtils(mList, updatedList)
        val diffUtilResult = DiffUtil.calculateDiff(commonDiffUtils)
        mList = updatedList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}