package fit.asta.health.profile.adapter

import android.view.ViewGroup
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.profile.listener.OnChangeListener
import fit.asta.health.profile.listener.OnChipActionListener
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProfileAdapter: BaseAdapter<ProfileItem>(), KoinComponent {

    private val viewHolderFactory: ProfileViewHolderFactory by inject()
    private  var changeListener: OnChangeListener? = null
    private  var onChipActionListener: OnChipActionListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ProfileItem> {
        return viewHolderFactory.create(parent, viewType, changeListener, onChipActionListener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ProfileItem>, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].profileType.value
    }

    fun getSpanCount(position: Int): Int {
        return when (items[position].profileType) {
            ProfileItemType.BodyTypeCard -> 2
            else -> 1
        }
    }

    fun setListener(listener: OnChangeListener) {
        changeListener = listener
    }

    fun setOnChipListener(listener: OnChipActionListener) {
        onChipActionListener = listener
    }
}