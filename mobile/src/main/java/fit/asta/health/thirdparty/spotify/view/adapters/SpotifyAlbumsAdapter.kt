package fit.asta.health.thirdparty.spotify.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.databinding.ItemSearchAlbumsBinding
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.utils.CommonDiffUtils

class SpotifyAlbumsAdapter(
    private var mList: List<Album>
) : RecyclerView.Adapter<SpotifyAlbumsAdapter.InnerViewHolder>() {

    class InnerViewHolder(private val binding: ItemSearchAlbumsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Album) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): InnerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSearchAlbumsBinding.inflate(layoutInflater, parent, false)
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

    fun setData(updatedList: List<Album>) {
        val commonDiffUtils = CommonDiffUtils(mList, updatedList)
        val diffUtilResult = DiffUtil.calculateDiff(commonDiffUtils)
        mList = updatedList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}