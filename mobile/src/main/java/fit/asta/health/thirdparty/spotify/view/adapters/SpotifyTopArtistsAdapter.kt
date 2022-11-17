package fit.asta.health.thirdparty.spotify.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.databinding.ItemCurrentUserTopArtistBinding
import fit.asta.health.thirdparty.spotify.model.net.top.ItemTopArtist
import fit.asta.health.thirdparty.spotify.utils.CommonDiffUtils

class SpotifyTopArtistsAdapter(
    private var mList: List<ItemTopArtist>
) : RecyclerView.Adapter<SpotifyTopArtistsAdapter.InnerViewHolder>() {

    class InnerViewHolder(private val binding: ItemCurrentUserTopArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemTopArtist: ItemTopArtist) {
            binding.topArtistItem = itemTopArtist
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): InnerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCurrentUserTopArtistBinding.inflate(layoutInflater, parent, false)
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

    fun setData(updatedList: List<ItemTopArtist>) {
        val commonDiffUtils = CommonDiffUtils(mList, updatedList)
        val diffUtilResult = DiffUtil.calculateDiff(commonDiffUtils)
        mList = updatedList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}