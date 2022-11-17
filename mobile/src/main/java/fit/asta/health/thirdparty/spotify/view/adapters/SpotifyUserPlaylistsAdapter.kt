package fit.asta.health.thirdparty.spotify.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.databinding.ItemUserPlaylistBinding
import fit.asta.health.thirdparty.spotify.model.net.playlist.UserPlaylistItem
import fit.asta.health.thirdparty.spotify.utils.CommonDiffUtils

class SpotifyUserPlaylistsAdapter(
    private var mList: List<UserPlaylistItem>
) : RecyclerView.Adapter<SpotifyUserPlaylistsAdapter.InnerViewHolder>() {

    class InnerViewHolder(private val binding: ItemUserPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userPlaylistItem: UserPlaylistItem) {
            binding.userPlaylistItem = userPlaylistItem
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): InnerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserPlaylistBinding.inflate(layoutInflater, parent, false)
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

    fun setData(updatedList: List<UserPlaylistItem>) {
        val commonDiffUtils = CommonDiffUtils(mList, updatedList)
        val diffUtilResult = DiffUtil.calculateDiff(commonDiffUtils)
        mList = updatedList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}