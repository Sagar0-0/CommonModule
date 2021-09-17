package fit.asta.health.subscription.adapter

import android.view.ViewGroup
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.subscription.data.SubscriptionItem
import fit.asta.health.subscription.listner.SubPlanSelectionListener
import org.koin.core.KoinComponent
import org.koin.core.inject

class SubscriptionAdapter : BaseAdapter<SubscriptionItem>(), KoinComponent {

    private val viewHolderFactory: SubscriptionViewHolderFactory by inject()
    private var changeSelectionListener: SubPlanSelectionListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SubscriptionItem> {
        return viewHolderFactory.create(
            parent,
            viewType,
            changeSelectionListener
        ) { notifyDataSetChanged() }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<SubscriptionItem>, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].selected.value
    }

    fun setListener(selectionListener: SubPlanSelectionListener) {
        changeSelectionListener = selectionListener
    }

}