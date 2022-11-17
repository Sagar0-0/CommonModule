package fit.asta.health.thirdparty.spotify.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.databinding.ItemCurrentUserTopTrackBinding
import fit.asta.health.thirdparty.spotify.model.net.top.ItemTopTrack
import fit.asta.health.thirdparty.spotify.utils.CommonDiffUtils

class SpotifyTopTracksAdapter(
    private var mList: List<ItemTopTrack>
) : RecyclerView.Adapter<SpotifyTopTracksAdapter.InnerViewHolder>() {

    class InnerViewHolder(private val binding: ItemCurrentUserTopTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemTopTrack: ItemTopTrack) {
            binding.topTrackItem = itemTopTrack
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): InnerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCurrentUserTopTrackBinding.inflate(layoutInflater, parent, false)
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

    fun setData(updatedList: List<ItemTopTrack>) {
        val commonDiffUtils = CommonDiffUtils(mList, updatedList)
        val diffUtilResult = DiffUtil.calculateDiff(commonDiffUtils)
        mList = updatedList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}